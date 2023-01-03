package org.example;

import javafx.application.Application;
import org.example.gui.App;
import org.example.map.MapBuilder;

public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);

//        Example
//        WorldMap map = MapBuilder.builder()
//                .setWidth(100)
//                .setAnimalEnergy(100)
//                .setAnimalBreedingCost(100)
//                .build();
//
//        MapBuilder builder = new MapBuilder();
//        int w = getWidthFromFront();
//        builder.setWidth(w);

    }
}