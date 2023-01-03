package org.example.map;

import org.example.map.objects.animal.genes.GenesFactory;
import org.example.map.options.IEdge;
import org.example.map.objects.plants.IPlants;

public class MapBuilder {
    // TODO default values (useful when some methods are not used)

    // Map parameters
    private int width;
    private int height;
    private IEdge iEdge;

    // Plants parameters
    private int numberOfPlantsAtStart;
    private int plantEnergy;
    private int plantsSeededEachDay;
    private IPlants iPlants;

    // Animals parameters
    private int numberOfAnimalsAtStart;
    private int animalEnergy;
    private int animalEnergyBreedingThreshold;
    private int animalBreedingCost;
    private int animalMinMutations;
    private int animalMaxMutations;
    private int genesLength;
    private boolean isCrazyBehavior;
    private boolean isFullRandom;

    public static MapBuilder builder() {
        return new MapBuilder();
    }

    public MapBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public MapBuilder withHeight(int height) {
        this.height = height;
        return this;
    }

    public MapBuilder withiEdge(IEdge iEdge) {
        this.iEdge = iEdge;
        return this;
    }

    public MapBuilder withNumberOfPlantsAtStart(int numberOfPlantsAtStart) {
        this.numberOfPlantsAtStart = numberOfPlantsAtStart;
        return this;
    }

    public MapBuilder withPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
        return this;
    }

    public MapBuilder withPlantsSeededEachDay(int plantsSeededEachDay) {
        this.plantsSeededEachDay = plantsSeededEachDay;
        return this;
    }

    public MapBuilder withIPlants(IPlants iPlants) {
        this.iPlants = iPlants;
        return this;
    }

    public MapBuilder withNumberOfAnimalsAtStart(int numberOfAnimalsAtStart) {
        this.numberOfAnimalsAtStart = numberOfAnimalsAtStart;
        return this;
    }

    public MapBuilder withAnimalEnergy(int animalEnergy) {
        this.animalEnergy = animalEnergy;
        return this;
    }

    public MapBuilder withAnimalEnergyBreedingThreshold(int animalEnergyBreedingThreshold) {
        this.animalEnergyBreedingThreshold = animalEnergyBreedingThreshold;
        return this;
    }

    public MapBuilder withAnimalBreedingCost(int animalBreedingCost) {
        this.animalBreedingCost = animalBreedingCost;
        return this;
    }

    public MapBuilder withAnimalMinMutations(int animalMinMutations) {
        this.animalMinMutations = animalMinMutations;
        return this;
    }

    public MapBuilder withAnimalMaxMutations(int animalMaxMutations) {
        this.animalMaxMutations = animalMaxMutations;
        return this;
    }

    public MapBuilder withGenesLength(int genesLength) {
        this.genesLength = genesLength;
        return this;
    }

    public MapBuilder withCrazyBehavior(boolean isCrazyBehavior) {
        this.isCrazyBehavior = isCrazyBehavior;
        return this;
    }

    public MapBuilder withFullRandom(boolean isFullRandom) {
        this.isFullRandom = isFullRandom;
        return this;
    }


    public WorldMap build() {
        // TODO: add other gene parameters to Factory.
        GenesFactory genesFactory = new GenesFactory(isFullRandom,
                isCrazyBehavior,
                genesLength,
                animalMinMutations,
                animalMaxMutations);

        return new WorldMap(width,
                height,
                iEdge,
                numberOfPlantsAtStart,
                plantEnergy,
                plantsSeededEachDay,
                iPlants,
                numberOfAnimalsAtStart,
                animalEnergy,
                animalEnergyBreedingThreshold,
                animalBreedingCost,
                genesFactory
        );
    }

}


