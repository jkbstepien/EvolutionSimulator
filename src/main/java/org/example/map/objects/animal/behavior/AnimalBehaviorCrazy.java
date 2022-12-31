package org.example.map.objects.animal.behavior;

import java.util.Random;

public class AnimalBehaviorCrazy implements IAnimalBehavior {

    private final Random randomGenerator = new Random();

    @Override
    public int nextGeneIndex(int current, int genesLength) {
        if(randomGenerator.nextInt(5) <= 3){
            return (current + 1) % genesLength;
        }
        return randomGenerator.nextInt(genesLength);
    }
}
