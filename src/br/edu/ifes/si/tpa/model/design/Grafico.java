package br.edu.ifes.si.tpa.model.design;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import br.edu.ifes.si.tpa.model.domain.Aresta;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Grafico {
    private AnchorPane myPanel;
    private ArrayList<Arrow> listLines = new ArrayList<Arrow>();
    private Ball[] listBalls;

    private String pathINI, pathPNG;
    private Digrafo digrafo;

    public Grafico(AnchorPane painel, In in) {
        myPanel = painel;
        construirDesenho(in);
        loadMyPanel(digrafo.nVertices(), digrafo.nArestas());
    }

    public Digrafo getDigrafo() {
        return digrafo;
    }

    public void construirDesenho(In in) {
        // guardando o caminho do arquivo
        pathINI = in.getPathName().replace(".txt", ".ini");
        pathPNG = in.getPathName().replace(".txt", "_print.png");
        digrafo = new Digrafo(in);
    }

    private void loadMyPanel(int nV, int nA) {
        loadBalls(nV);
        loadLines();
        Platform.runLater(() -> {
            myPanel.getChildren().clear();
            for (Arrow line : listLines) {
                myPanel.getChildren().add(line);
            }
            for (Ball ball : listBalls) {
                myPanel.getChildren().add(ball);
                myPanel.getChildren().add(ball.getTexto());
            }
        });
    }

    private void loadLines() {
        for (Vertice v : digrafo.getListVertices()) {
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
        Vertice vertice = digrafo.getVertice(id);
        Ball ball = new Ball(posX, posY, vertice.getID());
        ball.getStyleClass().add("cor_" + vertice.getAutor().getID());
        listBalls[id] = ball;
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

    public void descolorir() {
        Platform.runLater(() -> {
            for (Ball ball : listBalls) {
                ball.apaga();
            }
            for (Arrow arrow : listLines) {
                arrow.apaga();
            }
        });
    }

    public void colorir(List<Integer> caminho) {
        List<Thread> tasks = new ArrayList<>();
        int origem = caminho.get(0);
        int destino = 0;
        Ball origemBall = getBall(origem);
        // acende: vertice de origem
        Platform.runLater(() -> origemBall.acende());
        caminho.remove(0);

        // Arestas
        for (Integer i : caminho) {
            destino = i;
            for (Arrow arrow : listLines) {
                if (arrow.isConection(origem, destino)) {
                    Thread t = new Thread(() -> Platform.runLater(() -> arrow.acende()));
                    tasks.add(t);
                }
            }
            origem = destino;
        }
        new Thread(() -> {
            for (Thread thread : tasks) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread.start();
            }
        }).start();

        // acende: vertice de destino
        Ball destinoBall = getBall(destino);
        Platform.runLater(() -> destinoBall.acende());
    }

    private Ball getBall(int id) {
        for (Ball ball : listBalls) {
            if (ball.getID() == id) {
                return ball;
            }
        }
        return null;
    }
}
