package org.example.map.objects.animal.behavior;

import java.util.List;

public class AnimalBehaviorPredestination implements IAnimalBehavior {
    @Override
    public int getGene(List<Integer> genes, int current) {
        return genes.get(current);
    }
}
