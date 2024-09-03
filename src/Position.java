public class Position {
    private int row;
    private int col;

    /**
     * Constructor for the position, this is a class that keeps track of the positions in the board
     * @param row - the row position
     * @param col - the column position
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * the row position
     * @return - a row position
     */
    public int getRow(){
        return row;
    }

    /**
     * the column position
     * @return - a col position
     */
    public int getCol(){
        return col;
    }
}
