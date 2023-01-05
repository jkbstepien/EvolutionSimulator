package org.example.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.utils.GetFromFile;
import org.example.utils.Preferences;

import java.io.IOException;

public class App extends Application {

    private final GridPane layout = new GridPane();
    private final Scene configScene = new Scene(this.layout, 500, 500);

    private final GetFromFile getFromFile = new GetFromFile();

    private final TextField textField = new TextField("Give path to file");


    public void start(Stage primaryStage) {
        Button startSimulationButton1 = getButton("src/main/resources/conf1.json", 1);
        Button startSimulationButton2 = getButton("src/main/resources/conf2.json", 2);
        Button startSimulationButton3 = getButton("src/main/resources/conf3.json", 3);

        // Position buttons at center.
        GridPane.setHalignment(startSimulationButton1, HPos.CENTER);
        GridPane.setHalignment(startSimulationButton2, HPos.CENTER);
        GridPane.setHalignment(startSimulationButton3, HPos.CENTER);

        layout.addRow(1, startSimulationButton1);
        layout.addRow(2, startSimulationButton2);
        layout.addRow(3, startSimulationButton3);
        customPath(layout);

        primaryStage.setTitle("EvolutionSimulator");
        primaryStage.setScene(configScene);
        primaryStage.show();

    }

    private Button getButton(String path, int number) {
        Button startSimulationButton = new Button("Go to simulation " + number);
        startSimulationButton.setOnMouseClicked(event -> {
            Preferences preferences = null;
            try {
                preferences = getFromFile.getPreferencesFromFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var simulationStage = new SimulationStage(preferences);
            simulationStage.setTitle("World Map");

//            primaryStage.close();
        });
        return startSimulationButton;
    }

    private void customPath(GridPane layout) {

        Button button = new Button("Read simulation from file");
        GridPane.setHalignment(button, HPos.CENTER);
        button.setOnMouseClicked(event -> {
            Preferences preferences = null;
            try {
                preferences = getFromFile.getPreferencesFromFile(textField.getText());
                var simulationStage = new SimulationStage(preferences);
                simulationStage.setTitle("World Map");
            } catch (IOException e) {
                textField.setText("Wrong path");
            }

        });

        layout.addRow(4, textField, button);
    }
}
