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
import javax.swing.JTextField;
import javax.swing.Timer;


/*
 * Space Travel Game
 * 
 * This is the base game where the player used the arrow keys to dodge asteroids.
 * 
 * In easy mode, the asteroids travel slower compared to hard mode. 
 * 
 * 1 point is earned for dodging an asteroid and 1 point is deducted for a collision with an asteroid.
 */


public class SpaceFrameDodge extends JFrame implements KeyListener, MouseListener {

	
	static final int SCREEN_WIDTH = 650;
	static final int SCREEN_HEIGHT = 675;
	Timer timer0;
	Timer timer1;
	Timer timer2;
	int seconds = 30;
	Random rando = new Random();
	
	// this is the spaceship that the player controls
	public JLabel label;
	// the is the array of possible asteroids, there can only be a maximum of 5 asteroids at a given time
	static JLabel[] asteroids = new JLabel[5];
	JTextField timeDisplay;
	JButton results;
	int score;
	
	public SpaceFrameDodge() {
		// set frame defaults: close, 700px X 700px, key listener
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		this.addKeyListener(this);
		
		// create spaceship player icon, set location/size/font/image/background
		label = new JLabel();
		ImageIcon icon = new ImageIcon(getClass().getResource("rocket_icon.png"));
		label.setBounds(275, 275, 128, 128);
		label.setBackground(Color.magenta);
		label.setOpaque(true);
		label.setIcon(icon);
		
		// add score display, instructions and background
		this.getContentPane().setBackground(Color.black);
		this.add(label);
		this.setVisible(true);
		this.add(Main.scoreDisplay);
		Main.scoreDisplay.setFocusable(false);
		this.add(Main.instruction);
		Main.instruction.setText("Dodge the asteroids!");
		
		// make time display and set text/font/size
		timeDisplay = new JTextField();
		timeDisplay.setFocusable(false);
		timeDisplay.setBounds(180, 0, 200, 100);
		timeDisplay.setFont(new Font("Verdana", Font.PLAIN, 30));
		timeDisplay.setHorizontalAlignment(JTextField.CENTER);
		timeDisplay.setText(Integer.toString(seconds) + " sec");
		this.add(timeDisplay);
		
		// results/pause button, set size/location, add mouse listener
		results = new JButton("Results/Pause");
		results.setBounds(535, 0, 150, 100);
		results.addMouseListener(this);
		results.setFocusable(false);
		this.add(results);
		
		// initialize score
		this.score = 0;
		
		startGame();
	}
	
	public void startGame() {
		// game timer 
		timer0 = new Timer(1000, e -> {
			// decrement game timer
			seconds--;
			timeDisplay.setText(Integer.toString(seconds) + " sec");
			// which time runs out, call game over
			if (seconds == 0) {
				gameOver();
			}
			
			});
		timer0.start();	
		newAster();
		move();
	}
	
	
	
