package org.example.utils;

import java.util.Random;

public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTHEAST,
    NORTHWEST,
    SOUTHEAST,
    SOUTHWEST;

    public String toString() {
        return switch(this) {
            case NORTH -> "north";
            case SOUTH -> "south";
            case EAST -> "east";
            case WEST -> "west";
            case NORTHEAST -> "north-east";
            case NORTHWEST -> "north-west";
            case SOUTHEAST -> "south-east";
            case SOUTHWEST -> "south-west";
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTHEAST -> new Vector2d(1,1);
            case NORTHWEST -> new Vector2d(-1,1);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
        };
    }

    public static MapDirection fromInt(int value){
        return switch (value) {
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTHEAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTHEAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTHWEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTHWEST;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public MapDirection randomDirection(){
        Random random = new Random();
        return MapDirection.fromInt(random.nextInt(8));
    }

    public MapDirection opposite(){
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
            case NORTHEAST -> SOUTHWEST;
            case SOUTHWEST -> NORTHEAST;
            case SOUTHEAST -> NORTHWEST;
        };
    }
}
