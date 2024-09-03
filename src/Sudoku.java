import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Sudoku player. Implemented for Computer Programming 2
 * Developed April, 2021
 * @author Andrew Foerst, Ethan Kesterholt
 */
public abstract class Sudoku implements ActionListener{
    // Each integer in the stack is a position on the board
    // Used for undo
    private Stack<Position> moves = new Stack<>();
    private int[][] currentBoard;
    private int[][] correctBoard;
    private int size;

    JFrame frame;
    JButton[][] board; // Array of buttons that is the board
    JButton[] numButtons = new JButton[9]; // Array of Buttons 1-9
    JButton undoButton; // Undo button on the GUI
    static JButton easyButton;
    static JButton mediumButton;
    static JButton hardButton;

    /**
     * Abstract method, fills the board for each difficulty level
     */
    public abstract int[][] fill();

    public static void main(String[] args) throws IOException{
        play();
    }

    /**
     * Constructor, fills the currentBoard and correctBoard instance variables. It also creates the GUI.
     */
    public Sudoku(int size) throws Exception {
        this.size = size;

        correctBoard = getCorrectBoard();
        currentBoard = fill();

        frame = new JFrame("SODOKU!"); // Initiates Frame for Sudoku
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program stops on exit
        frame.setSize(1920, 1080); // Sets the size of sudoku to 1920x1080
        frame.setLayout(null); // No layout needed for sudoku
        board = new JButton[9][9]; // Sets up the box 9x9 box for sudoku

        int tempX = 395;
        int tempY = 60;

        // Sets the needed values for each text field element
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[0].length;c++){
                board[r][c] = new JButton();
                if(currentBoard[r][c] != 0){
                    board[r][c].setText(String.valueOf(currentBoard[r][c]));
                    board[r][c].setBackground(new Color(255,255,255));
                }
                board[r][c].addActionListener(this);
                board[r][c].setFocusable(false);

                if((r == 3 || r==6) && c==0){
                    tempY += 4;
                }

                board[r][c].setBounds(tempX, tempY, 60, 60);

                if(c==2 || c==5){
                    tempX += 4;
                }
                tempX += 60;
            }
            tempX=395;
            tempY+=60;
        }

        // Puts the button elements in the frame
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[0].length; c++){
                frame.add(board[r][c]);
            }
        }
        frame.setVisible(true); //frame is visible

        tempX = 90;
        tempY = 180;
        for(int i = 0; i< numButtons.length; i++){
            numButtons[i] = new JButton(String.valueOf(i+1));
            numButtons[i].addActionListener(this);
            numButtons[i].setFocusable(false);
            numButtons[i].setBounds(tempX,tempY,70,70);
            tempX += 70;
            if(i == 2 || i==5){
                tempY += 70;
                tempX = 90;
            }
            frame.add(numButtons[i]);
        }

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        undoButton.setFocusable(false);
        undoButton.setBounds(90,tempY+70, 210, 70);
        frame.add(undoButton);
    }

    public int getSize(){
        return size;
    }
    /**
     * Checks if the value being checked in checkRight is in the row
     * @param row - the row position of the value
     * @param col - the column position of the value
     * @return - true or false, true if the value is in the row, false if not
     */
    private boolean inRow(int row, int col){
        boolean found = false;
        for(int c = 0; c< currentBoard.length; c++){
            if(c == col){
                if(col== currentBoard.length-1){
                    break;
                }
                c++;
            }
            if(currentBoard[row][c] == currentBoard[row][col]){
                found = true;
            }
        }
        return found;
    }

    /**
     * Checks if the number being checked in checkRight is in the column
     * @param row - the row position of the value
     * @param col - the column position of the value
     * @return - true or false, true if the value is in the column; false if not
     */
    private boolean inCol(int row, int col){
        boolean found = false;
        for(int r = 0; r< currentBoard.length; r++){
            if(r == row){
                if(row == currentBoard.length-1){
                    break;
                }
                r++;
            }
            if(currentBoard[r][col] == currentBoard[row][col]){
                found = true;
            }
        }
        return found;
    }

    /**
     * Checks if the value that is being checked in checkRight is in the box
     * @param row - the row position of the value
     * @param col - the column position of the value
     * @return - true or false, true if the value is in the box, false if the value is not
     */
    private boolean inBox(int row, int col){
        int rowStartingPoint;
        int rowEndingPoint;
        int colStartingPoint;
        int colEndingPoint;
        boolean found = false;

        if(row<=2){
            rowStartingPoint = 0;
            rowEndingPoint = 2;
        }else if(row <=5){
            rowStartingPoint = 3;
            rowEndingPoint = 5;
        }else{
            rowStartingPoint = 6;
            rowEndingPoint = 8;
        }

        if(col <= 2){
            colStartingPoint = 0;
            colEndingPoint = 2;
        }else if(col<=5){
            colStartingPoint = 3;
            colEndingPoint = 5;
        }else{
            colStartingPoint=6;
            colEndingPoint=8;
        }
        for(int r  = rowStartingPoint; r<=rowEndingPoint; r++){
            for(int c = colStartingPoint; c<=colEndingPoint; c++){
                if(c == col){
                    if(col == colEndingPoint){
                        break;
                    }
                    c++;
                }
                if(currentBoard[r][c] == currentBoard[row][col]){
                    found = true;
                    break;
                }
            }
        }
        return found;
    }


    /**
     * Returns a random board that will be played with
     * @return - a 2d array that is the board that will be played with
     * @throws IOException - may have a problem in makeMap
     */
    private int[][] getCorrectBoard() throws IOException{
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

    /**
     * Sends an easily searchable collection of boards
     * @return - a HashMap of boards with the board number as the key
     * @throws IOException - may have a problem when accessing the correctBoards file
     */
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
     * Checks if the play made is correct
     * @param row - the row the play was made at
     * @param col - the column the play was made at
     * @return - true or false, true if the correct move, false if the incorrect move
     */
    public boolean checkRight(int row, int col, boolean lateGame){
        if(!lateGame){ // If it is not late in the game then check board
            return currentBoard[row][col] == correctBoard[row][col];
        }
        return !inRow(row, col) && !inCol(row, col)
                && !inBox(row,col);
    }

    /**
     * Takes away the last move from the board.
     */
    public void undo(){
        if(moves.size() > 0) { // do this as long as there is a move to undo
            Position undoable = moves.pop();
            currentBoard[undoable.getRow()][undoable.getCol()] = 0;
            frame.remove(board[undoable.getRow()][undoable.getCol()]);
            frame.repaint(); // Presents the current frame again (used because the program would not update
                             // without).

            int colNum;
            int tempX;
            int tempY;

            if(undoable.getCol() == 0){
                colNum = undoable.getCol()+1;
                tempX = board[undoable.getRow()][colNum].getX() - 60;
                tempY = board[undoable.getRow()][colNum].getY();
            }else{
                colNum = undoable.getCol()-1;
                tempX = board[undoable.getRow()][colNum].getX() + 60;
                tempY = board[undoable.getRow()][colNum].getY();
            }

            if(undoable.getCol() == 3 || undoable.getCol() == 6){
                tempX += 4;
            }

            board[undoable.getRow()][undoable.getCol()] = new JButton();

            board[undoable.getRow()][undoable.getCol()].addActionListener(this);
            board[undoable.getRow()][undoable.getCol()].setFocusable(false);
            board[undoable.getRow()][undoable.getCol()].setBounds(tempX, tempY, 60, 60);
            frame.add(board[undoable.getRow()][undoable.getCol()]);
        }
    }

    /**
     * Checks if the position played at already has a number played
     * @param row - the row played at
     * @param col - the column played at
     * @return - true or false, true if the position is taken, false if not
     */
    private boolean taken(int row, int col){return currentBoard[row][col] != 0;}

    /**
     * Private method to check if the board is full
     * @return - true or false, true if board is full; false if board is not full
     */
    private boolean boardFull(){
        for(int r = 0; r< currentBoard.length; r++){
            for(int c = 0; c< currentBoard[0].length; c++){
                if(currentBoard[r][c] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Copies the correct board
     * @return - a 2d array that is the correct board
     */
    public int[][] copyCorrectBoard(){
        int[][] returnable = new int[9][9];
        for(int r = 0; r<correctBoard.length; r++){
            for(int c = 0; c<correctBoard[0].length; c++){
                returnable[r][c] = correctBoard[r][c];
            }
        }
        return returnable;
    }

    /**
     * Checks if the board is nearly full
     * @return - true if the board is only missing four or fewer numbers false if not
     */
    private boolean isLateGame(){
        int zeroCounter = 0;
        for(int r = 0; r<currentBoard.length; r++){
            for(int c = 0; c<currentBoard[0].length; c++){
                if(currentBoard[r][c]==0){
                    zeroCounter++;
                }
            }
        }
        return zeroCounter<5;
    }

    private Position pos = new Position(-1,-1); // Position for the next move

    /**
     * The method that interacts with the GUI sudoku board
     * @param e - the event that caused this method to be called
     */
    public void actionPerformed(ActionEvent e) {

        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[0].length; c++){
                if(e.getSource().equals(board[r][c])){
                    pos = new Position(r,c);
                }
            }
        }

        for(int i = 0; i<numButtons.length; i++){
            if(e.getSource().equals(numButtons[i])){
                if(pos.getCol() != -1 && pos.getRow() != -1){
                    if(!taken(pos.getRow(), pos.getCol())){
                        currentBoard[pos.getRow()][pos.getCol()] = i+1;
                        board[pos.getRow()][pos.getCol()].setText(String.valueOf(i+1));
                        moves.push(pos);

                        if(checkRight(pos.getRow(), pos.getCol(), isLateGame())) {
                            board[pos.getRow()][pos.getCol()].setBackground(new Color(255, 255, 255));
                            if(boardFull()){
                                winner();
                                frame.setVisible(false);
                            }
                        }else{
                            board[pos.getRow()][pos.getCol()].setBackground(Color.PINK);
                        }
                        pos = new Position(-1,-1);
                    }
                }
            }
        }

        if(e.getSource().equals(undoButton)){
            undo();
        }
    }
    private static void play(){
        JFrame homeScreen = new JFrame();
        homeScreen.setSize(1920,1080);
        homeScreen.setVisible(true);
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setLayout(null);

        ActionListener action = e -> {
            if(e.getSource().equals(easyButton)){
                try{
                    Sudoku play = new Easy();
                    homeScreen.setVisible(false);
                }catch(Exception error){
                    System.exit(1);
                }
            }

            if(e.getSource().equals(mediumButton)){
                try{
                    Sudoku play = new Normal();
                    homeScreen.setVisible(false);
                }catch(Exception error){
                    System.exit(1);
                }
            }

            if(e.getSource().equals(hardButton)){
                try {
                    Sudoku play = new Hard();
                    homeScreen.setVisible(false);
                }catch(Exception error){
                    System.exit(1);
                }

            }
        };

        int size = 200;

        easyButton = new JButton();
        mediumButton = new JButton();
        hardButton = new JButton();

        easyButton.setFocusable(false);
        easyButton.addActionListener(action);
        easyButton.setBounds(250,250, size, size);
        easyButton.setText("Easy :D");
        homeScreen.add(easyButton);

        mediumButton.addActionListener(action);
        mediumButton.setFocusable(false);
        mediumButton.setBounds(550, 250, size, size);
        mediumButton.setText("Medium :|");
        homeScreen.add(mediumButton);

        hardButton.addActionListener(action);
        hardButton.setFocusable(false);
        hardButton.setBounds(850,250,size,size);
        hardButton.setText("Hard D:");
        homeScreen.add(hardButton);
    }
    private void winner(){
        JFrame winnerScreen = new JFrame();
        winnerScreen.setLayout(null);
        winnerScreen.setSize(1920,1080);
        winnerScreen.setVisible(true);
        winnerScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton playAgain = new JButton("Play Again?");

        ActionListener action = e -> {
            if(e.getSource().equals(playAgain)){
                play();
            }
        };

        playAgain.setBounds(550, 250, 200, 200);
        playAgain.setFocusable(false);
        playAgain.addActionListener(action);
        winnerScreen.add(playAgain);
    }
}