	public void newAster() {
		
		int generate;
		// set asteroid generation time based on difficulty - strategy pattern applied
		if (Start.dif) {
			generate = 2000;
		} else {
			generate = 4000;
		}
		// timer to create asteroids 
		timer1 = new Timer(generate, e -> {
			
			
			Random rando = new Random();
			// select random asteroid index
			int randoInd = rando.nextInt(5);
			if (asteroids[randoInd] == null) {
				// select a location for the asteroid
				int asteroidX = rando.nextInt(SCREEN_WIDTH);
				asteroids[randoInd] = new JLabel();
				ImageIcon icon = new ImageIcon(Main.class.getResource("asteroid.png"));
				// initialize asteroids 
				Image image = icon.getImage();
				Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				ImageIcon now = new ImageIcon(newImg);
				asteroids[randoInd].setIcon(now);
				asteroids[randoInd].setBackground(Color.white);
				asteroids[randoInd].setOpaque(true);
				asteroids[randoInd].setBounds(asteroidX, 0, 50, 50);
				// add asteroid to frame
				this.add(asteroids[randoInd]);
			}
			
		});
		timer1.start();	
	}
	
	
	public void move() {
		
		int move;
		// set asteroid moving speed based on difficulty
		if (Start.dif) {
			move = 3;
		} else {
			move = 3;
		}
		
		timer2 = new Timer(30, e -> {
			// move asteroid down the frame
			for (int i = 0; i < 5; i++) {
				if (asteroids[i] != null) {
					asteroids[i].setLocation(asteroids[i].getX(), asteroids[i].getY() + move);
					if (asteroids[i].getY() >= SCREEN_HEIGHT) {
						// remove asteroid and increase score if it reaches bottom of the screen
						this.remove(asteroids[i]);
						Main.increaseScore(1);
						score++;
						asteroids[i] = null;
					}
				}
				
			}
			// check asteroid collisions by looping through asteroids
			for (int i = 0; i < asteroids.length; i++) {
				if(asteroids[i] != null) {
					// calculate asteroid width and height
					int[] asteroidX = {asteroids[i].getX(), asteroids[i].getX() + 50};
					int[] asteroidY = {asteroids[i].getY(), asteroids[i].getY() + 50};
					// calculate spaceship width and height
					int[] shipX = {label.getX(), label.getX() + 128};
					int[] shipY = {label.getY(), label.getY() + 128};
					
					//spaceship's left side is within an asteroid
					if (shipX[0] >= asteroidX[0] && shipX[0] <= asteroidX[1]) {
						// spaceship's top is within asteroid = collision
						if (shipY[0] >= asteroidY[0] && shipY[0] <= asteroidY[1]) {
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
						// spaceship's bottom is within asteroid = collision
						if (shipY[1] >= asteroidY[0] && shipY[1] <= asteroidY[1]) {
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
						// spaceship is within asteroid = collision
						if (shipY[0] <= asteroidY[0] && shipY[1] >= asteroidY[1]) {
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
 					}
					// spaceship's right side is within asteroid
					if (shipX[1] >= asteroidX[0] && shipX[1] <= asteroidX[1]) {
						if (shipY[0] >= asteroidY[0] && shipY[0] <= asteroidY[1]) {
							// spaceship's top is within asteroid = collision
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
						if (shipY[1] >= asteroidY[0] && shipY[1] <= asteroidY[1]) {
							// spaceship's bottom is within asteroid = collision
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
						if (shipY[0] <= asteroidY[0] && shipY[1] >= asteroidY[1]) {
							// spaceship's is within asteroid = collision
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
					}
					// spaceship horizontally aligns with asteroid
					if (shipX[0] <= asteroidX[0] && shipX[1] >= asteroidX[1]) {
						// spaceship's top is within asteroid = collision
						if (shipY[0] >= asteroidY[0] && shipY[0] <= asteroidY[1]) {
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
						if (shipY[1] >= asteroidY[0] && shipY[1] <= asteroidY[1]) {
							//spaceship's bottom is within asteroid = collision
							this.remove(asteroids[i]);
							Main.decreaseScore(1);
							score--;
							asteroids[i] = null;
							break;
						}
					}
				}
			}
			
		});
		timer2.start();		
		
		
	}
	
	
	public void gameOver() {
		// stop all timers when game has ended
		timer0.stop();
		timer1.stop();
		timer2.stop();
		// remove all asteroids
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[i] != null) {
				this.remove(asteroids[i]);
				asteroids[i] = null;
			}
			
		}
		// send score to singleton results arraylist
		Results.addScore("SpaceTravel", score);
		this.dispose();
		
		
		// generate random mini-game
		int randInt = rando.nextInt(3);
		
		if (randInt == 0) {
			new Treasure();
		} else if (randInt == 1) {
			new TicTacToeFrame();
		} else {
			new QuizFrame3();
		}
		
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// move your icon
		switch(e.getKeyCode()) {
			// move up
			case 38: 
				if(label.getY() != 100) {
					label.setLocation(label.getX(),  label.getY() - 5);
				};
				break;
			// move down
			case 40: 
				if (label.getY() != 700 - 165) {
				label.setLocation(label.getX(), label.getY() + 5); 
				}
				break;
			// move left
			case 37:
				if (label.getX() != 0) {
					label.setLocation(label.getX() - 5, label.getY());
				}
				break;
			// move right
			case 39: 
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
		
		// pause and move to results page
		if (e.getSource() == results) {
			timer0.stop();
			timer1.stop();
			timer2.stop();
			// remove asteroids
			for (int i = 0; i < asteroids.length; i++) {
				if (asteroids[i] != null) {
					this.remove(asteroids[i]);
					asteroids[i] = null;
				}
				
			}
			this.dispose();
			// add score to results page
			Results.addScore("Space Travel", score);
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
