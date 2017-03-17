package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import game.ClientWindow;

public class UDPClient {

	private DatagramSocket socket;
	private int port;
	private InetAddress inetAddress;
	private ClientSender sender;
	private ClientListener listener;
	private ClientWindow window;

	public UDPClient(DatagramSocket socket, int serverPort, InetAddress serverAddress) {
		this.socket = socket;
		this.port = serverPort;
		this.inetAddress = serverAddress;
		this.sender = new ClientSender(serverPort, inetAddress, socket);
		this.window = new ClientWindow(this.sender);
		this.listener = new ClientListener(socket, window);
		this.window.cl = listener;
		sender.start();
		listener.start();
	}
	
	public void addToQueue(int[] ints) {
		sender.addToQueue(ints);
	}


}
