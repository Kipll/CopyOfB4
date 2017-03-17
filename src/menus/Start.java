package menus;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import audio.BGM;

import network.TCPClient;

/*
 * @author Jack Marshman
 */


//TODO
//Create and add graphics to new JButtons w/ Miruna
//Comment Start.java correctly
//Add graphics to resources
public class Start extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mainframe m;
	
	public JButton btnFindGame;
	public JButton btnControls;
	public JButton btnAudio;
	public JButton btnExit;
	
	private BGM click;
	public String test = null;
	
	public Start(Mainframe m)
	{
		
		super();
		this.m = m;
		
		setBackground(Color.BLACK);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		click = new BGM(10,"/Music/SFX_Click.wav");
		
		btnFindGame = new JButton();
		btnFindGame.setBounds(10, 88, 414, 50);
		ImageIcon btnFindGameIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnFindGame.setIcon(btnFindGameIcon);
		btnFindGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				click.playOnce();
				test="find";
				String[] args = new String[] {"4444", "localhost"};
				TCPClient.main(args);
			}
		});
		add(btnFindGame);
		
		btnControls = new JButton();
		btnControls.setBounds(10, 201, 90, 50);
		ImageIcon btnControlsIcon = new ImageIcon(new ImageIcon("Resources/Images/controlsLogo1.png").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnControls.setIcon(btnControlsIcon);
		btnControls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{	
				click.playOnce();
				test="control";
				m.setMenu(2);
			}
		});
		add(btnControls);
		
		/*
		 * Audio button
		 */
		btnAudio = new JButton();
		btnAudio.setBounds(171, 201, 90, 50);
		ImageIcon btnAudioIcon = new ImageIcon(new ImageIcon("Resources/Images/audioLogo1.png").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnAudio.setIcon(btnAudioIcon);
		btnAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				click.playOnce();
				test="audio";
				m.setMenu(3);
			}
		});
		add(btnAudio);
		
		/*
		 * Exit button
		 */
		btnExit = new JButton();
		btnExit.setBounds(334, 201, 90, 50);
		ImageIcon btnExitIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnExit.setIcon(btnExitIcon);
//		btnExit.addActionListener(e -> System.exit(0));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				click.playOnce();
				test = "exit";
				System.exit(0);
			}
		});
		add(btnExit);
		
		/*
		 * Betrayal logo JLabel
		 */
		int logoIconWidth = 250;
		int logoIconHeight = 80;
		JLabel logoLabel = new JLabel();
		logoLabel.setBounds(96, 11, logoIconWidth, logoIconHeight);
		ImageIcon logoIcon = new ImageIcon(new ImageIcon("Resources/Images/betrayalLogo1.png").getImage().getScaledInstance(logoIconWidth, logoIconHeight, Image.SCALE_DEFAULT));
		logoLabel.setIcon(logoIcon);
		add(logoLabel);
	}
}