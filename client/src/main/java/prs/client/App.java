package prs.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import prs.client.control.Controller;
import prs.client.view.ViewGlobal;
import prs.client.view.ViewLogin;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
    	Controller control = new Controller(stage);
        //Scene scene = new Scene(new ViewGlobal());
        
        Scene scene = new Scene(new ViewLogin(control), 640, 480);
        stage.setTitle("Projet L3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}