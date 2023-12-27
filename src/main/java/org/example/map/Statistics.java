package org.example.map;

import java.util.List;

public record Statistics(
        int numberOfAllAnimals,
        int numberOfAllPlants,
        int freeField,
        List<List<Integer>> mostPopularGenotypes,
        double averageEnergy,
        double averageDeadLifespan,
        int dayCounter) {

    public static String csvHeader() {
        return "numberOfAllAnimals,numberOfAllPlants,freeField,mostPopularGenotypes,averageEnergy,averageDeadLifespan,dayCounter\n";
    }

    public String toCsvRow() {
        return String.format("%d,%d,%d,%s,%f,%f,%d\n",
                numberOfAllAnimals,
                numberOfAllPlants,
                freeField,
                mostPopularGenotypes,
                averageEnergy,
                averageDeadLifespan,
                dayCounter);
    }
}
