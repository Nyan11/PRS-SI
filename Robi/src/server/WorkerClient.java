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
import logic.command.AddScript;
import logic.command.DelElement;
import logic.command.InfoReference;
import logic.command.PrintScreen;
import logic.command.SetColor;
import logic.command.Sleep;
import stree.parser.SNode;
import stree.parser.SParser;
import stree.parser.SSyntaxError;
import utils.Message;

public class WorkerClient implements Runnable {

	private static Message STOP = new Message("stop", "");
	private Socket clientSocket;
	private Environment environment;
	private GSpace space;
	private BufferedReader br = null;
	private PrintStream ps = null;
	private Interpreter interpreter;
	private boolean stepByStep = false;
	private boolean quit = false;

	public WorkerClient(Socket clientSocket) {
		try {
			this.clientSocket = clientSocket;
			br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			ps = new PrintStream(this.clientSocket.getOutputStream());
			this.interpreter = new Interpreter(ps);
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
				ps.println(Message.toJson(new Message("greeting", "Hello from Robi server !")));
				ps.println(Message.toJson(STOP));
				while(!quit) {
					commande = retrieveCommande();
					if(commande.equals("quit")) quit = true;

					System.out.println(commande);
					if(!quit) {
						try {
							parseCommande(commande);
						} catch (SSyntaxError e) {
							ps.println(Message.toJson(new Message("error", "SSyntaxError")));
							ps.println(Message.toJson(new Message("error", e.getMessage())));
							System.out.println("Erreur de syntaxe");
						} finally {
							ps.println(Message.toJson(STOP));
						}
					}
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
		spaceRef.addCommand("addScript", new AddScript());
		spaceRef.addCommand("print", new PrintScreen(space));

		spaceRef.getEnvironment();

		environment.addReference("space", spaceRef);
		space.open();
	}

	private void parseCommande(String commande) throws IOException, SSyntaxError {
		Message receive;
		SParser<SNode> parser = new SParser<>();
		List<SNode> compiled = null;
		compiled = parser.parse(commande);
		Iterator<SNode> itor = compiled.iterator();
		while (itor.hasNext()) {
			if(stepByStep) {
				if((receive = Message.fromJson(br.readLine())).getType().compareTo("next") != 0) {
					if(receive.getType().compareTo("quit") == 0) quit = true;
					stepByStep = false;
				}
				ps.println(Message.toJson(STOP));
			}
			interpreter.compute(environment, itor.next());
			space.repaint();
		}
	}

	private String retrieveCommande() throws IOException {
		Message receive;
		String commande = "";
		String type;
		while((receive = Message.fromJson(br.readLine())).getType().compareTo("instruction") == 0) {
			commande += receive.getMessage() + "\n";
		}
		type = receive.getType();
		if (type.compareTo("instruction") == 0) {
			return commande;
		} else if(type.compareTo("step") == 0) {
			stepByStep = true;
			return commande;
		} else if(type.compareTo("nostep") == 0) {
			stepByStep = false;
			return commande;
		} else if(type.compareTo("quit") == 0) {
			return "quit";
		} else
			return commande;
	}
}
