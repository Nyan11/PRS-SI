package prs.client.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import prs.client.control.Controller;

public class Communicator {
	//private static Message STOP = new Message("stop", "");
	private static Message NEXT = new Message("next", "");
	private static Message STEP = new Message("step", "");
	private static Message NOSTEP = new Message("nostep", "");
	private static Message QUIT = new Message("quit", "");
	protected static Socket socket;
	protected static BufferedReader br;
	protected static PrintStream ps;
	
	public Communicator(String host, int port) throws UnknownHostException, IOException {
		Communicator.socket = new Socket(host, port);
		Communicator.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Communicator.ps = new PrintStream(socket.getOutputStream());
	}
	
	public static void closeAll() throws IOException {
		Communicator.socket.close();
		Communicator.br.close();
		Communicator.ps.close();
	}
	
	public String sendInstructions(String message, boolean step) throws IOException {
		System.out.println("Sending message");
		Message send = new Message("instruction", message);
		ps.println(Message.toJson(send));
		if(step) {
			ps.println(Message.toJson(STEP));
			return "Step by Step mode\n";
		}
		else {
			ps.println(Message.toJson(NOSTEP));
			return retreiveMessage();
		}
	}
	
	public String sendNext() throws IOException {
		ps.println(Message.toJson(NEXT));
		return retreiveMessage();
	}

	public String retreiveMessage() throws IOException {
		Message receive;
		String sret = "";
		while((receive = Message.fromJson(br.readLine())).getType().compareTo("stop") != 0) {
			if(receive.getType().compareTo("print") == 0) {
				Controller.saveImage(receive.getMessage());
			} else if(receive.getType().compareTo("error") == 0) {
				sret += " # " + receive.getMessage() + "\n";
			} else if(receive.getType().compareTo("info") == 0) {
				sret += ">" + receive.getMessage().replace("§", "\n>");
				sret = sret.substring(0, sret.length() - 1);
			} else {
				sret += receive.getMessage() + "\n";
			}
		}
		return sret;
	}
	
	public String retreiveWelcome() throws IOException {
		return retreiveMessage();
	}
	
	public static void sendQuit() {
		ps.println(Message.toJson(QUIT));
		try {
			closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
