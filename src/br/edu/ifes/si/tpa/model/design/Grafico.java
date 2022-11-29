package br.edu.ifes.si.tpa.model.design;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Grafico {
    private AnchorPane myPanel;

    private HashMap<Integer, Autor> autores = new HashMap<>();
    private ArrayList<Arrow> listLines = new ArrayList<Arrow>();
    private Ball[] listBalls;
    private Vertice[] listVertices;

    private String pathINI, pathPNG;

    public Grafico(AnchorPane painel, In in) {
        myPanel = painel;
        construirDesenho(in);
    }

    private void construirDesenho(In in) {
        // guardando o caminho do arquivo
        pathINI = in.getPathName().replace(".txt", ".ini");
        pathPNG = in.getPathName().replace(".txt", "_print.png");

        int nVertices = Integer.parseInt(in.readLine());
        int nArestas = Integer.parseInt(in.readLine());
        listVertices = new Vertice[nVertices];

        // lendo vertices e arestas do arquivo
        for (int i = 0; i < nVertices; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int vertice = Integer.parseInt(st.nextToken().trim()); // verticeInicial
            int autor = Integer.parseInt(st.nextToken().trim()); // verticeFinal
            listVertices[i] = new Vertice(vertice, getAutor(autor));
        }

        // System.out.println("\nArestas: " + arestas);
        for (int i = 0; i < nArestas; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int orig = Integer.parseInt(st.nextToken().trim()); // arestaInicial
            int dest = Integer.parseInt(st.nextToken().trim()); // arestaFinal
            listVertices[orig].addAdj(new Aresta(orig, dest));
        }

        in.close();
        loadMyPanel(nVertices, nArestas);
    }

    // =================================================================================

    private void loadMyPanel(int nV, int nA) {
        loadBalls(nV);
        loadLines();
        myPanel.getChildren().clear();
        for (Arrow line : listLines) {
            myPanel.getChildren().add(line);
        }
        for (Ball ball : listBalls) {
            myPanel.getChildren().add(ball);
            myPanel.getChildren().add(ball.getTexto());
        }
    }

    private void loadLines() {
        for (Vertice v : listVertices) {
            for (Aresta a : v.getAllAdj()) {
                Ball orin = listBalls[a.getV1()];
                Ball dest = listBalls[a.getV2()];
                Arrow line = new Arrow(orin, dest);
                listLines.add(line);
            }
        }
    }

    private void loadBalls(int n) {
        listBalls = new Ball[n];
        Random random = new Random();
        boolean aleatoria = false;
        int ID, posY, posX;
        try {
            In ini = new In(pathINI);
            if (ini.exists()) {
                for (int i = 0; i < n; i++) {
                    // lendo a linha do arquivo com as posicoes
                    StringTokenizer st = new StringTokenizer(ini.readLine(), ":");
                    ID = Integer.parseInt(st.nextToken().trim());
                    posX = Integer.parseInt(st.nextToken().trim()); // X
                    posY = Integer.parseInt(st.nextToken().trim()); // Y
                    // Criando
                    createBall(ID, posX, posY);
                }
                ini.close();
            } else {
                aleatoria = true;
            }
        } catch (Exception e) {
            aleatoria = true;
        }

        // se erro na leitura ou não existe
        if (aleatoria) {
            for (int id = 0; id < n; id++) {
                // posição aleatoria para a bola iniciar
                posX = random.nextInt(Ball.RAIO(), (int) myPanel.getMinWidth() - Ball.RAIO());
                posY = random.nextInt(Ball.RAIO(), (int) myPanel.getMinHeight() - Ball.RAIO());
                createBall(id, posX, posY);
            }
        }
    }

    private void createBall(int id, int posX, int posY) {
        // Criando
        Vertice vertice = listVertices[id];
        Ball ball = new Ball(posX, posY, vertice.getID());
        ball.getStyleClass().add("cor_" + vertice.getAutor().getID());
        listBalls[id] = ball;
    }

    // metodos publicos
    public Autor getAutor(int ID) {
        if (!autores.containsKey((Integer) ID)) {
            autores.put((Integer) ID, new Autor(ID));
        }
        return autores.get((Integer) ID);
    }

    public List<Vertice> getVertices() {
        return Arrays.asList(listVertices);
    }

    // =============== Acoes da tela principal ====================================
    public void salvaPrint() {
        try {
            Image snapshot = myPanel.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File(pathPNG));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void salvaLocalizacoes() {
        Out out = new Out(pathINI);
        for (Ball ball : listBalls) {
            out.writeLine(ball.getID() + ":" + ball.getX() + ":" + ball.getY());
        }
        out.close();
        System.out.println();
    }
}