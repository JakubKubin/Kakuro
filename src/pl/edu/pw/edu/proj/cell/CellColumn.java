package pl.edu.pw.edu.proj.cell;

public class CellColumn extends Cell {
    /**
     * Required sum in a column of cell
     */
    public final int sumInColumn;
    public CellColumn(int _sumInColumn, int _x_coords, int _y_coords){
        super(_x_coords,_y_coords);
        this.setType("Column");
        sumInColumn = _sumInColumn;
    }
    /**
     * Getter of required sum in a column
     * @return sum in column
     */
    public int getSumInColumn() {
        return sumInColumn;
    }
}
