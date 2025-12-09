package app;

import java.util.Random;


/* 
 * Question class
 * 
 * This is the class that returns a question. It is called by the factory class and returns a question.
 * 
 * It has 3 variables and 3 getter methods for each variable (question, choices, answer).  
 */

public class Question {

	String question;
	String[] choices;
	String answer;
	Random rando = new Random();
	
	Question(String difficulty) {
		if (difficulty == "easy") {
			// return easy question: there are 5 total easy questions and a random one is chosen from the database
			int index = rando.nextInt(5);
			this.question = EasyQ.questions[index];
			this.choices = EasyQ.choices[index];
			this.answer = EasyQ.answers[index];
		} else {
			// return hard question: there are 3 total hard questions right now and a random one is chosen
			int index = rando.nextInt(3);
			this.question = HardQ.questions[index];
			this.choices = HardQ.choices[index];
			this.answer = HardQ.answers[index];
		}
	}
	
	// get method for question
	public String getQuestion() {
		return question;
	}
	// get method for choices
	public String[] getChoices() {
		return choices;
	}
	// get method for answer
	public String getAnswer() {
		return answer;
	}
	
}
