package org.example.map.options;

import org.example.map.objects.animal.Animal;
import org.example.utils.Vector2d;

public interface IEdge {
    // Deal with map variants: Earth, HellPortal.
    // Return animal position after move.
    Vector2d handleMove(Vector2d position, Animal animal);
}
