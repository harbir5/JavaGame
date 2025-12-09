Harbir S. Dhillon
CS5278 Final Project 

In this project, I constructed a Java video game. It consists of a Start page, a base space travel game and 3 mini-games (Tic-Tac-Toe, trivia, treasure hunt).  

The player starts at the start page and picks between two difficulty levels: easy and hard. These easy and hard modes reflect the strategy design pattern in each of the games. After pressing the start button, the game starts.

Each fresh instance of the game starts at the base space travel game. In this game, the player is assigned a spaceship and tasked with dodging the incoming asteroids.  This continues for 30 seconds. In hard mode, the asteroids are more plentiful and move faster. After the 30 seconds, this game is done and the program randomly selects from the following 3 mini-games. This game uses the iterator pattern when it checks for collisions in the screen.

In the treasure mini-game, the player is again assigned a spaceship and the objective is to acquire as many treasure boxes as possible before time runs out. As a treasure if acquired, a new instance of the treasure is placed somewhere on the screen. In hard mode, there is also a bomb placed on the screen which if landed on, will decrease the player's score. When the timer runs out, the player is returned to the space travel game.

In the quiz mini-game, the player is given a trivia question with 4 choices.  The player is given 25 seconds to answer the question.  The player only receives 1 chance to select the correct answer.  The player is awarded points based on the difficulty of the question. After the timer runs out, the player is returned the space travel game.

The tic-tac-toe game is simply the classic game from our childhoods where the objective is to select three squares in a row before your pattern.  In this game, the strategy pattern is used to dictate computer AI agent play. In easy mode, the computer selects a random box whereas in hard mode, the computer makes selections based on the state of the game and the player's moves. On a side note, I'm not sure if the computer strategy can be further optimized so that it will never lose. Something to ponder some day... After the game is over, the player is returned to the space travel game. 

Finally, there is a results page that displays the results for each game.  The results page uses a singleton pattern to keep track of all the games played and their respective scores. It then uses the iterator pattern to display the results to the player so they can analyze their performance.  This results page also allows the player to take a break from the game if they wish.  If the player wishes to return, they will be sent to the space travel game. 

Overall, the requirement of the project was to use 4 design patterns. I chose the factory pattern to control accessing my easy and hard trivia question database.  The strategy pattern was used to create an easy and difficult mode to the game.  The iterator pattern was used to display lists of scores to the player and check for interactions on the screen.  The singleton pattern was used to create an instance of a list that keep track of all the scores in the game. Please see the PatternExplanation text file for a detailed breakdown of the patterns usage. 
