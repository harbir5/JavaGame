package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/* 
 * Start Page
 * 
 * This is the beginning frame for the game.
 * 
 * The player selects either easy or hard mode.
 * 
 * Then the player presses start to start the game.
 * 
 */


public class Start extends JFrame implements MouseListener {
	
	// easy and hard buttons
	JButton easyB;
	public static JButton hardB;
	// start button
	JButton start;
	// difficulty boolean
	public static boolean dif = false;

	public Start() {
		// set frame defaults
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		
		// start banner, set location/size, font and alignment
		JTextField startMes = new JTextField("Welcome to SpaceApp");
		startMes.setBounds(100, 100, 500, 100);
		startMes.setFont(new Font("Verdana", Font.PLAIN, 39));
		startMes.setHorizontalAlignment(JTextField.CENTER);
		startMes.setFocusable(false);
		this.add(startMes);
		
		// image for start page, scale appropriately
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon(getClass().getResource("astronaut.png"));
		Image image = icon.getImage();
		Image newImg = image.getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH);
		ImageIcon now = new ImageIcon(newImg);
		// set image location, size and icon
		label.setBounds(300, 250, 100, 150);
		label.setOpaque(true);
		label.setIcon(now);
		this.add(label);
		
		// easy difficulty button, set color, location/size, mouse listener
		easyB = new JButton("Easy");
		easyB.setBackground(Color.green);
		easyB.setBounds(257, 450, 75, 50);
		easyB.addMouseListener(this);
		this.add(easyB);
		easyB.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.yellow));
		
		// hard difficulty button, set color, location/size, mouse listener
		hardB = new JButton("Hard");
		hardB.setBackground(Color.red);
		hardB.setBounds(357, 450, 75, 50);
		hardB.addMouseListener(this);
		this.add(hardB);
		
		// start button
		start = new JButton("Start");
		start.setBounds(311, 550, 75, 50);
		start.addMouseListener(this);
		this.add(start);
		
		// background image - space image, scale it and add to frame background
		ImageIcon background=new ImageIcon(getClass().getResource("space.jpg"));
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(700,700,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(0,0,700,700);
	    back.setOpaque(true);
	    this.add(back);		
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		// Strategy pattern choice that will dictate the difficulty of all the games
		
		// set game difficulty - easy (color background of the option to yellow)
		if (e.getSource() == easyB) {
			easyB.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.yellow));
			hardB.setBorder(null);
			dif= false;
		}
		// set game difficulty - hard (color background of the option to yellow)
		if (e.getSource() == hardB) {
			hardB.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.yellow));
			easyB.setBorder(null);
			dif = true;
		}
		// start game
		if (e.getSource() == start) {
			this.dispose();
			new SpaceFrameDodge();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
