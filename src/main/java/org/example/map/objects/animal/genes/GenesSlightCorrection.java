package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.behavior.IAnimalBehavior;

public class GenesSlightCorrection extends Genes{

    public GenesSlightCorrection(IAnimalBehavior makeMove, int genesLength, int minMutations, int maxMutations) {
        super(makeMove, genesLength, minMutations, maxMutations);
    }

    public GenesSlightCorrection(IAnimalBehavior makeMove, Animal father, Animal mother, int percentageFatherEnergy, int percentageMotherEnergy) {
        super(makeMove, father, mother, percentageFatherEnergy, percentageMotherEnergy);
    }

    @Override
    protected void mutate(int minMutations, int maxMutations) {

    }
}
