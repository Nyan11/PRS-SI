package prs.client.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class AboutView extends VBox {

	public AboutView() {
		super();
		generateView();
	}
	
	private void generateView() {
		Label name = new Label("ROBIC");
		this.getChildren().add(name);
		Label description = new Label("Description:");
		this.getChildren().add(description);
		Label dis= new Label("Nous pouvons exécuter des programmes ROBI à distance. Le "
				+ "programme ROBI peut être saisi sur une machine, la machine de commande "
				+ "appelée ROBIC");
		this.getChildren().add(dis);
		
		Label authors = new Label("Créé par:");
		this.getChildren().add(authors);
		Text meshari= new Text("ALHAWAS MESHARI");
		this.getChildren().add(meshari);
		Text yann= new Text("LE GOFF YANN");
		this.getChildren().add(yann);
		Text sabrina= new Text("SABRINA TOUAOULA");
		this.getChildren().add(sabrina);
		Text david= new Text("DAVID GRUBER");
		this.getChildren().add(david);
		
		//Label files = new Label("© PixelPerfect - Freepik - ArcIcons");
		//this.getChildren().add(files);
		
		this.setPadding(new Insets(16));
		dis.setMaxWidth(180);
		dis.setWrapText(true);
		VBox.setVgrow(dis, Priority.ALWAYS);
		
		description.setPadding(new Insets(30));
		authors.setPadding(new Insets(30,0,10,0));
		name.setPadding(new Insets(12));
		
		name.setFont(Font.font(null,FontWeight.EXTRA_BOLD,25));
		description.setFont(Font.font(null,FontWeight.EXTRA_BOLD,13));
		authors.setFont(Font.font(null,FontWeight.EXTRA_BOLD,13));
		
		name.setTextAlignment(TextAlignment.CENTER);
		description.setTextAlignment(TextAlignment.CENTER);
		dis.setTextAlignment(TextAlignment.CENTER);
		authors.setTextAlignment(TextAlignment.CENTER);
		//files.setTextAlignment(TextAlignment.CENTER);
		
		this.setAlignment(Pos.TOP_CENTER);
	}
}
