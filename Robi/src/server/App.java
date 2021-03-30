package server;

import java.awt.Dimension;

import graphicLayer.GRect;
import graphicLayer.GSpace;

public class App {

    public static void main(String[] args) {
    	System.out.println("Le Serveur Robi");
		LauncherServer server = new LauncherServer(2121);
		new Thread(server).start();
		GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
		GRect robi = new GRect();
		space.addElement(robi);
		space.open();
    }

}