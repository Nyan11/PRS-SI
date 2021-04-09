package prs.client.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import prs.client.control.Controller;

public class ViewGlobal extends BorderPane {
	TextArea inputText;
	TextArea outputText;
	Button executeButton;
	Button stepButton;
	Button nextButton;
	private boolean step = false;

	MenuItem fichierOuvrir;
	MenuItem fichierSauver;
	MenuItem fichierFermer;
	MenuItem aideApropos;

	Controller control;

	public ViewGlobal(Controller control, String welcomeMessage) {
		super();
		this.control = control;
		generateView();
		buttonAction();
		menuAction();
		this.outputText.setText(welcomeMessage + "\n");
	}

	private void buttonAction() {
		executeButton.setOnAction(e -> {
			this.step = false;
			nextButton.setDisable(true);
			outputText.setText("");
			control.sendInstructions(inputText.getText(), outputText, false);
		});
		stepButton.setOnAction(e -> {
			this.step = true;
			nextButton.setDisable(false);
			outputText.setText("");
			control.sendInstructions(inputText.getText(), outputText, true);
		});
		nextButton.setOnAction(e -> {
			control.sendNext(outputText);
		});
	}

	private void menuAction() {
		fichierOuvrir.setOnAction(e -> {
			File file;
			BufferedReader bin;
			String text = "";
			String line = "";
			file = Controller.showDialog(false, "user.home", "txt", "Text file(*.txt)");

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
			file = Controller.showDialog(true, "user.home", "txt", "Text file(*.txt)");
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
			Scene scene = new Scene(new AboutView());
			stage.setScene(scene);
			stage.setTitle("A Propos");
			stage.show();
		});
	}

	private void generateView() {
		HBox mainHBox = new HBox();
		TilePane buttonBox = new TilePane();
		double buttonSize = 160;

		this.inputText = new TextArea();
		this.outputText = new TextArea();
		this.executeButton = new Button("execute");
		this.stepButton = new Button("step by step");
		this.nextButton = new Button("next step");
		
		this.executeButton.setMaxWidth(buttonSize);
		this.executeButton.setMinWidth(buttonSize);
		this.stepButton.setMaxWidth(buttonSize);
		this.stepButton.setMinWidth(buttonSize);
		this.nextButton.setMaxWidth(buttonSize);
		this.nextButton.setMinWidth(buttonSize);
		
		nextButton.setDisable(true);

		this.inputText.setPrefSize(2000, 2000);
		this.outputText.setPrefSize(2000, 2000);
		
		buttonBox.setTileAlignment(Pos.CENTER);
		buttonBox.setHgap(16);
		BorderPane.setAlignment(buttonBox, Pos.CENTER);
		BorderPane.setMargin(buttonBox, new Insets(12,64,12,64));
		
		this.executeButton.setMaxWidth(120);
		
		mainHBox.getChildren().addAll(inputText, outputText);
		buttonBox.getChildren().addAll(executeButton, stepButton, nextButton);
		this.setTop(generateMenu());
		this.setCenter(mainHBox);
		this.setBottom(buttonBox);
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
