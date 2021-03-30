package prs.client.view;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import prs.client.control.Controller;

public class ViewLogin extends VBox {

	private TextField hostField;
	private TextField portField;
	private Button loginButton;
	private Label incorectConnectLabel;
	private Controller controller;
	
	public ViewLogin(Controller controller) {
		super();
		this.controller = controller;
		generateView();
		loginAction();
	}
	
	private void loginAction() {
		loginButton.setOnMouseClicked(e -> {
			if(checkLoginEmptyField()) {
				System.out.println("Error: empty field for login");
			}
			else {
				try {
					if(!controller.connect(hostField.getText(), portField.getText())) {
						incorectConnectLabel.setText("Error connection refused");
					}
					else {
						controller.goToApp();
					}
				} catch (UnknownHostException e1) {
					incorectConnectLabel.setText("Error unknown host");
					return;
				} catch (IOException e1) {
					incorectConnectLabel.setText("Error connection refused (IOException)");
					return;
				}
				incorectConnectLabel.setText("");
			}
		});
	}
	private boolean checkLoginEmptyField() {
		boolean ret = false;
		if(hostField.getText().equals("")) {
			incorectConnectLabel.setText("Host empty");
			ret = true;
		} else if(portField.getText().equals("")) {
			incorectConnectLabel.setText("Port empty");
			ret = true;
		}
		else {
			incorectConnectLabel.setText("");
		}
		return ret;
	}
	
	private void generateView() {
		VBox titleBox = new VBox();
		VBox buttonBox = new VBox();
		HBox connectBox = new HBox();
		VBox hostBox = new VBox();
		VBox portBox = new VBox();

		Label titleLabel = new Label("ROBIC");
		Label hostLabel = new Label("Host");
		Label portLabel = new Label("Port");
		
		hostField = new TextField("127.0.0.1");
		portField = new TextField("2121");
		loginButton = new Button("Log In");
		incorectConnectLabel = new Label("");
		
		titleLabel.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 64));
		hostField.setPromptText("Ex: 127.0.0.1");
		portField.setPromptText("Ex: 2121");
		incorectConnectLabel.setTextFill(Color.rgb(210, 39, 30));
		
		
		this.getChildren().addAll(titleBox, connectBox, buttonBox);
		titleBox.getChildren().addAll(titleLabel);
		connectBox.getChildren().addAll(hostBox, portBox);
		hostBox.getChildren().addAll(hostLabel, hostField, incorectConnectLabel);
		portBox.getChildren().addAll(portLabel, portField);
		buttonBox.getChildren().addAll(loginButton);
		
		this.setPadding(new Insets(16));
		this.setSpacing(16);
		connectBox.setSpacing(16);
		
		hostField.setMinWidth(180);
		portField.setMinWidth(80);
		portField.setPrefWidth(80);
		
		titleBox.setAlignment(Pos.CENTER);
		buttonBox.setAlignment(Pos.CENTER);
		
		HBox.setHgrow(hostBox, Priority.ALWAYS);
		VBox.setVgrow(buttonBox, Priority.ALWAYS);
		VBox.setVgrow(connectBox, Priority.ALWAYS);
		
		
	}
	
}
