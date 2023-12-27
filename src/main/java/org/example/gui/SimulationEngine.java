package org.example.gui;


import javafx.application.Platform;
import org.example.map.Statistics;
import org.example.map.WorldMap;
import org.example.map.objects.animal.AnimalStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimulationEngine extends Thread {
    private final WorldMap map;
    private final SimulationStage simulationStage;
    private final List<Statistics> statistics = new ArrayList<>();
    private final int dayCounter = 0;

    public SimulationEngine(WorldMap map, SimulationStage simulationStage) {
        this.map = map;
        this.simulationStage = simulationStage;
    }

    private void day() {
        Statistics currentStats = map.day();
        Optional<AnimalStatistics> animalStatistics = map.getTrackedAnimalStatistics();

        Platform.runLater(() -> {
            simulationStage.displayMap();
            simulationStage.displayStats(currentStats);
            animalStatistics.ifPresent(simulationStage::displayAnimalStats);
        });
    }

    @Override
    public void run() {
        while (true) {
            day();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
