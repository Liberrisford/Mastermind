import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * The GameMode class deals with all of the input by the user once the game mode has been selected. Depending on the game mode selected from the Mastermind main
 * class a different method within this class will be called.
 * @author liamberrisford
 * @release 05/01/2016
 * @See Settings.java, MainGame.java
 *
 */

public class GameMode {
	private String input;
	private BufferedReader br;
	private Settings newGameSettings;
	private MainGame newGame;
	private int blackPegs;
	private ArrayList<String> previousGuesses = new ArrayList<String>();
	
	/**
	 * Constructor for the game mode. Sets the black pegs to 0, and creates the buffered reader that is needed for the input. And instances for the classes settings
	 * and the main game.
	 */
	public GameMode() {
		br = new BufferedReader(new InputStreamReader(System.in));
		newGameSettings = new Settings();
		newGame = new MainGame();
		blackPegs = 0;
	}
	
	/**
	 * Method that is called when a both codebreaker and maker are to be human. 
	 * @throws IOException
	 */
	public void humanCodemakerHumanCodebreaker() throws IOException {
		
		//HUMAN CODEMAKER section of code.
		
		//Used to input the amount of colours that are to be in the game, it will continually ask for the value until it is entered in the desired format. 
		System.out.println("Please enter the amount of colours that are to be in the game:");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setNumberColours(input);
		} while(!newGameSettings.setNumberColours(input));
	
