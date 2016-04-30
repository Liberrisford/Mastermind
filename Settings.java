/**
 * The Settings class holds all of settings of the game and checks that they are all of the correct format. The majority of the defensive programming is done in this class
 * as it is where alot of the user input data is handled. 
 * program.
 * @author liamberrisford
 * @release 05/01/2016
 *
 */

public class Settings {
	private int coloursInPlay;
	private String currentCode;
	private int codeLength;
	private int guessesAllowed;
	private boolean errorOutputOnce = false;
	
	//Constructor used to create an instance of the settings class.
	public Settings() {
	}
	
	/**
	 * Method used to check that the data input by the user for the amount of colours in the game is of the correct format and to store it in a field with the
	 * instance of the class.
	 * @param input - The data that was input by the user.
	 * @return - True is returned if the amount of colours that are to be in the game has correctly been set. False if returned if the user needs to input the 
	 * desired amount again.
	 */
	public boolean setNumberColours(String input) {
		
		//Trys to pass the input as an integer, if it is not an integer then it will ask the user to enter their input again.
		try {
			int amount = Integer.parseInt(input);
			
			//Check to make sure that the value that the user entered is of the correct magnitude. If not they will be asked to input their value again.
			if(amount < 3 || amount > 9) {
				//If statement to ensure that the error message is only printed out once.
				if(!errorOutputOnce) {
					System.err.println("Please enter an integer value between 4 and 8.");
					errorOutputOnce = true;
				}
				return false;
			} else {
				coloursInPlay = amount;
				return true;
			}
		} catch (NumberFormatException nfe) {
			//If statement to make sure that the error message is only printed out once. 
			if(!errorOutputOnce) {
				System.err.println("Please enter an integer value between 4 and 8.");
				errorOutputOnce = true;
			}
			return false;
		}
	}

	/**
	 * Method used to set the code that is to be cracked by code breaker. It also makes sure that the code only contains the permitted characters.
	 * @param input - This is the data that the user has input for the code.
	 * @return - True is returned if the user entered data that was permitted and false is returned if the data input was not correct and they need to renter their code.
	 */
	public boolean setCurrentCode(String input) {
		
		//Checks to make sure that the code that is input only contains the colours that are in the game, and no other non permitted characters.
		if(input.matches("^[" + "RGBYOPCM".substring(0, getColoursInPlay()) +"]+$")) {
			
			//Checks to make sure that the code that is entered does not go over the amount of colours that can make up the code.
			if(input.length() > 8) {
				//If statement to make sure that the error message is only printed out once.
				if(!errorOutputOnce) {
					System.err.println("The code you enetered is to long, please enter a code that is 8 colours long.");
					errorOutputOnce = true;
				}
				return false;
			} else {
				currentCode = input;
				setCodeLength(input);
				return true;
			}
		} else {
			//If statement to make sure that the error message is only printed out once.
			if(!errorOutputOnce) {
				System.err.println("Please only enter the charecters " + "RGBYOPCM".substring(0, getColoursInPlay()));
				errorOutputOnce = true;
			}
			return false;
		}
	}
	
	/**
	 * Method used to check that the guess that the user has entered is permitted, if it is correct both in terms of content and length then it will be tested against the 
	 * code that is being cracked.
	 * @param guess - This is what the user input for their guess of the code.
	 * @return - It will return true if it is in the correct form. False is returned if it is of an incorrect form.
	 */
	public boolean guessValidityCheck(String guess) {
		//True is only returned if both the guessLengthCheck and guessContentCheck also return true.
		if(guessLengthCheck(guess)) {
			if(guessContentCheck(guess)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method used to check the length of the input from the user.
	 * @param guess - This is the data that the user has entered for their guess. 
	 * @return - True is returned if the guess was of the correct length, false is returned if the guess is of an incorrect length. 
	 */
	public boolean guessLengthCheck(String guess){
		//True is only returned if the guess is equal to the length of the code. Otherwise the user is asked for another input and told the length that the input needs to be.
		if(guess.length() == getCodeLength()) {
			return true;
		} else {
			System.err.println("Please enter a guess for the code that is of length " + getCodeLength() + ".");
			return false;
		}
	}
	
	/**
	 * Method used to check the content of the input from the user.
	 * @param guess - This is the data that the user has entered for their guess.
	 * @return - True is returned if the guess was of the correct content, false is returned if the guess is of an incorrect content.
	 */
	public boolean guessContentCheck(String guess) {
		//The code colours left variable are the colours that can make up the code.
		String codeColoursLeft = "RGBYOPCM".substring(0, coloursInPlay);
		
		//Checks to make sure that the input from the user only contains the characters that could potentially make up the code.
		//If the code does not contain only the permitted characters then the user is told what characters are permitted.
		if(guess.matches("^[" + codeColoursLeft + "]+$")) {
				return true;
		}
		System.out.println("The code is only made up of " + "RGBYOPCM".substring(0, coloursInPlay));
		return false;
		
	}
	
	/**
	 * Method used to set the guesses that the code breaker is allowed to crack the code from the users input.
	 * @param input - This is the data that the user has input for the value they want to have as guesses allowed for the code breaker.
	 * @return - True is returned if the input was of the correct format and data type, if not then false is returned and the user is asked to input the value again.
	 */
	public boolean setGuessesAllowed(String input) {
		//The input is attempted to be changed to an integer if it can then the user is told what they need to input as the value.
		try {
			int amount = Integer.parseInt(input);
			//If the value entered for the value is between 10 and 60 then it will be assigned to the relevant field, if not then the user will be asked to reinput their value.
			if(amount < 10 || amount > 60) {
				//If statement to make sure that the error message is only printed out once.
				if(!errorOutputOnce) {
					System.err.println("Please enter an integer value between 10 and 60.");
					errorOutputOnce = true;
				}
				return false;
			} else {
				guessesAllowed = amount;
				return true;
			}
		} catch (NumberFormatException nfe) {
			//If statement to make sure that the error message is only printed out once.
			if(!errorOutputOnce) {
				System.err.println("Please enter an integer value between 10 and 60.");
				errorOutputOnce = true;
			}
			return false;
		}
	}
	
	//Mutator. Sets the length of the code that is to be cracked.
	public void setCodeLength(String input) {
		 codeLength = input.length();
	}

	//Accessor. @return - return the value of the amount of colours that the code could be made up of
	public int getColoursInPlay() {
		return coloursInPlay;
	}
	
	//Accessor. @return - returns the current code that needs to be cracked.
	public String getCurrentCode() {
		return currentCode;
	}
	
	//Accessor. @return - returns the length of the code that is trying to be cracked.
	public int getCodeLength() {
		return codeLength;
	}
	
	//Accessor. @return - returns the amount of guesses that have been made by the code breaker so far.
	public int getGuessesAllowed() {
		return guessesAllowed;
	}
	
	//Method to reset a boolean back to false so that each error message is only printed out once.
	public void resetErrorOutputOnce() {
		errorOutputOnce = false;
	}
	
	
	
	
}
