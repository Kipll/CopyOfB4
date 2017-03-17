package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//user Sender
//sends to server
public class ServerSender extends Thread {

	private BlockingQueue<byte[]> queue;
	private byte[] data;
	private DatagramPacket packet;
	private DatagramSocket socket;
	private int remotePort;
	private InetAddress inetAddress;

	public ServerSender(int p, InetAddress inetAddress, DatagramSocket socket) {
		this.queue = new LinkedBlockingQueue<>(); // queue for sending
													// packets;
		this.data = new byte[28]; // byte array of length 28 with is the
									// same as 7 ints
		this.packet = new DatagramPacket(data, data.length); // datagram
																// packet
																// which
																// sends
																// whatever
																// bytes are
																// in data
		this.socket = socket; // the socket data is being send on
		this.remotePort = p;
		this.inetAddress = inetAddress;
	}

	public void run() {
		
		while (true) {
			try {
				
		
				this.data = queue.take();
				packet = new DatagramPacket(data, data.length, inetAddress, remotePort);
				socket.send(packet);
			} catch (InterruptedException | IOException e) {

				e.printStackTrace();
			}
		}
	}

	public void addToQueue(int[] ints) {
		try {
			queue.put(ByteConversion.toBytes(ints));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

}
//public class ServerSender extends Thread {
//	private DataOutputStream out;
//	private BlockingQueue<Integer> queue;
//
//	public ServerSender(DataOutputStream out) {
//		this.out = out;
//		this.queue = new LinkedBlockingQueue<Integer>();
//	}
//
//	public void run() {
//
//		try {
//			while (true) {
//				Integer tmp = queue.take();
//				//System.out.println("sending " + tmp.intValue() + " to server");
//				out.writeInt(tmp);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void addToQueue(Integer input) {
//			queue.add(input);
//	
//	}
//}
