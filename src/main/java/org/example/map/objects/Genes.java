package org.example.map.objects;

import org.example.map.options.IMutation;
import org.example.utils.MutationOption;

import java.util.*;

public abstract class Genes {

    private final Random randomGenerator;
    private final List<Integer> genotype;

    private List<Integer> randomGenotype(){
        List<Integer> newGenotype = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            genotype.add(randomGenerator.nextInt(8));
        }
        return newGenotype;
    }

    private List<Integer> inheritedGenotype(Genes father, Genes mother, int percentageFatherEnergy, int percentageMotherEnergy){
        boolean chooseSide = randomGenerator.nextInt(100) < 50;
        List<Integer> newGenotype = new ArrayList<>();

        if (chooseSide) {
            for (int x = 0; x < percentageFatherEnergy; x++) newGenotype.add(father.getGenotype().get(x));
            for (int y = percentageFatherEnergy; y < 32; y++) newGenotype.add(mother.getGenotype().get(y));
        } else {
            for (int x = 0; x < percentageMotherEnergy; x++) newGenotype.add(mother.getGenotype().get(x));
            for (int y = percentageMotherEnergy; y < 32; y++) newGenotype.add(father.getGenotype().get(y));
        }
        mutate();
        return newGenotype;
    }


    public Genes(Random randomGenerator){
        // Constructor creates genotypes for first animals ever placed on map
        genotype = randomGenotype();
        Collections.sort(genotype);
        mutate();
    }


    public Genes(Random randomGenerator, Genes father, Genes mother, int percentageFatherEnergy, int percentageMotherEnergy) {
        // Constructor creates genotypes for descendants of first animals.
        this(randomGenerator);
        genotype = inheritedGenotype(father, mother, percentageFatherEnergy, percentageMotherEnergy);
        mutate();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.genotype.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public int getRandomGene() {
        return genotype.get(randomGenerator.nextInt(32));
    }

    @Override
    public int hashCode() {
        return Objects.hash(genotype);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genes genome = (Genes) o;
        return genotype.equals(genome.genotype);
    }

    public List<Integer> getGenotype(){
        return genotype;
    }

    protected abstract void mutate();
}
