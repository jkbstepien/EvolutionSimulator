package org.example.map.objects.animal.genes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneChoice {
    // a helper class to store indexes of non-mutated genes

    private final List<Integer> indexes = new ArrayList<>();
    private final Random randomGenerator = new Random();
    private final int genesLength;

    public GeneChoice(int genesLength){
        this.genesLength = genesLength;
        for(int i = 0; i< genesLength; i++){
            indexes.add(i);
        }
    }

    public Integer next(){
        Integer chosenIndex = indexes.get(randomGenerator.nextInt(indexes.size()));
        indexes.remove(chosenIndex);
        if (indexes.size() == 0){
            for(int i=0; i<genesLength; i++){
                indexes.add(i);
            }
        }
        return chosenIndex;
    }

}
