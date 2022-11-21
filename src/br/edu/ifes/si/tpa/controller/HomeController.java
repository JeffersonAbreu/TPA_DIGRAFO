package br.edu.ifes.si.tpa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifes.si.tpa.Main;
import br.edu.ifes.si.tpa.model.design.Digrafo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {
    boolean closeAtivo = false;
    static Digrafo DIGRAFO = null;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private Button close;
    

    @FXML
    private BorderPane borderPane;

    @FXML
    void actionCarregaArquivo(MouseEvent event) {
        loadUI("carregaArquivo");
    }

    @FXML
    void actionHome(MouseEvent event) {
        loadUI("dashBoard");
    }

    @FXML
    void onClose(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closePrint(MouseEvent event) {
        if (!closeAtivo) {

        } else {
        }
        closeAtivo = !closeAtivo;
    }

    @FXML
    void initialize() {
        loadUI("dashBoard");
    }

    private void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("view/" + ui + ".fxml"));
            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeDash(Digrafo digrafo) {
        DIGRAFO = digrafo;
        this.actionHome(null);
    }
}
