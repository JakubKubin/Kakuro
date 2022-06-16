package pl.edu.pw.edu.proj.board;

import pl.edu.pw.edu.proj.cell.*;

import java.util.Objects;

public class BoardSaver {

    private static Cell[][] cells;
    /**
     * Prepared string
     */
    public String mapstring;

    BoardSaver(Cell[][] _cells){
        cells = _cells;
        this.makeItText();
    }

    /**
     * Convert cell map to text
     */
    public void makeItText(){
        String[][] text = new String[cells.length+1][cells[0].length+1];
        mapstring = "";
        int i =0;
        int j =0;
        for(Cell[] cellArr : cells){
            j =0;
            String str = "";
            for(Cell cell : cellArr){
                if(Objects.equals(cell.getType(), "Value")){
                    CellValue temp = (CellValue) cell;
                    str = ""+temp.getValue();
                }
                else if(Objects.equals(cell.getType(), "Column")){
                    CellColumn temp = (CellColumn) cell;
                    str = "C"+temp.getSumInColumn();
                }
                else if(Objects.equals(cell.getType(), "Row")){
                    CellRow temp = (CellRow) cell;
                    str = "R"+temp.getSumInRow();
                }
                else if(Objects.equals(cell.getType(), "blocked")){
                    str = "*";
                }
                else if(Objects.equals(cell.getType(), "null")){
                    str = "";
                }
                else if(Objects.equals(cell.getType(), "Splited")) {
                    CellColumnAndRow temp = (CellColumnAndRow) cell;
                    str ="C"+temp.getSumInColumn()+"/R"+temp.getSumInRow();
                }
                text[i][j] = str;
                j++;
            }
            i++;
        }

        for(int x = 0; x <= i;x++){
            for(int y=0; y< j;y++){
                if(text[y][x]==null) {
                }
                else if(y ==i-1)
                    mapstring = mapstring + text[y][x];
                else
                    mapstring = mapstring + text[y][x] + ",";
            }
            mapstring = mapstring + "\n";
        }
    }


}
