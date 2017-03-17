package network;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import game.ClientWindow;



public class TCPClient {
	
	private int port;
	private String name;
	private UDPClient udpC;
	
	public TCPClient(int port, String name) {
		this.port = port;
		this.name = name;
		
		try {
			Socket s = new Socket(name, port); //create new socket with given port and name;
			int serverPort = s.getPort(); // gets the port number of the server;
			int localPort = s.getLocalPort();
			InetAddress serverAddress = s.getInetAddress(); // gets the Inet address of the server
			s.close();
			
			DatagramSocket socket = new DatagramSocket(localPort);
			this.udpC = new UDPClient(socket, serverPort, serverAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
	}
	
	public void addToQueue(int[] ints) {
		udpC.addToQueue(ints);
	}
	
	public static void main(String[] args){
		TCPClient c = new TCPClient(Integer.parseInt(args[0]), args[1]);
	}
}
//public class Client {
//
//	private ServerSender sender;
//	private ClientListener listener;
//	private Socket server;
//
//	private ClientWindow window;
//
//	/**
//	 * 
//	 */
//	public Client() {
//		this.sender = null;
//		this.listener = null;
//		this.server = null;
//		this.window = null;
//	}
//
//	/**
//	 * main method for the class, the client will try to connect to a specified
//	 * server socket, and open input and output streams to it
//	 * 
//	 * @param port
//	 *            The port number you wish to connect to
//	 * @param name
//	 *            The name of the server on the network
//	 * @return returns true if a connection is successfully established. False
//	 *         otherwise.
//	 */
//	public boolean connect(String name, int port) {
//		try {
//
//			this.server = new Socket(name, port);
//			sender = new ServerSender(new DataOutputStream(server.getOutputStream()));
//			window = new ClientWindow(sender);
//			listener = new ClientListener(new DataInputStream(server.getInputStream()), window);
//			window.cl = listener;
//			sender.start();
//			listener.start();
//			return true;
//		} catch (IOException e) {
//			System.out.println("couldnt connect to specified host, connection refused.");
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public void addToQueue(Integer input) {
//
//		sender.addToQueue(input);
//
//	}
//
//	public static void main(String[] args) {
//		Client c = new Client();
//		c.connect(args[0], Integer.parseInt(args[1]));
//	}
//
//}
//
