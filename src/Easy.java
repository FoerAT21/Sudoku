import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Easy extends Sudoku{

    /**
     * Constructor, fills the currentBoard and correctBoard instance variables
     */
    public Easy() throws IOException {}

    /**
     * Fills the board for the easy version
     */
    public int[][] fill(){
        Random rand = new Random();
        int numToRemove = rand.nextInt(4) + 42;

        int[][] returnable = copyCorrectBoard();
        ArrayList<Position> positions = fillPos();

        for(int i = 0; i<numToRemove; i++){
            int random = rand.nextInt(positions.size());
            returnable[positions.get(random).getRow()][positions.get(random).getCol()] = 0;
        }
        return returnable;
    }

    private ArrayList<Position> fillPos(){
        ArrayList<Position> returnable = new ArrayList<>();
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                returnable.add(new Position(i,j));
            }
        }
        return returnable;
    }
}
