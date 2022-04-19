import java.util.Random;
public class Normal extends Sudoku{
    /**
     * Fills the board for the normal version
     */

    int[][] board = new int[9][9];
    public int[][] fill(){
        Random r = new Random();
        board = super.createBoard();
        //Each box has a minimum of 3 numbers
        int boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));

        //for the random amount of numbers in the box, removes that many numbers for the first 3 by 3 square.
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3)][r.nextInt(3)] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 3][r.nextInt(3)] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 6][r.nextInt(3)] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3)][r.nextInt(3) + 3] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 3][r.nextInt(3) + 3] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 6][r.nextInt(3) + 3] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3)][r.nextInt(3) + 6] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 3][r.nextInt(3) + 6] = 0;
        }

        boxNum = (r.nextInt(5) + (r.nextInt(3)+ 5));
        for(int n = 0; n < boxNum; n++){
            board[r.nextInt(3) + 6][r.nextInt(3) + 6] = 0;
        }

        return board;
    }
}

