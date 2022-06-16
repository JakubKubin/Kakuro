package pl.edu.pw.edu.proj.cell;


public class CellRow extends Cell {
    /**
     * Required sum in a row of cell
     */
    public final int sumInRow;
    public CellRow(int _sumInRow, int x_coords, int y_coords){
        super(x_coords,y_coords);
        this.setType("Row");
        sumInRow = _sumInRow;
    }

    /**
     * Getter of required sum in a row
     * @return sum in row
     */
    public int getSumInRow(){
        return sumInRow;
    }
}
