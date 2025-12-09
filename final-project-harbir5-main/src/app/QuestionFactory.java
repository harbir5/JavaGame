package app;

/*
 * Factory class to return question based on 2 possible choices: easy or hard
 */

public class QuestionFactory {

	// string parameter choices: "easy", "hard"
	public static Question create(String difficulty) {
		
		return new Question(difficulty);
		
	}
	
}
