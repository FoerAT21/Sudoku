import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class Normal extends Sudoku{

    /**
     * Fills the board for the normal version
     */
    public Normal() throws IOException{}

    public int[][] fill(){
        int[][] returnable = copyCorrectBoard();
        ArrayList<Position> positions = fillPos();
        Random rand = new Random();
        int numToRemove = rand.nextInt(3) + 50;
        for(int i = 0; i<numToRemove; i++){
            ;
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

