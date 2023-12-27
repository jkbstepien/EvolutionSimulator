package org.example.map.objects.plants;

import org.example.utils.Vector2d;

import java.util.*;

public class PlantsEquator implements IPlants, IPlantObserver{

    private final List<Vector2d> equator = new ArrayList<>();

    private final List<Vector2d> nonEquator = new ArrayList<>();

    private final int mapWidth, mapHeight;

    private int equatorStartRow, equatorEndRow;

    private final Random generator = new Random();

    private void addRowsPositions(List<Vector2d> positions, int startRow, int endRow){
        for(int y = startRow; y <= endRow; y++){
            for(int x = 0; x < mapWidth; x++){
                positions.add(new Vector2d(x ,y));
            }
        }
    }

    private void calcEquatorBorders(){
        int equatorRows = mapHeight / 5;
        int rowsLeft = equatorRows;
        if(mapHeight % 2 == 1){
            int middle = mapHeight / 2;
            equatorStartRow = middle - equatorRows / 2;
            rowsLeft -= equatorRows / 2 + 1;
            equatorEndRow = middle + rowsLeft;
        }
        else{
            equatorEndRow = mapHeight / 2 + equatorRows / 2 - 1;
            rowsLeft -= equatorRows / 2;
            equatorStartRow = equatorEndRow - rowsLeft;
        }
    }

    private void prepareAvailablePositions(){
        addRowsPositions(equator, equatorStartRow, equatorEndRow);
        addRowsPositions(nonEquator, 0, equatorStartRow - 1);
        addRowsPositions(nonEquator, equatorEndRow + 1, mapHeight - 1);
    }

    public PlantsEquator(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        calcEquatorBorders();
        prepareAvailablePositions();
    }

    private boolean equatorAccessible(){
        return equator.size() > 0;
    }

    private boolean nonEquatorAccessible(){
        return nonEquator.size() > 0;
    }

    private Vector2d randomPosition(List<Vector2d> positions){
        return positions.get(generator.nextInt(positions.size()));
    }


    private synchronized Vector2d positionInsideEquator() throws CannotPlacePlantException{
        if(!equatorAccessible()){
            throw new CannotPlacePlantException("Can't place plant on preferred position");
        }
        return randomPosition(equator);
    }

    private synchronized Vector2d positionOutsideEquator() throws CannotPlacePlantException{
        if (!nonEquatorAccessible()) {
            throw new CannotPlacePlantException("Can't place plant on non-preferred position");
        }
        return randomPosition(nonEquator);
    }

    @Override
    public Vector2d grow(boolean preferred) throws CannotPlacePlantException{
        if(preferred){
            return positionInsideEquator();
        }
        return positionOutsideEquator();
    }

    @Override
    public void plantPlaced(Plant plant){
        Vector2d position = plant.getPosition();
        equator.remove(position);
        nonEquator.remove(position);
    }

    @Override
    public void plantEaten(Plant plant){
        Vector2d position = plant.getPosition();
        if(position.y < equatorStartRow || position.y > equatorEndRow){
            nonEquator.add(position);
        }
        else{
            equator.add(position);
        }
    }
}
