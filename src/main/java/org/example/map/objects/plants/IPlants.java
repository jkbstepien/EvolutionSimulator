package org.example.map.objects.plants;

import org.example.utils.Vector2d;

public interface IPlants {
//    void setWorldMap(WorldMap map);

    // Deals with plants seeding variants:
    // Forested equators or toxic corpses.
    // Returns position of new plant to grow on.
    Vector2d grow(boolean preferred) throws CannotPlacePlantException;
}
