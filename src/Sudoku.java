import java.io.File;
import java.io.IOException;
import java.util.*;


public abstract class Sudoku {
    // Each integer in the stack is a position on the board
    // Used for undo
    private Stack<Integer> moves = new Stack<>();
    private int[][] currentBoard;
    private int[][] correctBoard;

    /**
     * Abstract method, fills the board for each difficulty level
     */
    public abstract int[][] fill();

    /**
     * Constructor, fills the currentBoard and correctBoard instance variables
     */
    public Sudoku() throws IOException {
        correctBoard = getCorrectBoard();
        currentBoard = fill();
    }

    public int[][] getCorrectBoard() throws IOException{
        int[][] returnable = new int[9][9];
        HashMap<String, String> boards = makeMap();
        Random rand = new Random();
        int boardNum = rand.nextInt(boards.size())+1;
        String key = "Board " + boardNum;
        String board = boards.get(key);
        Scanner boardScanner = new Scanner(board);

        int rowNum = 0;
        int colNum = 0;

        while(boardScanner.hasNextLine()){
            String line = boardScanner.nextLine();
            Scanner parser = new Scanner(line);
            while(parser.hasNextInt()){
                int input = parser.nextInt();
                returnable[rowNum][colNum] = input;
                colNum++;
            }
            rowNum++;
            colNum=0;
        }
        return returnable;
    }

    private HashMap<String, String> makeMap() throws IOException{
        HashMap<String, String> returnable = new HashMap<>();
        Scanner fileIn = new Scanner(new File("correctBoards.txt"));
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> tempBoards = new ArrayList<>();
        String tempBoard = "";

        while(fileIn.hasNextLine()){
            String line = fileIn.nextLine();
            if(!line.equals("")) {
                if (line.charAt(0) == 'B'){
                    keys.add(line.substring(0,line.indexOf(":")));
                    line = fileIn.nextLine();
                    while(!line.equals("")){
                        tempBoard += line + "\n";
                        line = fileIn.nextLine();
                    }
                }
            }
            tempBoards.add(tempBoard);
            tempBoard = "";
        }
        for(int i = 0; i<tempBoards.size(); i++){
            returnable.put(keys.get(i), tempBoards.get(i));
        }
        return returnable;
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

    public String printCorrectBoard(){

        StringBuilder returnable = new StringBuilder();
        for(int row = 0; row<correctBoard[0].length; row++){
            for(int col = 0; col<correctBoard[0].length; col++){
                if(correctBoard[row][col] != 0){
                    returnable.append("|"+correctBoard[row][col] + "");
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

    public int[][] copyCorrectBoard(){
        int[][] returnable = new int[9][9];
        for(int r = 0; r<correctBoard.length; r++){
            for(int c = 0; c<correctBoard[0].length; c++){
                returnable[r][c] = correctBoard[r][c];
            }
        }
        return returnable;
    }

}
