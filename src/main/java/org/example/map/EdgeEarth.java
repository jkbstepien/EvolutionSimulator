package org.example.map;

import org.example.map.options.IEdge;
import org.example.utils.Vector2d;
import org.example.map.objects.animal.Animal;

public class EdgeEarth implements IEdge {

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final int width;
    private final int height;

    public EdgeEarth(Vector2d lowerLeft, Vector2d upperRight, int width, int height) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.width = width;
        this.height = height;
    }

    @Override
    public Vector2d handleMove(Animal animal) {
        Vector2d position = animal.getNewPosition();
        if (position.y < 0 || position.y >= height) {
            animal.turn();
            return animal.getPosition();
        } else if (position.x >= width) {
            return new Vector2d(0, position.y);
        } else if (position.x < 0) {
            return new Vector2d(width - 1, position.y);
        }
//        if (position.follows(lowerLeft) && position.precedes(upperRight)) {
//            return position;
//        } else {
//            // Return opposite direction if out of map.
//            if (position.x < lowerLeft.x) {
//                position = new Vector2d(upperRight.x, position.y);
//            } else if (position.x > upperRight.x) {
//                position = new Vector2d(lowerLeft.x, position.y);
//            } else {
//                animal.turn();
//            }
//        }
        return position;
    }
}
