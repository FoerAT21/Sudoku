import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Easy extends Sudoku implements ActionListener {

    /**
     * Constructor, fills the currentBoard and correctBoard instance variables
     */
    public Easy() throws Exception{
        super(9);
    }

    /**
     * Fills the board for the easy version
     */
    public int[][] fill(){
        Random rand = new Random();
        int numToRemove = rand.nextInt(4) + 42; // Easy loses about 45 numbers each

        int[][] returnable = copyCorrectBoard();
        ArrayList<Position> positions = fillPos();

        for(int i = 0; i<numToRemove; i++){
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
