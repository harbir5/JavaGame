package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;


/*
 * Treasure game
 * 
 * Objective of the game is to move your spaceship and collect as many treasures as possible before time runs out.
 * 
 * In difficult mode, there are also bombs that appear which decrease your score if you land on them.
 * 
 */


public class Treasure extends JFrame implements KeyListener, MouseListener {

	// spaceship label
	public JLabel label;
	ImageIcon icon;
	Random rando = new Random();
	// treasure icon
	JLabel treasure;
	// bomb icon
	JLabel bomb;
	// initialize score for game
	int score = 0;
	Timer timer;
	// seconds given
	int seconds = 15;
	JTextField timeDisplay;
	JButton results;
	
	public Treasure() {
		// frame default settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		this.setResizable(false);
		
		// add player icon (spaceship), set size/location/background color
		label = new JLabel();
		icon = new ImageIcon(getClass().getResource("rocket_icon.png"));
		label.setBounds(275, 275, 128, 128);
		label.setBackground(Color.magenta);
		label.setOpaque(true);
		label.setIcon(icon);;
		this.add(label);
		
		// add treasure, scale and set background color
		treasure = new JLabel();
		ImageIcon icon1 = new ImageIcon(getClass().getResource("treasure.png"));
		Image image = icon1.getImage();
		Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		ImageIcon now = new ImageIcon(newImg);
		treasure.setIcon(now);
		treasure.setBackground(Color.white);
		treasure.setOpaque(true);
		
		// add score display and instructions
		this.add(Main.scoreDisplay);
		if (Start.dif) {
			// instructions for hard mode- avoid bombs
			JTextArea instruction = new JTextArea("Collect treasure, \n avoid bombs");
			instruction.setBounds(390, 25, 140, 50);
			instruction.setFocusable(false);
			this.add(instruction);
		} else {
			Main.instruction.setText("Collect treaure");
			this.add(Main.instruction);
		}
		Main.scoreDisplay.setFocusable(false);	
		
		// set time display, set text/font/location/size
		timeDisplay = new JTextField();
		timeDisplay.setFocusable(false);
		timeDisplay.setBounds(180, 0, 200, 100);
		timeDisplay.setText(Integer.toString(seconds) + " sec");
		timeDisplay.setFont(new Font("Verdana", Font.PLAIN, 30));
		timeDisplay.setHorizontalAlignment(JTextField.CENTER);
		this.add(timeDisplay);
		
		// add results button, set size/location
		results = new JButton("Results/Pause");
		results.setBounds(535, 0, 150, 100);
		results.addMouseListener(this);
		results.setFocusable(false);
		this.add(results);
		
		// prepare bomb icon, scale it, set location and size
		bomb = new JLabel();
		ImageIcon icon2 = new ImageIcon(getClass().getResource("bomb.png"));
		Image image2 = icon2.getImage();
		Image newImg2 = image2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon now2 = new ImageIcon(newImg2);
		bomb.setIcon(now2);
		bomb.setBounds(500, 500, 30, 30);
		bomb.setBackground(Color.white);
		bomb.setOpaque(true);
		// add first treasure
		this.add(treasure);
		
		this.setVisible(true);
		this.addKeyListener(this);
		// start game
		startGame();
	}
	
	public void startGame() {
		
		// game timer
		Timer timer0 = new Timer(1000, e -> {
			// decrement seconds
			seconds--;
			timeDisplay.setText(Integer.toString(seconds) + " sec");
			// game over if time runs out
			if (seconds == 0) {
				gameOver();
			}
			
			});
		timer0.start();	
		
		newTreasure();
		// add bomb if hard difficulty
		if (Start.dif) {
			newBomb();
		}
		
		// check collisions repeatedly 
		if (Start.dif) {
			timer = new Timer(100, e -> {
				checkCollisions();
				// check for bombs collisions as well if hard difficulty chosen
				bombCollide();
			});
		} else {
			timer = new Timer(100, e -> {
				checkCollisions();
			});
		}
		
		timer.start();
	}
	
