package org.example.map.objects.plants;

import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.IAnimalObserver;
import org.example.utils.Vector2d;

import java.util.*;

public class PlantsToxicCorpses implements IPlants, IAnimalObserver, IPlantObserver {

    private int mapWidth;

    private int mapHeight;

    private final Set<Vector2d> plantPositions = new HashSet<>();

    private final Map<Vector2d, Integer> deathsCounted = new HashMap<>();

    private final SortedSet<Vector2d> gravesSorted = new TreeSet<>(new ByDeathsComparator(deathsCounted));

    private final Random generator = new Random();

    public PlantsToxicCorpses(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
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
        if(!deathsCounted.containsKey(position)){
            deathsCounted.put(position, 1);
            gravesSorted.add(position);
        }
        else{
            Integer count = deathsCounted.get(position);
            deathsCounted.replace(position, count + 1);
            gravesSorted.remove(position);
            gravesSorted.add(position);
        }
    }

    @Override
    public synchronized void plantEaten(Plant plant){
        plantPositions.remove(plant.getPosition());
    }

    @Override
    public synchronized void plantPlaced(Plant plant){
        plantPositions.add(plant.getPosition());
    }

    private int plantsOnGraves(){
        return Math.toIntExact(
                plantPositions.stream()
                .filter(deathsCounted::containsKey)
                .count()
        );
    }

    private boolean nonGravesAccessible(){
        int plants = plantPositions.size();
        int deaths = deathsCounted.size();
        return plants - plantsOnGraves() < mapWidth * mapHeight - deaths;
    }

    private Vector2d placeOnNonGrave(){
        Vector2d position;
        int i = 0;
        do{
            System.out.println(i);
            int x = generator.nextInt(mapWidth);
            int y = generator.nextInt(mapHeight);
            position = new Vector2d(x, y);
            i++;
        }while(deathsCounted.containsKey(position));
        return position;
    }

    private Vector2d preferredOnGrave(){
        return gravesSorted.stream()
                            .filter(g->!plantPositions.contains(g))
                            .findFirst()
                            .orElse(null);
    }

    private Vector2d preferredPosition() throws IllegalArgumentException{
        Vector2d position;
        if(nonGravesAccessible()){
            return placeOnNonGrave();
        }
        position = preferredOnGrave();
        if(position == null){
            throw new IllegalArgumentException("Can't place plant on preferred position");
        }
        return position;
    }

    private Vector2d nonPreferredOnGrave(){
        Vector2d[] sample = gravesSorted.stream()
                                        .filter(g->!plantPositions.contains(g))
                                        .toArray(Vector2d[]::new);
        return sample[generator.nextInt(sample.length)];
    }


    Vector2d nonPreferredPosition() throws IllegalArgumentException{
        Vector2d position;
        if(nonGravesAccessible()){
            position = placeOnNonGrave();
            if(position == null){
                throw new IllegalArgumentException("Can't place plant on non-preferred position");
            }
            return position;
        }
        try{
            return nonPreferredOnGrave();
        }
        catch(ArrayIndexOutOfBoundsException ex){
            throw new IllegalArgumentException("Can't place plant on non-preferred position");
        }

    }

    public Vector2d grow(boolean preferred) throws IllegalArgumentException{
        if(preferred){
            return preferredPosition();
        }
        return nonPreferredPosition();
    }

}
