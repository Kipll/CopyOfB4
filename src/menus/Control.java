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
import menus.Mainframe;

/*
 * @author Jack Marshman
 */

//TODO
//Create and add graphics to new JButtons
//Add events and sounds to button clicks
//Integrate into game
//Add graphics to resources
public class Control extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Mainframe m;
	
	public Control(Mainframe m) 
	{
		
		super();
		this.m = m;
		
		setBackground(Color.BLACK);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		/*
		 * Back button
		 */
		JButton btnBack = new JButton();
		btnBack.setBounds(10, 201, 90, 50);
		ImageIcon btnBackIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnBack.setIcon(btnBackIcon);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				m.setMenu(1);
			}
		});
		add(btnBack);
		
		/*
		 * Exit button
		 */		
		JButton btnExit = new JButton();
		btnExit.setBounds(334, 201, 90, 50);
		ImageIcon btnExitIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT));
		btnExit.setIcon(btnExitIcon);
		btnExit.addActionListener(e -> System.exit(0));
		add(btnExit);
		
		/*
		 * Controls title graphic
		 */
		JLabel titleLabel = new JLabel();
		titleLabel.setBounds(108, 11, 213, 64);
		ImageIcon titleIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(200,64, Image.SCALE_DEFAULT));
		titleLabel.setIcon(titleIcon);
		add(titleLabel);
		
		/*
		 * Controls graphic
		 */
		int controlsWidth = 350;
		int controlsHeight = 120;
		JLabel controlsLabel = new JLabel();
		controlsLabel.setBounds(44, 56, 363, 166);
		ImageIcon controlsIcon = new ImageIcon(new ImageIcon("").getImage().getScaledInstance(controlsWidth, controlsHeight, Image.SCALE_DEFAULT));
		controlsLabel.setIcon(controlsIcon);
		add(controlsLabel);	
	}
}