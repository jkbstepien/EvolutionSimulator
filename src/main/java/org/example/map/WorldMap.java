package org.example.map;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.map.objects.animal.genes.Genes;
import org.example.map.objects.animal.genes.GenesFactory;
import org.example.map.objects.plants.Plant;
import org.example.map.objects.plants.PlantsToxicCorpses;
import org.example.map.options.IEdge;
import org.example.map.objects.plants.IPlants;
import org.example.utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldMap implements IAnimalObserver {
    // Map parameters
    private final int width;
    private final int height;
    private final IEdge iEdge;

    // Plants parameters
    private final int numberOfPlantsAtStart;
    private final int plantEnergy;
    private final int plantsSeededEachDay;
    private final IPlants iPlants;

    // Animals parameters
    private final int numberOfAnimalsAtStart;
    private final int animalEnergyAtStart;
    private final int animalEnergyBreedingThreshold;
    private final int animalBreedingCost;
    private final GenesFactory genesFactory;

    // State parameters
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final List<Animal> deadAnimals = new LinkedList<>();
    private final Random generator = new Random();

    public WorldMap(int width,
                    int height,
                    IEdge iEdge,
                    int numberOfPlantsAtStart,
                    int plantEnergy,
                    int plantsSeededEachDay,
                    IPlants iPlants,
                    int numberOfAnimalsAtStart,
                    int animalEnergy,
                    int animalEnergyBreedingThreshold,
                    int animalBreedingCost,
                    GenesFactory genesFactory) {
        this.width = width;
        this.height = height;
        this.iEdge = iEdge;
        this.numberOfPlantsAtStart = numberOfPlantsAtStart;
        this.plantEnergy = plantEnergy;
        this.plantsSeededEachDay = plantsSeededEachDay;
        this.iPlants = iPlants;
        this.numberOfAnimalsAtStart = numberOfAnimalsAtStart;
        this.animalEnergyAtStart = animalEnergy;
        this.animalEnergyBreedingThreshold = animalEnergyBreedingThreshold;
        this.animalBreedingCost = animalBreedingCost;
        this.genesFactory = genesFactory;
//        placePlants();
//        placeAnimals();
    }


    public List<Vector2d> animalPositionsSortedByDeaths(){
        Map<Vector2d, Long> deathsCounted = deadAnimals.stream()
                                                        .collect(Collectors.groupingBy(Animal::getPosition, Collectors.counting()));
        Vector2d[] positions = deathsCounted.keySet()
                                            .toArray(Vector2d[]::new);
        Long[] deaths = deathsCounted.values()
                                    .toArray(Long[]::new);
        Integer[] indexes = IntStream.range(0,deaths.length)
                                    .boxed()
                                    .toArray(Integer[]::new);
//        Arrays.sort(indexes, new ByCorrespondingValues(deaths));
        return Arrays.stream(indexes)
                    .map(index->positions[index])
                    .toList();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private Vector2d plantPosition(){
        boolean isPreferred = generator.nextInt(5) != 4;
        try{
            return iPlants.grow(isPreferred);
        }
        catch (IllegalArgumentException ex){
            return iPlants.grow(!isPreferred);
        }
    }

    private void placeOnePlant(){
        try {
            Plant plant = new Plant(plantPosition());
            plants.put(plant.getPosition(), plant);
        }
        catch(IllegalArgumentException ignored){
        }
    }

    private void placePlants(){
        // TODO does map need to place many plants at once?
        // it might be only the simulation's job
        for(int i = 0; i < numberOfPlantsAtStart; i++){
            placeOnePlant();
        }
    }

    private void moveAnimal(Animal animal){
        Vector2d v = animal.getNewPosition();
        Vector2d finalPosition = iEdge.handleMove(v, animal);
        animal.move(finalPosition);
    }

    @Override
    public void animalPlaced(Animal animal) {
        Vector2d position = animal.getPosition();
        if(animals.containsKey(position)){
            animals.get(position).add(animal);
        } else{
            animals.put(position, new LinkedList<>(List.of(animal)));
        }
    }

    @Override
    public void animalMoved(Animal animal, Vector2d oldPosition) {
        animals.get(oldPosition).remove(animal);

        List<Animal> animalsAtNewPosition = animals
                .computeIfAbsent(animal.getPosition(), k -> new LinkedList<>());
        animalsAtNewPosition.add(animal);
    }

    @Override
    public void animalDied(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
        deadAnimals.add(animal);
    }

    public void moveAll() {
        animals.values().stream().flatMap(List::stream).forEach(this::moveAnimal);
    }

    public void eatPlants() {
        plants.values().forEach(plant -> {
            Vector2d position = plant.getPosition();
            List<Animal> animalsAtPosition = animals.get(position);
            if(animalsAtPosition != null && !animalsAtPosition.isEmpty()){
                animalsAtPosition.sort(Comparator.comparingInt(Animal::getEnergy));
                Animal strongest = animalsAtPosition.get(0);
                strongest.addEnergy(plantEnergy);
            }
        });
    }

    public void breeding() {
        animals.values().forEach(animals -> {
            if (animals.size() > 1) {
                animals.sort(Comparator.comparingInt(Animal::getEnergy));
                Animal strongest = animals.get(0);
                Animal secondStrongest = animals.get(1);
                if (strongest.getEnergy() >= animalEnergyBreedingThreshold && secondStrongest.getEnergy() >= animalEnergyBreedingThreshold) {
                    Genes genes = genesFactory.createGenes(strongest, secondStrongest);
                    Animal child = new Animal(strongest.getPosition(), 2*animalBreedingCost, genes);
                    strongest.addEnergy(-animalBreedingCost);
                    secondStrongest.addEnergy(-animalBreedingCost);
                    child.addObserver(this);
                    if (iPlants instanceof PlantsToxicCorpses){
                        child.addObserver((PlantsToxicCorpses) iPlants);
                    }
                    child.place();
                }
            }
        });
    }

    public void growPlants() {
        for(int i = 0; i < plantsSeededEachDay; i++){
            placeOnePlant();
        }
    }
}
