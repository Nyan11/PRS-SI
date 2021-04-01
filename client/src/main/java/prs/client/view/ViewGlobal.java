package prs.client.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import prs.client.control.Controller;

public class ViewGlobal extends BorderPane {
	TextArea inputText;
	TextArea outputText;
	Button executeButton;

	MenuItem fichierOuvrir;
	MenuItem fichierSauver;
	MenuItem fichierFermer;
	
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
			FileChooser fileChooser = new FileChooser();
			File fileToLoad;
			BufferedReader bin;
			String text = "";
			String line = "";

			//only allow text files to be selected using chooser
			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
					);

			//set initial directory somewhere user will recognise
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

			//let user select file
			fileToLoad = fileChooser.showOpenDialog(null);

			if (fileToLoad != null) {
				System.out.println("file = "+fileToLoad.getPath());
				try {
					bin = new BufferedReader(new FileReader(fileToLoad));

					while((line = bin.readLine()) != null) {
						text += "\n" + line;
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
		fichierOuvrir = new MenuItem("Ouvrir");
		fichierSauver = new MenuItem("Sauver");
		fichierFermer = new MenuItem("Fermer");

		menuBar.getMenus().add(menuFichier);

		menuFichier.getItems().addAll(fichierOuvrir, fichierSauver, fichierFermer);
		return menuBar;
	}
}
