package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class WorkerClient implements Runnable {

	private Socket clientSocket;
	
	public WorkerClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		PrintStream ps = null;
		String commande = "";
		try {
			try {
				br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
				ps = new PrintStream(this.clientSocket.getOutputStream());

				ps.println("Connection with ROBI ok");

				while(!(commande=br.readLine()).equals("xxx")) {
					System.out.println(">> "+commande);
					// DO SOMETHING
				}

				ps.close();
				br.close();
				System.out.println("Deconnexion du client");
			} catch (NullPointerException e) {
				System.out.println("Deconnexion du client (Null Pointer Exception)");
				ps.close();
				br.close();
				this.clientSocket.close();
			} catch (SocketException e) {
				System.out.println("Deconnexion du client (Socket Exception");
				ps.close();
				br.close();
				this.clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
