package org.example.map;

import org.example.map.objects.animal.Animal;
import org.example.map.options.IEdge;
import org.example.utils.Vector2d;

public class EdgeHellPortal implements IEdge {

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public EdgeHellPortal(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    @Override
    public Vector2d handleMove(Vector2d position, Animal animal) {
        if (position.follows(lowerLeft) && position.precedes(upperRight)) {
            return position;
        } else {
            // Return random position if out of map.
            if (position.x < lowerLeft.x || position.x > upperRight.x) {
                position = new Vector2d((int) (Math.random() * (upperRight.x - lowerLeft.x + 1) + lowerLeft.x),
                        (int) (Math.random() * (upperRight.y - lowerLeft.y + 1) + lowerLeft.y));
            }
        }
        return position;
    }
}
