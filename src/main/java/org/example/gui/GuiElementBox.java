package org.example.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.map.options.IMapElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private final IMapElement mapElement;

    public GuiElementBox(IMapElement mapElement) {
        this.mapElement = mapElement;
    }

    public VBox getGuiVisualization() {
        if (this.mapElement == null)
            return new VBox();

        Image image = null;
        try {
            image = new Image(new FileInputStream(this.mapElement.getSourceAddress()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Label label = new Label(this.mapElement.toString());
        VBox vBox = new VBox(imageView, label);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }
}


