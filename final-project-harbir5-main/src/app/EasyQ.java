package app;

// Database of easy questions.  Can increase further if desired in the future. 

public class EasyQ {
	
	// Has 3 properties - question, choices, answers. Think of "Who Wants to be a Millionaire"

	static String[] questions = {
			"Which state has the most national parks?",
			"Which country has the largest population?",
			"Which NFL team has won the most super bowls?", 
			"What is the only food that can never go bad?",
			"Which of the following is NOT a feature of React JS"
	};
	static String[][] choices = {{"Utah", "Alaska", "California", "Colorado"}, {"China", "United States", "India", "Indonesia"}, {"Dallas Cowboys", "Kansas City Chiefs", "San Francisco 49ers", "Pittsburgh Steelers"}, {"Honey", "Milk", "Eggs", "Apples"}, {"It is written in JSX", "It is an imperative language", "Only 1 component can be returned", "It utilizes the virtual DOM"}};
	static String[] answers = {"C", "C", "D", "A", "B"};
	
}
