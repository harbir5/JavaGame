package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;


/*
 * Tic-Tac-Toe Mini-game
 * 
 * This game is a basic tic-tac-toe game.
 * 
 * The objective is to get three "O" in a row.
 * 
 * There is a computer player against the human player.  
 * 
 * In easy mode, the computer selects a random square. 
 * 
 * In hard mode, the computer is smarter and more calculated in its choices.
 */


public class TicTacToeFrame extends JFrame implements MouseListener{
	
	boolean pTurn = false;
	JButton[] buttons = new JButton[9];
	int moves = 0;
	int seconds = 30;
	JButton results;
	int score = 0;
	boolean gameDone = false;
	Timer timer0;
	
	TicTacToeFrame() {
		// set frame default setting: close, size, layout, visibility
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		this.setVisible(true);
		this.setFocusable(false);
		
		// add score display and instruction
		this.add(Main.scoreDisplay);
		this.add(Main.instruction);
		Main.instruction.setText("Tic-Tac-Toe! O Player");
		
		// add time display, set location/size, font, alignment
		JTextField timeDisplay = new JTextField();
		timeDisplay.setFocusable(false);
		timeDisplay.setBounds(180, 0, 200, 100);
		timeDisplay.setText(Integer.toString(seconds) + " sec");
		timeDisplay.setFont(new Font("Verdana", Font.PLAIN, 30));
		timeDisplay.setHorizontalAlignment(JTextField.CENTER);
		this.add(timeDisplay);
		
		// results button, location/size, mouse listener
		results = new JButton("Results/Pause");
		results.setBounds(535, 0, 150, 100);
		results.addMouseListener(this);
		results.setFocusable(false);
		this.add(results);
		
		this.score = 0;
		
		// timer for game
		timer0 = new Timer(1000, e -> {
			// decrement seconds
			seconds--;
			timeDisplay.setText(Integer.toString(seconds) + " sec");
			// if seconds are done, end game and return to space travel game
			if (seconds == 0) {
				Results.addScore("TicTacToe", score);
				timer0.stop();;
				this.dispose();
				new SpaceFrameDodge();
			}
			
			});
		timer0.start();	
		
		// add 8 tic-tac-toe squares, set their sizes and locations as well as add mouse listeners
		JButton button0 = new JButton();
		button0.setBounds(100, 150, 150, 150);
		button0.addMouseListener(this);
		this.add(button0);
		buttons[0] = button0;
		
		JButton button1 = new JButton();
		button1.setBounds(250, 150, 150, 150);
		button1.addMouseListener(this);
		this.add(button1);
		buttons[1] = button1;
		
		JButton button2 = new JButton();
		button2.setBounds(400, 150, 150, 150);
		button2.addMouseListener(this);
		this.add(button2);
		buttons[2] = button2;
		
		JButton button3 = new JButton();
		button3.setBounds(100, 300, 150, 150);
		button3.addMouseListener(this);
		this.add(button3);
		buttons[3] = button3;
		
		JButton button4 = new JButton();
		button4.setBounds(250, 300, 150, 150);
		button4.addMouseListener(this);
		this.add(button4);
		buttons[4] = button4;
		
		JButton button5 = new JButton();
		button5.setBounds(400, 300, 150, 150);
		button5.addMouseListener(this);
		this.add(button5);
		buttons[5] = button5;
		
		JButton button6 = new JButton();
		button6.setBounds(100, 450, 150, 150);
		button6.addMouseListener(this);
		this.add(button6);
		buttons[6] = button6;
		
		JButton button7 = new JButton();
		button7.setBounds(250, 450, 150, 150);
		button7.addMouseListener(this);
		this.add(button7);
		buttons[7] = button7;
		
		JButton button8 = new JButton();
		button8.setBounds(400, 450, 150, 150);
		button8.addMouseListener(this);
		this.add(button8);
		buttons[8] = button8;
		
		// set fonts for buttons
		for (JButton button : buttons) {
			button.setFont(new Font("Verdana", Font.PLAIN, 35));
		}
		
		// generate first turn randomly between player and computer
		Random rando = new Random();
		int randNum = rando.nextInt(2);
		if (randNum == 0) {
			pTurn = true;
		} else {
			if (Start.dif) {
				cTurnDif();
			} else {
				cTurn();
			}
		}
	}
	
