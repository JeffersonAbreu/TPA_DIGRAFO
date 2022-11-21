package br.edu.ifes.si.tpa;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application { // atualizado

    private static Stage primaryStage;
    private static Scene scene;
    private double x, y;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        scene = new Scene(((BorderPane) loadFXML("home")));
        scene.setFill(Color.TRANSPARENT);

        scene.getRoot().setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.getRoot().setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Retorna o palco principal.
     * 
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getBP() {
        return rootLayout;
    }
}