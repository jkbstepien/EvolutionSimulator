package org.example.map.objects.plants;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.utils.Vector2d;

import java.util.*;

public class PlantsToxicCorpses implements IPlants, IAnimalObserver, IPlantObserver {

    private final int mapWidth, mapHeight;

    private final Map<Vector2d, Integer> positionsUsed = new HashMap<>();

    private final Map<Vector2d, Integer> positionsNotUsed = new HashMap<>();
    private final Random generator = new Random();

    private void positionsToChoose(){
        for(int y=0; y<mapHeight; y++){
            for(int x=0; x<mapWidth; x++){
                positionsNotUsed.put(new Vector2d(x, y), 0);
            }
        }
    }

    public PlantsToxicCorpses(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        positionsToChoose();
    }

    @Override
    public synchronized void animalPlaced(Animal animal){

    }

    @Override
    public synchronized void animalMoved(Animal animal, Vector2d oldPosition){
    }

    @Override
    public synchronized void animalDied(Animal animal){
        Vector2d position = animal.getPosition();
        int counter;
        if(positionsNotUsed.containsKey(position)){
            counter = positionsNotUsed.get(position);
            positionsNotUsed.replace(position, counter + 1);
        }
        else{
            counter = positionsUsed.get(position);
            positionsUsed.replace(position, counter + 1);
        }
    }

    @Override
    public synchronized void plantEaten(Plant plant){
        Vector2d position = plant.getPosition();
        int counter = positionsUsed.get(position);
        positionsUsed.remove(position);
        positionsNotUsed.put(position, counter);
    }

    @Override
    public synchronized void plantPlaced(Plant plant){
        Vector2d position = plant.getPosition();
        int counter = positionsNotUsed.get(position);
        positionsNotUsed.remove(position);
        positionsUsed.put(position, counter);
    }

    private synchronized Vector2d preferredPosition(){
        Integer minDeaths = Collections.min(positionsNotUsed.values());
        List<Vector2d> suitablePositions = positionsNotUsed.entrySet()
                .stream()
                .filter(entry-> Objects.equals(entry.getValue(), minDeaths))
                .map(Map.Entry::getKey)
                .toList();
        return suitablePositions.get(generator.nextInt(suitablePositions.size()));
    }

    private synchronized Vector2d nonPreferredPosition(){
        Integer minDeaths = Collections.min(positionsNotUsed.values());
        List<Vector2d> suitablePositions = positionsNotUsed.entrySet()
                .stream()
                .filter(entry-> !Objects.equals(entry.getValue(), minDeaths))
                .map(Map.Entry::getKey)
                .toList();
        return suitablePositions.get(generator.nextInt(suitablePositions.size()));
    }

    public Vector2d grow(boolean preferred) throws CannotPlacePlantException{
        try {
            if (preferred) {
                return preferredPosition();
            }
            return nonPreferredPosition();
        }
        catch (NoSuchElementException | IllegalArgumentException ex){
            throw new CannotPlacePlantException("Can't place any new plants");
        }
    }

}