	// end of game = disable buttons and color win/loss
	public void gameOver(int one, int two, int three, int who) {
		// if player wins, set buttons to green
		if (who == 0) {
			buttons[one].setBackground(Color.green);
			buttons[two].setBackground(Color.green);
			buttons[three].setBackground(Color.green);
		} else if (who == 1) {
			// if computer wins, set buttons to red
			buttons[one].setBackground(Color.red);
			buttons[two].setBackground(Color.red);
			buttons[three].setBackground(Color.red);
		}
		// disable buttons at end of game
		for (JButton button: buttons) {
			button.removeMouseListener(this);
		}
		// set seconds left to 5 at end of game to wrap things up
		seconds = 5;
		gameDone = true;
			
	}
	
	// easy computer strategy where it picks a random square
	public void cTurn() {
		Random rando = new Random();
		boolean guessing = true;
		// loop to select square, loop terminates when blank space is occupied
		while(guessing && moves != 9) {
			int num = rando.nextInt(9);
			if (buttons[num].getText() == "") {
				buttons[num].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
	}
	
	// harder computer strategy to try to defeat player
	public void cTurnDif() {
		if (moves == 9) {
			return;
		}
		
		// winning move: box 0 is chosen to complete three in a row (horizontal, vertical, diagnonal)
		if ((buttons[1].getText() == "X" && buttons[2].getText() == "X") || (buttons[3].getText() =="X" && buttons[6].getText() == "X") || (buttons[4].getText() == "X" && buttons[8].getText() == "X")) {
			if (buttons[0].getText() == "") {
				buttons[0].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 1 is chosen to complete three in a row (horizontal, vertical)
		if ((buttons[0].getText() == "X" && buttons[2].getText() == "X") || (buttons[4].getText() =="X" && buttons[7].getText() == "X")) {
			if (buttons[1].getText() == "") {
				buttons[1].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// winning move: box 2 is chosen to complete three in a row (horizontal, vertical, diagonal)
		if ((buttons[0].getText() == "X" && buttons[1].getText() == "X") || (buttons[5].getText() =="X" && buttons[8].getText() == "X") || (buttons[4].getText() == "X" && buttons[6].getText() == "X")) {
			if (buttons[2].getText() == "") {
				buttons[2].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 3 is chosen to complete three in a row (vertical, horizontal)
		if ((buttons[0].getText() == "X" && buttons[6].getText() == "X") || (buttons[4].getText() =="X" && buttons[5].getText() == "X")) {
			if (buttons[3].getText() == "") {
				buttons[3].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 4 is chosen to complete three in a row (diagonal, vertical, diagonal, horizontal)
		if ((buttons[0].getText() == "X" && buttons[8].getText() == "X") || (buttons[1].getText() =="X" && buttons[7].getText() == "X") || (buttons[2].getText() == "X" && buttons[6].getText() == "X") || (buttons[3].getText() == "X" && buttons[5].getText() == "X") ) {
			if (buttons[4].getText() == "") {
				buttons[4].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 5 is chosen to complete three in a row (horizontal, vertical)
		if ((buttons[3].getText() == "X" && buttons[4].getText() == "X") || (buttons[2].getText() =="X" && buttons[8].getText() == "X")) {
			if (buttons[5].getText() == "") {
				buttons[5].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 6 is chosen to complete three in a row (vertical, diagonal, horizontal)
		if ((buttons[0].getText() == "X" && buttons[3].getText() == "X") || (buttons[2].getText() =="X" && buttons[4].getText() == "X") || (buttons[7].getText() == "X" && buttons[8].getText() == "X")) {
			if (buttons[6].getText() == "") {
				buttons[6].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 7 is chosen to complete three in a row (vertical, horizontal)
		if ((buttons[1].getText() == "X" && buttons[4].getText() == "X") || (buttons[6].getText() =="X" && buttons[8].getText() == "X")) {
			if (buttons[7].getText() == "") {
				buttons[7].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// winning move: box 8 is chosen to complete three in a row (diagonal, vertical, horizontal)
		if ((buttons[0].getText() == "X" && buttons[4].getText() == "X") || (buttons[2].getText() =="X" && buttons[5].getText() == "X") || (buttons[6].getText() == "X" && buttons[7].getText() == "X")) {
			if (buttons[8].getText() == "") {
				buttons[8].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		
		// defense move: take box 0 to prevent player three in a row (horizontal, vertical, diagonal)
		if ((buttons[1].getText() == "O" && buttons[2].getText() == "O") || (buttons[3].getText() =="O" && buttons[6].getText() == "O") || (buttons[4].getText() == "O" && buttons[8].getText() == "O")) {
			if (buttons[0].getText() == "") {
				buttons[0].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 1 to prevent player three in a row (horizontal, vertical)
		if ((buttons[0].getText() == "O" && buttons[2].getText() == "O") || (buttons[4].getText() =="O" && buttons[7].getText() == "O")) {
			if (buttons[1].getText() == "") {
				buttons[1].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// defense move: take box 2 to prevent player three in a row (horizontal, vertical, diagonal)
		if ((buttons[0].getText() == "O" && buttons[1].getText() == "O") || (buttons[5].getText() =="O" && buttons[8].getText() == "O") || (buttons[4].getText() == "O" && buttons[6].getText() == "O")) {
			if (buttons[2].getText() == "") {
				buttons[2].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 3 to prevent player three in a row (vertical, horizontal)
		if ((buttons[0].getText() == "O" && buttons[6].getText() == "O") || (buttons[4].getText() =="O" && buttons[5].getText() == "O")) {
			if (buttons[3].getText() == "") {
				buttons[3].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 4 to prevent player three in a row (diagonal, vertical, diagonal, horizontal)
		if ((buttons[0].getText() == "O" && buttons[8].getText() == "O") || (buttons[1].getText() =="O" && buttons[7].getText() == "O") || (buttons[2].getText() == "O" && buttons[6].getText() == "O") || (buttons[3].getText() == "O" && buttons[5].getText() == "O") ) {
			if (buttons[4].getText() == "") {
				buttons[4].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 5 to prevent player three in a row (horizontal, vertical)
		if ((buttons[3].getText() == "O" && buttons[4].getText() == "O") || (buttons[2].getText() =="O" && buttons[8].getText() == "O")) {
			if (buttons[5].getText() == "") {
				buttons[5].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 6 to prevent player three in a row (vertical, diagonal, horizontal)
		if ((buttons[0].getText() == "O" && buttons[3].getText() == "O") || (buttons[2].getText() =="O" && buttons[4].getText() == "O") || (buttons[7].getText() == "O" && buttons[8].getText() == "O")) {
			if (buttons[6].getText() == "") {
				buttons[6].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 7 to prevent player three in a row (vertical, horizontal)
		if ((buttons[1].getText() == "O" && buttons[4].getText() == "O") || (buttons[6].getText() =="O" && buttons[8].getText() == "O")) {
			if (buttons[7].getText() == "") {
				buttons[7].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		} 
		// defense move: take box 8 to prevent player three in a row (diagonal, vertical, horizontal)
		if ((buttons[0].getText() == "O" && buttons[4].getText() == "O") || (buttons[2].getText() =="O" && buttons[5].getText() == "O") || (buttons[6].getText() == "O" && buttons[7].getText() == "O")) {
			if (buttons[8].getText() == "") {
				buttons[8].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		
		//prevent player from grabbing two corners
		if (buttons[1].getText() == "O" && buttons[3].getText() == "O") {
			if (buttons[0].getText() == "") {
				buttons[0].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// prevent player from grabbing two corners
		if (buttons[1].getText() == "O" && buttons[5].getText() == "O") {
			if (buttons[2].getText() == "") {
				buttons[2].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// prevent player from grabbing two corners
		if (buttons[5].getText() == "O" && buttons[7].getText() == "O") {
			if (buttons[8].getText() == "") {
				buttons[8].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// prevent player from grabbing two corners
		if (buttons[3].getText() == "O" && buttons[7].getText() == "O") {
			if (buttons[6].getText() == "") {
				buttons[6].setText("X");
				moves++;
				checkWin();
				pTurn = true;
				return;
			}
		}
		// if no move made so far, pick random square
		Random rando = new Random();
		boolean guessing = true;
		while(guessing && moves != 9) {
			int num = rando.nextInt(9);
			if (buttons[num].getText() == "") {
				buttons[num].setText("X");
				guessing = false;
				pTurn = true;
				moves++;
				checkWin();
			}
		}
		
	}
	
	// check for win and award points based on difficulty
	public void checkWin() {
		// horizontal player win - first row, add 5 points for hard/3 for easy
		if (buttons[0].getText() == "O" && buttons[1].getText() == "O" && buttons[2].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			};
			gameOver(0, 1 ,2, 0);
			return;
		}
		// horizontal computer win - first row, lose 1 point for hard/3 for easy
		if (buttons[0].getText() == "X" && buttons[1].getText() == "X" && buttons[2].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(0, 1, 2, 1);
			return;
		}
		// horizontal player win - second row, add 5 points for hard/3 for easy
		if (buttons[3].getText() == "O" && buttons[4].getText() == "O" && buttons[5].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score -= 3;
			}
			gameOver(3, 4, 5, 0);
			return;
		}
		// horizontal computer win - second row, lose 1 point for hard/3 for easy
		if (buttons[3].getText() == "X" && buttons[4].getText() == "X" && buttons[5].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(3, 4, 5, 1);
			return;
		}
		// horizontal player win - third row, add 5 points for hard/3 for easy
		if (buttons[6].getText() == "O" && buttons[7].getText() == "O" && buttons[8].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(6, 7, 8, 0);
			return;
		}
		// horizontal computer win - third row, lose 1 point for hard/3 for easy
		if (buttons[6].getText() == "X" && buttons[7].getText() == "X" && buttons[8].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				 score -= 3;
			}
			gameOver(6, 7, 8, 1);
			return;
		}
		// vertical player win - first column, add 5 points for hard/3 for easy
		if (buttons[0].getText() == "O" && buttons[3].getText() == "O" && buttons[6].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(0, 3, 6, 0);
			return;
		}
		// vertical computer win - first column, lose 1 point for hard/3 for easy
		if (buttons[0].getText() == "X" && buttons[3].getText() == "X" && buttons[6].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(0, 3, 6, 1);
			return;
		}
		// vertical player win - second column, add 5 points for hard/3 for easy
		if (buttons[1].getText() == "O" && buttons[4].getText() == "O" && buttons[7].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(1, 4, 7, 0);
			return;
		}
		// vertical computer win - second column, lose 1 point for hard/3 for easy
		if (buttons[1].getText() == "X" && buttons[4].getText() == "X" && buttons[7].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(1, 4, 7, 1);
			return;
		}
		// vertical player win - third column, add 5 points for hard/3 for easy
		if (buttons[2].getText() == "O" && buttons[5].getText() == "O" && buttons[8].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(2, 5, 8, 0);
			return;
		}
		// vertical computer win - third column, lose 1 point for hard/3 for easy
		if (buttons[8].getText() == "X" && buttons[5].getText() == "X" && buttons[8].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(2, 5, 8, 1);
			return;
		}
		// diagonal player win, add 5 points for hard/3 for easy
		if (buttons[0].getText() == "O" && buttons[4].getText() == "O" && buttons[8].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(0, 4, 8, 0);
			return;
		}
		// diagonal computer win, lose 1 point for hard/3 for easy
		if (buttons[0].getText() == "X" && buttons[4].getText() == "X" && buttons[8].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(0, 4, 8, 1);
			return;
		}
		// diagonal player win, add 5 points for hard/3 for easy
		if (buttons[2].getText() == "O" && buttons[4].getText() == "O" && buttons[6].getText() == "O"){
			if (Start.dif) {
				Main.increaseScore(5);
				score += 5;
			} else {
				Main.increaseScore(3);
				score += 3;
			}
			gameOver(2, 4, 6, 0);
			return;
		}
		// diagonal computer win, lose 1 point for hard/3 for easy
		if (buttons[2].getText() == "X" && buttons[4].getText() == "X" && buttons[6].getText() == "X"){
			if (Start.dif) {
				Main.decreaseScore(1);
				score--;
			} else {
				Main.decreaseScore(3);
				score -= 3;
			}
			gameOver(2, 4, 6, 1);
			return;
		}
		// tie, no score change
		if (moves == 9) {
			gameOver(0, 0, 0, 2);
			return;
		}
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
		// go to results page
		if (e.getSource() == results) {
			// add score to results page
			Results.addScore("TicTacToe", score);
			// stop time
			timer0.stop();
			// dispose frame and go to results
			this.dispose();
			new Results();
		}
		
		// user picks square 0
		if (e.getSource() == buttons[0]) {
			if (pTurn && buttons[0].getText() == "") {
				buttons[0].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 1
		if (e.getSource() == buttons[1]) {
			if (pTurn && buttons[1].getText() == "") {
				buttons[1].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 2
		if (e.getSource() == buttons[2]) {
			if (pTurn && buttons[2].getText() == "") {
				buttons[2].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 3
		if (e.getSource() == buttons[3]) {
			if (pTurn && buttons[3].getText() == "") {
				buttons[3].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 4
		if (e.getSource() == buttons[4]) {
			if (pTurn && buttons[4].getText() == "") {
				buttons[4].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 5
		if (e.getSource() == buttons[5]) {
			if (pTurn && buttons[5].getText() == "") {
				buttons[5].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 6
		if (e.getSource() == buttons[6]) {
			if (pTurn && buttons[6].getText() == "") {
				buttons[6].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 7
		if (e.getSource() == buttons[7]) {
			if (pTurn && buttons[7].getText() == "") {
				buttons[7].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
		}
		// user picks square 8
		if (e.getSource() == buttons[8]) {
			if (pTurn && buttons[8].getText() == "") {
				buttons[8].setText("O");
				pTurn = false;
				moves++;
				checkWin();
				if (Start.dif && !gameDone) {
					cTurnDif();
				} else if (!gameDone) {
					cTurn();
				}
			}
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
