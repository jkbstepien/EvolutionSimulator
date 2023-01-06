package org.example.map;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.AnimalStatistics;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.map.objects.animal.genes.Genes;
import org.example.map.objects.animal.genes.GenesFactory;
import org.example.map.objects.plants.IPlantObserver;
import org.example.map.objects.plants.Plant;
import org.example.map.objects.plants.PlantsToxicCorpses;
import org.example.map.options.IEdge;
import org.example.map.objects.plants.IPlants;
import org.example.utils.Vector2d;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorldMap implements IAnimalObserver, IPlantObserver {
    // Map parameters
    private final int width;
    private final int height;
    private final IEdge iEdge;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

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

    private int dayCunter = 0;

    // State parameters
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final List<Animal> deadAnimals = new LinkedList<>();
    private final Random generator = new Random();

    private int numberOfAnimals = 0;

    private List<Animal> animalList = new ArrayList<>();
    private List<Statistics> statistics = new ArrayList<>();

    private Optional<Animal> trackedAnimal = Optional.empty();


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
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.numberOfPlantsAtStart = numberOfPlantsAtStart;
        this.plantEnergy = plantEnergy;
        this.plantsSeededEachDay = plantsSeededEachDay;
        this.iPlants = iPlants;
        this.numberOfAnimalsAtStart = numberOfAnimalsAtStart;
        this.animalEnergyAtStart = animalEnergy;
        this.animalEnergyBreedingThreshold = animalEnergyBreedingThreshold;
        this.animalBreedingCost = animalBreedingCost;
        this.genesFactory = genesFactory;
        placePlants();
        placeAnimals();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
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
        Plant plant;
        try {
            plant = new Plant(plantPosition(), plantEnergy);
            plant.addObserver(this);
            plant.addObserver((IPlantObserver) iPlants);
            plant.place();
        }
        catch(IllegalArgumentException ignored){
        }
    }

    private void placePlants(){
        for(int i = 0; i < numberOfPlantsAtStart; i++){
            placeOnePlant();
        }
    }

    private Vector2d animalCoords(){
        int x, y;
        Vector2d position;
        do{
            x = generator.nextInt(width);
            y = generator.nextInt(height);
            position = new Vector2d(x, y);
        }while(animals.getOrDefault(position, Collections.emptyList()).size() > 0);
        return position;
    }

    private void placeOneAnimal(){
        Vector2d position = animalCoords();
        Genes genotype = genesFactory.createGenes();
        Animal animal = new Animal(position, animalEnergyAtStart, genotype);
        animal.addObserver(this);
        if(iPlants instanceof IAnimalObserver) {
            animal.addObserver((IAnimalObserver) iPlants);
        }
        animal.place();
    }

    private void placeAnimals(){
        for(int i=0; i<numberOfAnimalsAtStart; i++){
            placeOneAnimal();
        }
    }


    @Override
    public synchronized void animalPlaced(Animal animal) {
        Vector2d position = animal.getPosition();
        if(animals.containsKey(position)){
            animals.get(position).add(animal);
        } else{
            animals.put(position, new LinkedList<>(List.of(animal)));
        }
        numberOfAnimals++;
        animalList.add(animal);
        animal.calculateAge(this.dayCunter);
    }

    @Override
    public synchronized void animalMoved(Animal animal, Vector2d oldPosition) {
        animals.get(oldPosition).remove(animal);
        if(animals.get(oldPosition).isEmpty()){
            animals.remove(oldPosition);
        }

        List<Animal> animalsAtNewPosition = animals
                .computeIfAbsent(animal.getPosition(), k -> new LinkedList<>());
        animalsAtNewPosition.add(animal);
    }

    @Override
    public synchronized void animalDied(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
        if (animals.get(animal.getPosition()).isEmpty()) {
            animals.remove(animal.getPosition());
        }
        deadAnimals.add(animal);
        animalList.remove(animal);
        numberOfAnimals--;
    }

    @Override
    public synchronized void plantEaten(Plant plant){
        plants.remove(plant.getPosition());
    }

    @Override
    public synchronized void plantPlaced(Plant plant){
        plants.put(plant.getPosition(), plant);
    }

    public void removeDeadAnimals(){
        List<Animal> animalsIterator = new ArrayList<>(animalList);
        animalsIterator.forEach(Animal::removeIfDied);
    }

    public void eatPlants() {
        List<Plant> plantsIterator = new ArrayList<>(plants.values());
        plantsIterator.forEach(plant -> {
            Vector2d position = plant.getPosition();
            List<Animal> animalsAtPosition = animals.get(position);
            if(animalsAtPosition != null && !animalsAtPosition.isEmpty()){
                animalsAtPosition.sort(Comparator.comparingInt(Animal::getEnergy));
                Animal strongest = animalsAtPosition.get(0);
                plant.beEaten(strongest);
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
                    child.setBornDay(this.dayCunter);
                    child.calculateAge(this.dayCunter);
                }
                strongest.incrementChildren();
                secondStrongest.incrementChildren();
            }
        });
    }

    public void growPlants() {
        for(int i = 0; i < plantsSeededEachDay; i++){
            placeOnePlant();
        }
    }


    public int freeFields(){
        int usedFields = animals.size();
        for(Vector2d position: plants.keySet()){
            if(!animals.containsKey(position)){
                usedFields++;
            }
        }
        return width * height - usedFields;
    }

    public List<List<Integer>> mostPopularGenotypes(){
        Map<List<Integer>, Long> genotypesCounted = animalList.stream()
            .map(Animal::getGenes)
                .map(Genes::getGenotype)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        int longest = genotypesCounted.values().stream()
            .mapToInt(Long::intValue)
            .max()
            .orElse(0);
        return genotypesCounted.entrySet().stream()
            .filter(entry -> entry.getValue() == longest)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public double averageEnergy(){
        OptionalDouble average = animals.values()
                .stream()
                .flatMap(List::stream)
                .map(Animal::getEnergy)
                .mapToDouble(a->a)
                .average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }

    public double averageDeadsLifespan(){
        OptionalDouble average = deadAnimals.stream()
                .map(Animal::getAge)
                .mapToDouble(a->a)
                .average();
        return (average.isPresent() ? average.getAsDouble() : 0);
    }

    public void moveAllAnimals(){
        animalList.forEach(this::moveAnimal);
    }

    private void moveAnimal(Animal animal){
        Vector2d position = iEdge.handleMove(animal);
        animal.move(position);
    }

    public String contentLabel(Vector2d position){
        if(animals.containsKey(position) && !animals.get(position).isEmpty()){
            return animals.get(position).get(0).toString();
        }
        if(plants.containsKey(position)){
            return plants.get(position).toString();
        }
        return " ";
    }

    public Statistics getStats(){
        return new Statistics(
                numberOfAnimals,
                plants.size(),
                freeFields(),
                mostPopularGenotypes(),
                animalList.stream().mapToInt(Animal::getEnergy).average().orElse(0),
                deadAnimals.stream().mapToInt(Animal::getAge).average().orElse(0),
                dayCunter);
    }

    public Statistics day(){
        dayCunter++;
        removeDeadAnimals();
        moveAllAnimals();
        eatPlants();
        breeding();
        growPlants();
        Statistics currentStats = getStats();
        statistics.add(currentStats);
        return currentStats;
    }

    public List<Statistics> getAllStatistics(){
        return statistics;
    }

    public List<Animal> animalsWithDominantGenes() {
        List<List<Integer>> mostPopularGenotypes = mostPopularGenotypes();
        return animalList.stream()
                .filter(animal -> mostPopularGenotypes.contains(animal.getGenes().getGenotype()))
                .toList();
    }

    public Optional<AnimalStatistics> getTrackedAnimalStatistics(){
        return trackedAnimal.map(Animal::getStatistics);
    }

    public void setTrackedAnimal(Vector2d position) {
        if (animals.containsKey(position)) {
            trackedAnimal = Optional.of(animals.get(position).get(0));
        }
    }
}
