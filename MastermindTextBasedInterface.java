import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Mastermind class is where the user decides where game mode they want to play, or if
 * they want to continue a previous game. 
 * @author liamberrisford
 * @version 1.0
 * @release 05/01/2016 
 * @See MainGame.java SaveLoadGameState.java
 */
public class MastermindTextBasedInterface {
	public static void main(String[] args) throws IOException {
		
		//Declare a boolean that will change to true once a a mode has been selected and a string where the input from the user will be stored.
		boolean modeChoosen = false;
		String input;
		
		//Sets up a buffered reader to take the user input.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//Setting up instances the of game mode and the save load game state classes so that they can be used in the class.
		GameMode newGame = new GameMode();
		SaveLoadGameState loadState = new SaveLoadGameState();
		
		//Where the user is told which options they have and what the corresponding integer input is.  
		System.out.println("Please choose the game mode from:");
		System.out.println("1 - Human codebreaker and codemaker.");
		System.out.println("2 - Computer codemaker and human codebreaker.");
		System.out.println("3 - Human codemaker and computer codebreaker.");
		System.out.println("4 - Computer codemaker and computer codebreaker.");
		System.out.println("5 - Load previous game.");
		
		//The do while loop will continue to run until the user input data that chooses a game mode. 
		do {
			input = br.readLine();
			
			//The try catch will make sure that the user only inputs an integer, and if they don't it will ask for the input again.
			try {
				int modeSelected = Integer.parseInt(input);
				if(modeSelected == 1) {
					newGame.humanCodemakerHumanCodebreaker();
					modeChoosen = true;
				} else if(modeSelected == 2) {
					newGame.computerCodemakerHumanCodebreaker();
					modeChoosen = true;
				} else if(modeSelected == 3) {
					newGame.humanCodemakerComputerCodebreaker();
					modeChoosen = true;
				} else if(modeSelected == 4) {
					newGame.computerCodemakerComputerCodebreaker();
					modeChoosen = true;
				} else if (modeSelected == 5) {
					loadState.loadGame();
					modeChoosen = true;
				}
			} catch(NumberFormatException e) {
				System.err.println("Please enter one of the specified intergers: 1,2,3,4,5.");
			}
			
		} while(!modeChoosen);
	}
}