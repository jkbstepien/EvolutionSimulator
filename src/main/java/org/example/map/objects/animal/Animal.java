package org.example.map.objects.animal;

import org.example.map.objects.animal.genes.Genes;
import org.example.map.options.IMapElement;
import org.example.utils.MapDirection;
import org.example.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
    private final List<IAnimalObserver> observers = new ArrayList<>();
    private Vector2d position;
    private MapDirection direction;
    private final Genes genes;
    private int energy;
    private int age;
    private int bornDay;
    private int children = 0;
    private int plantsEaten;

    public Animal(Vector2d position, int energy, Genes genes) {
        // Basic parameters.
        this.age = 0;
        this.position = position;
        this.direction = MapDirection.NORTH.randomDirection();
        this.energy = energy;
        this.genes = genes;
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
    public String getSourceAddress() {
        return null;
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

    public Genes getGenes() {
        return genes;
    }

    public void setBornDay(int bornDay) {
        this.bornDay = bornDay;
    }

    public void incrementChildren() {
        this.children++;
    }

    public void addObserver(IAnimalObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(IAnimalObserver observer) {
        observers.remove(observer);
    }

    public void place() {
        for (IAnimalObserver observer : observers) {
            observer.animalPlaced(this);
        }
    }

    public void move(Vector2d newPosition) {
        Vector2d oldPosition = this.position;
        this.position = newPosition;

        for (IAnimalObserver observer : observers) {
            observer.animalMoved(this, oldPosition);
        }
        age++;
        energy--;
    }

    public Vector2d getNewPosition() {
        int nextGene = genes.getMoveDirection();
        MapDirection direction = MapDirection.fromInt(nextGene);
        return position.add(direction.toUnitVector());
    }

    public void removeIfDied() {
        if (!isAlive()) {
            for (IAnimalObserver observer : observers) {
                observer.animalDied(this);
            }
        }
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void addEnergy(int energy) {
        this.plantsEaten++;
        this.energy += energy;
    }

    public void turn() {
        direction = direction.opposite();
    }

    public int getAge() {
        return age;
    }

    public void calculateAge(int day) {
        this.age = Math.abs(day - bornDay);
    }

    public AnimalStatistics getStatistics() {
        return new AnimalStatistics(genes.getGenotype(), direction, energy, plantsEaten, children, age, bornDay);
    }
}
