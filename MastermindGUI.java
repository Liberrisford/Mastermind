import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * The Mastermind Graphical User Interface class deals with all of the elements of the game that are needed for the GUI. It includes the methods that are used within
 * the GUI version of the game and the components that make up the GUI itself.
 * @author liamberrisford
 * @release 05/01/2016
 * @See ComputerCodebreaker.java, ComputerCodemaker.java, MainGame.java, Settings.java
 *
 */
public class MastermindGUI implements ActionListener {
	//Instances of other classes that are used for the GUI.
	private MainGame mainGame = new MainGame();
	private Settings newGameSettings = new Settings();
	private SaveLoadGameState saveLoadGame = new SaveLoadGameState();
	private ComputerCodemaker Codemaker = new ComputerCodemaker();
	
	//The values that are used within the JCombo Boxes within the GUI.
	private Integer[] numberOfColours = {3,4,5,6,7,8};
	private Integer[] numberOfGuesses = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60};
	private String[] possibleColours = {"red", "green", "blue", "yellow", "orange", "pink", "cyan", "magenta"};
	
	//The JCombo Boxes that are used within the GUI.
	private JComboBox<Integer> setColoursInPlay = new JComboBox<Integer>(numberOfColours);
	private JComboBox<Integer> setGuessesAllowed = new JComboBox<Integer>(numberOfGuesses);
	private JComboBox<String> possibleColoursList1 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList2 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList3 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList4 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList5 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList6 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList7 = new JComboBox<String>(possibleColours);
	private JComboBox<String> possibleColoursList8 = new JComboBox<String>(possibleColours);
	private JComboBox[] comboBoxArray = {possibleColoursList1, possibleColoursList2, possibleColoursList3, possibleColoursList4, possibleColoursList5, possibleColoursList6, possibleColoursList7, possibleColoursList8};
	
	//The JPanels that are used for the graphical representation of the colours that have been selected.
	private JPanel colourBox1 = new JPanel();
	private JPanel colourBox2 = new JPanel();
	private JPanel colourBox3 = new JPanel();
	private JPanel colourBox4 = new JPanel();
	private JPanel colourBox5 = new JPanel();
	private JPanel colourBox6 = new JPanel();
	private JPanel colourBox7 = new JPanel();
	private JPanel colourBox8 = new JPanel();
	private JPanel[] colourBoxArray = {colourBox1,colourBox2,colourBox3,colourBox4,colourBox5,colourBox6,colourBox7,colourBox8};
	
	//The JPanels that are used for the graphical representation of the pegs recieved for a submitted guess.
	private JPanel peg1 = new JPanel();
	private JPanel peg2 = new JPanel();;
	private JPanel peg3 = new JPanel();;
	private JPanel peg4 = new JPanel();;
	private JPanel peg5 = new JPanel();;
	private JPanel peg6 = new JPanel();;
	private JPanel peg7 = new JPanel();;
	private JPanel peg8 = new JPanel();;
	private JPanel[] pegArray = {peg1, peg2, peg3, peg4, peg5, peg6, peg7, peg8};
	
	//Components used for the button within the GUI.
	private JButton submitCode;
	private JButton submitGuess;
	private JPanel buttonPanel;
	
	//Components used for the submitting and representation of guesses and the code.
	private String currentCode;
	private String currentGuessString;
	private ArrayList<Character> codeBuilder = new ArrayList<Character>();
	private String lastGuessPegsString;
	private int[] pegsForCurrentGuess;
	private int iOccurences;
	private int gameModeValue;
	private String brokenCode;
	
	//The current instruction JTextArea is there to give the user instructions if they have made an incorrect order of input, or input.
	private JTextArea currentInstruction = new JTextArea();
	
	//Boolenas used for the defensive programming of the game.
	private boolean guessesAllowedChoosen = false;
	private boolean coloursInPlayChoosen = false;
	private volatile boolean gameModeChoosen = false;
	
	//Components used in the GUI for displaying the current state of the game.
	private JPanel gameInformation;
	private JLabel guessesTaken;
    private JLabel guessesAllowed;
    private JLabel coloursInPlay;
    private JPanel mainPane;
    private JPanel pegs;
    private JPanel code;
    private JPanel codeSelection;
    private JLabel codeSelectionDescription;
   
    private ArrayList<String> previousGuesses = new ArrayList<String>();
    
    //Array used within the loading of a previous game.
    private ArrayList<String> savedData;

    protected void initUI() {
    	//For loop to populate the code builder with a default value, so that the i's can be swapped for the relevant colour character.
    	for(int i = 0; i < 8; i++) {
    		codeBuilder.add('i');
    	}
    	
    	//Code regarding the generals about the window that the GUI will be in.
    	final JFrame frame = new JFrame("Mastermind Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Code regarding the Menu bar at the top, including the action performer when one of the sub menu items is clicked by the user.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu gameModes = new JMenu("Game Modes");
        
        //The first things a user must select is the game mode that they wish to play from the sub menu of game modes.
        //As both game mode 1 and 3 both have human input first then there is no methods are called within the action listener.
        JMenuItem gameMode1 = new JMenuItem("Human Codemaker and Human Codebreaker");
        gameMode1.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) {
        	  gameModeChoosen = true;
        	  gameModeValue = 1;
        	  } 
        	} );
        
        //Within game mode two the computer has to perform tasks before the human and so the computerCodemaker method is called first before any human input.
        JMenuItem gameMode2 = new JMenuItem("Computer Codemaker and Human Codebreaker");
        gameMode2.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  computerCodemaker();
      		  gameModeChoosen = true;
      		  gameModeValue = 2;
      		  } 
      		} );      	  	 
        JMenuItem gameMode3 = new JMenuItem("Human Codemaker and Computer Codebreaker");
        gameMode3.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  gameModeChoosen = true;
      		  gameModeValue = 3;
      		  } 
      		} );
        
        //After the user clicking the sub menu item for game mode four there is no human input and so both the code breaker and code maker methods are called 
        //that then stops the user from performing any input. 
        JMenuItem gameMode4 = new JMenuItem("Computer Codemaker and Computer Codemaker");
        gameMode4.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  computerCodemaker();
      		  computerCodebreaker();
      		  gameModeChoosen = true;
      		  gameModeValue = 4;
      		  } 
      		} );
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  //The game can only be saved when the game is either in game mode 1 or 2, when there is a human code breaker.
        		  if(gameModeValue == 1 || gameModeValue == 2) { 
        			  //If statement to check that at least one guess for the code has been made by the code breaker.
        			  if(mainGame.getGuessesTaken() == 0) {
        				  currentInstruction.setText("Please make at least one guess before saving!");
        				  return;
        			  }
        			try {
        				//The program will then take the current game state and save it to a file so that it can be read at a later date when the user wants to resume
        				//the game.
						saveLoadGame.saveGame(currentCode, currentGuessString, mainGame.getGuessesTaken(), "" + newGameSettings.getColoursInPlay(), gameModeValue, newGameSettings.getGuessesAllowed(), previousGuesses);
						frame.remove(codeSelection);
						currentInstruction.setText("Your game has successively been saved!");
					} catch (IOException e1) {
						
					}
        		  } else {
        			  currentInstruction.setText("Please first choose a game mode where there is a human codebreaker to save the game.");
        		  }
        		 } 
        		} );
        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  //The set GUI method is called so that the boolean that will change the result of the load game function to return an array list.
      		  saveLoadGame.setGUI();
      		  //A previous game can only be resumed if the user is playing a game where there is a human code breaker.
    		  if(gameModeValue == 1 || gameModeValue == 2) { 
    			guessesAllowedChoosen = true;
    			coloursInPlayChoosen = true;
    			try {
    				//This method will then return all of the saved data to the saved data array that was wrote to the Saved game text file.
					savedData = saveLoadGame.loadGame();
				} catch (IOException e1) {
				}
    			//The relevant files within the class are then populated with the data for the current state of the game that was saved.
    			currentCode = savedData.get(0);
    			currentGuessString = savedData.get(1);
    			mainGame.setGuessesTaken(Integer.parseInt(savedData.get(2)));
    			
    			//The combo boxes items that are not needed for the amount of colours in the code are then removed.
    			for(int i = 0; i < 8; i++) {
        			for(int j = 7; j > (Integer.parseInt(savedData.get(3))-1); j--) {
        				comboBoxArray[i].removeItemAt(j);
        			}
        		}
    		    gameModeValue = Integer.parseInt(savedData.get(4));
    		    newGameSettings.setGuessesAllowed(savedData.get(5));
    		    
    		    //The combo boxes, pegs, colour boxes, and the submit code button are then removed from the GUI.
    		    for(int i = 7; i >= currentCode.length(); i--){
    			    pegs.remove(pegArray[i]);
    			    codeSelection.remove(comboBoxArray[i]);
    			    code.remove(colourBoxArray[i]);
    		    }
    		    buttonPanel.remove(submitCode);
    		    
    		    pegsForCurrentGuess = mainGame.currentGuess(currentCode, currentGuessString, currentCode.length());
    		  	
    		  	//The pegs that the last guess recieved is then stored within a string that can then be shown to the user, and the JPanel for representing 
    		  	//the recieved pegs are then updated to represent the pegs that the guess recieved. 
    		  	lastGuessPegsString = "Your last guess: " + currentGuessString + " recieved the pegs: ";
    		  	for(int i = 0; i < pegsForCurrentGuess[0]; i ++) {
    		  		pegArray[i].setBackground(Color.white);
    		  		lastGuessPegsString += "White ";
    		  	}
    		  	for(int i = pegsForCurrentGuess[0]; i < pegsForCurrentGuess[2]; i++) {
    		  		pegArray[i].setBackground(Color.black);
    		  		lastGuessPegsString += "Black ";
    		  	}
    		  	lastGuessPegsString += ".";
    		  	currentInstruction.setText(lastGuessPegsString);
    		    
    		    //The combo boxes for selecting the guesses and the colour in the code are removed and a JLabel with the value from the loaded game is then populated.
    		    gameInformation.remove(setGuessesAllowed);
    		    guessesAllowed.setText("Guesses to crack the code: " + savedData.get(5));
    		    gameInformation.remove(setColoursInPlay);
		  	    coloursInPlay.setText("The code is made up of the colours: " + "RGBYOPCM".substring(0, Integer.parseInt(savedData.get(3))));
		  	    
		  	    //The colour boxes are updated to represent the colour within the last guess the user made.
		  	    calculateFullStringColours(currentGuessString);
  		      } else {
    			  currentInstruction.setText("Please first choose a game mode where there is a human codebreaker to save the game.");
    		  }
    		 }
    		} );
        JMenuItem readMe = new JMenuItem("Read Me");
        readMe.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Method called to run the method that will create a window with the contents of the read me file in it.
					createReadMeFrame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        	}
        });
        
        
        //All of the elements that make up the menu bar are then added to the JMenuBar.
        gameModes.add(gameMode1);
        gameModes.add(gameMode2);
        gameModes.add(gameMode3);
        gameModes.add(gameMode4);
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.add(readMe);
        fileMenu.add(gameModes);
        menuBar.add(fileMenu);
        
        //Settings for the text area so that the text will function like a Jlabel that will wrap text.
        currentInstruction.setWrapStyleWord(true);
        currentInstruction.setLineWrap(true);
        currentInstruction.setOpaque(false);
        currentInstruction.setEditable(false);
        currentInstruction.setFocusable(false);
        currentInstruction.setBackground(UIManager.getColor("Label.background"));
        currentInstruction.setFont(UIManager.getFont("Label.font"));
        currentInstruction.setBorder(UIManager.getBorder("Label.border"));
        
        //JLabel used for instruction to the user so that they know what each JComboBox is used for.
        guessesTaken = new JLabel("");
        guessesAllowed = new JLabel("Guesses to crack the code: ");
        coloursInPlay = new JLabel("The code is made up of the colours: ");
        
        //Components of the GUI that are used for the representation of the current state of the game to the user.
        gameInformation = new JPanel();
        gameInformation.add(guessesTaken);
        gameInformation.add(guessesAllowed);
        gameInformation.add(setGuessesAllowed);
        
        //The combo box that is used for setting the guesses that the code breaker is allowed 
        setGuessesAllowed.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		  //Defensive code to make sure that the button is not clicked before the game mode is selected.
      		  if(gameModeChoosen == false) {
      			  currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
	    		  return;
      		  }
      		  
      		  //Gets the selected item from the combo box and stores it in a field to be used.
    		  String choosenValueForGuesses = "" + setGuessesAllowed.getSelectedItem();
    		  
    		  //Puts the selected value into the settings so that it can be used for a condition check.
    		  newGameSettings.setGuessesAllowed(choosenValueForGuesses);
    		  
    		  //Removes the combo box and will replace it will a label that will display the value choosen by the user.
    		  gameInformation.remove(setGuessesAllowed);
    		  guessesAllowed.setText("Guesses to crack the code: " + choosenValueForGuesses);
    		  
    		  //The error check boolean is then set to true and the GUI is reset to show the component changes that have been made. 
    		  guessesAllowedChoosen = true;
    		  frame.setVisible(true);
    		  } 
    		} );
        gameInformation.add(coloursInPlay);
        gameInformation.add(setColoursInPlay);
        
        //The combo box that is used to set how many colours that could be in the code.
        setColoursInPlay.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  //Defensive code to make sure that the button is not clicked before the game mode is selected.
        		  if(gameModeChoosen == false) {
      	    		  currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
      	    		  return;
      	    	  }
        		  
        		  //Gets the selected item from the combo box and stores it in a field to be used.
        		  String choosenValueForColours = "" + setColoursInPlay.getSelectedItem();
        		  
        		  //Puts the selected value into the settings so that it can be used for a condition check.
        		  newGameSettings.setNumberColours(choosenValueForColours);
        		  
        		  //The colours that can't be in the code are then removed from each of the combo boxes.
        		  for(int i = 0; i < 8; i++) {
        			for(int j = 7; j > (Integer.parseInt(choosenValueForColours)-1); j--) {
        				comboBoxArray[i].removeItemAt(j);
        			}
        		  }
        		  //The combo box is then removed and replaces with a JLabel with the value selected.
        		  gameInformation.remove(setColoursInPlay);
        		  coloursInPlay.setText("The code is made up of the colours: " + "RGBYOPCM".substring(0, Integer.parseInt(choosenValueForColours)));
        		  
        		  //The error check boolean is then set to true and the GUI is reset to show the component changes that have been made.
        		  coloursInPlayChoosen = true;
        		  frame.setVisible(true);
        		  } 
        		} );
        
        //Code for the section of the GUI that deals with the current code graphical representation.
        JLabel codeDescription = new JLabel("Current code:");
        code = new JPanel();
        code.add(codeDescription);
        
        //For loop to create and add all of the JPanels thats are used for representing the colours that are selected in the combo boxes.
        for(int i = 0; i < 8; i++) {
        	colourBoxArray[i].setPreferredSize(new Dimension(40,40));
        	colourBoxArray[i].setBackground(Color.black);
        	code.add(colourBoxArray[i]);
        }
        
        //Code for the section of the GUI that deals with the choosing of the current guess or code.
        codeSelectionDescription = new JLabel("Please choose your guess:");
        codeSelection = new JPanel();
        codeSelection.add(codeSelectionDescription);
        
        //For loop to create and add all of the combo boxes that are used for the selecting the colours within the code or guess.
        for(int i = 0; i < 8; i++ ) {
        	comboBoxArray[i].addActionListener(this);
        	codeSelection.add(comboBoxArray[i]);
        }
        
        //Code for the section of the GUI that deals with the pegs that were recieved for the last guess.
        JLabel descriptionPegs = new JLabel("Pegs recieved for the last guess: ");
        pegs = new JPanel();
        pegs.add(descriptionPegs);
        
        //For loop to create and add all of the JPanels that are used for the representation of the pegs that the last guess submitted recieved. 
        for(int i = 0; i < 8; i++) {
        	pegArray[i].setPreferredSize(new Dimension(20,20));
            pegArray[i].setBackground(Color.gray);
            pegs.add(pegArray[i]);
        }
        
        //Code for the section of the GUI that deals with the submitting of the guess and code.
        buttonPanel = new JPanel(new GridLayout());
        submitGuess = new JButton("Submit Guess");
        submitGuess.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  	//Defensive programming, the button cant be pressed unless the game mode has been choosen.
        		    if(gameModeChoosen == false) {
        	    		currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
        	    		return;
        	    	}
        		    
        		    //Checks to make sure that the code breaker still has guesses left to crack the code, if not the code breaker is told the code maker has won
        		    //and the game will then end.
        		  	if(newGameSettings.getGuessesAllowed() < mainGame.getGuessesTaken()) {
        		  		currentInstruction.setText("The codemaker has won! The codemaker has failed to crack the code in " + newGameSettings.getGuessesAllowed() + " guesses");
        		  		
        		  		//The combo boxes are then removed to stop another guess and the GUI is refreshed to show the changes.
        		  		mainPane.remove(codeSelection);
        		  		frame.setVisible(true);
        		  		return;
        		  	}
        		  	for(int i = 0; i < currentCode.length(); i++) {
        		  		pegArray[i].setBackground(Color.gray);
        		  	}
        		  	//The array that stores the selected items of the combo box is then turned into a string so that it can be passed to a method to calculate the pegs.
        		  	StringBuilder result = new StringBuilder(codeBuilder.size());
        		  	for (Character c : codeBuilder) {
        		  		result.append(c);
        		  	}
        		  	currentGuessString = result.toString();
        		  	
        		  	//Any i's that are in the guess are then removed, as these were only a place holder to avoid a null pointer error.
        		  	currentGuessString = currentGuessString.replace("i", "");
        		  	
        		  	//The amount of pegs for the guess are then calculated and stored in a intger array. 
        		  	pegsForCurrentGuess = mainGame.currentGuess(currentCode, currentGuessString, currentCode.length());
        		  	
        		  	//The pegs that the last guess recieved is then stored within a string that can then be shown to the user, and the JPanel for representing 
        		  	//the recieved pegs are then updated to represent the pegs that the guess recieved. 
        		  	lastGuessPegsString = "Your last guess: " + currentGuessString + " recieved the pegs: ";
        		  	for(int i = 0; i < pegsForCurrentGuess[0]; i ++) {
        		  		pegArray[i].setBackground(Color.white);
        		  		lastGuessPegsString += "White ";
        		  	}
        		  	for(int i = pegsForCurrentGuess[0]; i < pegsForCurrentGuess[2]; i++) {
        		  		pegArray[i].setBackground(Color.black);
        		  		lastGuessPegsString += "Black ";
        		  	}
        		  	lastGuessPegsString += ".";
        		  	currentInstruction.setText(lastGuessPegsString);
        		  	
        		  	//The guesses taken is then updated.
        		  	guessesTaken.setText("Guesses Taken So Far: " + mainGame.getGuessesTaken());
        		  	
        		  	//If statement that is ran to check whetehr or not the game has been won, if so the combo boxes for colour selection are removed and the user
        		  	//informed that they have won the game.
        		  	if(pegsForCurrentGuess[1] == currentCode.length()) {
        		  		currentInstruction.setText("Your last guess of: " + currentGuessString + " is the correct code, the code breaker has won!");
        		  		mainPane.remove(codeSelection);
        		  		frame.setVisible(true);
        		  	} 
        		  	
        		  	//The GUI is then updated to represent the changes to the componets.
        		  	frame.setVisible(true);
        		  } 
        		} );
        submitCode = new JButton("Submit Code");
        submitCode.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  //Defensive programming, used to make sure that the game mode has been choosen.
        		  if(gameModeChoosen == false) {
        	    		currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
        	    		return;
        	      }
        		  
        		  ////The array that stores the selected items of the combo box is then turned into a string so that it can be passed to a method to calculate the pegs.
        		  StringBuilder result = new StringBuilder(codeBuilder.size());
        		  for (Character c : codeBuilder) {
        		    result.append(c);
        		  }
        		  currentCode = result.toString();
        		  
        		  //Will go through the code that the human code maker 
        		  int counter = 0;
        		  for(int i = 0; i < currentCode.length(); i++) {
        			  if("RGBYOPCM".contains("" + currentCode.charAt(i))) {
        				  counter += 1;
        			  }
        		  }
        		  //Defensive Programming, to make sure that the human code maker chooses at least three colours to be in the code.
        		  if(counter < 3) {
        			  currentInstruction.setText("Please choose at least three colours to be in the code, you have only choosen " + counter);
        			  return;
        		  }
        		  
        		  //Removes any i's that are in the code, this occurs if the code is not 8 colours long.
        		  if(currentCode.contains("i")){
        			iOccurences = currentCode.length() - currentCode.replace("i", "").length();
        			currentCode = currentCode.replace("i", "");
      		  	  } 
        		  
        		  
        		  //The colour that are not needed are then removed from the combo boxes.
        		  for(int i = 0; i < currentCode.length(); i++) {
        			  comboBoxArray[i].setSelectedIndex(0);
        		  }
        		  
        		  //The combo boxs, pegs, and the JPanels that are not needed for the length of code input are then removed from the GUI.
        		  for(int i = 7; i > (7-iOccurences); i--){
        			  pegs.remove(pegArray[i]);
        			  codeSelection.remove(comboBoxArray[i]);
        			  code.remove(colourBoxArray[i]);
        		  }
        		  currentInstruction.setText("Please take your first guess at the code!");
        		 
        		  //The submit code button is then removed from the GUI to stop another click.
        		  buttonPanel.remove(submitCode);
        		  
        		  //The colour of the JPanels that were used for the selection foteh code are then reset back to black so that the codebreaker does not know what
        		  //colours were selected.
        		  for(int i = 0; i < 8; i++) {
        			  colourBoxArray[i].setBackground(Color.black);
        		  }
        		  frame.setVisible(true);
        		  //If the game mode that was choosen was the computer codebreaker then the method that breaks the code is then called.
        		  if(gameModeValue == 3) {
        			  computerCodebreaker();
        		  }     		  
        		  } 
        		} );
        
        buttonPanel.add(submitGuess);
        buttonPanel.add(submitCode);
        buttonPanel.add(currentInstruction);
        
        //Section of the code for adding all of the components to a single JPanel, and then adding them to the JFrame.
        mainPane = new JPanel();
        mainPane.add(code, BorderLayout.NORTH);
        mainPane.add(codeSelection, BorderLayout.SOUTH);
        mainPane.add(pegs, BorderLayout.CENTER);
        
        frame.add(gameInformation, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(mainPane, BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 400);
        frame.setVisible(true);
    }
    
    //Method that is called when an item within one of the combo boxes for the colour selection is clicked.
    public void actionPerformed(ActionEvent e) {
    	//Defensive programming, the method will only run if the game mode is selected.
    	if(gameModeChoosen == false) {
    		currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
    		return;
    	}
    	
    	//Defensive programming, the if statement will only pass if both the guesses allowed and the colours that are to be in the code have been selected by the user.
    	if(coloursInPlayChoosen && guessesAllowedChoosen) {
    		
    		//Calculated which os the combo boxes has been clicked so that the relevant JPanel can then be updated, a method is called to do this.
    		JComboBox cb = (JComboBox) e.getSource();
    		String colourSelected = (String) cb.getSelectedItem();
    		if(e.getSource() == possibleColoursList1) {
    			calculateBackground(colourSelected, colourBox1, 0);
    		} else if(e.getSource() == possibleColoursList2) {
    			calculateBackground(colourSelected, colourBox2, 1);
    		} else if(e.getSource() == possibleColoursList3) {
    			calculateBackground(colourSelected, colourBox3, 2);
    		} else if(e.getSource() == possibleColoursList4) {
    			calculateBackground(colourSelected, colourBox4, 3);
    		} else if(e.getSource() == possibleColoursList5) {
    			calculateBackground(colourSelected, colourBox5, 4);
    		} else if(e.getSource() == possibleColoursList6) {
    			calculateBackground(colourSelected, colourBox6, 5);
    		} else if(e.getSource() == possibleColoursList7) {
    			calculateBackground(colourSelected, colourBox7, 6);
    		} else if(e.getSource() == possibleColoursList8) {
    			calculateBackground(colourSelected, colourBox8, 7);
    		} 
    	} else {
    		currentInstruction.setText("Please choose the guesses allowed and colours in play, or choose a computer codemaker mode.");
    	}
	}
	
	public void calculateBackground(String colour, JPanel colourBox, int guessChange) {
		//Defensive programming, the method is only ran if the game mode has already been selected.
		if(gameModeChoosen == false) {
    		currentInstruction.setText("Please first choose a game mode from File -> GameModes in the top left corner of the window.");
    		return;
    	}
		
		//This will then calculate what colour the JPanel needs to be changed too so that it will match the text that has been selected in the combo box.
		if(colour == "red") {
			colourBox.setBackground(Color.red);
			codeBuilder.set(guessChange, 'R');
		} else if(colour == "green") {
			colourBox.setBackground(Color.green);
			codeBuilder.set(guessChange, 'G');
		} else if(colour == "blue") {
			colourBox.setBackground(Color.blue);
			codeBuilder.set(guessChange, 'B');
		} else if(colour == "yellow") {
			colourBox.setBackground(Color.yellow);
			codeBuilder.set(guessChange, 'Y');
		} else if(colour == "orange") {
			colourBox.setBackground(Color.orange);
			codeBuilder.set(guessChange, 'O');
		} else if(colour == "pink") {
			colourBox.setBackground(Color.pink);
			codeBuilder.set(guessChange, 'P');
		} else if(colour == "cyan") {
			colourBox.setBackground(Color.cyan);
			codeBuilder.set(guessChange, 'C');
		} else if(colour == "magenta") {
			colourBox.setBackground(Color.magenta);
			codeBuilder.set(guessChange, 'M');
		} 
	}
	
	//Method that is used to changes the colours of the JPanels when a full string guess is used, this is used in the case of the restoring of a game or when the
	//computer code breaker is used.
	public void calculateFullStringColours(String input) {
		char[] inputArray = input.toCharArray();
		for(int i = 0; i < inputArray.length; i++) {
			if(inputArray[i] == 'R') {
				colourBoxArray[i].setBackground(Color.red);
			} else if(inputArray[i] == 'G') {
				colourBoxArray[i].setBackground(Color.green);
			} else if(inputArray[i] == 'B') {
				colourBoxArray[i].setBackground(Color.blue);
			} else if(inputArray[i] == 'Y') {
				colourBoxArray[i].setBackground(Color.yellow);
			} else if(inputArray[i] == 'O') {
				colourBoxArray[i].setBackground(Color.orange);
			} else if(inputArray[i] == 'P') {
				colourBoxArray[i].setBackground(Color.pink);
			} else if(inputArray[i] == 'C') {
				colourBoxArray[i].setBackground(Color.cyan);
			} else if(inputArray[i] == 'M')
				colourBoxArray[i].setBackground(Color.magenta);
		}
	}
	
	//Method that is called when the game mode that is being played involves the computer code maker.
	public void computerCodemaker() {
		//Method is called to generate the code and then retrieve and store it within a field.
		Codemaker.generateCode(); 
		currentCode = Codemaker.getGeneratedCode();
		
		//Gets the needed details about the game, such as the colours that could be in the code and the guesses that are allowed.
		newGameSettings.setGuessesAllowed("" + Codemaker.randomValueInRange(10,60));
		newGameSettings.setNumberColours("" + Codemaker.getColoursInPlay());
		guessesAllowedChoosen = true;
		coloursInPlayChoosen = true;
		
		//Removes the components of the GUI that are not needed for the code that has been generated.
		for(int i = 7; i >= currentCode.length(); i--){
		    pegs.remove(pegArray[i]);
			codeSelection.remove(comboBoxArray[i]);
			code.remove(colourBoxArray[i]);
		}
		
		//Removes the colours from the combo boxes that cant be in the code.
		for(int i = 0; i < 8; i++) {
			for(int j = 7; j > (newGameSettings.getColoursInPlay()-1); j--) {
				comboBoxArray[i].removeItemAt(j);
			}
		}
		//Sets the instruction for the user and removes the submit code button. 
		currentInstruction.setText("Please take your first guess at the code!");
		buttonPanel.remove(submitCode);
		
		//Removes the combo box for the guesses allowed and colours in play, and replaces them with a JLabels with the values that have been choosen.
		gameInformation.remove(setGuessesAllowed);
		guessesAllowed.setText("Guesses to crack the code: " + newGameSettings.getGuessesAllowed());
		gameInformation.remove(setColoursInPlay);
		coloursInPlay.setText("The code is made up of the colours: " + "RGBYOPCM".substring(0,newGameSettings.getColoursInPlay()));
	
	}
	
	//Method that is called when the game mode involves a computer code breaker.
	public void computerCodebreaker() {
		//The code breaker is input with all of the details about the code that it needed to be able to break the code.
		ComputerCodebreaker codebreaker = new ComputerCodebreaker(currentCode, currentCode.length(), newGameSettings.getColoursInPlay(), newGameSettings.getGuessesAllowed());
		
		//Boolean that is used to change the result of the code break method, it will stop the program from waiting for user input on the command line.
		codebreaker.setGUIBoolean();
		codebreaker.codebreak();
		
		//Accessor used for receiveing the broken code made by code break method.
		brokenCode = codebreaker.getBrokenCode();
		calculateFullStringColours(brokenCode);
		
		//If statement to check whether the code maker or the code breaker has won the game.
		if(newGameSettings.getGuessesAllowed() > mainGame.getGuessesTaken()) {
			currentInstruction.setText("The codemaker has won it took the computer " + codebreaker.getGuessesTaken() + " crack the code!");
		} else {
			currentInstruction.setText("The code has been broken it was: " + brokenCode + " it took the computer  " + mainGame.getGuessesTaken() + " guesses to crack the code!");
		}
		
		codeSelection.remove(codeSelectionDescription);
		for(int i = (brokenCode.length()-1); i >= 0; i--) {
			codeSelection.remove(comboBoxArray[i]);	
		}
		
		for(int i = 0; i < brokenCode.length(); i++) {
			pegArray[i].setBackground(Color.black);
		}
	}
	
	//Method used to create a window and populate it with the contents of the read me file.
	public void createReadMeFrame() throws IOException{
		//Creates another frame (window) to have all of the read me file contents.
		JFrame readMeFrame = new JFrame("Read Me File");
		
		//The settings that will create a text area that acts like a JLabel that wraps.
		JTextArea readMeText = new JTextArea();
		readMeText.setWrapStyleWord(true);
		readMeText.setLineWrap(true);
		readMeText.setOpaque(false);
		readMeText.setEditable(false);
		readMeText.setFocusable(false);
		readMeText.setBackground(UIManager.getColor("Label.background"));
		readMeText.setBorder(UIManager.getBorder("Label.border"));
		readMeText.setFont(UIManager.getFont("Label.font"));
		
		//Buffered reader that will continue to read from the read me file aslong as there is still data within the file.
		BufferedReader br = new BufferedReader(new FileReader("README.txt"));
		String readMeContents = "";
		String input;
		while((input = br.readLine()) != null) {
			//For each line within the file it will append the line onto the string and then add a new line to the string.
			readMeContents += input;
			readMeContents += "\n";
		}
		br.close();
		
		//The full file is then added to the JTextArea that is then added to the new frame to house the whole of the file.
		readMeText.setText(readMeContents);
		readMeFrame.add(readMeText);
		readMeFrame.setSize(750, 650);
		readMeFrame.setLocationRelativeTo(null);
		readMeFrame.setVisible(true);
	}

	
	//Main method that is used for the GUI interface, it will run the initUI method where the flow of execution for the GUI version of the game will stay for
	//the duration of the program.
    public static void main(String[] args) {  	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MastermindGUI().initUI();
            }
        });
    }
}