package org.example.utils;

import org.example.map.objects.animal.genes.Genes;

import java.util.Comparator;
import java.util.Map;

public class ByCorrespondingValuesComparator implements Comparator<Genes> {
    private final Map<Genes, Long> values;

    public ByCorrespondingValuesComparator(Map<Genes, Long> values){
        this.values = values;
    }

    @Override
    public int compare(Genes firstGenes, Genes secondGenes){
        return (int) (values.get(firstGenes) - values.get(secondGenes));
    }
}
