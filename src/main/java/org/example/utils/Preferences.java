package org.example.utils;


import org.example.map.EdgeEarth;
import org.example.map.EdgeHellPortal;
import org.example.map.WorldMap;
import org.example.map.objects.animal.genes.GenesFactory;
import org.example.map.objects.plants.IPlants;
import org.example.map.objects.plants.PlantsEquator;
import org.example.map.objects.plants.PlantsToxicCorpses;
import org.example.map.options.IEdge;

public record Preferences(
        // Map parameters
         int width,
         int height,
         String iEdge,

        // Plants parameters
         int numberOfPlantsAtStart,
         int plantEnergy,
         int plantsSeededEachDay,
         String iPlants,

        // Animals parameters
         int numberOfAnimalsAtStart,
         int animalEnergy,
         int animalEnergyBreedingThreshold,
         int animalBreedingCost,
         int animalMinMutations,
         int animalMaxMutations,
         int genesLength,
         boolean isCrazyBehavior,
         boolean isFullRandom
) {
    public WorldMap toWorldMap() {
        // TODO: add other gene parameters to Factory.
        GenesFactory genesFactory = new GenesFactory(isFullRandom,
                isCrazyBehavior,
                genesLength,
                animalMinMutations,
                animalMaxMutations);

        IPlants iPlantsImplementation;
        if (iPlants.equals("equator")) {
            iPlantsImplementation = new PlantsEquator();
        } else {
            iPlantsImplementation = new PlantsToxicCorpses();
        }
        IEdge iEdgeImplementation;
        if (iEdge.equals("earth")) {
            iEdgeImplementation = new EdgeEarth();
        } else {
            iEdgeImplementation = new EdgeHellPortal();
        }
        return new WorldMap(width,
                height,
                iEdgeImplementation,
                numberOfPlantsAtStart,
                plantEnergy,
                plantsSeededEachDay,
                iPlantsImplementation,
                numberOfAnimalsAtStart,
                animalEnergy,
                animalEnergyBreedingThreshold,
                animalBreedingCost,
                genesFactory
        );
    }

}
