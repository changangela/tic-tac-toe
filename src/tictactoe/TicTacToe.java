package tictactoe;

import java.util.*;

/**
 *
 * Name: Angela Chang Date: July 17, 2015 Course: ICS3U Teacher: Mr Bond
 *
 */
public class TicTacToe {

    private static Scanner userInput = new Scanner(System.in);//define a scanner for obtaining user input

    private static String[][] Cell = new String[3][3];//a 2d array is used to store the tic tac toe board, position of x's and o's
    private static int[] Board = new int[9];//a 1d array is used to store the values -1, 0, and 1 to easily compute strategies

    private static final int CPU = -1;//a constant defined as the computers constant, -1
    private static final int PLY = +1;//a constant defined as the players constant, +1
    private static final int WIN = 10;//a constant defined as the number associated with winning

    private static int numMoves = 0;//the number of moves that have progressed throughout the game

    private static int numEasyGame = 0;//the number of games played in easy mode
    private static int numHardGame = 0;//the number of games played in hard mode
    private static int numEasyWin = 0;//the number of games won in easy mode
    private static int numHardWin = 0;//the number of games won in hard mode
    private static int numEasyDraw = 0;//the number of games drawn in easy mode
    private static int numHardDraw = 0;//the number of games drawn in hard mode
    private static int numEasyLost = 0;//the number of games lost in easy mode
    private static int numHardLost = 0;//the number of games lost in hard mode

    public static void main(String[] args) {

        //display program information to console
        System.out.println("   TIC TAC TOE");
        System.out.println("=================");
        System.out.println("By:  Angela Chang");
        System.out.println("");
        mainMenu();
    }

    private static void printInstructions() {
        //this method prints the tic tac toe playing instructions to console
        //display instructions to console
        System.out.println("");
        System.out.println("Below are the corresponding coordinates.");
        System.out.println("[0,0][0,1][0,2]");
        System.out.println("[1,0][1,1][1,2]");
        System.out.println("[2,0][2,1][2,2]");
        System.out.println("");
    }

    private static void mainMenu() {
        //this method displays a menu for user to select easy, medium, or hard mode
        System.out.println("===================================================");
        System.out.println("                     Main Menu                     ");
        System.out.println("===================================================");
        System.out.println("Easy Game (No AI, Random moves only)              1");
        System.out.println("Hard Game (With AI, User will never win)          2");
        System.out.println("Check Stats                                       3");
        System.out.println("Exit                                              4");
        System.out.println("===================================================");

        Boolean isValid = true;//define variable to condition the loop

        do //execute loop at least once
        {
            System.out.print("                                       Selection: ");//prompt user input
            //obtain user selection
            String choice = userInput.nextLine();

            switch (choice)//case select for the choice variable
            {
                case "1":
                    //if the user's choice was 1, then call a easy game
                    numEasyGame++;//increment easy games played counter
                    easyGame();
                    break;
                case "2":
                    //if the user's choice was 2, then call a hard game
                    numHardGame++;//increment hard games played counter
                    hardGame();
                    break;
                case "3":
                    //if the user's choice was 3, then call a checkStats
                    checkStats();
                case "4":
                    //if the user's choice was 4, then exit program
                    System.exit(CPU);
                    break;
                default:
                    //if the user's choice was none of the above, then prompt for correct input
                    System.out.println("INVALID SELECTION");
                    isValid = false;//set boolean value to false thereby condition for looping is true
            }
        } while (!isValid);//loop while the entry is invalid

    }

    private static void printBoard() {
        //this method prints the tic tac toe board to console, with corresponding x's and o's in location
        System.out.println("Move #" + numMoves);//print the move number
        System.out.println(Cell[0][0] + Cell[0][1] + Cell[0][2]);
        System.out.println(Cell[1][0] + Cell[1][1] + Cell[1][2]);
        System.out.println(Cell[2][0] + Cell[2][1] + Cell[2][2] + "\n");
    }

