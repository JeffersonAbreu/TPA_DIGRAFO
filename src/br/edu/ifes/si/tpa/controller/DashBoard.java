package br.edu.ifes.si.tpa.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import br.edu.ifes.si.tpa.model.design.Ball;
import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;
import br.edu.ifes.si.tpa.model.design.Out;
import br.edu.ifes.si.tpa.model.design.Vertice;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class DashBoard {
    private Digrafo digrafo;
    private int width = 680;
    private int height = 580;
    private List<String> listPositions;
    private String pathOutFile;

    @FXML
    private ButtonBar optionsBar;

    @FXML
    AnchorPane root;
    private List<Ball> listBall;

    @FXML
    void initialize() {
        this.optionsBar.setVisible(false);
        System.out.println(HomeController.class.getSimpleName() + " >> " + "initialize()");
    }

    public void start(In in) {
        System.out.println(HomeController.class.getSimpleName() + " >> " + "start(In in)");
        int vertices = Integer.parseInt(in.readLine());
        int arestas = Integer.parseInt(in.readLine());

        digrafo = new Digrafo(vertices);

        for (int i = 0; i < vertices; i++) {
            String a = in.readLine();
            StringTokenizer st = new StringTokenizer(a, " ");
            String vertice = st.nextToken().trim(); // verticeInicial
            int donoDoVertice = Integer.parseInt(st.nextToken().trim()); // verticeFinal
            digrafo.addVertice(vertice, donoDoVertice);
        }

        // System.out.println("\nArestas: " + arestas);
        for (int i = 0; i < arestas; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int de = Integer.parseInt(st.nextToken().trim()); // arestaInicial
            int para = Integer.parseInt(st.nextToken().trim()); // arestaFinal
            // System.out.println(i + 1 + " : " + de + " - " + para);
            digrafo.addAresta(de, para);
        }

        listBall = new ArrayList<>(vertices);
        pathOutFile = in.getPathName().replace(".txt", ".ini");
        In inPositions = new In(pathOutFile);
        listPositions = new ArrayList<String>();
        String line;
        if (inPositions.exists()) {
            for (int i = 0; i < vertices; i++) {
                line = inPositions.readLine();
                listPositions.add(line);
            }
            load(false);
            inPositions.close();
        } else {
            load(true);
        }
        this.optionsBar.setVisible(true);
        in.close();
    }

    private void load(boolean aleatoria) {
        System.out.println(HomeController.class.getSimpleName() + " >> " + "load(boolean)");
        Random random = new Random();
        int posY = 0, posX = 0;
        for (int i = 0; i < digrafo.getVertices().size(); i++) {
            // posição aleatoria para a bola iniciar
            if (aleatoria) {
                posY = random.nextInt(height / 2) + random.nextInt(height / 2);
                posX = random.nextInt(width / 2) + random.nextInt(width / 2);
            } else {
                String a = listPositions.get(i);
                StringTokenizer st = new StringTokenizer(a, ":");
                posX = Integer.parseInt(st.nextToken().trim()); // X
                posY = Integer.parseInt(st.nextToken().trim()); // Y
            }
            Vertice vertice = digrafo.getVertices().get(i);
            // Criando
            Ball ball = new Ball(posX, posY, vertice.getID());
            ball.getStyleClass().add("cor_" + vertice.getAutor().getID());
            root.getChildren().addAll(ball, ((Ball) ball).getToken());
            listBall.add(ball);
        }
    }

    /**
     * @param event
     */
    @FXML
    void actionSalvePositions(ActionEvent event) {
        Out out = new Out(pathOutFile);
        for (Ball ball : listBall) {
            System.out.println(ball.getPositionX() + ":" + ball.getPositionY());
            out.writeLine(ball.getPositionX() + ":" + ball.getPositionY());
        }
        out.close();
        System.out.println();
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
}
