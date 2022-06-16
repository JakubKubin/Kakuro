package pl.edu.pw.edu.proj.cell;

public class CellValue extends Cell {
    /**
     * value that cell contains and displays
     */
    private int value;

    /**
     * color of the cell on a board
     */
    private int color;

    public CellValue(int x_coords, int y_coords) {
        super(x_coords, y_coords);
        this.setType("Value");
        this.setColor(4); //blank
    }

    /**
     * Getter for a cell value
     * @return value of cell
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter for a cell value
     * @param newValue of cell
     */
    public void setValue(int newValue) {
        this.value = newValue;
    }

    /**
     * Setter for a cell color
     * @param newColor of value on board
     */
    public void setColor(int newColor) {
        this.color = newColor;
    }

    /**
     * Getter of a cell color
     * @return color of cell
     */
    public int getColor(){
        return color;
    }
}
