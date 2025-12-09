package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/* 
 * Results Class
 * 
 * This is the class that displays the results of the games played so far.
 * It displays the score for each game played - positive or negative.
 * It stores the games and score in arraylists and saves them in static variables.
 * It also displays the difficulty selected by the player on the Start frame at beginning of game.
 * 
 */


public class Results extends JFrame implements MouseListener {

	// lists of games and scores for each game played
	public static ArrayList<String> games = new ArrayList<String>();
	static ArrayList<Integer> scores = new ArrayList<Integer>();
	
	// initial y coordinate for the scores
	int yCoor = 125;
	// button to go back to game
	JButton results;
	public static JTextField diffi;
	
	public Results() {
		// set JFrame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setLayout(null);
		
		// add title, set location/size, font
		JLabel title = new JLabel("Results");
		title.setBounds(300, 0, 200, 100);
		title.setFont(new Font("Verdana", Font.PLAIN, 30));
		this.add(title);
		// add difficulty comment
		String dif;
		if (Start.dif) {
			dif = "Hard";
		} else {
			dif = "Easy";
		}
		// difficulty text field, set its size, color, location
		diffi = new JTextField("Difficulty: " + dif);
		diffi.setBounds(47, 32, 100, 50);
		diffi.setBackground(Color.yellow);
		diffi.setFocusable(false);
		diffi.setHorizontalAlignment(JTextField.CENTER);
		this.add(diffi);
		
		// resume button, set location and size
		results = new JButton("Resume");
		results.setBounds(535, 0, 150, 100);
		results.addMouseListener(this);
		results.setFocusable(false);
		this.add(results);
		
		// iterator pattern to display individual games and scores in JLabels, 
		// the y coordinate is increased at each iteration to stack the scores vertically
		for (int i = 0; i < games.size(); i++) {
			JLabel game = new JLabel(games.get(i));
			game.setBounds(195, yCoor, 150, 50);
			game.setFont(new Font("Verdana", Font.PLAIN, 15));
			this.add(game);
			JLabel score = new JLabel(String.valueOf(scores.get(i)));
			score.setBounds(430, yCoor, 150, 50);
			score.setFont(new Font("Verdana", Font.PLAIN, 15));
			this.add(score);
			yCoor += 50;
		}
		// line to separate individual scores from total score
		JLabel line = new JLabel();
		line.setBackground(Color.black);
		line.setBounds(153, yCoor + 8, 317, 3);
		line.setOpaque(true);
		this.add(line);
		
		// total score display
		JLabel total = new JLabel("Total");
		total.setBounds(205, yCoor, 100, 100);
		total.setFont(new Font("Verdana", Font.BOLD, 15));
		this.add(total);
		JLabel score = new JLabel(String.valueOf(Main.totScore));
		score.setBounds(425, yCoor, 100, 100);
		score.setFont(new Font("Verdana", Font.BOLD, 15));
		this.add(score);
		
		this.setVisible(true);
	}
	
	public static void addScore(String gameName, int score) {
		// this static method is called by the game to store to game name and its score earned
		games.add(gameName);
		scores.add(score);
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
		
		// resume game, dispose the results page and return to space travel
		if (e.getSource() == results) {
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
