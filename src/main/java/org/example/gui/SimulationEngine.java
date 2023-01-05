package org.example.gui;


import javafx.application.Platform;
import org.example.map.Statistics;
import org.example.map.WorldMap;
import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.genes.Genes;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine extends Thread {
    private final WorldMap map;
    private List<Statistics> statistics = new ArrayList<>();

    private int dayCounter = 0;
    private final SimulationStage simulationStage;

    public SimulationEngine(WorldMap map, SimulationStage simulationStage) {
        this.map = map;
        this.simulationStage = simulationStage;
    }

    private void day() {
        Statistics currentStats = map.day();

        Platform.runLater(() -> {
            simulationStage.displayMap();
            simulationStage.displayStats(currentStats);
        });
    }
    @Override
    public void run() {
//        while (map.numberOfAllAnimals() > 0) {
        while(true){
            day();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
