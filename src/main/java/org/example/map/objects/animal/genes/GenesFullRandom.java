package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.behavior.IAnimalBehavior;

public class GenesFullRandom extends Genes{

    public GenesFullRandom(IAnimalBehavior makeMove, int genesLength) {
        super(makeMove, genesLength);
    }

    public GenesFullRandom(IAnimalBehavior makeMove,
                           Genes fatherGenes,
                           Genes motherGenes,
                           int fatherEnergy,
                           int motherEnergy,
                           int minMutations,
                           int maxMutations) {
        super(makeMove, fatherGenes, motherGenes, fatherEnergy, motherEnergy, minMutations, maxMutations);
    }

    @Override
    protected Integer mutatedGene(Integer gene){
        return randomGenerator.nextInt(differentGenes);
    }
}
