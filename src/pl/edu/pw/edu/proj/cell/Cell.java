package pl.edu.pw.edu.proj.cell;

public class Cell {
    /**
     * Coordinate of x in cell
     */
    public final int x_coords;
    /**
     * Coordinate of y in cell
     */
    public final int y_coords;
    /**
     * Type of cell
     */
    private String type;

    Cell(int x_coords,int y_coords){
        type = "none";
        this.x_coords=x_coords;
        this.y_coords = y_coords;
    }

    /**
     * Getter of cell type
     * @return type of cell
     */
    public String getType(){
        return type;
    }

    /**
     * Setter of cell type
     * @param newType of cell
     */
    public void setType(String newType){
        type = newType;
    }

}
