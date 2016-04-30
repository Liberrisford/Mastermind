/**
 * The ComputerCodebreaker class is used for the computer code breaker and then use a methodical series of steps to break the code, only using 
 * the results of what pegs the guess returns to determine it next step. 
 * @author liamberrisford
 * @release 05/01/2016
 * @See MainGame.java
 */
public class ComputerCodebreaker {
	private String possibleColours;
	private char[] possibleColoursInCode;
	private int[] pegs;
	private MainGame newGame;
	private String currentGuess;
	private String currentCode;
	private int codeLength;
	private int coloursInPlay;
	private char[] unorderedCode;
	private char[] unsortedColours;
	private int guessesAllowed;
	private boolean GUI = false;
	private int guessesTaken;
	
	/**
	 * Constructor for the class. It populates the fields within the instance of the class from parameters, populates the other with their default and creates 
	 * an instance of the main game class.
	 * @param currentCode - This is the current code that is trying to be cracked.
	 * @param codeLength - This is the length of the code that is being cracked.
	 * @param coloursInPlay - THis is the amount of colours that the code can be made up of.
	 */
	public ComputerCodebreaker(String currentCode, int codeLength, int coloursInPlay, int guessesAllowed) {
		possibleColours = "RGBYOPCM";
		currentGuess = "";
		newGame = new MainGame();
		guessesTaken = 0;
		this.currentCode = currentCode;
		this.codeLength = codeLength;
		this.coloursInPlay = coloursInPlay;
		this.guessesAllowed = guessesAllowed;
	}
	
	/**
	 * This is the method that is called to break the code, it will call other methods to tackle different element of the code, such as the colours and order.
	 */
	public void codebreak() {
		
		//Char array of the possible colours that the code could be made up off.
		possibleColoursInCode = possibleColours.substring(0, coloursInPlay).toCharArray();
		
		//For the first guess of the code it will populate it with all of the colour R. 
		for(int i = 0; i < codeLength; i++) {
			currentGuess += "R";
		}
		
		//Calls a recursive method to find out which will calculate which and the amount of each colour that the code is made up of. This is then stored in the 
		//current guess field.
		coloursInCode(0);
		
		//The current guess is then put into two char arrays, the unordered code and and unsorted colours.
		unorderedCode = currentGuess.toCharArray();
		unsortedColours = currentGuess.toCharArray();
		
		//Iteration is then used to go through the whole of the current unordered guess, and work out the position.
		for(int i = 0; i < codeLength; i++) {
			//Method that is called that will calculate what colour is supposed to be in the position that is being passed as an arguement.
			sortCode(i);
			
		}
		
	}

	/**
	 * This method is used to calculate which colours are in the code and the amount. It works by testing how many pegs (black and white) that a guess recieves, 
	 * starting with RRRR. Whatever value for the pegs it then recieves it will move to that position in the guess. It will then change the colours from that point
	 * to the next colours, it will do this until the total pegs for the guess equals the code length. At which point it has found the colours that are in the code.
	 * @param coloursTested - This value is the colours that have so far been tested.
	 */
	public void coloursInCode(int coloursTested) {
		
		//The integer array pegs is populated with the value of all the pegs returned by the current guess method.
		pegs = newGame.currentGuess(currentCode, currentGuess, codeLength);
		guessesTaken += 1;
		//In the pegs array position 2, is where the value for the total pegs is kept, if the value is not equal to the code length then the program will keep calling
		//itself.
		if(!(pegs[2] == codeLength)) {
			//The current guess is put into a char array.
			char[] charCurrentGuess = currentGuess.toCharArray();
			
			//The for loop will start at the position at which the guess has for total pegs, it will then move through the pegs for the length of the code, and change
			//the rest of the characters in the string to the next colours to be tested.
			for(int j = pegs[2]; j < charCurrentGuess.length; j++) {
				charCurrentGuess[j] = possibleColoursInCode[coloursTested];
			}
			//The current guess is then changed to the new current guess from the char array.
			currentGuess = new String(charCurrentGuess);
		}
		
		//If the current guess has total pegs equal to the code length then it will print the colours the code, and then end the function. Otherwise the function
		//will be called with the next colours being tested.
		if(pegs[2] == codeLength) {
			System.out.println("The code contains the colours: " + currentGuess );
			return;
		} else {
			coloursInCode(coloursTested + 1);	
		}
	}
	
	/**
	 * This is the method that is used to work out the order in which the colours in the code come. It works by going through the list of possible colours that was
	 * determined by the coloursInCode method, and try each one in each position. It will start of trying each colours in position 0 (index), if the black pegs stay
	 * the same then it is known that it is not the needed colours, if the black pegs go up then that colours is now in the correct position, if the black pegs go 
	 * down then the pegs that was there before was in the correct position.
	 * @param position - This is the position in the guess whose colours is being calculated.
	 */
	public void sortCode(int position) {
		//This is the amount of black pegs that the last guess of the code got.
		int currentBlackPegs = pegs[1];
		//The string contains the last guess that was tested.
		String inputCode = new String(unorderedCode);
		
		//The for loop runs for the length of the code/
		for(int i = 0; i < currentGuess.length(); i++) {
			//The position in the code that is currently being tested is assigned one of the colours from the guess given by the coloursInCode method, it is then
			//put into a string.
			unorderedCode[position] = unsortedColours[i];
			String orderedCode = new String(unorderedCode);
			
			//The int array pegs is then populated with the values for the pegs that the new ordering of the guess recieves.
			pegs = newGame.currentGuess(currentCode, orderedCode, codeLength);
			guessesTaken += 1;
			//If the black pegs are equal to the code length then the last guess was the correct code, in which case the the state of the game and the code is 
			//printed, and the program exits.
			if(pegs[1] == codeLength) {
				System.out.println("The code has been found and is " + orderedCode);
				if(newGame.getGuessesTaken() > guessesAllowed) {
					System.out.println("It took the computer " + newGame.getGuessesTaken() + " guess to crack the code, the codemaker has won!");
				} else {
				System.out.println("It took the computer " + newGame.getGuessesTaken() + " guesses to crack the code, the code breaker has won!");
				}
				currentGuess = orderedCode;
				if(!GUI) {
					System.exit(0);
				}
			} 
			//If the black pegs for the new ordering is less than the previous guess then the unordered code is reverted to the last guess, and the black pegs in the
			//array are reverted to the value the previous guess reciveed.
			if(currentBlackPegs > pegs[1]) {
				unorderedCode = inputCode.toCharArray();
				pegs[1] = currentBlackPegs;
				return;
			} 
			//The black pegs that the new guess recieves is higher than the previous guess then the method ends and the next positions colour is calculated.
			if(pegs[1] > currentBlackPegs) {
				return;
			}
			//If the black pegs stay the same as the last guess then the next colours is tried.
		}
	}	
	
	//Accessor. Returns the code that was broken by the code breaker.
	public String getBrokenCode() {
		return currentGuess;
	}
	
	//Accessor. Returns the guesses taken by the current code breaker.
	public int getGuessesTaken() {
		return guessesTaken;
	}
	
	//Method called to change a boolean so that the result of some methods are changed when a GUI is being used.
	public void setGUIBoolean() {
		GUI = true;
	}
	
	
}
