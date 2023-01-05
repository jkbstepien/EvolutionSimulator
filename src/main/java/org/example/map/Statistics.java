package org.example.map;

import org.example.map.objects.animal.genes.Genes;

import java.util.List;

public record Statistics(
        int numberOfAllAnimals,
        int numberOfAllPlants,
        int freeField,
        List<Genes>mostPopularGenotypes,
        double averageEnergy,
        double averageDeadLifespan,
        int dayCounter) {
}
