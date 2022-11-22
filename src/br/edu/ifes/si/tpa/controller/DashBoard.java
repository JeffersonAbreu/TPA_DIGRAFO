package br.edu.ifes.si.tpa.controller;

import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import br.edu.ifes.si.tpa.model.design.Ball;
import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.Vertice;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class DashBoard {
    private HomeController homeApp;
    private Digrafo digrafo;
    private int width = 680;
    private int height = 580;

    @FXML
    private ButtonBar optionsBar;

    @FXML
    AnchorPane root;

    @FXML
    void initialize() {
        this.optionsBar.setVisible(false);
        if (HomeController.DIGRAFO != null) {
            this.optionsBar.setVisible(true);

            this.digrafo = HomeController.DIGRAFO;
            // Create a background Task
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(300); // Pause briefly
                    return null;
                }
            };
            task.setOnFailed(wse -> {
                wse.getSource().getException().printStackTrace();
            });
            task.setOnSucceeded(wse -> {
                load(true);
            });
            new Thread(task).start();
        }
    }

    private void load(boolean aleatoria) {
        Random random = new Random();
        int posY = 0, posX = 0;
        // digrafo.getVertices();
        for (int i = 0; i < digrafo.getVertices().size(); i++) {
            // posição aleatoria para a bola iniciar
            if (aleatoria) {
                posY = random.nextInt(height / 2) + random.nextInt(height / 2);
                posX = random.nextInt(width / 2) + random.nextInt(width / 2);
            }
            Vertice vertice = digrafo.getVertices().get(i);
            // Criando
            Ball ball = new Ball(posX, posY, vertice.getID());
            ball.getStyleClass().add("cor_" + vertice.getAutor().getID());
            root.getChildren().addAll(ball, ((Ball) ball).getToken());
        }
    }

    @FXML
    void actionSalvePositions(ActionEvent event) {
    }

    @FXML
    void actionSalveToImage(ActionEvent event) {
        try {
            Image snapshot = root.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    @FXML
    void actionSortAleatory(ActionEvent event) {
        root.getChildren().clear();
        load(true);
    }

    /**
     * É chamado pela aplicação principal para referenciar a si mesma.
     * 
     * @param homeApp
     */
    public void setHomeApp(HomeController homeApp) {
        this.homeApp = homeApp;
    }
}
