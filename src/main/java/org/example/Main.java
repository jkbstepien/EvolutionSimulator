package org.example;

import javafx.application.Application;
import org.example.gui.App;
import org.example.gui.SimulationEngine;
import org.example.gui.SimulationStage;
import org.example.map.MapBuilder;
import org.example.map.WorldMap;
import org.example.utils.GetFromFile;
import org.example.utils.Preferences;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);
//        GetFromFile getFromFile = new GetFromFile();
//        Preferences preferences = null;
//        try {
//            preferences = getFromFile.getPreferencesFromFile("src/main/resources/conf1.json");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        WorldMap map = preferences.toWorldMap();
//        var a  = 1;
//        while(true) {
//            map.day();
//        }
    }
}