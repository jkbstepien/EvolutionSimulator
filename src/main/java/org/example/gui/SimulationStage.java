package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.map.WorldMap;
import org.example.utils.Preferences;
import org.example.utils.Vector2d;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SimulationStage extends Stage implements IEngineRefreshObserver {
    private final GridPane gridPane = new GridPane();
    private final VBox layout = new VBox();
    private final Scene scene = new Scene(layout, 1280, 720);
    private final Label dominantGenotype = new Label();

    private final XYChart.Series<Number, Number> averageLifespan = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> numberOfLivingAnimals = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> numberOfPlants = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> averageEnergy = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> averageKidsPerParent = new XYChart.Series<>();

    int mapHeight = 50;
    int mapWidth = 50;
    WorldMap map;

    SimulationEngine engine;
    Thread engineThread;

    public SimulationStage(Preferences preferences) {
        map = preferences.toWorldMap();

        setOnCloseRequest(e -> {
            if (engineThread == null) return;
            engineThread.interrupt();
        });


//        Button startSimulationButton = new Button("Play");
//        startSimulationButton.setOnMouseClicked(event -> {
//            if (engineThread != null && engineThread.isAlive()) {
//                engineThread.interrupt();
//                startSimulationButton.setText("Play");
//            } else {
//                engineThread = new Thread(engine);
//                engineThread.start();
//                startSimulationButton.setText("Pause");
//            }
//
//        });
//
//        Button saveToFileButton = new Button();
//        saveToFileButton.setText("save to file");
//        saveToFileButton.setOnAction(event -> {
//            FileChooser fileChooser = new FileChooser();
//
//            //Set extension filter for csv files
//            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
//            fileChooser.getExtensionFilters().add(extFilter);
//
//            //Show save file dialog
//            File file = fileChooser.showSaveDialog(this);
//
//            if (file != null) {
//                if (!file.getName().endsWith(".csv")) {
//                    file = new File(file.getAbsolutePath() + ".csv");
//                }
//                saveStatisticsToFile(file);
//            }
//        });
//
//
//        HBox main = new HBox();
//        VBox right = new VBox();
//        right.getChildren().addAll(startSimulationButton, dominantGenotype, saveToFileButton);
//        main.getChildren().addAll(gridPane, right);
//        layout.getChildren().add(main);
//
//        HBox charts = new HBox();
//
//        var numberOfLivingAnimals = createLineChart("Animals number",
//                "Number of animals", this.numberOfLivingAnimals);
//        var numberOfPlants = createLineChart("Plants number",
//                "Number of plants", this.numberOfPlants);
//        var averageEnergy = createLineChart("Average animal energy",
//                "Energy", this.averageEnergy);
//        var averageLifespan = createLineChart("Average animal lifespan",
//                "Day", this.averageLifespan);
//        var averageKidsPerParent = createLineChart("Average kids per parent animal",
//                "Kids", this.averageKidsPerParent);
//
//
//        charts.getChildren().addAll(numberOfLivingAnimals, numberOfPlants, averageEnergy,
//                averageLifespan, averageKidsPerParent);
//
//        layout.getChildren().add(charts);
//
//
//        engine = new SimulationEngine(map, magic);
//        engine.addEngineObserver(this);

        setScene(scene);
        show();
    }


    private XYChart<Number, Number> createLineChart(String title, String yTitle, XYChart.Series<Number, Number> dataSeries) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        xAxis.setLabel("Day");
        xAxis.setForceZeroInRange(false);
        yAxis.setLabel(yTitle);
        chart.setLegendVisible(false);
        chart.setTitle(title);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);

        chart.getData().add(dataSeries);

        return chart;
    }

//    private String nameMapLabels(IWorldMap worldMap, Vector2d lowerLeft,
//                                 Vector2d upperRight, int borderMargin,
//                                 int x_position, int y_position) {
//
//        int mapBorderX = lowerLeft.x + x_position - borderMargin;
//        int mapBorderY = upperRight.y - y_position + borderMargin;
//
//        if (x_position == 0 && y_position == 0) {
//            return "y/x";
//        } else if (x_position == 0) {
//            return Integer.toString(mapBorderY);
//        } else if (y_position == 0) {
//            return Integer.toString(mapBorderX);
//        } else {
//            Object objectOnMap = worldMap.objectAt(new Vector2d(mapBorderX, mapBorderY));
//
//            if (objectOnMap == null) {
//                return " ";
//            } else {
//                return objectOnMap.toString();
//            }
//        }
//    }

