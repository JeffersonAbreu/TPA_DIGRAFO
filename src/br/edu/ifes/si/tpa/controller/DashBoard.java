package br.edu.ifes.si.tpa.controller;

import java.util.List;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.Grafico;
import br.edu.ifes.si.tpa.model.design.In;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;

public class DashBoard {
    @FXML
    private ButtonBar optionsBar;

    @FXML
    private AnchorPane painel;

    private Grafico graficoDesenho;

    @FXML
    void initialize() {
        this.optionsBar.setVisible(false);
    }

    @FXML
    void actionSalvePositions(ActionEvent event) {
        graficoDesenho.salvaLocalizacoes();
    }

    @FXML
    void actionSalveToImage(ActionEvent event) {
        graficoDesenho.salvaPrint();
    }

    public void start(In in) {
        graficoDesenho = new Grafico(painel, in);
        this.optionsBar.setVisible(true);
    }

    public Digrafo getDigrafo() {
        return graficoDesenho.getDigrafo();
    }

    public void colorir(int origem, int destino, List<Integer> caminhos) {
        graficoDesenho.colorir(origem, destino, caminhos);
    }

    public void descolorir() {
        graficoDesenho.descolorir();
    }
}
