package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.behavior.AnimalBehaviorCrazy;
import org.example.map.objects.animal.behavior.AnimalBehaviorPredestination;
import org.example.map.objects.animal.behavior.IAnimalBehavior;

public class GenesFactory {
    private boolean isFullRandom;
    private IAnimalBehavior makeMove;
    private int genesLength;
    private int animalMinMutations;
    private int animalMaxMutations;

    public GenesFactory(boolean isFullRandom,
                        boolean isCrazyBehavior,
                        int genesLength,
                        int animalMinMutations,
                        int animalMaxMutations) {
        this.isFullRandom = isFullRandom;
        if (isCrazyBehavior) {
            makeMove = new AnimalBehaviorCrazy();
        } else {
            makeMove = new AnimalBehaviorPredestination();
        }
        this.genesLength = genesLength;
        this.animalMinMutations = animalMinMutations;
        this.animalMaxMutations = animalMaxMutations;
    }

    public Genes createGenes() {
        if (isFullRandom) {
            return new GenesFullRandom(makeMove, genesLength, animalMinMutations, animalMaxMutations);
        } else {
            return new GenesSlightCorrection(makeMove, genesLength, animalMinMutations, animalMaxMutations);
        }
    }

    public Genes createGenes(Animal parent1, Animal parent2) {
        if (isFullRandom) {
            return new GenesFullRandom(makeMove, parent1, parent2, animalMinMutations, animalMaxMutations);
        } else {
            return new GenesSlightCorrection(makeMove, parent1, parent2, animalMinMutations, animalMaxMutations);
        }
    }
}
