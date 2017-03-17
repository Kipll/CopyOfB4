package network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import game.*;


public class MainServer extends Thread {

	private int port; // port to listen for new connections
	private LinkedList<Server> connections; // list of all connections
	private Game game;

	public MainServer(int port, Game game) {
		this.connections = new LinkedList<Server>();
		this.port = port;
		this.game = game;

	}

	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			int c = 0;
			while (true) {

				String name = getLocalName();

				System.out.println("To connect to this server use this name : " + name);

				Socket clientSocket = serverSocket.accept(); // accept a new
				System.out.println("past serverSocket.accept()");// connection,
																	// allocates
																	// ports for
																	// connection

				int p = clientSocket.getLocalPort(); // gets local port of
														// connection

				int p2 = clientSocket.getPort(); // gets remote port of the
													// connection
				InetAddress ip = clientSocket.getInetAddress(); // gets the Inet
																// address of
																// client
																// connection
				System.out.println(clientSocket.getLocalPort());
				clientSocket.close(); // closes the socket so can be used for as
										// Datagram Socket
				System.out.println("clientSocket.close() done");

				System.out.println(p);

				DatagramSocket socket = new DatagramSocket(p + c); // create a
				c++;
				// Datagram
				// socket using
				// the port
				// selected from
				// serversocket.accept()
				System.out.println("adding new connection from " + ip);

				KeyboardInput keyboard = new KeyboardInput();
				MouseInput mouse = new MouseInput();

				Player player = new Player(game, keyboard, mouse);
				player.viewport = new Viewport(game, player);
				//
				// new Server(clientSocket, p);
				//
				;

				connections.add(0, new Server(socket, p2, ip, player));	// creates
				game.players.add(player);									// a
																			// new
				connections.get(0).start(); // UDPServer
											// with the
											// Datagram
											// Server object
											// while adding
											// it to
			} // connections
		} catch (IOException e) {
			System.out.println("There seems to be a problem");
			e.printStackTrace();
		}

	}

	private String getLocalName() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr.getHostName();
	}

	public void broadcastMessage(int[] ints) {
		for (int i = 0; i < connections.size(); i++) {
			System.out.println("broadcasting message to " + connections.size() + " clients");
			connections.get(i).addToQueue(ints);
		}
	}
}

// public class MainServer extends Thread{
//
// //private Map<String, Server> upConnections;
//
// private int port;
// private Game game;
//
// public MainServer(int port, Game game) {
// this.port = port;
// this.game = game;
// }
//
// public void run(){
// ServerSocket serverSocket;
// try {
// serverSocket = new ServerSocket(port);
//
// String name = getLocalName();
//
// System.out.println("To connect to this server use this name : " + name);
// System.out.println("Waiting for connection...");
//
// while (true) {
//
// Socket clientSocket = serverSocket.accept();
// System.out.println("connection to " + clientSocket.getLocalAddress());
// //new Server(clientSocket).start();
//
//
// KeyboardInput keyboard = new KeyboardInput();
// MouseInput mouse = new MouseInput();
//
// Player p = new Player(game, keyboard, mouse);
// p.viewport = new Viewport(game, p);
//
// new Server(clientSocket, p);
//
// game.players.add(p);
//
//
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
//
// private String getLocalName() {
// InetAddress addr = null;
// try {
// addr = InetAddress.getLocalHost();
// } catch (UnknownHostException e) {
// e.printStackTrace();
// }
// return addr.getHostName();
// }
// }
