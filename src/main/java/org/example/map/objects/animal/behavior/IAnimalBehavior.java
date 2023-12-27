package org.example.map.objects.animal.behavior;

public interface IAnimalBehavior {
    // Deal with animal move variants:
    // Full predestination or Crazy.
    // Returns the index of next gene.
    int nextGeneIndex(int current, int genesLength);
}
