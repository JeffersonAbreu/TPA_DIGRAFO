package br.edu.ifes.si.tpa.controller;

import java.io.File;
import java.io.IOException;

import br.edu.ifes.si.tpa.Main;
import br.edu.ifes.si.tpa.model.design.In;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {
    boolean closeAtivo = false;

    @FXML
    private Button close;

    @FXML
    private BorderPane borderPane;

    // Reference to the main application.
    private Main mainApp;

    @FXML
    void actionCarregaArquivo(MouseEvent event) {
        ((CarregaArquivoController) loadUI("carregaArquivo")).setHomeApp(this);
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
        closeAtivo = !closeAtivo;
    }

    @FXML
    void initialize() {
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

    public void toDashBoard(In in) {
        ((DashBoard) loadUI("dashBoard")).start(in);
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
}
