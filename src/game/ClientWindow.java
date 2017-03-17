package game;
import javax.swing.JFrame;
import javax.swing.JPanel;

import network.ServerSender;
import network.ClientListener;
import network.ClientSender;

import java.awt.*;

public class ClientWindow extends JPanel{

	public ClientInputListener l;
	public ClientListener cl;
	
	private JFrame frame;
	
	public ClientWindow(ClientSender sender){
		super();
		
		frame = new JFrame("Hello World");
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,420);
		
		setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		
		l = new ClientInputListener(sender);
		
		setFocusable(true);
		
		addKeyListener(l);
		addMouseListener(l);
		addMouseMotionListener(l);
		addComponentListener(l);
		
		frame.setVisible(true);
	}
	
	
	@Override
	public void paint(Graphics g){
		cl.g = (Graphics2D)g;
	}
}