//    private void displayMap(IWorldMap worldMap) {
//        updateCharts();
//
//        gridPane.setGridLinesVisible(false);
//        gridPane.getColumnConstraints().clear();
//        gridPane.getRowConstraints().clear();
//        gridPane.getChildren().clear();
//        gridPane.setGridLinesVisible(true);
//
//        Vector2d lowerLeft = worldMap.getLowerLeft();
//        Vector2d upperRight = worldMap.getUpperRight();
//
//        int borderMargin = 1;
//        int maxValueX = upperRight.x - lowerLeft.x + borderMargin;
//        int maxValueY = upperRight.y - lowerLeft.y + borderMargin;
//
//        // Add map labels to gridPane.
//        for (int y = 0; y <= maxValueY; y++) {
//            for (int x = 0; x <= maxValueX; x++) {
//                int mapBorderX = lowerLeft.x + x - borderMargin;
//                int mapBorderY = upperRight.y - y + borderMargin;
//                Vector2d pos = new Vector2d(mapBorderX, mapBorderY);
//                StackPane cell = new StackPane();
//                if (x != 0 && y != 0) {
//                    cell.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//                }
//                if (pos.follows(map.getJungleLowerLeft()) && pos.precedes(map.getJungleUpperRight())) {
//                    cell.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//                }
//
//                String name = nameMapLabels(worldMap, lowerLeft, upperRight, borderMargin, x, y);
//                if (worldMap.objectAt(pos) instanceof Plant) {
//                    var guiElement = new GuiElementBox((IMapElement) worldMap.objectAt(new Vector2d(mapBorderX, mapBorderY)));
//                    cell.getChildren().add(guiElement.getGuiVisualization());
//                } else {
//                    Label label = new Label(name);
//                    cell.getChildren().add(label);
//                }
//                gridPane.add(cell, x, y, 1, 1);
//                GridPane.setHalignment(cell, HPos.CENTER);
//            }
//        }
//
//        // Format gridPane as well as set constraints on columns and rows.
//        for (int y = 0; y <= maxValueY; y++) {
//            gridPane.getRowConstraints().add(new RowConstraints(this.mapHeight));
//        }
//
//        for (int x = 0; x <= maxValueX; x++) {
//            gridPane.getColumnConstraints().add(new ColumnConstraints(this.mapWidth));
//        }
//    }

//    private void updateCharts() {
//        averageLifespan.getData().add(new XYChart.Data<>(engine.getDayCounter(), map.getLifespanAverage()));
//        numberOfLivingAnimals.getData().add(new XYChart.Data<>(engine.getDayCounter(), map.countAnimals()));
//        numberOfPlants.getData().add(new XYChart.Data<>(engine.getDayCounter(), map.countPlants()));
//        averageEnergy.getData().add(new XYChart.Data<>(engine.getDayCounter(), map.getEnergyAverage()));
//        averageKidsPerParent.getData().add(new XYChart.Data<>(engine.getDayCounter(), map.getKidsPerLivingParentAverage()));
//
//        dominantGenotype.setText("Dominant genotype:\t" + map.getDominatingGenotype());
//    }

    @Override
    public void refreshNeeded() {
//        Platform.runLater(() -> displayMap(map));
    }

//    private void saveStatisticsToFile(File file) {
//        try {
//            FileWriter out = new FileWriter(file);
//
//            out.append("Day number");
//            out.append(',');
//            out.append("Alive animals");
//            out.append(',');
//            out.append("Plants");
//            out.append(',');
//            out.append("Average (alive)");
//            out.append(',');
//            out.append("Average lifespan (dead animals)");
//            out.append(',');
//            out.append("Average number of children (alive animals)");
//            out.append('\n');
//
//            for (int i = 0; i < engine.getDayCounter(); i++) {
//                out.append(String.valueOf(i));
//                out.append(',');
//                out.append(String.valueOf(numberOfLivingAnimals.getData().get(i).getYValue()));
//                out.append(',');
//                out.append(String.valueOf(numberOfPlants.getData().get(i).getYValue()));
//                out.append(',');
//                out.append(String.valueOf(averageEnergy.getData().get(i).getYValue()));
//                out.append(',');
//                out.append(String.valueOf(averageLifespan.getData().get(i).getYValue()));
//                out.append(',');
//                out.append(String.valueOf(averageKidsPerParent.getData().get(i).getYValue()));
//                out.append('\n');
//            }
//
//            out.append("Average statistics,");
//            out.append(String.valueOf(numberOfLivingAnimals.getData().stream().mapToDouble(a -> a.getYValue().doubleValue()).average().orElse(0)));
//            out.append(',');
//            out.append(String.valueOf(numberOfPlants.getData().stream().mapToDouble(a -> a.getYValue().doubleValue()).average().orElse(0)));
//            out.append(',');
//            out.append(String.valueOf(averageEnergy.getData().stream().mapToDouble(a -> a.getYValue().doubleValue()).average().orElse(0)));
//            out.append(',');
//            out.append(String.valueOf(averageLifespan.getData().stream().mapToDouble(a -> a.getYValue().doubleValue()).average().orElse(0)));
//            out.append(',');
//            out.append(String.valueOf(averageKidsPerParent.getData().stream().mapToDouble(a -> a.getYValue().doubleValue()).average().orElse(0)));
//
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
