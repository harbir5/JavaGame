package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;


/*
 * Quiz Game
 * 
 * This is the quiz mini-game.  The player is presented with a question and given 4 options.  
 * 
 * After selecting an answer, the buttons are disabled and the player is given green for correct or red for wrong.
 * Points are awarded for a correct answer and deducted for a wrong answer. 
 * After answering, the timer is set to 5 seconds, after which the player is returned to the space travel frame. 
 * 
 */

public class QuizFrame3 extends JFrame implements MouseListener {

	// initialize buttons for 4 quiz choices
	JButton buttonA;
	JButton buttonB;
	JButton buttonC;
	JButton buttonD;
	
	// seconds to answer question
	int seconds = 25;
	// question object
	Question questionInfo;
	
	// button to go to results
	JButton results;
	// score for this mini-game
	int score;
	// timer for this mini-game
	Timer timer0;
	
	QuizFrame3() {
		// set JFrame attributes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		this.setResizable(false);
		
		// add instructions and score display
		this.add(Main.scoreDisplay);
		this.add(Main.instruction);
		Main.instruction.setText("Choose correct answer!");
		
		// add timer display, set location/size, font, alignment, text
		JTextField timeDisplay = new JTextField();
		timeDisplay.setFocusable(false);
		timeDisplay.setBounds(180, 0, 200, 100);
		timeDisplay.setText(Integer.toString(seconds) + " sec");
		timeDisplay.setFont(new Font("Verdana", Font.PLAIN, 30));
		timeDisplay.setHorizontalAlignment(JTextField.CENTER);
		this.add(timeDisplay);
		
		// results button
		results = new JButton("Results/Pause");
		results.setBounds(535, 0, 150, 100);
		results.addMouseListener(this);
		results.setFocusable(false);
		this.add(results);
		
		// initialize this game's score to 0 to start
		this.score = 0;
		
		// game timer
		timer0 = new Timer(1000, e -> {
			// decrement seconds 
			seconds--;
			timeDisplay.setText(Integer.toString(seconds) + " sec");
			// when time runs out, call game over function
			if (seconds == 0) {
				gameOver();
			}
			
			});
		timer0.start();	
		
		// call factory with easy or hard question - also using Strategy pattern to select question type
		if (Start.dif) {
			questionInfo = QuestionFactory.create("hard");
		} else {
			questionInfo = QuestionFactory.create("easy");
		}
		
		// set question
		JTextField question = new JTextField();
		question.setText(questionInfo.getQuestion());
		question.setBounds(0, 100, 700, 100);
		question.setHorizontalAlignment(JTextField.CENTER);		
		question.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.add(question);
		this.setVisible(true);
		
		// set choices, locations, size, font
		JLabel choiceA = new JLabel(questionInfo.getChoices()[0]);
		choiceA.setBounds(150, 200, 500, 100);
		choiceA.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.add(choiceA);
		
		
		JLabel choiceB = new JLabel(questionInfo.getChoices()[1]);
		choiceB.setBounds(150, 300, 500, 100);
		choiceB.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.add(choiceB);
		
		JLabel choiceC = new JLabel(questionInfo.getChoices()[2]);
		choiceC.setBounds(150, 400, 500, 100);
		choiceC.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.add(choiceC);
		
		JLabel choiceD = new JLabel(questionInfo.getChoices()[3]);
		choiceD.setBounds(150, 500, 500, 100);
		choiceD.setFont(new Font("Verdana", Font.PLAIN, 20));
		this.add(choiceD);
		
		// set buttons for answer along with location, size and mouse listeners
		buttonA = new JButton("A");
		buttonA.addMouseListener(this);
		buttonA.setBounds(0, 200, 100, 100);
		this.add(buttonA);
		buttonB = new JButton("B");
		buttonB.addMouseListener(this);
		buttonB.setBounds(0, 300, 100, 100);
		this.add(buttonB);
		buttonC = new JButton("C");
		buttonC.addMouseListener(this);
		buttonC.setBounds(0, 400, 100, 100);
		this.add(buttonC);
		buttonD = new JButton("D");
		buttonD.addMouseListener(this);
		buttonD.setBounds(0, 500, 100, 100);
		this.add(buttonD);
	}
	
	public void gameOver() {
		// send score to results class
		Results.addScore("Question", score);
		// dispose this frame
		this.dispose();
		// return to main space travel game
		new SpaceFrameDodge();
	}
	
	//disable buttons after selection made
	public void disableButtons() {
		seconds = 5;
		buttonA.removeMouseListener(this);
		buttonB.removeMouseListener(this);
		buttonC.removeMouseListener(this);
		buttonD.removeMouseListener(this);
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
			Results.addScore("Question", score);
			timer0.stop();
			this.dispose();
			new Results();
		}
		// check if answer correct and award/take away points based on difficulty
		if (e.getSource() == buttonA) {
			// correct choice, increase score (5 points for difficult, 3 for easy), color correct button green and disable buttons
			if (buttonA.getText() == questionInfo.getAnswer()) {
				if (Start.dif) {
					Main.increaseScore(5);
					score += 5;
					buttonA.setBackground(Color.green);
					disableButtons();
				} else {
					Main.increaseScore(3);
					score += 3;
					buttonA.setBackground(Color.green);
					disableButtons();
				}
			} else {
				// wrong choice, decrease score, color button red and disable buttons
				Main.decreaseScore(1);
				score--;
				buttonA.setBackground(Color.red);
				disableButtons();
			}
		}
		if (e.getSource() == buttonB) {
			// correct choice, increase score (5 points for difficult, 3 for easy), color correct button green and disable buttons
			if (buttonB.getText() == questionInfo.getAnswer()) {
				if (Start.dif) {
					Main.increaseScore(5);
					score += 5;
					buttonB.setBackground(Color.green);
					disableButtons();
				} else {
					Main.increaseScore(3);
					score +=3;
					buttonB.setBackground(Color.green);
					disableButtons();
				}
			} else {
				// wrong choice, decrease score, color button red and disable buttons
				Main.decreaseScore(1);
				score--;
				buttonB.setBackground(Color.red);
				disableButtons();
			}
		}
		if (e.getSource() == buttonC) {
			if (buttonC.getText() == questionInfo.getAnswer()) {
				// correct choice, increase score (5 points for difficult, 3 for easy), color correct button green and disable buttons
				if (Start.dif) {
					Main.increaseScore(5);
					score += 5;
					buttonC.setBackground(Color.green);
					disableButtons();
				} else {
					Main.increaseScore(3);
					score +=3;
					buttonC.setBackground(Color.green);
					disableButtons();
				}
			} else {
				// wrong choice, decrease score, color button red and disable buttons
				Main.decreaseScore(1);
				score--;
				buttonC.setBackground(Color.red);
				disableButtons();
			}
		}
		if (e.getSource() == buttonD) {
			if (buttonD.getText() == questionInfo.getAnswer()) {
				// correct choice, increase score (5 points for difficult, 3 for easy), color correct button green and disable buttons
				if (Start.dif) {
					Main.increaseScore(5);
					score += 5;
					buttonD.setBackground(Color.green);
					disableButtons();
				} else {
					Main.increaseScore(3);
					score +=3;
					buttonD.setBackground(Color.green);
					disableButtons();
				}
			} else {
				// wrong choice, decrease score, color button red and disable buttons
				Main.decreaseScore(1);
				score--;
				buttonD.setBackground(Color.red);
				disableButtons();
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
