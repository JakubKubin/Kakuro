package pl.edu.pw.edu.proj.cell;

public class CellColumnAndRow extends Cell {
    /**
     * Required sum in a row of cell
     */
    public final int sumInRow;
    /**
     * Required sum in a column of cell
     */
    public final int sumInColumn;

    public CellColumnAndRow(int _sumInColumn, int _sumInRow, int _x_coords, int _y_coords){
        super(_x_coords,_y_coords);
        this.setType("Splited");
        sumInColumn = _sumInColumn;
        sumInRow = _sumInRow;
    }
    /**
     * Getter of required sum in a row
     * @return sum in row
     */
    public int getSumInRow(){
        return sumInRow;
    }
    /**
     * Getter of required sum in a column
     * @return sum in column
     */
    public int getSumInColumn() {return sumInColumn;}
}

