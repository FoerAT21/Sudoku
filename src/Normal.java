import java.util.Random;
public class Normal extends Sudoku{
    /**
     * Fills the board for the normal version
     */
    public int[][] fill(){
        int[][] b = new int[9][9];
        Random rand = new Random();
        for(int i = 0; i<b.length;i++){
            for(int j = 0; j<b[0].length; j++){
                b[i][j] = rand.nextInt(10);
            }
        }
        return b;
    }
}

