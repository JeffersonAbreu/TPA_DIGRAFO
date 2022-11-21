package br.edu.ifes.si.tpa.model.design;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Ball extends Circle {
   private TextFlow token;

   double origenSceneX, origenSceneY;
   double origenTranslateX, origenTranslateY;

   public Ball(int posX, int posY, String ID) {
      super(22.0f, Color.RED);
      super.setCursor(Cursor.MOVE);
      super.setCenterX(posX);
      super.setCenterY(posY);
      setId(ID);
      createToken();

      this.setOnMousePressed((t) -> {
         origenSceneX = t.getSceneX();
         origenSceneY = t.getSceneY();

         origenTranslateX = ((Ball) (t.getSource())).getTranslateX();
         origenTranslateY = ((Ball) (t.getSource())).getTranslateY();

         ((Ball) (t.getSource())).toFront();
         ((TextFlow) (token)).toFront();
      });

      this.setOnMouseDragged((t) -> {
         double offsetX = t.getSceneX() - origenSceneX;
         double offsetY = t.getSceneY() - origenSceneY;
         double newTranslateX = origenTranslateX + offsetX;
         double newTranslateY = origenTranslateY + offsetY;

         ((Ball) (t.getSource())).setTranslateX(newTranslateX);
         ((Ball) (t.getSource())).setTranslateY(newTranslateY);

         // token update
         this.token.setLayoutX(newTranslateX);
         this.token.setLayoutY(newTranslateY);
      });
   }

   private void createToken() {
      Text text = new Text(getId());
      text.setFont(Font.font("Arial Black", 26));
      text.setFill(Color.BLACK);
      token = new TextFlow(text);
      if (getId().length() == 1)
         token.setTranslateX(this.getCenterX() - 8.5f);
      else
         token.setTranslateX(this.getCenterX() - 17.0f);
      token.setTranslateY(this.getCenterY() - 17.0f);
   }

   public TextFlow getToken() {
      return token;
   }
/* 
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
*/
   
}