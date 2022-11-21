package br.edu.ifes.si.tpa.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifes.si.tpa.Main;
import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.util.Arquivo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    // Reference to the main application.
    private Main mainApp;

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
        String path = new File("./Digrafo1.txt").getAbsolutePath();
        initializeDash(Arquivo.lerDigrafo(path));
    }

    /**
     * @param ui
     */
    private void loadUI(String ui) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("./view/" + ui + ".fxml"));
        try {
            borderPane.setCenter((BorderPane) loader.load());
            if (ui.equals("carregaArquivo")) {
                CarregaArquivoController controller = loader.getController();
                controller.setHomeApp(this);
            } else if (ui.equals("dashBoard")) {
                DashBoard controller = loader.getController();
                controller.setHomeApp(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeDash(Digrafo digrafo) {
        DIGRAFO = digrafo;
        this.actionHome(null);
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
