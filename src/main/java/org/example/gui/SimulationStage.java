package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.map.Statistics;
import org.example.map.WorldMap;
import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.AnimalStatistics;
import org.example.utils.Preferences;
import org.example.utils.Vector2d;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;


public class SimulationStage extends Stage implements IEngineRefreshObserver {
    private final GridPane gridPane = new GridPane();
    private final VBox layout = new VBox();
    private final Scene scene = new Scene(layout, 1280, 720);
    private final Label dominantGenotype = new Label();
    private final Button saveToFileButton = new Button("save to file");;
    int mapHeight = 50;
    int mapWidth = 50;
    WorldMap map;

    SimulationEngine engine;
    Thread engineThread;
    private final Label numberOfLivingAnimals = new Label("");
    private final Label numberOfPlants = new Label("");
    private final Label numberOfFreeFields = new Label("");
    private final Label mostPopularGenotype = new Label("");
    private final Label averageEnergy = new Label("");
    private final Label averageLifespan = new Label("");
    private final Label day = new Label("");
    private final Label trackedGenome = new Label("");
    private final Label trackedDirection = new Label("");
    private final Label trackedEnergy = new Label("");
    private final Label trackedPlants = new Label("");
    private final Label trackedChildren = new Label("");
    private final Label trackedAge = new Label("");




    public SimulationStage(Preferences preferences) {
        map = preferences.toWorldMap();

        setOnCloseRequest(e -> {
            if (engine != null) {
                engine.interrupt();
            }
        });

        Button startSimulationButton = new Button("Play");
        startSimulationButton.setOnMouseClicked(event -> {
            if (engine != null && engine.isAlive()) {
                engine.interrupt();
                saveToFileButton.setDisable(false);
                startSimulationButton.setText("Play");
                displayMapWithTracking();


            } else {
                engine = new SimulationEngine(map, this);
                engine.start();
                saveToFileButton.setDisable(true);
                startSimulationButton.setText("Pause");
            }

        });
        saveToFileButton.setDisable(true);
        saveToFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for csv files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(this);

            if (file != null) {
                if (!file.getName().endsWith(".csv")) {
                    file = new File(file.getAbsolutePath() + ".csv");
                }
                saveStatisticsToFile(file);
            }
        });
