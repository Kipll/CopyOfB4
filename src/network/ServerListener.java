package network;

import java.io.DataInputStream;
import java.io.IOException;

import game.KeyboardInput;
import game.MouseInput;
import game.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class ServerListener extends Thread {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private byte[] recievedData;
	public Player p;
	
	public ServerListener(DatagramSocket socket,  Player p ) {
		this.socket = socket;
		this.recievedData = new byte[12];
		this.packet = new DatagramPacket(recievedData, recievedData.length);
		this.p = p;
	}

	public void run() {
		while (true) {
			try {
				socket.receive(packet);
				recievedData = packet.getData();
				int[] data = new int[3];
				data = ByteConversion.toInts(recievedData);
				for (int i = 0; i < data.length; i++) {
					System.out.println(data[i]);
				}
				
				int flag = data[0];
				int keycodeOrCoordX = data[1];
				int coordY = data[2];
				
				switch(flag){
				case 0: //key released
					p.keyboard.keyCodeReleased(keycodeOrCoordX);
					break;
				case 1: //key pressed
					p.keyboard.keyCodePressed(keycodeOrCoordX);
					break;
				case 2: //lmb released
					p.mouse.mouseCodeReleased(0);
					break;
				case 3: //lmb pressed
					p.mouse.mouseCodePressed(0);
					break;
				case 4: //rmb released
					p.mouse.mouseCodeReleased(1);
					break;
				case 5: //rmb pressed
					p.mouse.mouseCodePressed(1);
					break;
				case 6: //mouse moved
					p.mouse.setPos(keycodeOrCoordX, coordY);
					break;
				case 7: //window resized
					p.viewport.screenW = keycodeOrCoordX;
					p.viewport.screenH = coordY;
					break;
				default:
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
//public class Listener extends Thread {
//
//	private DataInputStream in;
//
//	public Player p;
//
//	public Listener(DataInputStream in, Player p) {
//		this.in = in;
//		this.p = p;
//	}
//
//	public void run() {
//		try {
//			while (true) {
//				int flag = in.readInt();
//				int keycodeOrCoordX = in.readInt();
//				int coordY = in.readInt();
//				switch(flag){
//				case 0: //key released
//					p.keyboard.keyCodeReleased(keycodeOrCoordX);
//					break;
//				case 1: //key pressed
//					p.keyboard.keyCodePressed(keycodeOrCoordX);
//					break;
//				case 2: //lmb released
//					p.mouse.mouseCodeReleased(0);
//					break;
//				case 3: //lmb pressed
//					p.mouse.mouseCodePressed(0);
//					break;
//				case 4: //rmb released
//					p.mouse.mouseCodeReleased(1);
//					break;
//				case 5: //rmb pressed
//					p.mouse.mouseCodePressed(1);
//					break;
//				case 6: //mouse moved
//					p.mouse.setPos(keycodeOrCoordX, coordY);
//					break;
//				case 7: //window resized
//					p.viewport.screenW = keycodeOrCoordX;
//					p.viewport.screenH = coordY;
//					break;
//				default:
//				}
//			}
//		}
//		catch (IOException e) {
//			System.out.println(e + " : Connection was lost");
//
//		}
//	}
//}
