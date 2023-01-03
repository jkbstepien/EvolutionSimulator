package org.example.map.objects.plants;

import org.example.map.WorldMap;
import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.utils.Vector2d;

import java.util.*;

public class PlantsToxicCorpses implements IPlants, IAnimalObserver {

    private int mapWidth;

    private int mapHeight;

    private final Map<Vector2d, Integer> deathsCounted = new HashMap<>();

    private final SortedSet<Vector2d> positionsSorted = new TreeSet<>(new ByDeaths(deathsCounted));

    public PlantsToxicCorpses() {
    }

    @Override
    public void setWorldMap(WorldMap map){
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
    }

    @Override
    public Vector2d grow(boolean preferred) {
        return null;
    }

    @Override
    public void animalPlaced(Animal animal){

    }

    @Override
    public void animalMoved(Animal animal){}

    @Override
    public void animalDied(Animal animal){
        Vector2d position = animal.getPosition();
        if(!positionsSorted.contains(position)){
            deathsCounted.put(position, 1);
            positionsSorted.add(position);
        }
        else{
            Integer count = deathsCounted.get(position);
            deathsCounted.replace(position, count + 1);
            positionsSorted.remove(position);
            positionsSorted.add(position);
        }
    }

}
