package server;

public class App {

	public static void main(String[] args) {
		System.out.println("Le Serveur Robi");
		LauncherServer server = new LauncherServer(2121);
		server.start();
	}
}