package prs.client.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import prs.client.connect.Communicator;
import prs.client.view.ViewGlobal;
import prs.client.view.ViewLogin;

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
		
		try {
			height = stage.getScene().getHeight();
			width = stage.getScene().getWidth();
			app = new ViewGlobal(this, communicator.retreiveMessage());
			mainScene = new Scene(app, width, height);
			stage.setScene(mainScene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void goToLogin() {
		Scene mainScene;
		ViewLogin login;
		double height;
		double width;
		
		height = stage.getScene().getHeight();
		width = stage.getScene().getWidth();
		login = new ViewLogin(this);
		mainScene = new Scene(login, width, height);
		stage.setScene(mainScene);
		stage.show();
	}
	
	public void sendInstructions(String script, TextArea outputText) {
		String response;
		System.out.println(script);
		try {
			response = this.communicator.sendMessage(script.replace("\n", ""));
			System.out.println(response);
			outputText.setText(outputText.getText() + "\n" + response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendQuit() {
		this.communicator.sendQuit();
		goToLogin();
	}
	
	public static void saveImage(String imageBase64) {
		File file;
		file = showDialog(true, "user.home", "png", "PNG file(*.png)");
		try {
			Utils.decodeToImage(imageBase64, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File showDialog(boolean save, String location, String extension, String textExtension) {
		FileChooser fileChooser = new FileChooser();
		File file;
		
		
		fileChooser.setInitialDirectory(new File(System.getProperty(location)));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(textExtension, "*." + extension));
		
		if(save) {
			fileChooser.setInitialFileName("*." + extension);
			file = fileChooser.showSaveDialog(null);
		}
		else {
			file = fileChooser.showOpenDialog(null);
		}
		
		return file;

		
	}
}
