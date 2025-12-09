package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import app.Main;
import app.Question;
import app.QuestionFactory;
import app.Results;
import app.SpaceFrameDodge;
import app.Start;
import app.Treasure;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

class AppTest {

	
	@Test 
	void testFactory() {
		// test question factory return, create question
		Question sample5 = QuestionFactory.create("easy");
		String question = sample5.getQuestion();
		// check in question string is not null
		assertTrue(question != null);
	}
	
	@Test
	void testResults() {
		// test singleton results class
		new Main();
		new SpaceFrameDodge().gameOver();
		// make sure game instance was saved in results
		assertEquals(1, Results.games.size());
	}
	
	@Test 
	void testMainScore() {
		// test overall score
		// increment score
		Main.increaseScore(2);
		assertEquals(2, Main.totScore);
		// decrement score
		Main.decreaseScore(1);
		assertEquals(1, Main.totScore);
	}

	@Test 
	void testSingleton() {
		// test overall score
		new Start();
		assertEquals(false, Start.dif);
	}
	
	@Test 
	void testResultsText() {
		// test field text
		new Results();
		assertEquals("Difficulty: Easy", Results.diffi.getText());
		
	}
	
	
	@Test
	void testMoveTreas() {
		// simulate press up key
		
		Treasure testing = new Treasure();
		KeyEvent key = new KeyEvent(testing, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
	    testing.getKeyListeners()[0].keyPressed(key);
		// see if label moved
		assertEquals(testing.label.getY(), 270);
	}
	
	@Test
	void testMoveMajor() {
		// simulate press key
		
		SpaceFrameDodge testing = new SpaceFrameDodge();
		KeyEvent key = new KeyEvent(testing, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
	    testing.getKeyListeners()[0].keyPressed(key);
		// see if label moved up
		assertEquals(testing.label.getY(), 270);
	}
	
	@Test
	void testSpeed() {
		// test frame generation speed for start page
		long curTime = System.currentTimeMillis();
		new Start();
		long endTime = System.currentTimeMillis();
		assertTrue(endTime-curTime < 1000);
	}

	
}
