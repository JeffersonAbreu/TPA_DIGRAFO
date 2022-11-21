package br.edu.ifes.si.tpa;

import java.io.IOException;

import br.edu.ifes.si.tpa.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application { // atualizado

    private Stage primaryStage;
    private double x, y;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initRootLayout();
    }

    public BorderPane getBP() {
        return rootLayout;
    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./view/home.fxml"));
            rootLayout = (BorderPane) loader.load();

            Text t = new Text();
            t.setText("Seja bem-vindo(a) ao trabalho de digrafos!");
            t.setFont(Font.font("Arial Black", 24));
            t.setFill(Color.BLACK);
            rootLayout.setCenter(t);
            rootLayout.setStyle("-fx-background-color: #ffffff;");

            // Mostra a scene (cena) contendo oroot layout.
            Scene scene = new Scene(rootLayout);
            scene.setFill(Color.TRANSPARENT);

            rootLayout.getTop().setOnMousePressed((event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            rootLayout.getTop().setOnMouseDragged((event) -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });

            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            // Dá ao controller o acesso ao main app.
            HomeController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o palco principal.
     * 
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}