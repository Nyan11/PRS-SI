package server;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import graphicLayer.GSpace;
import logic.Environment;
import logic.Interpreter;
import logic.Reference;
import logic.command.AddElement;
import logic.command.DelElement;
import logic.command.InfoReference;
import logic.command.SetColor;
import logic.command.Sleep;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

public class WorkerClient implements Runnable {

	private Socket clientSocket;
	private Environment environment;
	private GSpace space;
	BufferedReader br = null;
	PrintStream ps = null;

	public WorkerClient(Socket clientSocket) {
		try {
			this.clientSocket = clientSocket;
			br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			ps = new PrintStream(this.clientSocket.getOutputStream());
			this.environment = new Environment();
			initEnv(clientSocket.getInetAddress().getHostName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String commande;
		try {
			try {
				ps.println("Connection with ROBI");
				ps.println("stop");
				while(true) {
					commande = retrieveCommande();
					if(commande.equals("quit")) break;
					
					System.out.println(commande);

					// Attraper toutes les exceptions de commande ici
					parseCommande(commande);
					
					
					// envoyer le bon message
					commande += " -- ok";
					ps.println(commande + "\nstop");
				}

				System.out.println("Deconnexion du client");
			} catch (NullPointerException e) {
				System.out.println("Deconnexion du client (Null Pointer Exception)");
			} catch (SocketException e) {
				System.out.println("Deconnexion du client (Socket Exception)");
			} finally {
				ps.close();
				br.close();
				this.clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		SwingUtilities.getWindowAncestor(space).dispose();
	}

	public void initEnv(String client) {
		space = new GSpace(client, new Dimension(800, 600));
		Reference spaceRef = new Reference(space);
		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());

		spaceRef.addCommand("add", new AddElement());
		spaceRef.addCommand("del", new DelElement());

		spaceRef.addCommand("info", new InfoReference());

		spaceRef.getEnvironment();

		environment.addReference("space", spaceRef);
		space.open();
	}
	
	private void parseCommande(String commande) throws IOException {
		SParser<SNode> parser = new SParser<>();
		List<SNode> compiled = null;
		compiled = parser.parse(commande);
		Iterator<SNode> itor = compiled.iterator();
		while (itor.hasNext()) {
			new Interpreter().compute(environment, itor.next());
			space.repaint();
		}
	}
	
	private String retrieveCommande() throws IOException {
		String line;
		String commande = "";
		while(!(line = br.readLine()).equals("stop") && !(line.equals("quit"))) {
			commande += line + "\n";
		}
		return (line.equals("quit")) ? "quit" : commande;
	}
}