//
//
        HBox main = new HBox();
        main.setPadding(new Insets(10, 10, 10, 10));
        VBox right = new VBox();
        right.setSpacing(10);
        right.setPadding(new Insets(10, 10, 10, 10));
        right.getChildren().addAll(startSimulationButton,
                numberOfLivingAnimals,
                numberOfPlants,
                numberOfFreeFields,
                mostPopularGenotype,
                averageEnergy,
                averageLifespan,
                day,
                saveToFileButton,
                trackedGenome,
                trackedDirection,
                trackedEnergy,
                trackedPlants,
                trackedChildren,
                trackedAge);
        main.getChildren().addAll(gridPane, right);
        layout.getChildren().add(main);

        engine = new SimulationEngine(map, this);
        displayMap();
        engine.start();
        setScene(scene);
        show();
    }

    private String nameMapLabels(Vector2d lowerLeft,
                                 Vector2d upperRight, int borderMargin,
                                 int x_position, int y_position) {

        int mapBorderX = lowerLeft.x + x_position - borderMargin;
        int mapBorderY = upperRight.y - y_position + borderMargin;

        if (x_position == 0 && y_position == 0) {
            return "y/x";
        } else if (x_position == 0) {
            return Integer.toString(mapBorderY);
        } else if (y_position == 0) {
            return Integer.toString(mapBorderX);
        } else {
            Vector2d position = new Vector2d(mapBorderX, mapBorderY);
            return map.contentLabel(position);
        }
    }

    public void displayStats(Statistics statistics) {
        // generate labels for stats
        numberOfLivingAnimals.setText("Number of living animals: " + statistics.numberOfAllAnimals());
        numberOfPlants.setText("Number of plants: " + statistics.numberOfAllPlants());
        numberOfFreeFields.setText("Number of free fields: " + statistics.freeField());
        mostPopularGenotype.setText("Most popular genotype: " + statistics.mostPopularGenotypes());
        averageEnergy.setText("Average energy: " + statistics.averageEnergy());
        averageLifespan.setText("Average lifespan: " + statistics.averageDeadLifespan());
        day.setText("Day: " + statistics.dayCounter());
    }

    public void displayAnimalStats(AnimalStatistics statistics) {
        trackedGenome.setText("Genome: " + statistics.genome());
        trackedDirection.setText("Direction: " + statistics.direction());
        trackedEnergy.setText("Energy: " + statistics.energy());
        trackedPlants.setText("Plants eaten: " + statistics.plantsEaten());
        trackedChildren.setText("Children: " + statistics.children());
        trackedAge.setText("Age: " + statistics.age());
    }
    public void displayMapWithTracking() {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        int borderMargin = 1;
        int maxValueX = upperRight.x - lowerLeft.x + borderMargin;
        int maxValueY = upperRight.y - lowerLeft.y + borderMargin;

        // Add map labels to gridPane.
        for (int y = 0; y <= maxValueY; y++) {
            for (int x = 0; x <= maxValueX; x++) {
                int mapBorderX = lowerLeft.x + x - borderMargin;
                int mapBorderY = upperRight.y - y + borderMargin;
                Vector2d pos = new Vector2d(mapBorderX, mapBorderY);
                StackPane cell = new StackPane();
                if (x != 0 && y != 0) {
                    cell.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    cell.onMouseClickedProperty().set(event -> {
                        map.setTrackedAnimal(new Vector2d(pos.x, pos.y));
                        map.getTrackedAnimalStatistics().ifPresent(this::displayAnimalStats);});
                }

                String name = nameMapLabels(lowerLeft, upperRight, borderMargin, x, y);
                Label label = new Label(name);
                cell.getChildren().add(label);

                gridPane.add(cell, x, y, 1, 1);
                GridPane.setHalignment(cell, HPos.CENTER);
            }
        }

        map.animalsWithDominantGenes().stream().map(Animal::getPosition).forEach(position -> {
            StackPane cell = new StackPane();
            cell.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            String name = nameMapLabels(lowerLeft, upperRight, borderMargin, position.x+1, map.getHeight() - position.y);
            Label label = new Label(name);
            cell.getChildren().add(label);
            gridPane.add(cell, position.x+1, map.getHeight() - position.y, 1, 1);
            cell.onMouseClickedProperty().set(event -> { map.setTrackedAnimal(new Vector2d(position.x, position.y));
                map.getTrackedAnimalStatistics().ifPresent(this::displayAnimalStats);
                map.getTrackedAnimalStatistics().ifPresent(this::displayAnimalStats);
            });
            GridPane.setHalignment(cell, HPos.CENTER);
        });

        // Format gridPane as well as set constraints on columns and rows.
        for (int y = 0; y <= maxValueY; y++) {
            gridPane.getRowConstraints().add(new RowConstraints(this.mapHeight));
        }

        for (int x = 0; x <= maxValueX; x++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(this.mapWidth));
        }
    }

    public void displayMap() {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        int borderMargin = 1;
        int maxValueX = upperRight.x - lowerLeft.x + borderMargin;
        int maxValueY = upperRight.y - lowerLeft.y + borderMargin;

        // Add map labels to gridPane.
        for (int y = 0; y <= maxValueY; y++) {
            for (int x = 0; x <= maxValueX; x++) {
                int mapBorderX = lowerLeft.x + x - borderMargin;
                int mapBorderY = upperRight.y - y + borderMargin;
                Vector2d pos = new Vector2d(mapBorderX, mapBorderY);
                StackPane cell = new StackPane();
                if (x != 0 && y != 0) {
                    cell.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                String name = nameMapLabels(lowerLeft, upperRight, borderMargin, x, y);
                Label label = new Label(name);
                cell.getChildren().add(label);

                gridPane.add(cell, x, y, 1, 1);
                GridPane.setHalignment(cell, HPos.CENTER);
            }
        }

        map.animalsWithDominantGenes().stream().map(Animal::getPosition).forEach(position -> {
            StackPane cell = new StackPane();
            cell.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            String name = nameMapLabels(lowerLeft, upperRight, borderMargin, position.x+1, map.getHeight() - position.y);
            Label label = new Label(name);
            cell.getChildren().add(label);
            gridPane.add(cell, position.x+1, map.getHeight() - position.y, 1, 1);
            GridPane.setHalignment(cell, HPos.CENTER);
        });

        // Format gridPane as well as set constraints on columns and rows.
        for (int y = 0; y <= maxValueY; y++) {
            gridPane.getRowConstraints().add(new RowConstraints(this.mapHeight));
        }

        for (int x = 0; x <= maxValueX; x++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(this.mapWidth));
        }
    }
    @Override
    public void refreshNeeded() {
        Platform.runLater(this::displayMap);
    }

    private void saveStatisticsToFile(File file) {
        try {
            FileWriter out = new FileWriter(file);
            out.append(Statistics.csvHeader());

            map.getAllStatistics().forEach(statistics -> {
                try {
                    out.append(statistics.toCsvRow());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
