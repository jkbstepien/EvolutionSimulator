package org.example.map.objects.animal;

import org.example.utils.Vector2d;

public interface IAnimalObserver {
    // It lets Animals inform other classes about changes of their states
    void animalPlaced(Animal animal);
    void animalMoved(Animal animal, Vector2d oldPosition);
    void animalDied(Animal animal);
}
