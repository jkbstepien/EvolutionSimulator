package org.example.map.objects;

import org.example.map.options.IMapElement;
import org.example.utils.MapDirection;
import org.example.utils.MutationOption;
import org.example.utils.Vector2d;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection direction;
    private Genes genotype; // TODO: correction variant
    private int numberOfMutations;
    private MutationOption mutationOption;
    private int energy;

    public Animal(Vector2d position, int energy, int numberOfMutations, MutationOption mutationOption) {
        // Basic parameters.
        this.position = position;
        this.direction = MapDirection.NORTH.randomDirection();
        this.energy = energy;

        // Obtaining genotype basing on mutation type.
        this.numberOfMutations = numberOfMutations;
        this.mutationOption = mutationOption;
        this.genotype = new Genes(this.numberOfMutations, this.mutationOption);
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
}
