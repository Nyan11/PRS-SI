package prs.client.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import prs.client.control.Controller;

public class Communicator {
	protected static Socket socket;
	protected static BufferedReader br;
	protected static PrintStream ps;
	
	public Communicator(String host, int port) throws UnknownHostException, IOException {
		Communicator.socket = new Socket(host, port);
		Communicator.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Communicator.ps = new PrintStream(socket.getOutputStream());
	}
	
	public void closeAll() throws IOException {
		Communicator.socket.close();
		Communicator.br.close();
		Communicator.ps.close();
	}
	
	public String sendMessage(String message) throws IOException {
		System.out.println("Sending message");
		message += "\nstop";
		ps.println(message);
		return retreiveMessage();
	}

	public String retreiveMessage() throws IOException {
		String message;
		String sret = "";
		System.out.println("Retreiving message");
		while(!(message = br.readLine()).startsWith("stop")) {
			if(message.startsWith("print::")) {
				Controller.saveImage(message.split(" ")[1]);
			}
			else {
				sret += message + "\n";
				System.out.println(message);
			}
		}
		return sret;
	}
	
	public String retreiveWelcome() throws IOException {
		return br.readLine();
	}
	
	public void sendQuit() {
		ps.println("quit");
		try {
			closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
