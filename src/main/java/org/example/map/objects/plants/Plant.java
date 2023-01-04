package org.example.map.objects.plants;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Plant {
    private final Vector2d plantPosition;

    private final List<IPlantObserver> observers = new ArrayList<>();

    private final int energy;

    public void addObserver(IPlantObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPlantObserver observer){
        observers.remove(observer);
    }

    public Plant(Vector2d plantPosition, int energy) {
        this.plantPosition = plantPosition;
        this.energy = energy;
    }

    public Vector2d getPosition() {
        return this.plantPosition;
    }

    public void beEaten(Animal animal){
        animal.addEnergy(energy);
        for(IPlantObserver observer: observers){
            observer.plantEaten(this);
        }
    }

    public void place(){
        for(IPlantObserver observer: observers){
            observer.plantPlaced(this);
        }
    }
}
