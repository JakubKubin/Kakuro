package pl.edu.pw.edu.proj.board;

import pl.edu.pw.edu.proj.cell.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BoardSolver {
    private final Cell[][] cells;
    private static final ArrayList<Cell[]> listOfArrays = new ArrayList<>(30);

    /**
     * Constructor for board solver and color changer
     * Board is instantly divided for smaller arrays
     * @param _cells list of cells
     */
    public BoardSolver(Cell[][] _cells) {
        cells = _cells;
        this.divideForSmallerArrays();
    }

    /**
     * Divide cell list for smaller arrays for rows and column to lately operate on them
     */
    public void divideForSmallerArrays(){
        for (int x =0;x<cells.length;x++){
            for(int y=0;y<cells[0].length;y++){
                Cell[] tempY = new Cell[cells[0].length-y];
                Cell[] tempX = new Cell[cells[0].length-x];
                switch (cells[x][y].getType()) {
                    case "Row" -> {
                        tempX[0] = cells[x][y];
                        for (int i = 1; i < cells.length - x; i++) {
                            if (cells[x + i][y].getType().equals("Value"))
                                tempX[i] = cells[x + i][y];
                            else
                                break;
                        }
                        int num = 0;
                        for (Cell cell : tempX) {
                            if (cell != null)
                                num++;
                        }
                        Cell[] arr = new Cell[num];
                        int index = 0;
                        for (Cell cell : tempX) {
                            if (cell != null) {
                                arr[index] = cell;
                                index++;
                            }
                        }
                        listOfArrays.removeIf(cellarr -> Arrays.equals(cellarr, arr));
                        if (arr[0] != null)
                            listOfArrays.add(arr);

                    }
                    case "Column" -> {
                        tempY[0] = cells[x][y];
                        for (int j = 1; j < cells[0].length - y; j++) {
                            if (cells[x][y + j].getType().equals("Value"))
                                tempY[j] = cells[x][y + j];
                            else
                                break;
                        }
                        int num = 0;
                        for (Cell cell : tempY) {
                            if (cell != null)
                                num++;
                        }
                        Cell[] arr = new Cell[num];
                        int index = 0;
                        for (Cell cell : tempY) {
                            if (cell != null) {
                                arr[index] = cell;
                                index++;
                            }

                        }
                        listOfArrays.removeIf(cellarr -> Arrays.equals(cellarr, arr));
                        if (arr[0] != null) {
                            listOfArrays.add(arr);
                        }
                    }
                    case "Splited" -> {
                        tempX[0] = cells[x][y];
                        for (int i = 1; i < cells.length - x; i++) {
                            if (cells[x + i][y].getType().equals("Value"))
                                tempX[i] = cells[x + i][y];
                            else
                                break;
                        }

                        int num = 0;
                        for (Cell cell : tempX) {
                            if (cell != null)
                                num++;
                        }
                        Cell[] arr = new Cell[num];
                        int index = 0;
                        for (Cell cell : tempX) {
                            if (cell != null) {
                                arr[index] = cell;
                                index++;
                            }
                        }
                        Cell[] finalArr = arr;
                        listOfArrays.removeIf(cellarr -> Arrays.equals(cellarr, finalArr));

                        if (arr[0] != null)
                            listOfArrays.add(arr);

                        tempY[0] = cells[x][y];
                        for (int j = 1; j < cells[0].length - y; j++) {
                            if (cells[x][y + j].getType().equals("Value"))
                                tempY[j] = cells[x][y + j];
                            else
                                break;
                        }
                        num = 0;
                        for (Cell cell : tempY) {
                            if (cell != null)
                                num++;
                        }
                        arr = new Cell[num];
                        index = 0;
                        for (Cell cell : tempY) {
                            if (cell != null) {
                                arr[index] = cell;
                                index++;
                            }
                        }
                        Cell[] finalArr1 = arr;
                        listOfArrays.removeIf(cellarr -> Arrays.equals(cellarr, finalArr1));

                        if (arr[0] != null) {
                            listOfArrays.add(arr);
                        }
                    }
                }
            }
        }
    }

    /**
     *  Check which color should be applied to exact cell
     * @param cell provided cell
     * @return color of cell
     */
    public int solve(Cell cell){
        int correct = 2;
        for(Cell[] cellArr:listOfArrays){
            for(Cell c : cellArr){
                if(c.equals(cell)) {
                    if(Objects.equals(cellArr[0].getType(), "Row")) {
                        CellRow cellrow = (CellRow) cellArr[0];
                        int sum = 0;
                        int[] values = new int[cellArr.length - 1];
                        for (int i = 1; i <= values.length; i++) { //taking all values from cells
                            CellValue temp = (CellValue) cellArr[i];
                            values[i - 1] = temp.getValue(); //table of values
                            sum = sum + temp.getValue(); //sum of values
                        }
                        for (int i = 0; i < values.length; i++) { //duplicate check
                            for (int j = i + 1; j < values.length; j++) {
                                if (values[i] == values[j] && values[i] != 0) {
                                    return 0; //found duplicate
                                }
                            }
                        }
                        boolean check = true; //condition on checking correctness of column

                        for (int val : values) {
                            if (val == 0) { //there must be value in each cell
                                check = false;
                                break;
                            }
                        }
                        if (sum == cellrow.getSumInRow() && check) {
                            correct= 1; //correct row
                        } else if (sum == cellrow.getSumInRow() && !check) { //sum is correct but not every cell is full
                            return 0; //incorrect row
                        } else if (sum > cellrow.getSumInRow() || (sum < cellrow.getSumInRow() && check)) { //sum is too big
                            return 0; //incorrect row
                        }
                    }
                    if(Objects.equals(cellArr[0].getType(), "Column")) {
                        CellColumn cellcol = (CellColumn) cellArr[0];
                        int sum = 0;
                        int[] values = new int[cellArr.length - 1];
                        for (int i = 1; i <= values.length; i++) { //taking all values from cells
                            CellValue temp = (CellValue) cellArr[i];
                            values[i - 1] = temp.getValue(); //table of values
                            sum = sum + temp.getValue(); //sum of values
                        }
                        for (int i = 0; i < values.length; i++) { //duplicate check
                            for (int j = i + 1; j < values.length; j++) {
                                if (values[i] == values[j] && values[i] != 0) {
                                    return 0; //found duplicate
                                }
                            }
                        }
                        boolean check = true; //condition on checking correctness of column

                        for (int val : values) {
                            if (val == 0) { //there must be value in each cell
                                check = false;
                                break;
                            }
                        }
                        if (sum == cellcol.getSumInColumn() && check) {
                            correct= 1; //correct column
                        } else if (sum == cellcol.getSumInColumn() && !check) { //sum is correct but not every cell is full
                            return 0; //incorrect column
                        } else if (sum > cellcol.getSumInColumn() || (sum < cellcol.getSumInColumn() && check)) { //sum is too big
                            return 0; //incorrect column
                        }
                    }
                    if(Objects.equals(cellArr[0].getType(), "Splited")) {
                        CellColumnAndRow cellcolumnrow = (CellColumnAndRow) cellArr[0];
                        int sum = 0;
                        int[] values = new int[cellArr.length - 1];
                        if (cell.x_coords == cellcolumnrow.x_coords) {
                            for (int i = 1; i <= values.length; i++) { //taking all values from cells
                                CellValue temp = (CellValue) cellArr[i];
                                values[i - 1] = temp.getValue(); //table of values
                                sum = sum + temp.getValue(); //sum of values
                            }
                            for (int i = 0; i < values.length; i++) { //duplicate check
                                for (int j = i + 1; j < values.length; j++) {
                                    if (values[i] == values[j] && values[i] != 0) {
                                        return 0; //found duplicate
                                    }
                                }
                            }
                            boolean check = true; //condition on checking correctness of row
                            for (int val : values) {
                                if (val == 0) {
                                    check = false;
                                    break;
                                }
                            }

                            if (sum == cellcolumnrow.getSumInColumn() && check) {
                                correct= 1; //correct column
                            } else if (sum == cellcolumnrow.getSumInColumn() && !check) { //sum is correct but not every cell is full
                                return 0; //incorrect column
                            } else if (sum > cellcolumnrow.getSumInColumn() || (sum < cellcolumnrow.getSumInColumn() && check)) { //sum is too big
                                return 0; //incorrect column
                            }
                        } else if (cell.y_coords == cellcolumnrow.y_coords) {
                            for (int i = 1; i <= values.length; i++) { //taking all values from cells
                                CellValue temp = (CellValue) cellArr[i];
                                values[i - 1] = temp.getValue(); //table of values
                                sum = sum + temp.getValue(); //sum of values
                            }
                            for (int i = 0; i < values.length; i++) { //duplicate check
                                for (int j = i + 1; j < values.length; j++) {
                                    if (values[i] == values[j] && values[i] != 0) {
                                        return 0; //found duplicate
                                    }
                                }
                            }
                            boolean check = true; //condition on checking correctness of column
                            for (int val : values) {
                                if (val == 0) { //there must be value in each cell
                                    check = false;
                                    break;
                                }
                            }
                            if (sum == cellcolumnrow.getSumInRow() && check) {
                                correct= 1; //correct row
                            } else if (sum == cellcolumnrow.getSumInRow() && !check) { //sum is correct but not every cell is full
                                return 0; //incorrect row
                            } else if (sum > cellcolumnrow.getSumInRow() || (sum < cellcolumnrow.getSumInRow() && check)) { //sum is too big
                                return 0; //incorrect row
                            }
                        }
                    }
                }
            }
        }
        if(correct==1){
            return correct;
        }
        return 2;
    }
}
