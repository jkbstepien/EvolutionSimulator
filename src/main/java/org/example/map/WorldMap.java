package org.example.map;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.genes.GenesFactory;
import org.example.map.objects.plants.Plant;
import org.example.map.options.IEdge;
import org.example.map.objects.plants.IPlants;
import org.example.utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldMap {
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
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final List<Animal> deadAnimals = new LinkedList<>();

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
        iPlants.setWorldMap(this);
    }

//    private void placeOnePlant(){
//        Vector2d position;
//        do{
//            position =
//        }
//    }
//
//    private void placePlants(){
//        for(int i = 0; i < numberOfPlantsAtStart; i++){
//            placeOnePLant();
//        }
//    }

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
}
