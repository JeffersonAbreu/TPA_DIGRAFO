package br.edu.ifes.si.tpa.controller;

import java.io.File;

import br.edu.ifes.si.tpa.model.design.In;
import javafx.application.Platform;
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
    private TextField caminhoFile;

    @FXML
    private Label err, percent;

    @FXML
    private Button carregar, construirGrafico, buscar;

    @FXML
    private ProgressBar progressBar;

    private In in;
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
    private void actionBuscar(ActionEvent event) {
        err.setVisible(false);
        construirGrafico.setVisible(false);
        percent.setText("");
        buscar.setDisable(true);

        FileChooser fileChooser = new FileChooser();
        // Define um filtro de extensão
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostra a janela de salvar arquivo e iniciar no ditetorio raiz do app
        fileChooser.setTitle("Selecione o arquivo");
        fileChooser.setInitialDirectory(new File("_dados"));

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
    private void actionCarregar(ActionEvent event) {
        carregar.setDisable(true);
        in = new In(caminhoFile.getText());
        if (in.exists()) {
            startMyProgressBar();
        } else {
            err.setVisible(true);
        }
    }

    private void startMyProgressBar() {
        // Create a background Task
        In test = new In(in.getPathName());
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Set the total number of steps in our process
                int steps = 1000;//1000

                // Simulate a long running task
                for (int i = 0; i <= steps; i++) {

                    Thread.sleep(1); // Pause briefly

                    // Update our progress and message properties
                    updateProgress(i, steps);
                    updateMessage(i / 10 + "%");
                }

                int v = Integer.valueOf(test.readLine());// lendo vertices
                int a = Integer.valueOf(test.readLine());// lendo arestas
                int d = v + a;
                for (int i = 0; i < d; i++) {
                    test.readLine();
                }
                test.close();
                return null;
            }
        };

        // This method allows us to handle any Exceptions thrown by the task
        task.setOnFailed(wse -> {
            err.setVisible(true);
            in.close();
            wse.getSource().getException().printStackTrace();
            Platform.exit();
        });

        // If the task completed successfully, perform other updates here
        task.setOnSucceeded(wse -> {
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
    private void actionConstruirGrafico() {
        homeApp.toDashBoard(in.getPathName());
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
