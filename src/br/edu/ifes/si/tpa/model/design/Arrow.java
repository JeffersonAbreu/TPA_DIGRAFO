package br.edu.ifes.si.tpa.model.design;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Arrow extends Group {

    private Color color = Color.BLACK;
    private double largura = 2.0f;

    private Ball orin; // vetice origem
    private Ball dest; // vertice destino
    private double peso; // Peso
    private Line linha;
    private Polygon triangle;

    private BooleanProperty selected = new SimpleBooleanProperty(false);
    // Define a variable property (bind)
    private IntegerProperty origemX;
    private IntegerProperty origemY;
    private IntegerProperty destinoX;
    private IntegerProperty destinoY;

    public Arrow(Ball origem, Ball destino, double peso) {
        this.orin = origem;
        this.dest = destino;
        this.peso = peso;
        origemX = new SimpleIntegerProperty();
        origemY = new SimpleIntegerProperty();
        destinoX = new SimpleIntegerProperty();
        destinoY = new SimpleIntegerProperty();
        conecta();
        direcionaFlecha();
        if (peso > 0)
            mostraPeso();

        orin.selectedProperty().addListener((observable, oldvalue, newvalue) -> {
            selected.set(!selected.get());
        });

        selected.addListener((observable, oldvalue, newvalue) -> {
            if (selected.get()) {
                linha.setStroke(Color.RED);
                linha.setStrokeWidth(3);
                linha.setOpacity(1);
                triangle.setFill(Color.RED);
                linha.toFront();
                triangle.toFront();
            } else {
                linha.setStroke(color);
                linha.setStrokeWidth(largura);
                linha.setOpacity(0.5);
                triangle.setFill(color);
            }
        });
        linha.setOpacity(0.5);
        triangle.toFront();
    }

    public Arrow(Ball origem, Ball destino) {
        this(origem, destino, 0);
    }

    public void mostraPeso() {
        TextFlow weight = new TextFlow();
        weight.setLayoutX((linha.startXProperty().doubleValue() + linha.endXProperty().doubleValue()) / 2);
        weight.setLayoutY((linha.startYProperty().doubleValue() + linha.endYProperty().doubleValue()) / 2);
        Text text = new Text(Double.toString(this.peso));
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        weight.getChildren().addAll(text);
        this.getChildren().add(weight);

        linha.endXProperty().addListener((observable, oldvalue, newvalue) -> {
            weight.setLayoutX((linha.startXProperty().get() + newvalue.doubleValue()) / 2);
        });

        linha.endYProperty().addListener((observable, oldvalue, newvalue) -> {
            weight.setLayoutY((linha.startYProperty().get() + newvalue.doubleValue()) / 2);

        });

        linha.startXProperty().addListener((observable, oldvalue, newvalue) -> {
            weight.setLayoutX((newvalue.doubleValue() + linha.endXProperty().get()) / 2);
        });

        linha.startYProperty().addListener((observable, oldvalue, newvalue) -> {
            weight.setLayoutY((newvalue.doubleValue() + linha.endYProperty().get()) / 2);
        });
    }

    public void conecta() {

        atualizaPontoOrigemDestino();
        orin.xProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontoOrigemDestino();
        });
        orin.yProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontoOrigemDestino();
        });
        dest.xProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontoOrigemDestino();
        });
        dest.yProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontoOrigemDestino();
        });
        // =================================================================
        linha = new Line();
        linha.setStrokeWidth(largura);

        linha.startXProperty().bind(origemX);
        linha.startYProperty().bind(origemY);

        linha.endXProperty().bind(destinoX);
        linha.endYProperty().bind(destinoY);

        linha.toBack();
        orin.toFront();
        dest.toFront();
        this.getChildren().add(linha);
    }

    private void atualizaPontoOrigemDestino() {
        double orinX = orin.getX();
        double destX = dest.getX();
        double orinY = orin.getY();
        double destY = dest.getY();
        float r = (float) Math.sqrt(Math.pow(orinX - destX, 2) + Math.pow(orinY - destY, 2));
        float cos = (float) ((destX - orinX) / r);
        float sen = (float) ((destY - orinY) / r);

        int deslocamento = Ball.RAIO() + 2;

        origemX.set((int) (orinX + Math.round(deslocamento * cos)));
        origemY.set((int) (orinY + Math.round(deslocamento * sen)));

        destinoX.set((int) (destX + Math.round(deslocamento * -cos)));
        destinoY.set((int) (destY + Math.round(deslocamento * -sen)));
    }

    protected void atualizaPontaDaFlecha() {
        int size = 5;
        int deslocamento = Ball.RAIO() + 1;

        double orinX = orin.getX();
        double destX = dest.getX();
        double orinY = orin.getY();
        double destY = dest.getY();
        float r = (float) Math.sqrt(Math.pow(orinX - destX, 2) + Math.pow(orinY - destY, 2));
        float cos = (float) ((destX - orinX) / r);
        float sen = (float) ((destY - orinY) / r);

        int xAB = size + deslocamento;
        int yA = size;
        int yB = -size;

        double p1X = destX + Math.round((deslocamento - 2) * -cos);
        double p1Y = destY + Math.round((deslocamento - 2) * -sen);
        double p2X = destX + Math.round(xAB * -cos - yA * -sen);
        double p2Y = destY + Math.round(xAB * -sen + yA * -cos);
        double p3X = destX + Math.round(xAB * -cos - yB * -sen);
        double p3Y = destY + Math.round(xAB * -sen + yB * -cos);

        triangle.getPoints().clear();
        triangle.getPoints().setAll(p1X, p1Y, p2X, p2Y, p3X, p3Y);
    }

    public void direcionaFlecha() {
        triangle = new Polygon();
        triangle.setFill(color);
        triangle.toFront();
        this.getChildren().add(triangle);

        linha.endXProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontaDaFlecha();
        });

        linha.endYProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontaDaFlecha();
        });

        linha.startXProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontaDaFlecha();
        });

        linha.startYProperty().addListener((observable, oldvalue, newvalue) -> {
            atualizaPontaDaFlecha();
        });

        atualizaPontaDaFlecha();
    }

}