    private static void clearBoard() {
        //this method clears every cell in the tic tac toe board, to restart game
        numMoves = 0;//reset the number of moves
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i * 3 + j] = 0;
                Cell[i][j] = "[ ]";

            }
        }
    }

    private static void gameEnd() {
        //this method is called when the game is over
        System.out.println("Press any key to go back to main menu...");

        //prompt user to press any key
        String key = userInput.nextLine();

        mainMenu();//go to main menu

    }

    private static void easyGame() {
//this method constructs a difficult game of tic tac toe which is impossible to beat

        //display game level to console
        System.out.println("===================================================");
        System.out.println("                     EASY MODE                     ");
        System.out.println("===================================================");

        printInstructions();//calls method to print instructions to console

        clearBoard();//call method to clear board, thereby resetting game

        Boolean gameOn = true;//variable used to determine whether or not the game is finished

        while (gameOn)//loop while the gameOn variable is turned on
        {
            numMoves++;//increase the numMoves variable by 1 each time a player makes a move

            String input = inputCoordinate();//call the inputCoordinate method to determine user input

            //store the x and y coordinates selected by user into variables
            int intX = Integer.parseInt(input.substring(0, 1));
            int intY = Integer.parseInt(input.substring(2, 3));

            //the cell selected by user is now marked by an "x"
            Cell[intX][intY] = "[x]";

            //the coordinate chosen by user is assigned the constant PLY in order for the program to conveniently check chosen locations
            Board[intX * 3 + intY] = PLY;

            System.out.println("");
            printBoard();//print the board, while displaying number of moves

            numMoves++;//increment the number of moves by 1 for computers turn

            if (checkWin(Board, PLY) == WIN) {
                //the user has won, display a winning message.
                System.out.println("CONGRATULATIONS YOU HAVE WON!");
                gameOn = false;//the game no longer continues
                numEasyWin++;//increment number of games won counter
            } else {
                if (numMoves == 10)//if the number of moves is 9
                {
                    //there are no more slots to move, it is a draw
                    gameOn = false;//the game no longer continues
                    System.out.println("IT'S A DRAW!");
                    numEasyDraw++;//increment number of games drawn counter
                } else//otherwise
                {
                    int temp = randomLocation();//uses the AI to choose a slot for computer to move

                    //the coordinate chosen by computer is assigned the constant CPU in order for the program to conveniently check chosen locations
                    Board[temp] = CPU;

                    Cell[(int) temp / 3][temp % 3] = "[o]";//the cell selected by computer is now marked by an "o"

                    printBoard();//print the board after the computer's turn

                    if (checkWin(Board, CPU) == -WIN) {
                        //if the computer wins display losing message
                        System.out.println("SORRY YOU HAVE LOST!");
                        gameOn = false;//the game no longer continues
                        numEasyLost++;//increment game lost counter
                    }
                }
            }
        }

        gameEnd();//call the gameEnd method because game is over
    }

    private static int randomLocation() {
        //this function returns a random integer between 0 and 8 to place the "o" into boar
        int rndLocation = 0 + (int) (Math.random() * ((8 - 0) + 1));

        if (Board[rndLocation] == 0)//if cell is not already taken
        {
            return rndLocation;//return integer 
        } else//if cell is taken
        {
            return randomLocation();//recursively loop this function
        }

    }

    private static String inputCoordinate() {
        //this function checks whether the inputted string is valid, and loops until there is a valid input to return

        String input;//define variable for user input to be stored
        int isValid;//define a boolean to condition the do while loop, if the user input is valid, it is true

        do//execute the loop at least once
        {
            isValid = 1;
            System.out.print("Enter coordinate [x,y]: ");//prompt user to input a coordinate to pick grid
            input = userInput.nextLine();//store input into variable

            if (input.length() == 3)//if the length of the input is 3
            {
                //continue if length of input is 3

                //if user inputed the first character is 0, 1, or 2
                if (input.substring(0, 1).equals("0") || input.substring(0, 1).equals("1") || input.substring(0, 1).equals("2")) {
                    //do nothing
                } else//otherwise
                {
                    //user input is invalid
                    System.out.println(input.substring(0, 1));
                    isValid = 0;
                }

                //if user inputed the third character as 0, 1, or 2
                if (input.substring(2, 3).equals("0") || input.substring(2, 3).equals("1") || input.substring(2, 3).equals("2")) {
                    //do nothing
                } else//otherwise
                {
                    //user input is invalid
                    isValid = 0;
                }

                if (isValid == 0)//if user input is not valid
                {
                    System.out.println("INVALID COORDINATE");//print error message
                }
            } else//if length of input is not 3
            {
                //user input is not valid
                System.out.println("INVALID INPUT");
                isValid = 0;
            }

        } while (isValid == 0);//execute loop if user input is not valid

        if (Cell[Integer.parseInt(input.substring(0, 1))][Integer.parseInt(input.substring(2, 3))].equals("[x]") || Cell[Integer.parseInt(input.substring(0, 1))][Integer.parseInt(input.substring(2, 3))].equals("[o]")) {
            //if the cell is already taken, reloop this method
            System.out.println("COORDINATE ALREADY TAKEN");//output error message
            return inputCoordinate();
        } else {
            //otherwise
            return input;//return input to game method
        }

    }

    private static void hardGame() {
        //this method constructs a difficult game of tic tac toe which is impossible to beat

        //display game level to console
        System.out.println("===================================================");
        System.out.println("                     HARD MODE                     ");
        System.out.println("===================================================");

        printInstructions();//calls method to print instructions to console

        clearBoard();//call method to clear board, thereby resetting game

        Boolean gameOn = true;//variable used to determine whether or not the game is finished

        while (gameOn)//loop while the gameOn variable is turned on
        {
            numMoves++;//increase the numMoves variable by 1 each time a player makes a move

            String input = inputCoordinate();//call the inputCoordinate method to determine user input

            //store the x and y coordinates selected by user into variables
            int intX = Integer.parseInt(input.substring(0, 1));
            int intY = Integer.parseInt(input.substring(2, 3));

            //the cell selected by user is now marked by an "x"
            Cell[intX][intY] = "[x]";

            //the coordinate chosen by user is assigned the constant PLY in order for the program to conveniently check chosen locations
            Board[intX * 3 + intY] = PLY;

            System.out.println("");
            printBoard();//print the board, while displaying number of moves

            numMoves++;//increment the number of moves by 1 for computers turn

            if (checkWin(Board, PLY) == WIN) {
                //the user has won, display a winning message.
                System.out.println("CONGRATULATIONS YOU HAVE WON!");
                numHardWin++;//increment won games counter
                gameOn = false;//the game no longer continues
            } else {
                if (numMoves == 10)//if the number of moves is 9
                {
                    //there are no more slots to move, it is a draw
                    gameOn = false;//the game no longer continues
                    System.out.println("IT'S A DRAW!");
                    numHardDraw++;//increment drawn game counter
                } else//otherwise
                {
                    int temp = artificialIntelligence(Board, CPU);//uses the AI to choose a slot for computer to move

                    //the coordinate chosen by computer is assigned the constant CPU in order for the program to conveniently check chosen locations
                    Board[temp] = CPU;

                    Cell[(int) temp / 3][temp % 3] = "[o]";//the cell selected by computer is now marked by an "o"

                    printBoard();//print the board after the computer's turn

                    if (checkWin(Board, CPU) == -WIN) {
                        //if the computer wins display losing message
                        System.out.println("SORRY, YOU HAVE LOST!");
                        gameOn = false;//the game no longer continues
                        numHardLost++;//increment loss game counter
                    }
                }
            }

        }

        gameEnd();//call the gameEnd method
    }

    private static int artificialIntelligence(int[] Cell, int player) {
        //this method uses an algorithm to determine what is the best spot for the computer to place an "o"

        int[] tempCell = new int[9];//create some temporary cells to store scenarios into

        //if it is possible to make 3 in a row with 1 move
        if (player == CPU)//if it is the computers turn 
        {
            for (int j = 0; j < 9; j++)//for all 9 cells
            {
                System.arraycopy(Cell, 0, tempCell, 0, 9);
                if (tempCell[j] == 0)//if the player were to place the marker in this cell, check what would happen
                {
                    tempCell[j] = player;
                    if (checkWin(tempCell, player) == player * WIN)//check if the player would win
                    {
                        //if player would win, return the position
                        return j;
                    }
                }
            }

            System.arraycopy(Cell, 0, tempCell, 0, 9);//copy the cells into a temporary cells array to store temporary board scenarios

            //these nested if statements check for special corner tricks in tic tac toe
            //if the player places "x" at 1 corner, the computer must place it in the middle
            //if the middle is taken, computer must place it at the opposite corner
            if ((tempCell[0] == PLY) || (tempCell[2] == PLY)
                    || (tempCell[6] == PLY) || (tempCell[8] == PLY))//if the player has taken a corner spot 
            {
                if (tempCell[4] == 0)//take the middle cell if it is free 
                {
                    tempCell[4] = CPU;
                    if (artificialIntelligence(tempCell, PLY) == 100) {
                        return 4;
                    }
                } else if (tempCell[4] == PLY)//if middle cell is occupied
                {
                    if (tempCell[8] == PLY || tempCell[0] == PLY) {

                        //return the opposite corner or another free corner
                        if (tempCell[2] == 0) {
                            tempCell[2] = CPU;
                            if (artificialIntelligence(tempCell, PLY) == 100) {
                                return 2;
                            }
                        } else if (tempCell[6] == 0) {
                            tempCell[6] = CPU;
                            if (artificialIntelligence(tempCell, PLY) == 100) {
                                return 6;
                            }
                        }
                    } else {
                        //return the opposite corner or the adjacent corner
                        if (tempCell[0] == 0) {
                            tempCell[0] = CPU;
                            if (artificialIntelligence(tempCell, PLY) == 100) {
                                return 0;
                            }
                        } else if (tempCell[8] == 0) {
                            tempCell[8] = CPU;
                            if (artificialIntelligence(tempCell, PLY) == 100) {
                                return 8;
                            }
                        }
                    }
                } else if (tempCell[1] == 0)//otherwise choose a side
                {
                    tempCell[1] = CPU;
                    if (artificialIntelligence(tempCell, PLY) == 100) {
                        return 1;
                    }
                } else if (tempCell[3] == 0)//otherwise choose a side
                {
                    tempCell[3] = CPU;
                    if (artificialIntelligence(tempCell, PLY) == 100) {
                        return 3;
                    }
                } else if (tempCell[5] == 0)//otherwise choose a side
                {
                    tempCell[5] = CPU;
                    if (artificialIntelligence(tempCell, PLY) == 100) {
                        return 5;
                    }
                } else if (tempCell[7] == 0)//otherwise choose a side
                {
                    tempCell[7] = CPU;
                    if (artificialIntelligence(tempCell, PLY) == 100) {
                        return 7;
                    }
                }
            }
        } else//if no corners are take
        {
            //check if there are winning moves
            for (int j = 0; j < 9; j++) {
                System.arraycopy(Cell, 0, tempCell, 0, 9);
                //if there is, take it and win the game
                if (tempCell[j] == 0) {
                    tempCell[j] = player;
                    if (checkWin(tempCell, player) == player * WIN) {
                        return j;
                    }
                }
            }

            return 100;
        }

        //check if there are places the player might win at
        for (int j = 0; j < 9; j++) {
            System.arraycopy(Cell, 0, tempCell, 0, 9);
            //if there is, take it before the player gets there
            if (tempCell[j] == 0) {
                tempCell[j] = CPU;
                if (artificialIntelligence(tempCell, PLY) == 100) {
                    return j;
                }
            }
        }
        return 100;
    }

    private static int checkWin(int[] Cell, int player) {
        //method checks all rows, columns, and diagonals to see if the user or computer has gotten a 3 in a row

        //using the following if statements, checks to see if the player has won the game
        if ((Cell[0] == player) && (Cell[1] == player) && (Cell[2] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[3] == player) && (Cell[4] == player)
                && (Cell[5] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[6] == player) && (Cell[7] == player)
                && (Cell[8] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[0] == player) && (Cell[3] == player)
                && (Cell[6] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[1] == player) && (Cell[4] == player)
                && (Cell[7] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[2] == player) && (Cell[5] == player)
                && (Cell[8] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[0] == player) && (Cell[4] == player)
                && (Cell[8] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[2] == player) && (Cell[4] == player)
                && (Cell[6] == player)) {
            return player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[0] == -player) && (Cell[1] == -player)
                && (Cell[2] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[3] == -player) && (Cell[4] == -player)
                && (Cell[5] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[6] == -player) && (Cell[7] == -player)
                && (Cell[8] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[0] == -player) && (Cell[3] == -player)
                && (Cell[6] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[1] == -player) && (Cell[4] == -player)
                && (Cell[7] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[2] == -player) && (Cell[5] == -player)
                && (Cell[8] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[0] == -player) && (Cell[4] == -player)
                && (Cell[8] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else if ((Cell[2] == -player) && (Cell[4] == -player)
                && (Cell[6] == -player)) {
            return -player * WIN;//the player has won, return the WIN constant
        } else {
            return 0;//nobody has won, return nothing
        }
    }

    private static void checkStats() {
        //this method displays the number of wins and losses in both easy and hard mode
        //this method displays the winning percentage in both easy and hard mode

        //create a table with column of equal width
        System.out.println("");
        System.out.printf("%-20s %-20s %-20s\n", "", "STATS", "");
        System.out.println("");
        System.out.printf("%-20s %-20s %-20s\n", "", "EASY MODE", "HARD MODE");
        System.out.printf("%-20s %-20s %-20s\n", "Games Won", numEasyWin, numHardWin);
        System.out.printf("%-20s %-20s %-20s\n", "Games Drawed", numEasyDraw, numHardDraw);
        System.out.printf("%-20s %-20s %-20s\n", "Games Lost", numEasyLost, numHardLost);
        System.out.printf("%-20s %-20s %-20s\n", "Games Played", numEasyGame, numHardGame);

        double tempWonEasy = numEasyWin;//create temporary double variable for division with doubles
        double tempWonHard = numHardWin;
        System.out.printf("%-20s %-20s %-20s\n", "win Percentage", tempWonEasy / numEasyGame*100 +"%", tempWonHard / numHardGame*100 + "%");

        gameEnd();//prompt user to go back to main menu
    }
}
