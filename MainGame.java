import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * The MainGame class is where the Mastermind game itself is handled. It contains the methods that are used for calculating the pegs for a
 * guess that is input. It also handles the passing of the game settings to the save game method. 
 * @author liamberrisford
 * @release 05/01/2016
 * @See SaveLoagGameState.java
 *
 */

public class MainGame {
	private String currentGuess;
	private char[] possibleColours;
	private SaveLoadGameState saveGame;
	private int guessesTaken;
	private int gameMode;
	private ArrayList<String> previousGuesses = new ArrayList<String>();
	private boolean pastGuesses = false;
	
	//Constructor that will populate the possible array with all the potential colours that the code could have if 8 colours are choosen to be in the game
	//and creates an instance of the save game class. 
	public MainGame() {
		possibleColours = "RGBYOPCM".toCharArray();
		saveGame = new SaveLoadGameState();
	}
	
	/**
	 * Method that is called to check the code against the code and calculates how many pegs it will get.
	 * @param currentCodeString - This is the current code that the code breaker is trying to crack.
	 * @param currentGuessString - This is the guess for the code that is being tested against the code.
	 * @param codeLength - This is the length of the code that is trying to be cracked. 
	 * @return - It will return an integer array that will continue three integers, that are the value for the black, white and total pegs the guess recieved. 
	 */
	public int[] currentGuess(String currentCodeString, String currentGuessString, int codeLength) {
		//Increments the guesses that the code breaker has had every time that the method is called.
		if(!pastGuesses) {
			guessesTaken += 1;
		}
		
		//Adds the current guess to an array that can then be printed if the game is restored.
		previousGuesses.add(currentGuessString);
		
		//Creates two array lists of characters for the code and the guess and then will populate them with the string that were passed into the method as parameters.
		ArrayList<Character> currentCodeArray = new ArrayList<Character>();
		ArrayList<Character> currentGuessArray= new ArrayList<Character>();
		for(char c : currentCodeString.toCharArray()) {
			currentCodeArray.add(c);
		}
		for(char c : currentGuessString.toCharArray()) {
			currentGuessArray.add(c);
		}
		
		//Resets the values of all of the fields for the pegs each time the method is called, so only the pegs for the relevant guess are printed to the console.
		int whitePegs = 0;
		int blackPegs = 0;
		int totalPegs = 0;
		
		//The for loop will go through both of the arrays, starting at the end.
		for(int i = (codeLength-1); i >= 0; i--) {
			//If the two elements at the same position are equal to one another, then black and total pins are incremented, and the elements are removed from their
			//respective arrays. This is done so that it does not get counted for a white peg as well as a black peg.
			if(checkPins(currentCodeArray.get(i), currentGuessArray.get(i))) {
				blackPegs += 1;
				totalPegs += 1;
				currentGuessArray.remove(i);
				currentCodeArray.remove(i);
			} 
		}
	
		//The elements that are then left in the code array are put into a string, and assigned to the string untested code.
		StringBuilder builder = new StringBuilder(currentCodeArray.size());
		for(Character ch : currentCodeArray) {
			builder.append(ch);
		}
		String untestedCode = builder.toString();
		
		//The for loop goes through each of the characters that are left in the guess array, testing each character in the guess to see if it deserves a white peg or not.
		for(int i = 0; i < currentGuessArray.size(); i++) {
			
			//The if statement passes if the current character from the array is within the string of characters that did not get a black pegs .
			if(untestedCode.contains("" + currentGuessArray.get(i))) {
				//The amount of times that the character comes up in the untested code is then count and added to the both the white pegs and total pegs field.
				whitePegs += untestedCode.length() - untestedCode.replace("" + currentGuessArray.get(i), "").length();
				totalPegs += untestedCode.length() - untestedCode.replace("" + currentGuessArray.get(i), "").length();
				
				//All occurrences of this characters is then removed from the untested code, this will leave only characters that did not get either a black or white peg.
				untestedCode = untestedCode.replace("" + currentGuessArray.get(i), "");
			}
		}	
		
		//The user is then told how many pegs they recieved for their last guess.
		System.out.print("The pegs for the guess " + currentGuessString + " are: " );
		for(int i = 0; i < whitePegs; i++) {
			System.out.print("W");
		}
		for(int i = 0; i < blackPegs; i++) {
			System.out.print("B");
		}
		System.out.println(".");
		
		//The pegs integer array is then populated with all of the values for the pegs it recieved, and the array is then returned. 
		int[] pegs = {whitePegs, blackPegs, totalPegs};
		return pegs;
		
	}
	
	/**
	 * Method that is used to save the current state of the game so that it can be resumes. 
	 * @param currentCode - This is the code that is trying to be cracked in the game.
	 * @param currentGuess - This was the last guess that the codebreaker made. 
	 * @param guessesTaken - This is the amount of guesses that the codebreaker has made so far in this game.
	 * @param coloursInPlay - This is the amount of colours that the code could be. 
	 * @param gameMode - This is the game mode that the user was playing when they saved the game.
	 * @param guessesAllowed - This is the guesses that the code breaker has to be able to crack the code.
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveGame(String currentCode, String currentGuess, int guessesTaken, String coloursInPlay, int gameMode, int guessesAllowed) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		saveGame.saveGame(currentCode, currentGuess, guessesTaken, coloursInPlay, gameMode, guessesAllowed, previousGuesses);
	}
	
	/**
	 * Method that is used to output to the user which colours the code could be made up of.
	 * @param coloursInPlay - the amount of colours that could be in the code.
	 */
	public void possibleColours(int coloursInPlay) {
		//For loop that will go through the possible colours of the code and print them to the console.
		System.out.print("The colours that the code could be are: ");
		for(int i = 0; i < coloursInPlay; i++){
			System.out.print("" + possibleColours[i]);
		}
		System.out.println("");
	}
	
	/**
	 * Method used to check to see if the code and guess characters are equal to one another.
	 * @param code - the character from the code that is to be compared.
	 * @param guess - the character from the guess that is to be compared.
	 * @return - If true is returned then the two characters were equal, if false if returned then they were not the same.
	 */
	public boolean checkPins(char code, char guess) {
		if(code == guess) {
			return true;
		} 
		return false;
	}
	
	//Mutator. This is used to set the game mode that is currently being played.
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
	
	
	
	//Mutator. Sets the current guess field within the class. 
	public void setCurrentGuess(String currentGuess) {
		this.currentGuess = currentGuess;
	}
	
	//Mutator. sets the current guesses taken by the user.
	public void setGuessesTaken(int guessesTaken) {
		this.guessesTaken = guessesTaken;
	}
	
	//Accessor.@return - returns the game mode that is currently being played.   
	public int getGameMode() {
		return gameMode;
	}
	
	//Accessor.@return - returns the guesses that have been taken so far in the current game.
	public int getGuessesTaken() {
		return guessesTaken;
	}
	
	//Accessor.@return - returns the last guess that was made by the code breaker.
	public String getCurrentGuess() {
		return currentGuess;
	}
	
	//Method used to change a boolean so that the past guesses are to be printed out in the 
	//case that the game being played is a text based interface.
	public void pastGuessesTrue() {
		pastGuesses = true;
	}
}
