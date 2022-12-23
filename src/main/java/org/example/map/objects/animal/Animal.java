package org.example.map.objects.animal;

import org.example.map.objects.animal.genes.Genes;
import org.example.map.options.IMapElement;
import org.example.utils.MapDirection;
import org.example.utils.Vector2d;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection direction;
    private Genes genotype;
    private int energy;

    public Animal(Vector2d position, int energy, Genes genotype) {
        // Basic parameters.
        this.position = position;
        this.direction = MapDirection.NORTH.randomDirection();
        this.energy = energy;
        this.genotype = genotype;
    }

    @Override
    public String toString() {
        return switch (this.direction) {
            case NORTH -> "↑";
            case EAST -> "→";
            case SOUTH -> "↓";
            case WEST -> "←";
            case NORTHEAST -> "↗";
            case NORTHWEST -> "↖";
            case SOUTHEAST -> "↘";
            case SOUTHWEST -> "↙";
        } + "\t" + energy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public int getEnergy() {
        return energy;
    }

    public Genes getGenotype() {
        return genotype;
    }
}
