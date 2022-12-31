package org.example.map.objects.animal.behavior;

public class AnimalBehaviorPredestination implements IAnimalBehavior {
    @Override
    public int nextGeneIndex(int current, int genesLength) {
        return (current + 1) % genesLength;
    }
}
