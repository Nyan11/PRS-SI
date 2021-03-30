package prs.client.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
		ps.println(message);
		return br.readLine();
	}

	public String retreiveMessage() throws IOException {
		return br.readLine();
	}
}