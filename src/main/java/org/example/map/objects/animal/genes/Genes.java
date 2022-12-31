package org.example.map.objects.animal.genes;

import org.example.map.objects.animal.behavior.IAnimalBehavior;

import java.util.*;

public abstract class Genes {

    protected final Random randomGenerator = new Random();
    protected final List<Integer> genotype;
    protected int currentGene;
    protected final IAnimalBehavior makeMove;
    protected final int genesLength;

    protected final int differentGenes = 8;


    private List<Integer> randomGenotype(){
        List<Integer> newGenotype = new ArrayList<>();
        for (int i = 0; i < genesLength; i++) {
            genotype.add(randomGenerator.nextInt(differentGenes));
        }
        return newGenotype;
    }

    private int relativeFatherEnergy(int fatherEnergy, int motherEnergy){
        int energySum = fatherEnergy + motherEnergy;
        float relativeEnergy = (float) fatherEnergy / (float) energySum * genesLength;
        return Math.round(relativeEnergy);
    }

    private int relativeMotherEnergy(int fatherEnergy, int motherEnergy) {
        int relativeFatherEnergy = relativeFatherEnergy(fatherEnergy, motherEnergy);
        return genesLength - relativeFatherEnergy;
    }

    private List<Integer> inheritedGenotype(Genes fatherGenes,
                                            Genes motherGenes,
                                            int relativeFatherEnergy,
                                            int relativeMotherEnergy) {
        boolean chooseSide = randomGenerator.nextInt(100) < 50;
        List<Integer> newGenotype = new ArrayList<>();
        List<Integer> fatherGenotype = fatherGenes.getGenotype();
        List<Integer> motherGenotype = motherGenes.getGenotype();

        if (chooseSide) {
            for (int x = 0; x < relativeFatherEnergy; x++) newGenotype.add(fatherGenotype.get(x));
            for (int y = relativeFatherEnergy; y < genesLength; y++) newGenotype.add(motherGenotype.get(y));
        } else {
            for (int x = 0; x < relativeMotherEnergy; x++) newGenotype.add(motherGenotype.get(x));
            for (int y = relativeMotherEnergy; y < genesLength; y++) newGenotype.add(fatherGenotype.get(y));
        }
        Collections.sort(newGenotype);
        return newGenotype;
    }


    public Genes(IAnimalBehavior makeMove,
                 int genesLength) {
        // Constructor creates genotypes for first animals ever placed on map
        this.makeMove = makeMove;
        this.genesLength = genesLength;

        genotype = randomGenotype();
    }


    public Genes(IAnimalBehavior makeMove,
                 Genes fatherGenes,
                 Genes motherGenes,
                 int fatherEnergy,
                 int motherEnergy,
                 int minMutations,
                 int maxMutations) {
        // Constructor creates genotypes for descendants of first animals.
        this.genesLength = fatherGenes.getGenesLength();
        this.makeMove = makeMove;

        genotype = inheritedGenotype(fatherGenes,
                                    motherGenes,
                                    relativeFatherEnergy(fatherEnergy, motherEnergy),
                                    relativeMotherEnergy(fatherEnergy, motherEnergy));
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
        // TODO why do we need this?
        return genotype.get(randomGenerator.nextInt(genesLength));
    }

    public Integer getMoveDirection() {
        int lastGene = currentGene;
        currentGene = makeMove.nextGeneIndex(currentGene, genesLength);
        return genotype.get(lastGene);
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

    protected void mutate(int minMutations, int maxMutations) {
        // change a gene to a different one, repeat
        // TODO ensure minMutations <= maxMutations
        // TODO ensure minMutations >= 0
        // TODO ensure maxMutations <= genotype.size()
        int mutations = randomGenerator.nextInt(maxMutations - minMutations) + minMutations;
        GeneChoice geneChoice = new GeneChoice(genesLength);
        for(int i = 0; i < mutations; i++) {
            int index = geneChoice.next();
            Integer oldGene = genotype.get(index);
            Integer newGene = mutatedGene(oldGene);
            genotype.set(index, newGene);
        }
    }

    protected abstract Integer mutatedGene(Integer gene);
}
