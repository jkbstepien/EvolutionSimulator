package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.utils.GetFromFile;
import org.example.utils.Preferences;

import java.io.IOException;

public class App extends Application {

    private final GridPane layout = new GridPane();
    private final Scene configScene = new Scene(this.layout);

    private final GetFromFile getFromFile = new GetFromFile();


    public void start(Stage primaryStage) {
        Button startSimulationButton = new Button("Go to simulations");
        startSimulationButton.setOnMouseClicked(event -> {
            Preferences preferences = null;
            try {
                preferences = getFromFile.getPreferencesFromFile("src/main/resources/conf1.json");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var simulationStage = new SimulationStage(preferences);
            simulationStage.setTitle("World Map");

//            primaryStage.close();
        });

        layout.addRow(9, startSimulationButton);

        primaryStage.setTitle("EvolutionSimulator");
        primaryStage.setScene(configScene);
        primaryStage.show();

    }
}
