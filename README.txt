Welcome to the README file for a Java Mastermind game!
============================================================
The game is based on the 1970 board game by Mordecai Meirowitz. The game works by a code 
maker making up a code made up of coloured pegs, this is then attempted to be cracked by 
the code breaker. The code breaker will then make a code that is made up of colours that 
could be in the code and is the same length as the code that was made by the code maker. 
The code maker then gives the code breaker black and white pegs based on their guess for 
the code. A black peg is given if a coloured pegs is both correct in position and colour, 
a white peg is given if the colour is within the code but is not in the correct position. 
A single coloured peg can only be given either a black or white peg, not both. 

Changes to the game:
============================================================
In the digital version of the game the amount of colours that could potentially be in the 
code are 3-8, and the length of code itself can be 3-8 pegs long. If the game mode 
involves a human codebreaker then the game can be saved. The object of the game is to 
receive the same amount of black pegs as the length of the code that is being cracked.
In this version of the game the amount of guesses can also be chosen, the code breaker
can have between 10 and 60 guesses to crack the code. The code maker will win if the code
breaker runs out of guesses before they have cracked the code.

Playing the game:
============================================================
The first thing you will need to do to play the game is to compile the game files, this is 
achieved by navigating to the directory that you have put the file into using the cd and 
ls commands. Once you are into the directory then input "javac *.java" into the terminal 
which will compile all of the files. Then you just need to choose which interface you want
to play the game on!
 
Interfaces in the game:
============================================================
Text Based Interface: This is played by inputting "java MastermindTextBasedInterface", 
the game is played within a terminal using text for the input of the game and the 
displaying of state of the game and its outputs. To save the game enter "savegame". To 
load the game chose from the game mode selection, with the number 5. The an also be exited 
with the input "quit".

Graphics Based Interface: This is played by inputting "java MastermindGUI" file, the game 
is played using graphical representation of the input into the game and the output of the 
game. To save the game go File -> Save Game. To load a previous game File -> Load Game. 

Versions of the game:
============================================================
Human Codemaker and Human Codebreaker - This is the version of the game where a human user 
will make the code and then another user will attempt to break the code.

Computer Codemaker and Human Codebreaker - This is the version of the game where the 
computer will generate a code for a human code breaker to solve. 

Human Codemaker and Computer Codebreaker - This is the version of the game where a human 
will make a code and then when the code is submitted the computer will solve the code.

Computer Codebreaker and Computer Codemaker - This is the version of the game where the 
computer will both generate a code and then break the code. 


Files needed for the game:
============================================================
- ComputerCodebreaker.java
- ComputerCodemaker.java
- GameMode.java
- MainGame.java
- MastermindGUI.java
- MastermindTextBasedInterface.java
- SaveLoadGameState.java
- Settings.java
- SavedGame.txt
- README.txt

Written by Liam Berrisford on 05/01/2016.