package org.example.map.objects.plants;

import org.example.utils.Vector2d;

public interface IPlantObserver {
    void plantEaten(Plant plant);
    void plantPlaced(Plant plant);
}
