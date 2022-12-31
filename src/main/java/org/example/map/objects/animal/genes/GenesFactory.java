package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.behavior.AnimalBehaviorCrazy;
import org.example.map.objects.animal.behavior.AnimalBehaviorPredestination;
import org.example.map.objects.animal.behavior.IAnimalBehavior;

public class GenesFactory {
    private final boolean isFullRandom;
    private final IAnimalBehavior makeMove;
    private final int genesLength;
    private final int animalMinMutations;
    private final int animalMaxMutations;

    private IAnimalBehavior behavior(boolean isCrazyBehavior){
        if (isCrazyBehavior){
            return new AnimalBehaviorCrazy();
        }
        else{
            return new AnimalBehaviorPredestination();
        }
    }

    public GenesFactory(boolean isFullRandom,
                        boolean isCrazyBehavior,
                        int genesLength,
                        int animalMinMutations,
                        int animalMaxMutations) {
        this.isFullRandom = isFullRandom;
        this.makeMove = behavior(isCrazyBehavior);
        this.genesLength = genesLength;
        this.animalMinMutations = animalMinMutations;
        this.animalMaxMutations = animalMaxMutations;
    }

    public Genes createGenes() {
        if (isFullRandom) {
            return new GenesFullRandom(makeMove, genesLength);
        } else {
            return new GenesSlightCorrection(makeMove, genesLength);
        }
    }

    public Genes createGenes(Animal father, Animal mother) {
        if (isFullRandom) {
            return new GenesFullRandom(
                    makeMove,
                    father.getGenotype(),
                    mother.getGenotype(),
                    father.getEnergy(),
                    mother.getEnergy(),
                    animalMinMutations,
                    animalMaxMutations);
        } else {
            return new GenesSlightCorrection(
                    makeMove,
                    father.getGenotype(),
                    mother.getGenotype(),
                    father.getEnergy(),
                    mother.getEnergy(),
                    animalMinMutations,
                    animalMaxMutations);
        }
    }
}
