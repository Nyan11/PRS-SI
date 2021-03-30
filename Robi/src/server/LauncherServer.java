package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LauncherServer implements Runnable{

	protected int serverPort   = -1;
	protected ServerSocket serverSocket = null;

	public LauncherServer(int port) {
		this.serverPort = port;
	}


	@Override
	public void run() {
		try {
			Socket clientSocket = null;
			WorkerClient worker = null;

			this.serverSocket = new ServerSocket(serverPort);

			while(true) {
				clientSocket = this.serverSocket.accept();
				worker = new WorkerClient(clientSocket);
				new Thread(worker).start();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
