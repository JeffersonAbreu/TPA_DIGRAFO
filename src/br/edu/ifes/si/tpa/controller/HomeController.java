package br.edu.ifes.si.tpa.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.ifes.si.tpa.Main;
import br.edu.ifes.si.tpa.model.design.In;
import br.edu.ifes.si.tpa.utils.MenorCaminho;
import br.edu.ifes.si.tpa.utils.TodosOsCaminhos;
import br.edu.ifes.si.tpa.utils.TopArtigos;
import br.edu.ifes.si.tpa.utils.TopAutores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController {
    boolean closeAtivo = false;

    @FXML
    private Label path, resultTitle, resultArea, lErro, lErro2;

    @FXML
    private Pane paneBusca, paneBusca2;

    @FXML
    private TextField tfDe, tfPara, tfDe2, tfPara2;

    @FXML
    private Button close, b0, b1, b2, b3, b4, b5;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane paneResult;

    @FXML
    private VBox menu;

    // Reference to the main application.
    private Main mainApp;
    private DashBoard dashBoard;

    private List<Button> buttons;
    private String[] nomesMenu = {
            "Home",
            "Menor caminho entre dois artigos",
            "Todos os caminhos entre dois artigos",
            "Top Artigos",
            "Top Autor",
            "Carregar Arquivo..."
    };

    @FXML
    void actionCarregaArquivo(MouseEvent event) {
        ((CarregaArquivoController) loadUI("carregaArquivo")).setHomeApp(this);
    }

    @FXML
    void actionHome(MouseEvent event) {
        if (validacao()) {
            dashBoard = (DashBoard) loadUI("dashBoard");
            dashBoard.start(new In(path.getText()));
        }
    }

    @FXML
    void actionAbrirPanelBusca(MouseEvent event) {
        if (validacao()) {
            dashBoard.descolorir();
            paneBusca2.setPrefHeight(0);
            paneBusca2.setVisible(false);
            paneBusca.setPrefHeight(125);
            paneBusca.setVisible(true);
            lErro.setText("");
            tfDe.setText("");
            tfPara.setText("");
        }
    }

    @FXML
    void actionAbrirPanelBusca2(MouseEvent event) {
        if (validacao()) {
            dashBoard.descolorir();
            paneBusca.setPrefHeight(0);
            paneBusca.setVisible(false);
            paneBusca2.setPrefHeight(125);
            paneBusca2.setVisible(true);
            lErro2.setText("");
            tfDe2.setText("");
            tfPara2.setText("");
        }
    }

    @FXML
    void actionMenorCaminho(MouseEvent event) {
        String text = validacaoPanel(tfDe, tfPara);
        if (text.isBlank()) {
            lErro.setText("");
            int origem = Integer.valueOf(tfDe.getText());
            int destino = Integer.valueOf(tfPara.getText());
            List<Integer> caminho = MenorCaminho.run(dashBoard.getDigrafo(), origem, destino);
            String retorno = String.format("%2d para %2d:  \n", origem, destino);
            String title = "Menor Caminho";
            acaoRecolhe();
            if (caminho.isEmpty()) {
                lErro.setText("Nenhum caminho encontrado!");
                retorno += "\nNenhum caminho\nencontrado!";
                paneResultExpande(title, retorno);
            } else {
                retorno += MenorCaminho.caminhoToString(caminho);
                paneResultExpande(title, retorno);
                // pinta o caminho e vertices
                dashBoard.colorir(caminho);
            }
        } else {
            lErro.setText(text);
        }
    }

    @FXML
    void actionTodosOsCaminhos(MouseEvent event) {
        String text = validacaoPanel(tfDe2, tfPara2);
        if (text.isBlank()) {
            lErro2.setText("");
            int origem = Integer.valueOf(tfDe2.getText());
            int destino = Integer.valueOf(tfPara2.getText());
            List<List<Integer>> caminhos = TodosOsCaminhos.run(dashBoard.getDigrafo(), origem, destino);
            String title = "Todos os Caminhos";
            String resultado = String.format("%2d para %2d:  \n", origem, destino);
            acaoRecolhe();
            if (caminhos.isEmpty()) {
                lErro2.setText("Nenhum caminho encontrado!");
                resultado += "\nNenhum caminho\n encontrado!";
                paneResultExpande(title, resultado);
            } else {
                for (List<Integer> caminho : caminhos) {
                    resultado += TodosOsCaminhos.caminhoToString(caminho) + "\n";
                }
                paneResultExpande(title, resultado);
                // Pintar caminho e vertices
                // for (List<Integer> caminho : caminhos) {
                // dashBoard.colorir(caminho);
                // }
                // Faz pausadamente
                List<Thread> tasks = new ArrayList<>();
                for (List<Integer> caminho : caminhos) {
                    tasks.add(new Thread(() -> dashBoard.colorir(caminho)));
                }
                new Thread(() -> {
                    long tempoDeEspera = 1000;
                    for (int i = 0; i < tasks.size(); i++) {
                        try {
                            Thread.sleep(tempoDeEspera);
                        } catch (Exception e) {
                        }
                        tempoDeEspera += i * 1000;
                        tasks.get(i).start();
                    }
                }).start();

            }
        } else {
            lErro2.setText(text);
        }
    }

    @FXML
    void actionTopArtigos(MouseEvent event) {
        dashBoard.descolorir();
        if (validacao()) {
            dashBoard.descolorir();
            String title = "Top Artigos";
            String retorno = TopArtigos.run(dashBoard.getDigrafo());
            paneResultExpande(title, retorno);
        }
    }

    @FXML
    void actionTopAutores(MouseEvent event) {
        dashBoard.descolorir();
        if (validacao()) {
            dashBoard.descolorir();
            String title = "Top Autores";
            String retorno = TopAutores.run(dashBoard.getDigrafo());
            paneResultExpande(title, retorno);
        }
    }

    private void paneResultExpande(String title, String retorno) {
        acaoRecolhe();
        resultTitle.setText(title);
        resultArea.setText(retorno);
        paneResult.setPrefWidth(200);
    }

    @FXML
    void acaoExpande() {
        menu.setPrefWidth(253);
        menu.setMinWidth(253);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(nomesMenu[i]);
        }
        paneResultRecolhe();
    }

    @FXML
    void acaoRecolhe() {
        menu.setPrefWidth(52);
        menu.setMinWidth(52);
        paneBusca.setPrefHeight(0);
        paneBusca.setVisible(false);
        paneBusca2.setPrefHeight(0);
        paneBusca2.setVisible(false);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText("");
        }
    }

    @FXML
    void onClose(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closePrint(MouseEvent event) {
        closeAtivo = !closeAtivo;
    }

    @FXML
    void initialize() {
        path.setText("");
        buttons = Arrays.asList(b0, b1, b2, b3, b4, b5);
        acaoRecolhe();
        paneResultRecolhe();
    }

    private void paneResultRecolhe() {
        paneResult.setPrefWidth(0);
    }

    private String validacaoPanel(TextField de, TextField para) {
        String err = "";
        int id;
        try {
            id = Integer.parseInt(de.getText());
            dashBoard.getDigrafo().getVertice(id);
        } catch (Exception e) {
            err = "Origem: inválido!";
            return err;
        }
        try {
            id = Integer.parseInt(para.getText());
            dashBoard.getDigrafo().getVertice(id);
        } catch (Exception e) {
            err = "Destino: inválido!";
            return err;
        }
        return err;
    }

    /**
     * @param ui
     */
    private Object loadUI(String ui) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("./view/" + ui + ".fxml"));
        try {
            borderPane.setCenter((BorderPane) loader.load());
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toDashBoard(String in) {
        path.setText(in);
        actionHome(null);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public Main getMainApp() {
        return mainApp;
    }

    private boolean validacao() {
        if (path.getText().equals("")) {
            Text t = new Text();
            t.setText("ERRO\nSelecione um arquivo de digrafo!");
            t.setFont(Font.font("Arial Black", 24));
            t.setFill(Color.RED);
            borderPane.setCenter(t);
            borderPane.setStyle("-fx-background-color: #ffffff;");
            return false;
        }
        return true;
    }
}
