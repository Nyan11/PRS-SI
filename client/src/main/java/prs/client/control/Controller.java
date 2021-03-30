package prs.client.control;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import prs.client.connect.Communicator;
import prs.client.view.ViewGlobal;

public class Controller {
	
	public String host;
	public Communicator communicator = null;
	public Stage stage;
	
	public Controller(Stage stage) {
		this.stage = stage;
	}
	
	public boolean connect(String stringHost, String stringPort) throws UnknownHostException, IOException {
		int port;
		try {
			communicator.closeAll();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (NullPointerException e) {
			// Do nothing
		}
		port = Integer.parseInt(stringPort);
		if(port < 0) {
			return false;
		}
		communicator = new Communicator(stringHost, port);
		this.host = stringHost;
		return true;
	}

	public void goToApp() {
		Scene mainScene;
		ViewGlobal app;
		double height;
		double width;

		height = stage.getScene().getHeight();
		width = stage.getScene().getWidth();
		app = new ViewGlobal();
		mainScene = new Scene(app, width, height);
		
		stage.setScene(mainScene);
		stage.show();
		
	}
}
