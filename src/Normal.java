import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class Normal extends Sudoku implements ActionListener {

    /**
     * Fills the board for the normal version
     */
    public Normal() throws IOException, Exception{
        super(9);
    }

    /**
     * Fills the board for the easy version
     */
    public int[][] fill(){
        int[][] returnable = copyCorrectBoard();
        ArrayList<Position> positions = fillPos();
        Random rand = new Random();
        int numToRemove = rand.nextInt(3) + 47;
        for(int i = 0; i<numToRemove; i++){
            ;
            int random = rand.nextInt(positions.size());
            returnable[positions.get(random).getRow()][positions.get(random).getCol()] = 0;
            positions.remove(random);
        }
        return returnable;
    }

    /**
     * Fills an arraylist full of positions
     * @return - an arraylist full of positions from (0,0) - (8,8)
     */
    private ArrayList<Position> fillPos(){
        ArrayList<Position> returnable = new ArrayList<>();
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                returnable.add(new Position(i,j));
            }
        }
        return returnable;
    }

    /**
     * Required for the class, the method calls directly to the parent class
     * @param e - the event that caused this method to be called
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }
}

