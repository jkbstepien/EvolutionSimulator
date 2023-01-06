package org.example.map.objects.plants;


public interface IPlantObserver {
    // It lets Plants inform other classes about changes of their states
    void plantEaten(Plant plant);
    void plantPlaced(Plant plant);
}
