package app;

import java.awt.Font;
import javax.swing.JTextField;

/* 
 * Main class
 * 
 * This is where the game is initialized. It has 3 static variables.
 * The first variable is the total score in the entire game. 
 * There 2 text field variables to hold the total score as well as hold the instructions of the game.
 * 
 * In  addition, there are two methods:
 * There is an increase and decrease score methods that will be called by the mini-games to increment/decrement the total score.
 * 
 */

public class Main {

	public static int totScore = 0;
	static JTextField scoreDisplay = new JTextField();
	static JTextField instruction = new JTextField();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// create score display for all components of game: initialize location, font, text and alignment
		scoreDisplay.setBounds(0, 0, 150, 100);
		scoreDisplay.setFont(new Font("Verdana", Font.PLAIN, 25));
		scoreDisplay.setText("Score: " + String.valueOf(totScore));
		scoreDisplay.setHorizontalAlignment(JTextField.CENTER);
		
		// create instruction template for all components of game: initialize location, size, alignment
		instruction.setBounds(390, 0, 140, 100);
		instruction.setHorizontalAlignment(JTextField.CENTER);
		instruction.setFocusable(false);
		
		// start game
		new Start();
		
		
		
	}
	// increase total score and update score display
	public static void increaseScore(int value) {
		totScore += value;
		scoreDisplay.setText("Score: " + String.valueOf(totScore));
	}
	// decrease total score and update score display
	public static void decreaseScore(int value) {
		totScore -= value;
		scoreDisplay.setText("Score: " + String.valueOf(totScore));
	}

}
