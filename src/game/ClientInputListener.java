package game;

import java.awt.event.*;

import network.ClientSender;
import network.ServerSender;

import java.awt.Point;

public class ClientInputListener implements MouseMotionListener, MouseListener, KeyListener, ComponentListener {

	
	private static final int KEY_COUNT = 256;
	
	private int[] message;
	public ClientSender sender;

	public ClientInputListener(ClientSender sender2) {

		this.sender = sender2;
	}

	public void mousePressed(MouseEvent e) {
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			message = new int[]{3,-1,-1};
			sender.addToQueue(message);
			break;
		case MouseEvent.BUTTON3:
			message = new int[]{5,-1,-1};
			sender.addToQueue(message);
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			message = new int[]{2,-1,-1};
		sender.addToQueue(message);

			break;
		case MouseEvent.BUTTON3:

			message = new int[]{4,-1,-1};
			sender.addToQueue(message);
			break;
		}
	}

	public void mouseMoved(MouseEvent e) {

		message = new int[]{6,e.getX(),e.getY()};
		sender.addToQueue(message);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {

		message = new int[]{6,e.getX(),e.getY()};
		sender.addToQueue(message);
	}

	public synchronized void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode >= 0 && keyCode < KEY_COUNT) {


			message = new int[]{1,keyCode,-1};
			sender.addToQueue(message);

		}

	}

	public synchronized void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode >= 0 && keyCode < KEY_COUNT) {


			message = new int[]{0,keyCode,-1};
			sender.addToQueue(message);;

		}

	}

	public void keyTyped(KeyEvent e) {

		// Not needed

	}
	
	public void componentResized(ComponentEvent e) {
	

		message = new int[]{7,e.getComponent().getWidth(),e.getComponent().getHeight()};
		sender.addToQueue(message);
	}
	
	
	public void componentHidden(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	public void componentShown(ComponentEvent e){}
}
