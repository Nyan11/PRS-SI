package prs.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prs.client.connect.Communicator;
import prs.client.control.Controller;
import prs.client.view.ViewLogin;

/*


(space add canva (rect.class new))
(space.canva setDim 500 500)
(space.canva add robi (rect.class new))
(space.canva.robi translate 130 50)
(space.canva.robi setColor yellow)
(space add momo (oval.class new))
(space.momo setColor red)
(space.momo translate 80 80)
(space add pif (image.class new alien.gif))
(space.pif translate 100 0)
(space add hello (label.class new "Hello world"))
(space.hello translate 10 10)
(space.hello setColor black)
(space sleep 1000)
(space del canva)






 */

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
    	Controller control = new Controller(stage);
        //Scene scene = new Scene(new ViewGlobal(control, "hello"));
    	//Scene scene = new Scene(new AboutView());
        Scene scene = new Scene(new ViewLogin(control), 640, 480);
        stage.setOnCloseRequest(e -> {
        	try {
        	Communicator.sendQuit();
        	}
        	catch(Exception ex) {}
        });
    	stage.setTitle("Projet L3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}