	public void newBomb() {
		// generate new bomb and place on screen if hard mode selected
		while (true) {
			int itemX = rando.nextInt(600);
			int itemY = 100 + rando.nextInt(500);
			
			// place bomb where the treasure is not located: within treasure, left top, left middle, left bottom, top middle, bottom middle, right top, right middle, right bottom
			if (!(
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY <= treasure.getY() + 50) 					
						|| 
				(itemX + 25 >= treasure.getX() && itemX + 25 <= treasure.getX() + 50 && itemY + 25 >= treasure.getY() && itemY + 25 <= treasure.getY() + 50) 
						||
				(itemX + 25 >= treasure.getX() && itemX + 25 <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY + 25 <= treasure.getY() + 50) 
						||
				(itemX + 25 >= treasure.getX() && itemX + 25 <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY <= treasure.getY() + 50) 
						||
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY + 25 >= treasure.getY() && itemY + 25 <= treasure.getY() + 50) 
						||
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY <= treasure.getY() + 50) 
						||
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY + 25 >= treasure.getY() && itemY + 25 <= treasure.getY() + 50) 
						||
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY + 25 <= treasure.getY() + 50) 
						||
				(itemX >= treasure.getX() && itemX <= treasure.getX() + 50 && itemY >= treasure.getY() && itemY <= treasure.getY() + 50) 
					)) {
					bomb.setBounds(itemX, itemY, 25, 25);
					this.add(bomb);					
					break;
			}
		}
	}
	
	// generate treasure location
	public void newTreasure() {
		int itemX = rando.nextInt(600);
		int itemY = 100 + rando.nextInt(500);
		
		
		treasure.setBounds(itemX, itemY, 50, 50);
	}
	
	public void checkCollisions() {
		int[] treasureX = {treasure.getX(), treasure.getX() + 50};
		int[] treasureY = {treasure.getY(), treasure.getY() + 50};
		int[] shipX = {label.getX(), label.getX() + 128};
		int[] shipY = {label.getY(), label.getY() + 128};
		
		
		// treasure to left
		if (shipX[0] >= treasureX[0] && shipX[0] <= treasureX[1]) {
			//treasure to top
			if (shipY[0] >= treasureY[0] && shipY[0] <= treasureY[1]) {
				
				score++;
				Main.increaseScore(1);
				newTreasure();
			}
			// treasure to bottom
			if (shipY[1] >= treasureY[0] && shipY[1] <= treasureY[1]) {

				score++;
				Main.increaseScore(1);
				newTreasure();
			}
			// treasure within
			if (shipY[0] <= treasureY[0] && shipY[1] >= treasureY[1]) {
				score++;
				Main.increaseScore(1);
				newTreasure();
			}
		}
		//treasure to right
		if (shipX[1] >= treasureX[0] && shipX[1] <= treasureX[1]) {
			//treasure to top
			if (shipY[0] >= treasureY[0] && shipY[0] <= treasureY[1]) {
				score++;
				Main.increaseScore(1);
				newTreasure();
			}
			// treasure to bottom
			if (shipY[1] >= treasureY[0] && shipY[1] <= treasureY[1]) {
				score++;
				Main.increaseScore(1);
				newTreasure();
			}
			// treasure within
			if (shipY[0] <= treasureY[0] && shipY[1] >= treasureY[1]) {
				score++;
				newTreasure();
				Main.increaseScore(1);
			}
		}
		// treasure within x
		if (shipX[0] <= treasureX[0] && shipX[1] >= treasureX[1]) {
			// treasure to top
			if (shipY[0] >= treasureY[0] && shipY[0] <= treasureY[1]) {
				score++;
				newTreasure();
				Main.increaseScore(1);
			}
			// treasure to bottom
			if (shipY[1] >= treasureY[0] && shipY[1] <= treasureY[1]) {
				score++;
				newTreasure();
				Main.increaseScore(1);
			}
			// treasure within
			if (shipY[0] <= treasureY[0] && shipY[1] >= treasureY[1]) {
				score++;
				newTreasure();
				Main.increaseScore(1);
			}
		}
		
	}
	
	// check bomb collisions if player selected hard difficulty
	public void bombCollide() {
		int[] bombX = {bomb.getX(), bomb.getX() + 25};
		int[] bombY = {bomb.getY(), bomb.getY() + 25};
		int[] shipX = {label.getX(), label.getX() + 128};
		int[] shipY = {label.getY(), label.getY() + 128};
		// bomb to left
			if (shipX[0] >= bombX[0] && shipX[0] <= bombX[1]) {
				//bomb to top
				if (shipY[0] >= bombY[0] && shipY[0] <= bombY[1]) {
					score--;
					Main.decreaseScore(1);
					newBomb();
				}
				// bomb to bottom
				if (shipY[1] >= bombY[0] && shipY[1] <= bombY[1]) {
					score--;
					Main.decreaseScore(1);
					newBomb();
				}
				// bomb within
				if (shipY[0] <= bombY[0] && shipY[1] >=	bombY[1]) {
					score--;
					Main.decreaseScore(1);
					newBomb();
				}
			}
			//bomb to right
			if (shipX[1] >= bombX[0] && shipX[1] <= bombX[1]) {
				//bomb to top
				if (shipY[0] >= bombY[0] && shipY[0] <= bombY[1]) {
					score--;
					Main.decreaseScore(1);
					newBomb();
				}
				// bomb to bottom
				if (shipY[1] >= bombY[0] && shipY[1] <= bombY[1]) {
					score--;
					Main.decreaseScore(1);
					newBomb();
				}
				// bomb within
				if (shipY[0] <= bombY[0] && shipY[1] >=	bombY[1]) {
					score--;
					newBomb();
					Main.decreaseScore(1);
				}
			}
			// bomb within x
			if (shipX[0] <= bombX[0] && shipX[1] >= bombX[1]) {
				// bomb to top
				if (shipY[0] >= bombY[0] && shipY[0] <= bombY[1]) {
					score--;
					newBomb();
					Main.decreaseScore(1);
				}
				// bomb to bottom
				if (shipY[1] >= bombY[0] && shipY[1] <= bombY[1]) {
					score--;
					newBomb();
					Main.decreaseScore(1);
				}
				// bomb within
				if (shipY[0] <= bombY[0] && shipY[1] >= bombY[1]) {
					score--;
					newBomb();
					Main.decreaseScore(1);
				}
			}
	}
	
	public void gameOver() {
		// dispose this frame
		this.dispose();
		// add score to results
		Results.addScore("Treasure Hunt", score);
		// go back to space travel
		new SpaceFrameDodge();
	}
	


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		// move spaceship
		case 38: 
			// move up
			if(label.getY() != 100) {
				label.setLocation(label.getX(),  label.getY() - 5);
			};
			break;
		case 40: 
			// move down
			if (label.getY() != 700 - 165) {
			label.setLocation(label.getX(), label.getY() + 5); 
			}
			break;
		case 37:
			// move left
			if (label.getX() != 0) {
				label.setLocation(label.getX() - 5, label.getY());
			}
			break;
		case 39: 
			// move right
			if (label.getX() != 700-140) {
				label.setLocation(label.getX() + 5,  label.getY());
			}
			break;
		
	}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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
		// pause and go to results
		if (e.getSource() == results) {
			// add score to results
			Results.addScore("Treasure Hunt", score);
			// dispose frame
			this.dispose();
			// go to results page
			new Results();
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
