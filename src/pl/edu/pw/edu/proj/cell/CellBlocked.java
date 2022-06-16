package pl.edu.pw.edu.proj.cell;

public class CellBlocked extends Cell { //unfillable cell that only sperates or blocks rows and columns
    public CellBlocked(int _x_coords, int _y_coords){
        super(_x_coords, _y_coords);
        this.setType("blocked");
    }

}
