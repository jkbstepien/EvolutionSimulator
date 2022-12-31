package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.behavior.IAnimalBehavior;

public class GenesSlightCorrection extends Genes{

    public GenesSlightCorrection(IAnimalBehavior makeMove, int genesLength) {
        super(makeMove, genesLength);
    }

    public GenesSlightCorrection(IAnimalBehavior makeMove,
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
        if(randomGenerator.nextBoolean()){
            return (gene + 1) % differentGenes;
        }
        return (gene + differentGenes - 1) % differentGenes;
    }
}
