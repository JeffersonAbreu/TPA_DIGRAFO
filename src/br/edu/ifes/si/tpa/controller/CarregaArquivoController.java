package br.edu.ifes.si.tpa.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.util.Arquivo;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CarregaArquivoController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TextField caminhoFile;

    @FXML
    private Label err, percent;

    @FXML
    private Button carregar, construirGrafico;

    @FXML
    private ProgressBar progressBar;

    double progress = 0.0f;

    private Digrafo digrafo;
    private HomeController homeApp;

    @FXML
    public void initialize() {
        err.setText("Erro ao ler o arquivo.");
        err.setVisible(false);
        percent.setVisible(false);
        progressBar.setVisible(false);
        construirGrafico.setVisible(false);
        progressBar.setStyle("--fx-accent: #00FF00;");
    }

    @FXML
    void actionBuscar(ActionEvent event) {
        err.setVisible(false);
        progress = ProgressBar.INDETERMINATE_PROGRESS;
        progressBar.setProgress(progress);
        construirGrafico.setVisible(false);
        percent.setText("0%");

        FileChooser fileChooser = new FileChooser();
        // Define um filtro de extensão
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostra a janela de salvar arquivo e iniciar no ditetorio raiz do app
        fileChooser.setTitle("Selecione o arquivo");
        fileChooser.setInitialDirectory(new File("./"));

        File file = fileChooser.showOpenDialog(homeApp.getMainApp().getPrimaryStage());
        if (file != null) {
            caminhoFile.setText(file.getAbsolutePath());
            carregar.setDisable(false);
            percent.setVisible(true);
            progressBar.setVisible(true);
        } else {
            carregar.setDisable(true);
        }
    }

    @FXML
    void actionCarregar(ActionEvent event) {
        carregar.setDisable(true);
        Digrafo digrafo = Arquivo.lerDigrafo(caminhoFile.getText());
        if (digrafo == null) {
            err.setVisible(true);
        } else {
            startMyProgressBar();
        }
        this.digrafo = digrafo;
    }

    void startMyProgressBar() {
        // Create a background Task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // Set the total number of steps in our process
                int steps = 1000;

                // Simulate a long running task
                for (int i = 0; i <= steps; i++) {

                    Thread.sleep(3); // Pause briefly

                    // Update our progress and message properties
                    updateProgress(i, steps);
                    updateMessage(i / 10 + "%");
                }
                return null;
            }
        };

        // This method allows us to handle any Exceptions thrown by the task
        task.setOnFailed(wse -> {
            wse.getSource().getException().printStackTrace();
        });

        // If the task completed successfully, perform other updates here
        task.setOnSucceeded(wse -> {
            caminhoFile.setText("");
            System.out.println("Carregouuuuuuuu ! ! ! ! !\n");
            System.out.println("  _____[]_____");
            System.out.println(" /\\           \\");
            System.out.println("/  \\___________\\");
            System.out.println("| _ | [] [] [] |");
            System.out.println("|[ ]|__________|");
            construirGrafico.setVisible(true);
            construirGrafico.setDefaultButton(true);
            construirGrafico.setDisable(false);
        });

        // Before starting our task, we need to bind our UI values to the properties on
        // the task
        progressBar.progressProperty().bind(task.progressProperty());
        percent.textProperty().bind(task.messageProperty());

        // Now, start the task on a background thread
        new Thread(task).start();
    }

    @FXML
    void actionConstruirGrafico() {
        homeApp.initializeDash(digrafo);
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
