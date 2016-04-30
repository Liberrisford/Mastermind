import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * The SaveLoadGameState class deals with the saving and the restoring of a game. It will write the game setting to a file that can then be 
 * read if the player wants to restart the game later on. 
 * @author liamberrisford
 * @release 05/01/2016
 *
 */

public class SaveLoadGameState {
	private String currentCode;
	private String currentGuess;
	private int guessesTaken;
	private String coloursInPlay;
	private int gameMode;
	private String guessesAllowed;
	private boolean GUIInterface = false;
	private ArrayList<String> savedData = new ArrayList<String>();
	
	//Constructor used to create an instance of the SaveLoadGameState class.
	public SaveLoadGameState() {
	}
	
	/**
	 * Method that is used to save the current state of the game so that it can be resumed later on. It works by writing all of the settings of the current game state
	 * to a file that can then be accessed after the program has ended.
	 * @param currentCode - This is the code for the current game.
	 * @param currentGuess - This is the last guess that the code breaker made.
	 * @param guessesTaken - This is the amount of guesses that have been taken by the code breaker.
	 * @param coloursInPlay - These are the colours that the code in the current game could be made up of.
	 * @param gameMode - This is the game mode that was being played when the user went to save the game.
	 * @param guessesAllowed - This is the amount of guesses that the code breaker has in the current game.
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveGame(String currentCode, String currentGuess, int guessesTaken, String coloursInPlay, int gameMode, int guessesAllowed, ArrayList<String> previousGuesses) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		//Stores the integer values into a string so that they can be wrote to a file.
		String guessesTakenString = "" + guessesTaken;
		String gameModeString = "" + gameMode;
		String guessesAllowedString = "" + guessesAllowed;

		//The method will write to a file all of the Strings, with a new line being made after each line so that all of the strings are on successive lines.
		//It will override the current save file in the directory. 
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SavedGame.txt"), "utf-8"))) {
			writer.write(currentCode);
			writer.write(System.getProperty("line.separator"));
			writer.write(currentGuess);
			writer.write(System.getProperty("line.separator"));
			writer.write(guessesTakenString);
			writer.write(System.getProperty("line.separator"));
			writer.write(coloursInPlay);
			writer.write(System.getProperty("line.separator"));
			writer.write(gameModeString);
			writer.write(System.getProperty("line.separator"));
			writer.write(guessesAllowedString);
			writer.write(System.getProperty("line.separator"));
					
			//Writes all of the previous guesses that the player has made during the game to the save game file.
			for(int i = 0; i < previousGuesses.size(); i++) {
				writer.write(previousGuesses.get(i));
				writer.write(System.getProperty("line.separator"));
			}
		}
	}
	
	/**
	 * Method used to load a previously saved game. It will read the save file and populate the fields within the class with the data in the files.
	 * @throws IOException
	 * @See GameMode.java
	 */
	public ArrayList<String> loadGame() throws IOException {
		//Creates an instance of the game mode class and a buffered reader to read the data in the file from the SaveGame.txt file.
		GameMode continueGame = new GameMode();
		MainGame previousGameResult = new MainGame();
		BufferedReader reader = new BufferedReader(new FileReader("SavedGame.txt"));
		
		//Assigns each of the fields in the class with the next line in the file.
		currentCode = reader.readLine();
		currentGuess = reader.readLine();
		guessesTaken = Integer.parseInt(reader.readLine());
		coloursInPlay = reader.readLine();
		gameMode = Integer.parseInt(reader.readLine());
		guessesAllowed = reader.readLine();		
		
		//Method called to change a boolean that will stop the guesses taken from incrementing.
		previousGameResult.pastGuessesTrue();
				
		//Will continue to read the guesses from the file as long as there is still data in the file.
		String input;
		while((input = reader.readLine()) != null) {
			//Will calculate the amount of pegs for the guess and print them to the console.
			previousGameResult.currentGuess(currentCode, input, currentCode.length());
		}
		
		reader.close();
		
		//If statement that is only ran if the version of the game being played is the text based version.
		if(!GUIInterface) {
			//The method in the Game Mode class is then called with all of the data so that the game can be restarted from where it was saved by the user.
			continueGame.restartGame(currentCode, currentGuess, guessesTaken, coloursInPlay, gameMode, guessesAllowed);
		}
		
		//Adds all of the data from the saved game text file and puts them into an array list to be returned
		//if the version of the game being played is the GUI version.
		savedData.add(currentCode);
		savedData.add(currentGuess);
		savedData.add("" + guessesTaken);
		savedData.add(coloursInPlay);
		savedData.add("" + gameMode);
		savedData.add(guessesAllowed);
		return savedData;
	}
	
	//Method that is called when the version of the game being played uses the GUI.
	public void setGUI() {
		GUIInterface = true;
	}
	
}

