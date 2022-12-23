package org.example.map.objects.animal.behavior;

import java.util.List;

public interface IAnimalBehavior {
    // Deal with animal move variants:
    // Full predestination or Crazy.
    // Returns animal gene to use and does not change current gene.
    int getGene(List<Integer> genes, int current);
}
