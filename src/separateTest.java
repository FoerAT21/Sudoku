import java.util.ArrayList;
import java.util.Random;

public class separateTest {
    public static void main(String[] args) {

        //printBoard(board);
    }

    private static void printBoard(int[][] board){
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[0].length; c++){
                System.out.print(board[r][c]+ " ");
            }
            System.out.println();
        }
    }
    public static boolean available(int board[][], int row, int col, int num){
        return !checkIfBox(board, row, col, num) && !checkIfCol(board,row, num)
                && !checkIfRow(board, col ,num);
    }
    private static ArrayList<Position> fillPos(){
        ArrayList<Position> returnable= new ArrayList<>();
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9;j++){
                returnable.add(new Position(i,j));
            }
        }
        return returnable;
    }
    private static boolean checkIfRow(int[][] board, int col, int num){
        for(int r = 0; r<9; r++){
            if(board[r][col] == num){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfCol(int[][] board, int row, int num){
        for(int col = 0; col < 9; col++){
            if(board[row][col] == num){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfBox(int[][] board, int row, int col, int num){
        int startingRowPos = 0;
        int endingRowPos;
        if(row <= 2){
            endingRowPos = 2;
        }else if (row <= 5){
            startingRowPos = 3;
            endingRowPos = 5;
        }else{
            startingRowPos = 6;
            endingRowPos = 8;
        }

        int startingColPos = 0;
        int endingColPos = 0;
        if(col <= 2){
            endingRowPos = 2;
        }else if (col <= 5){
            startingColPos = 3;
            endingColPos = 5;
        }else{
            startingColPos = 6;
            endingColPos = 8;
        }

        for(int r = startingRowPos; r<=endingRowPos; r++){
            for(int c = startingColPos; c<=endingColPos; c++){
                if(board[r][c] == num){
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayList<Integer> fillNums(){
        ArrayList<Integer> returnable = new ArrayList<>();
        for(int i = 1; i<10; i++){
            returnable.add(i);
        }
        return returnable;
    }
}
