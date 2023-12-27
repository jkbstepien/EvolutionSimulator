package org.example.map;

import org.example.map.options.IEdge;
import org.example.utils.Vector2d;
import org.example.map.objects.animal.Animal;

public class EdgeEarth implements IEdge {

    private final int width;
    private final int height;

    public EdgeEarth(int width, int height) {
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
        return position;
    }
}