		//Used to input the code that is to be cracked by the opposition, it will continually ask for the code until it is entered in the desired format.
		System.out.println("Please enter the code which you wish your oppoent to break (in the form R=red etc):");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setCurrentCode(input);
		} while(!newGameSettings.setCurrentCode(input));
		
		//Used to input the amount of guesses that are allowed by the opposition, it will continually ask for the value until it is entered in the desired format. 
		System.out.println("Please enter the amount of guesses that the code breaker is allowed:");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setGuessesAllowed(input);
		} while(!newGameSettings.setGuessesAllowed(input));
		
		//Used to calculate the colours that the code could be given the amount of colours that are in the game.
		newGame.possibleColours(newGameSettings.getColoursInPlay());
	
		//This sets the game mode so that if the game is saved then the relevant game mode can be restarted. 
		newGame.setGameMode(1);
	
		//HUMAN CODEBREAKER section of code.
		
		//Gives the user information about the code that they need to crack and asks for their first guess.
		System.out.println("The code is made up of " + newGameSettings.getCodeLength() + " colours.");
		System.out.println("Please enter the first guess for the code:");
		
		//This part of the code deals with the input of the guess for the code by the user. It will wait until the user enter a guess and then process this input, 
		//through a series of checks.
		while((input=br.readLine()) != null) {
			
			//First it will check to see if the input is "savegame" if it is then it will save the game and end the program.
			if(saveGameCheck(input)) {
				System.out.println("Your game has been saved!");
			}
		
			//Then it will check to see if the input is "quit" if so then it will terminate the program and end the game.
			if(input.equals("quit")) {
				//If they do then the program will terminate.
				System.exit(0);
			}
			
			//It will then check that the user still has guesses left, if they do then it will pass this check and test the guess against the code.
			if(guessesTakenCheck(newGame.getGuessesTaken())) {
				return;
			}	
		
				
			//It will then check that the guess is of the correct format.
			if(newGameSettings.guessValidityCheck(input)) {
				
				//If it is of the correct format then it will stored within the settings.
				newGame.setCurrentGuess(input);
				previousGuesses.add(input);
				//It will then calculate and store the amount of black pegs that the guess gets. 
				blackPegs = newGame.currentGuess(newGameSettings.getCurrentCode(), newGame.getCurrentGuess(), newGameSettings.getCodeLength())[1];
			}
			
			//Checks to see if the guess that was entered for the is correct, if it is then it tells the user and ends the program. 
			if(winConditionCheck()) {
				return;
			}
		}
	}
	
	/**
	 * Method that is called when the code maker is the computer and there is a human codebreaker. 
	 * @throws IOException
	 * @See ComputerCodemaker.java
	 */
	public void computerCodemakerHumanCodebreaker() throws IOException {
		
		//COMPUTER CODEMAKER section of code. 
		
		//Creates an instance of the code maker class and will generate the code.
		ComputerCodemaker computerCodemaker = new ComputerCodemaker();
		computerCodemaker.generateCode();
		
		//It will then put into the settings all of the details about the code that was generated that are needed.
		newGameSettings.setNumberColours("" + computerCodemaker.getColoursInPlay());
		newGameSettings.setCurrentCode(computerCodemaker.getGeneratedCode());
		newGame.possibleColours(computerCodemaker.getColoursInPlay());
		
		//Random generates a number for the guessess allowed by the user and input them into the settings, and tell the user the result.
		newGameSettings.setGuessesAllowed("" + computerCodemaker.randomValueInRange(10, 60));
		System.out.println("The guesses allowed are " + newGameSettings.getGuessesAllowed() + ".");
		
		////This sets the game mode so that if the game is saved then the relevant game mode can be restarted.
		newGame.setGameMode(2);
	
		//HUMAN CODEBREAKER section of code. 
	
		//Gives the user information about the code that they need to crack and asks for their first guess.
		System.out.println("The code is made up of " + newGameSettings.getCodeLength() + " colours.");
		System.out.println("Please enter the first guess for the code:");
		
		//This part of the code deals with the input of the guess for the code by the user. It will wait until the user enter a guess and then process this input, 
		//through a series of checks.
		while((input=br.readLine()) != null) {	
			
			//First it will check to see if the input is "savegame" if it is then it will save the game and end the program.
			if(saveGameCheck(input)) {
				System.out.println("Your game has been saved!");
				return;
			}
			
			//Then it will check to see if the input is "quit" if so then it will terminate the program and end the game.
			if(input.equals("quit")) {
				//If they do then the program will terminate.
				System.exit(0);
			}
			
			//It will then check that the user still has guesses left, if they do then it will pass this check and test the guess against the code.
			if(guessesTakenCheck(newGame.getGuessesTaken())) {
				return;
			}	
			
			//It will then check that the guess is of the correct format.
			if(newGameSettings.guessValidityCheck(input)) {
				
				//If it is of the correct format then it will stored within the settings.
				newGame.setCurrentGuess(input);	
				
				//It will then calculate and store the amount of black pegs that the guess gets.
				blackPegs = newGame.currentGuess(computerCodemaker.getGeneratedCode(), newGame.getCurrentGuess(), computerCodemaker.getCodeLength())[1];
			}
			
			//Checks to see if the guess that was entered for the is correct, if it is then it tells the user and ends the program.
			if(winConditionCheck()) {
				return;
			}
		}
	}
	
	/**
	 * Method that is used when the codemaker is to be human and the codebreaker is the computer.
	 * @throws IOException
	 * @See ComputerCodebreaker.java
	 */
	public void humanCodemakerComputerCodebreaker() throws IOException {
		
		//HUMAN CODEMAKER section of code.
		
		//Used to input the amount of colours that are to be in the game, it will continually ask for the value until it is entered in the desired format. 
		System.out.println("Please enter the amount of colours that are to be in the game:");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setNumberColours(input);
		} while(!newGameSettings.setNumberColours(input));
			
		//Used to input the code that is to be cracked by the opposition, it will continually ask for the code until it is entered in the desired format.
		System.out.println("Please enter the code which you wish your oppoent to break (in the form R=red etc):");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setCurrentCode(input);
		} while(!newGameSettings.setCurrentCode(input));
		
		//Used to input the amount of guesses that are allowed by the opposition, it will continually ask for the value until it is entered in the desired format. 
		System.out.println("Please enter the amount of guesses that the code breaker is allowed:");
		do {
			input = br.readLine();
			newGameSettings.resetErrorOutputOnce();
			newGameSettings.setGuessesAllowed(input);
		} while(!newGameSettings.setGuessesAllowed(input));
				
		//Used to calculate the colours that the code could be given the amount of colours that are in the game.
		newGame.possibleColours(newGameSettings.getColoursInPlay());
		
		//This sets the game mode so that if the game is saved then the relevant game mode can be restarted.
		newGame.setGameMode(3);

		//COMPUTER CODEBREAKER section of code.
		
		//Creates a new instance of the computer code breaker class with the settings from the user input.
		ComputerCodebreaker computerCodebreaker = new ComputerCodebreaker(newGameSettings.getCurrentCode(), newGameSettings.getCodeLength(), newGameSettings.getColoursInPlay(), newGameSettings.getGuessesAllowed());
		
		//Calls the method that will crack the code and output the relevant details about how the computer cracked the code. 
		computerCodebreaker.codebreak();
	}
	
	/**
	 * Method that is called when both the code maker and code breaker are the computer.
	 * @throws IOException
	 * @See ComputerCodemaker.java, ComputerCodebreaker.java
	 */
	public void computerCodemakerComputerCodebreaker() {
		
		//COMPUTER CODEMAKER section of code. 
		
		//Creates an instance of the code maker class and will generate the code.
		ComputerCodemaker computerCodemaker = new ComputerCodemaker();
		computerCodemaker.generateCode();
				
		//It will then put into the settings all of the details about the code that was generated that are needed.
		newGameSettings.setNumberColours("" + computerCodemaker.getColoursInPlay());
		newGameSettings.setCurrentCode(computerCodemaker.getGeneratedCode());
		newGame.possibleColours(computerCodemaker.getColoursInPlay());
		
		//Random generates a number for the guessess allowed by the user and input them into the settings, and tell the user the result.
		newGameSettings.setGuessesAllowed("" + computerCodemaker.randomValueInRange(10, 60));
		System.out.println("The guesses allowed are " + newGameSettings.getGuessesAllowed() + ".");

		//This sets the game mode so that if the game is saved then the relevant game mode can be restarted.
		newGame.setGameMode(4);
		
		//COMPUTER CODEBREAKER section of code.
		
		//Creates a new instance of the computer code breaker class with the settings from the user input.
		ComputerCodebreaker computerCodebreaker = new ComputerCodebreaker(computerCodemaker.getGeneratedCode(), computerCodemaker.getCodeLength(), computerCodemaker.getColoursInPlay(), newGameSettings.getGuessesAllowed());
		
		//Calls the method that will crack the code and output the relevant details about how the computer cracked the code.
		computerCodebreaker.codebreak();
		}
	
	
	/**
	 * This is the method that is called to check to see if the input specifies that the user wants to save the game, if so then all of the settings about the 
	 * current state of the game are passed to the save game method within the main game class.
	 * @param input - This is the input from the user that is checked to see if the user wants to save the game.
	 * @return If they do it will save the game and then return true which will end the program, if not then false is returned and the game continues.
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean saveGameCheck(String input) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(newGame.getGuessesTaken() == 0) {
			System.err.println("Please make at least one guess for the code before saving the game!");
			return false;
		}
		
		//Checks to see if the user input is equal to "savegame" if so they have specified they want to save the game.
		if(input.equals("savegame")) {
			
			//Passes the need data from this instace into the save game method where the other needed data to save the data is gathered. 
			newGame.saveGame(newGameSettings.getCurrentCode(),newGame.getCurrentGuess(), newGame.getGuessesTaken(),"" + newGameSettings.getColoursInPlay(), newGame.getGameMode(), newGameSettings.getGuessesAllowed());
			return true;
		}
		return false;
	}
	
	/**
	 * This is the method that checks the amount of guesses that the human code breaker has taken, if they are over the amount specified by their opponent then 
	 * the program is ended and the the code breaker is told they lost. 
	 * @param guessesTaken - This is the amount of guesses the code breaker has so far taken.
	 * @return - If true is returned then the use does not have any more guesses and the program will end, if not then the program will continue.
	 */
	public boolean guessesTakenCheck(int guessesTaken) {
		
		//Tells the user how many guesses they have taken so far. 
		System.out.println("You have taken " + guessesTaken + " guesses so far.");
		
		//Checks if the user has had all of their permitted guesses so far. 
		if(guessesTaken > newGameSettings.getGuessesAllowed()) {
			System.out.println("You have had your " + newGameSettings.getGuessesAllowed() +  " guesses and not cracked the code, the codemaker has won!");
			return true;
		}
		return false;
	}
	
	/**
	 * This is the method that is called to check whether or not the current guess is correct and if they have won the game.
	 * @return - True is returned if they have won the game, and the program will end otherwise the game will continues.
	 */
	public boolean winConditionCheck() {
		
		//It checks to see if the amount of black pegs that was recieved for the last guess is equal to the length of the code, if so the
		//correct code was entered. 
		if(blackPegs == newGameSettings.getCodeLength()) {
			System.out.println("The code has been correctly cracked, the code breaker has won!");
			return true;
		}
		return false;
	}
	
	/**
	 * This is the method that is called when the game is to be restarted, it will then repopulate all of the needed fields with the state of what the last game 
	 * so that the game can be continued from the point where it was a. 
	 * @param currentCode - This is what the code for the saved game was.
	 * @param currentGuess - This is what the last guess the user made for the code was.
	 * @param guessesTaken - The amount of guesses the user has made when they saved the game.
	 * @param coloursInPlay - The colours that the code could be made up of.
	 * @param gameMode - The game mode that they were playing 
	 * @param guessesAllowed - The guesses that the user has to crack the code. 
	 * @throws IOException
	 */
	public void restartGame(String currentCode, String currentGuess, int guessesTaken, String coloursInPlay, int gameMode, String guessesAllowed) throws IOException {
		
		//Sets all of the fields in the needed class with all of relevant data, so that the game can be continued from the place where it was. 
		newGameSettings.setNumberColours(coloursInPlay);
		newGameSettings.setCurrentCode(currentCode);
		newGame.setGuessesTaken(guessesTaken);
		newGameSettings.setGuessesAllowed(guessesAllowed);
		newGame.possibleColours(Integer.parseInt(coloursInPlay));
		
		//Tells the user what the last guess they made for the code was and the pegs that they recieved for the guess.
		System.out.print("The last guess for the game was: " + currentGuess + ". ");
		newGame.currentGuess(currentCode, currentGuess, currentCode.length());
		
		//The code then just continues as a human codebreaker. 
		//HUMAN CODEBREAKER section of code.
		
		//Gives the user information about the code that they need to crack and asks for their first guess.
		System.out.println("The code is made up of " + newGameSettings.getCodeLength() + " colours.");
		System.out.println("Please enter the first guess for the code:");
				
		//This part of the code deals with the input of the guess for the code by the user. It will wait until the user enter a guess and then process this input, 
		//through a series of checks.
		while((input=br.readLine()) != null) {
					
			//First it will check to see if the input is "savegame" if it is then it will save the game and end the program.
			if(saveGameCheck(input)) {
				System.out.println("Your game has been saved");
				return;
			}
					
			//It will then check that the user still has guesses left, if they do then it will pass this check and test the guess against the code.
			if(guessesTakenCheck(newGame.getGuessesTaken())) {
				return;
			}	
					
			//It will then check that the guess is of the correct format.
			if(newGameSettings.guessValidityCheck(input)) {
						
				//If it is of the correct format then it will stored within the settings.
				newGame.setCurrentGuess(input);
				previousGuesses.add(input);
				
				//It will then calculate and store the amount of black pegs that the guess gets. 
				blackPegs = newGame.currentGuess(newGameSettings.getCurrentCode(), newGame.getCurrentGuess(), newGameSettings.getCodeLength())[1];
			}
					
			//Checks to see if the guess that was entered for the is correct, if it is then it tells the user and ends the program. 
			if(winConditionCheck()) {
				return;
			}
		}
	}
}