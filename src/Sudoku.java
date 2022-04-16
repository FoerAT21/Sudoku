import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Scanner;



public abstract class Sudoku<Arraylist> {
    // Each integer in the stack is a position on the board
    // Used for undo
    private Stack<Integer> moves = new Stack<Integer>();
    private int[][] currentBoard;
    private int[][] correctBoard;

    /**
     * Abstract method, fills the board for each difficulty level
     */
    public abstract int[][] fill();

    /**
     * Constructor, fills the currentBoard and correctBoard instance variables
     */
    public Sudoku(){
        correctBoard = createBoard();
        currentBoard = this.fill();
    }

    /**
     * Plays a move
     * @throws Exception
     */
    public void play() throws Exception{
        Scanner input = new Scanner(System.in);
        int row;
        int col;
        int play;
        try {
            System.out.println("What row do you want to play in?");
            row = input.nextInt();
            if(row <0 || row>8){
                throw new IndexOutOfBoundsException();
            }
            System.out.println("What column do you want to play in?");
            col = input.nextInt();
            if(col <0 || col>8){
                throw new IndexOutOfBoundsException();
            }
            System.out.println("What number do you want to play there?");
            play = input.nextInt();
            if(play < 1 || play>9){
                throw new IOException();
            }
            if(!taken(row,col)){
                currentBoard[row][col] = play;
            }else{
                System.out.println("That position is already taken!");
            }
            if(checkRight(row,col)){
                System.out.println("Good move!");
            }else{
                System.out.println("That is the wrong move! Undo and try again!");
            }
            moves.push(row);
            moves.push(col);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Row and Column must be between 0 and 8 inclusive.");
        }catch(IOException e){
            System.out.println("The number that you play must be between 1 and 9 (inclusive)");
        }
    }

    /**
     * Checks if the play made is correct
     * @param row - the row the play was made at
     * @param col - the column the play was made at
     * @return - true or false, true if the correct move, false if the incorrect move
     */
    public boolean checkRight(int row, int col){return currentBoard[row][col] == correctBoard[row][col];}

    /**
     * Takes away the last move.
     */
    public void undo(){
        int col = moves.pop();
        int row = moves.pop();
        currentBoard[row][col] = 0;
    }

    /**
     * Checks if the position played at already has a number played
     * @param row - the row played at
     * @param col - the column played at
     * @return - true or false, true if the position is taken, false if not
     */
    public boolean taken(int row, int col){return currentBoard[row][col] != 0;}

    /**
     * Creates the board that will be played with
     * @return the board that will be played with
     */
    public int[][] createBoard(){
        int[][] returnable = new int[9][9];
        ArrayList<Integer> leftInRow = refill();
        Random rand = new Random();
        boolean present = false;
        int number;
        int index;
        int stuckCounter = 0; // Used to check if the board has gotten stuck

        for(int r = 0; r<returnable.length; r++){
            for(int c = 0; c<returnable[0].length; c++){
                index = rand.nextInt(leftInRow.size()); // The index being taken from the arraylist
                number = leftInRow.get(index); // the specific value that may be put into the board
                for(int row = 0; row<r; row++){
                    if(returnable[row][c] == number){
                        present = true; // True, if the number is found in the column above
                    }
                }

                if(!present){ //Number is not present above:
                    returnable[r][c] = number; // Adds number to board
                    leftInRow.remove(index); //Removes that number from the list
                }else{ // Number is present above
                    c--; //Goes back to the same column
                    present = false; // Reset the test for presence column
                    stuckCounter++; // Checks if the algorithm is stuck
                    if(stuckCounter > 6){
                        r--;
                        stuckCounter = 0; // Reset stuck test
                        break; // if the row gets stuck, we reset the row and try again.
                    }

                }
            }
            leftInRow = refill(); // Refill the used arrayList
        }
        return returnable;
    }

    /**
     * Returns a filled arraylist of 1-9, used in the createBord method to simplify the process
     * of filling the needed arrayList
     * @return - filled arraylist of 1-9
     */
    private ArrayList<Integer> refill(){
        ArrayList<Integer> returnable = new ArrayList<>();
        for(int i = 1; i<10; i++){
            returnable.add(i);
        }
        return returnable;
    }


    /**
     * Prints out the current sudoku
     * @return - the current sudoku
     */
    public String toString(){
        StringBuilder returnable = new StringBuilder();
        for(int row = 0; row<currentBoard[0].length; row++){
            for(int col = 0; col<currentBoard[0].length; col++){
                if(currentBoard[row][col] != 0){
                    returnable.append("|"+currentBoard[row][col] + "");
                }else{
                    returnable.append("| ");
                }
                if(col == 8){
                    returnable.append("|");
                }
                if(col%3==2 && col !=8){
                    returnable.append("|");
                }
            }
            returnable.append("\n");
        }
        return returnable.toString();
    }
}
