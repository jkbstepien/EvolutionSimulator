package org.example.map.objects;

import org.example.utils.Vector2d;

public class Plant {
    private final Vector2d plantPosition;

    public Plant(Vector2d plantPosition) {
        this.plantPosition = plantPosition;
    }

    public Vector2d getPosition() {
        return this.plantPosition;
    }
}
