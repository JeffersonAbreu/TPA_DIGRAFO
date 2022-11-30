package br.edu.ifes.si.tpa.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.edu.ifes.si.tpa.Main;
import br.edu.ifes.si.tpa.model.design.In;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController {
    boolean closeAtivo = false;

    @FXML
    Label path;

    @FXML
    private Button close, b0, b1, b2, b3, b4, b5;

    @FXML
    private BorderPane borderPane;

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
            "Número de citações de cada artigo",
            "Número de citações de cada autor",
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
    void actionAlgoritimoMenorQtdArtigosLidos(MouseEvent event) {
        if (validacao()) {
            dashBoard.actionAlgoritimoMenorQtdArtigosLidos();
        }
    }

    @FXML
    void actionAlgoritimoTodosCaminhos(MouseEvent event) {
        if (validacao()) {
            dashBoard.actionAlgoritimoTodosCaminhos();
        }
    }

    @FXML
    void actionAlgoritimoTopArtigos(MouseEvent event) {
        if (validacao()) {
            dashBoard.actionAlgoritimoTopArtigos();
        }
    }

    @FXML
    void actionAlgoritimoTopAutores(MouseEvent event) {
        if (validacao()) {
            dashBoard.actionAlgoritimoTopAutores();
        }
    }

    @FXML
    void acaoExpande(MouseEvent event) {
        menu.setPrefWidth(253);
        menu.setMinWidth(253);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(nomesMenu[i]);
        }
    }

    @FXML
    void acaoRecolhe(MouseEvent event) {
        menu.setPrefWidth(52);
        menu.setMinWidth(52);
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

    /**
     * 
     */
    @FXML
    void initialize() {
        path.setText("");
        buttons = Arrays.asList(b0, b1, b2, b3, b4, b5);
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

    /**
     * É chamado pela aplicação principal para referenciar a si mesma.
     * 
     * @param mainApp
     */
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
