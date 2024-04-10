package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;   
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Foothill extends Application {
   @Override
   public void start(Stage primaryStage) {
      try {
         StackPane root = new StackPane();
         Scene scene = new Scene(root,400,400);
         
         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
         root.getChildren().add(new Button("OK"));
         primaryStage.setScene(scene);
         primaryStage.show();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void main(String[] args) {
      launch(args);
   }
}
