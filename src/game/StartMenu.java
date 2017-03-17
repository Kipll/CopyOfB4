package game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

import javax.swing.JPanel;


import network.TCPClient;

import javax.swing.JLabel;
public class StartMenu 
{
	//FIX THIS WHOLE FUCKING IMPLEMENTATION
	
	
	//Create JButtons
	//Position JButtons on JFrame
	//Create JFrame
	//Add JPanel to JFrame
	
	
	
	private boolean play;
	public int width = 500;
	public int height = 500;
	public int buttonX = 0; //x location
	public int buttonY = 0; //y location
	public int buttonWidth = 100; //button width
	public int buttonHeight = 50; //button height
	
	
	public StartMenu()
	{

		//Create JFrame
		JFrame menu = new JFrame("Betrayal");
		menu.setSize(width, height);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create JPanel
		JPanel menuPanel = new JPanel();
		Color brown = new Color(102, 51, 00);
		menuPanel.setBackground(brown);
		
		//Create JLabel
		JLabel logo = new JLabel("GAME NAME HERE");
		logo.setText("GAME NAME HERE");
		
		//Creates JButtons, sets boundaries and size
		//Add Buttons to JPanel
		JButton findGame = new JButton("Find Game");
		findGame.setSize(buttonWidth * 2, buttonHeight);
		findGame.setLocation(buttonX + 150, buttonY + 125); 
		findGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						
				String[] args = new String[] {"localhost", "4444"};
				menu.dispose();
				TCPClient.main(args);
				
			}
		});

		JButton options = new JButton("Options");
		options.setSize(buttonWidth, buttonHeight);
		options.setLocation(buttonX + 20, buttonY + 300);
		
		JButton controls = new JButton("Controls");
		controls.setSize(buttonWidth, buttonHeight);
		controls.setLocation(buttonX + 195 , buttonY + 300);
		controls.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ControlMenu();
				
			}
		});
		
		JButton quit = new JButton("Quit");
		quit.setSize(buttonWidth, buttonHeight);
		quit.setLocation(buttonX + 360, buttonY + 300);
		quit.addActionListener(e -> System.exit(0));
	
		menuPanel.setLayout(null);
		menuPanel.add(findGame);
		menuPanel.add(options);
		menuPanel.add(controls);
		menuPanel.add(quit);
		
		
		//Add JPanel to JFrame
		menu.add(menuPanel);
		menu.setVisible(true);
		menu.revalidate();
		
	}
	
	public static void main(String[] args)
	{
		new StartMenu();
	}	
	
	
}
