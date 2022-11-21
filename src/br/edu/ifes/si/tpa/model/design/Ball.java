package br.edu.ifes.si.tpa.model.design;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Ball extends Circle {
   private double radius = 24.0f;
   private double size;
   private Text token;

   double orgSceneX, orgSceneY;
   double orgTranslateX, orgTranslateY;

   public Ball(int posX, int posY, String value) {
      super(22.0f, Color.RED);
      super.setCursor(Cursor.MOVE);
      super.setCenterX(posX);
      super.setCenterY(posY);

      this.size = 24.0f; // colocar valor multiplo de 8
      this.radius = this.size / 2;
      double tX = 0, tY = posY + (radius / 2) + 1;
      tX = value.length() == 1 ? posX - radius + 2 : posX + 3 - radius * 2;
      this.token = new Text(tX, tY, value);
      this.token.getStyleClass().add("token");

      this.setOnMousePressed(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Ball) (t.getSource())).getTranslateX();
            orgTranslateY = ((Ball) (t.getSource())).getTranslateY();

            ((Ball) (t.getSource())).toFront();
            ((Text) token).toFront();
         }
      });

      this.setOnMouseDragged(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((Ball) (t.getSource())).setTranslateX(newTranslateX);
            ((Ball) (t.getSource())).setTranslateY(newTranslateY);

            // token update
            token.setLayoutX(newTranslateX);
            token.setLayoutY(newTranslateY);
         }
      });

      this.setOnMouseReleased(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent t) {
            // token.setVisible(true);
         }
      });
   }

   public Text getToken() {
      return token;
   }

   LinearGradient _gradient_100 = new LinearGradient(
         0.0, 0.2114, 0.817, 0.8517, true, CycleMethod.REFLECT,
         new Stop(0.0, new Color(1.0, 0.0, 0.0, 1.0)),
         new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
   LinearGradient _gradient_75 = new LinearGradient(
         0.0, 0.2114, 0.817, 0.8517, true, CycleMethod.REFLECT,
         new Stop(0.0, new Color(1.0, 0.0, 0.0, 1.0)),
         new Stop(1.0, new Color(1.0, 0.0, 0.0, 0.5)));
   LinearGradient _gradient_50 = new LinearGradient(
         0.0, 0.2114, 0.817, 0.8517, true, CycleMethod.REFLECT,
         new Stop(0.0, new Color(1.0, 0.0, 0.0, 1.0)),
         new Stop(1.0, new Color(1.0, 0.0, 0.0, 0.0)));
   LinearGradient _gradient_25 = new LinearGradient(
         0.0, 0.2114, 0.817, 0.8517, true, CycleMethod.REFLECT,
         new Stop(0.0, new Color(1.0, 0.0, 0.0, 0.5)),
         new Stop(1.0, new Color(1.0, 0.0, 0.0, 0.0)));
   LinearGradient _gradient_00 = new LinearGradient(
         0.0, 0.2114, 0.817, 0.8517, true, CycleMethod.REFLECT,
         new Stop(0.0, new Color(1.0, 0.0, 0.0, 0.0)),
         new Stop(1.0, new Color(1.0, 0.0, 0.0, 0.0)));
}