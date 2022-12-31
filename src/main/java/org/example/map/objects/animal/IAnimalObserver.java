package org.example.map.objects.animal;

import org.example.utils.Vector2d;

public interface IAnimalObserver {
    void animalPlaced(Animal animal);
    void animalMoved(Animal animal);
    void animalDied(Animal animal);
}
