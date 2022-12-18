package org.example.utils;

import java.util.Random;

public class Gene {
    private final int number;
    private final Random randomGenerator;

    public Gene(Random randomGenerator){
        this.randomGenerator = randomGenerator;

    public Gene(Random randomGenerator, int number){
        this(randomGenerator);
        this.number = number;
    }

    public static Gene next(Gene gene){
        int nextNumber = (gene.getNumber() + 1) % 7;
        return this(randomGenerator, nextNumber)
    }

    public int getNumber(){
        return number;
    }

}
