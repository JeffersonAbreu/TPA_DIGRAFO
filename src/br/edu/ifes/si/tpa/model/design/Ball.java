package br.edu.ifes.si.tpa.model.design;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Ball extends Circle {
   private TextFlow token;

   double origenSceneX, origenSceneY;
   double origenTranslateX, origenTranslateY;

   private final int ID;

   // Define a variable to store the property
   private IntegerProperty x;
   private IntegerProperty y;
   private BooleanProperty selected = new SimpleBooleanProperty(false);

   // Define a getter for the property's value
   public final int getX() {
      return x.get();
   }

   public final int getY() {
      return y.get();
   }

   // Define a setter for the property's value
   public final void setX(int value) {
      x.set(value);
   }

   public final void setY(int value) {
      y.set(value);
   }

   // Define a getter for the property itself
   public IntegerProperty xProperty() {
      return x;
   }

   public IntegerProperty yProperty() {
      return y;
   }

   public int getID() {
      return ID;
   }

   public static int RAIO() {
      return 22;
   }

   public Ball(int posX, int posY, int ID) {
      super(RAIO(), Color.RED);
      super.setCursor(Cursor.MOVE);
      super.setCenterX(posX);
      super.setCenterY(posY);
      this.ID = ID;

      criaTexto();
      x = new SimpleIntegerProperty(posX);
      y = new SimpleIntegerProperty(posY);

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

         int pX = (int) (this.getCenterX() + newTranslateX);
         int pY = (int) (this.getCenterY() + newTranslateY);
         AnchorPane a = (AnchorPane) this.getParent();
         int maxWidth = (int) a.getMinWidth() - RAIO();
         int maxHeight = (int) a.getMinHeight() - RAIO();

         if (pX > RAIO() && pY > RAIO() && pX < maxWidth && pY < maxHeight) {
            ((Ball) (t.getSource())).setTranslateX(newTranslateX);
            ((Ball) (t.getSource())).setTranslateY(newTranslateY);
            // token update
            this.token.setLayoutX(newTranslateX);
            this.token.setLayoutY(newTranslateY);

            // property
            setX(pX);
            setY(pY);
         }
      });
      token.setOnMouseClicked((t) -> {
         selected.set(!selected.get());
      });

      selected.addListener((observable, oldvalue, newvalue) -> {
         if (selected.get()) {
            this.setStroke(Color.LIMEGREEN);
            this.setStrokeWidth(3);
         } else {
            this.setStroke(Color.TRANSPARENT);
            this.setStrokeWidth(0);
         }
      });
   }

   private void criaTexto() {
      Text text = new Text(String.valueOf(ID));
      text.setFont(Font.font("Arial Black", 26));
      text.setFill(Color.BLACK);
      token = new TextFlow(text);
      if (ID >= 10)
         token.setTranslateX(this.getCenterX() - 17.8f);
      else
         token.setTranslateX(this.getCenterX() - 9.0f);
      token.setTranslateY(this.getCenterY() - 18.5f);
   }

   public TextFlow getTexto() {
      return token;
   }

   public void toFront() {
      super.toFront();
      this.token.toFront();
   }

   public void setSelected(boolean value) {
      selected.set(value);
   }

   public BooleanProperty selectedProperty() {
      return selected;
   }
}