package prs.client.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import prs.client.control.Controller;

public class ViewGlobal extends BorderPane {
	TextArea inputText;
	TextArea outputText;
	Button executeButton;

	MenuItem fichierOuvrir;
	MenuItem fichierSauver;
	MenuItem fichierFermer;
	MenuItem aideApropos;

	Controller control;

	public ViewGlobal(Controller control, String welcomeMessage) {
		super();
		this.control = control;
		generateView();
		executeAction();
		menuAction();
		this.outputText.setText(welcomeMessage + "\n");
	}

	private void executeAction() {
		executeButton.setOnAction(e -> {
			control.sendInstructions(inputText.getText(), outputText);
		});
	}

	private void menuAction() {
		fichierOuvrir.setOnAction(e -> {
			File file;
			BufferedReader bin;
			String text = "";
			String line = "";
			file = control.showDialog(false, "user.home", "txt", "Text file(*.txt)");

			if (file != null) {
				System.out.println("file = "+file.getPath());
				try {
					bin = new BufferedReader(new FileReader(file));

					while((line = bin.readLine()) != null) {
						text += line + "\n";
					}
					inputText.setText(text);
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else {
				System.out.println("file = null");
			}
		});
		fichierSauver.setOnAction(e -> {
			File file;
			PrintStream out;
			file = control.showDialog(true, "user.home", "txt", "Text file(*.txt)");
			if (file != null) {
				if(file.getName().endsWith(".txt")) {
					try {
						out = new PrintStream(new FileOutputStream(file));
						out.print(inputText.getText());
						out.close();
					}	catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					System.out.println(file.getName() + " has no valid file-extension.");
				}
			}
			else {
				System.out.println("file = null");
			}
		});
		fichierFermer.setOnAction(e -> {
			this.control.sendQuit();
		});
		aideApropos.setOnAction(e -> {
			Stage stage = new Stage();
			Scene scene = new Scene(new AboutView(), 640, 480);
			stage.setScene(scene);
			stage.setTitle("A Propos");
			stage.show();
		});
	}

	private void generateView() {
		HBox mainHBox = new HBox();

		this.inputText = new TextArea();
		this.outputText = new TextArea();
		this.executeButton = new Button("executer programme ROBI");

		mainHBox.getChildren().addAll(inputText, outputText);
		this.setTop(generateMenu());
		this.setCenter(mainHBox);
		this.setBottom(executeButton);
		mainHBox.setPadding(new Insets(16));
		mainHBox.setSpacing(8);
	}

	private MenuBar generateMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuFichier = new Menu("Fichier");
		Menu menuAide = new Menu("Aide");
		fichierOuvrir = new MenuItem("Ouvrir");
		fichierSauver = new MenuItem("Sauver");
		fichierFermer = new MenuItem("Déconnexion");
		aideApropos = new MenuItem("A propos");

		menuBar.getMenus().addAll(menuFichier, menuAide);

		menuFichier.getItems().addAll(fichierOuvrir, fichierSauver, fichierFermer);
		menuAide.getItems().addAll(aideApropos);
		return menuBar;
	}
}
