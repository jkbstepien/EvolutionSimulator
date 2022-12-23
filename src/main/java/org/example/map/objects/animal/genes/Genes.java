package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.behavior.IAnimalBehavior;

import java.util.*;

public abstract class Genes {

    private final Random randomGenerator = new Random();
    private List<Integer> genotype;
    private int currentGene;
    private final IAnimalBehavior makeMove;
    private final int genesLength;

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


    public Genes(IAnimalBehavior makeMove,
                 int genesLength,
                 int minMutations,
                 int maxMutations) {
        // Constructor creates genotypes for first animals ever placed on map
        this.makeMove = makeMove;
        this.genesLength = genesLength;

        genotype = randomGenotype();
        Collections.sort(genotype);

        mutate(minMutations, maxMutations);
    }


    public Genes(IAnimalBehavior makeMove,
                 Animal father,
                 Animal mother,
                 int minMutations,
                 int maxMutations) {
        // Constructor creates genotypes for descendants of first animals.
        this.genesLength = father.getGenotype().getGenesLength();
        this.makeMove = makeMove;

        genotype = inheritedGenotype(father.getGenotype(),
                mother.getGenotype(),
                percentageFatherEnergy,
                percentageMotherEnergy);
        // TODO: calculate percentageFatherEnergy and percentageMotherEnergy
        mutate(minMutations, maxMutations);
    }

    private int getGenesLength() {
        return genesLength;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.genotype.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public int getRandomGene() {
        return genotype.get(randomGenerator.nextInt(32));
    }

    public Integer getMoveDirection() {
        //
        currentGene++;
        return makeMove.getGene(genotype, currentGene);
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

    protected abstract void mutate(int minMutations, int maxMutations);
}
