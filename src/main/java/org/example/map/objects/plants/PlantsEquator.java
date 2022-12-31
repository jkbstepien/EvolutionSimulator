package org.example.map.objects.plants;

import org.example.map.WorldMap;
import org.example.utils.Vector2d;

import java.util.*;
import java.util.stream.IntStream;

public class PlantsEquator implements IPlants, IPlantObserver{

    private final List<Integer> equator = new ArrayList<>();

    private final List<Integer> nonEquator = new ArrayList<>();

    private final Map<Integer, Integer> rowStats = new HashMap<>();

    private final int mapWidth;

    private final int mapHeight;

    private final Random generator = new Random();

    private void findEquator(){
        int equatorRows;
        int startUp, startDown;
        if(mapHeight % 2 == 1) {
            int middle = mapHeight / 2;
            equator.add(middle);
            startUp = middle - 1;
            startDown = middle + 1;
            equatorRows = mapHeight * 2 / 5 - 1;
        }
        else {
            startDown = mapHeight / 2;
            startUp = startDown - 1;
            equatorRows = mapHeight * 2 / 5;
        }
        for(int i=0; i<equatorRows/2; i++){
            equator.add(startUp - i);
            equator.add(startDown + i);
        }
    }

    private void findNonEquator(){
        for(int i=0; i<mapHeight; i++){
            nonEquator.add(i);
        }
        equator.forEach(nonEquator::remove);
    }

    public PlantsEquator(WorldMap map){
        this.mapHeight = map.getHeight();
        this.mapWidth = map.getWidth();
        findEquator();
        findNonEquator();
    }

    private int rowInsideEquator(){
        // TODO raise an error if not possible
        return equator.get(generator.nextInt(equator.size()));
    }

    private int rowOutsideEquator(){
        // TODO raise an error if not possible
        return nonEquator.get(generator.nextInt(nonEquator.size()));
    }

    private boolean equatorAccessible(){
        for(Integer row: equator){
            if(rowStats.get(row) < mapWidth) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector2d grow(boolean preferred) {
        // TODO this may throw errors
        int y;
        if(preferred){
            // use while
            y = rowInsideEquator();
        }
        else {
            // use while
            y = rowOutsideEquator();
        }
        int x = generator.nextInt(mapWidth);
        return new Vector2d(x, y);
    }

    @Override
    public void plantEaten(Plant plant){
        // instead of stats, store set of plant positions
        Vector2d position = plant.getPosition();
        Integer rowCount = rowStats.get(position.y);
        rowStats.replace(position.y, rowCount - 1);
    }

    @Override
    public void plantPlaced(Plant plant){
        Vector2d position = plant.getPosition();
        if(!rowStats.containsKey(position.y)){
            rowStats.put(position.y, 1);
        }
        else{
            Integer rowCount = rowStats.get(position.y);
            rowStats.replace(position.y, rowCount + 1);
        }
    }
}
