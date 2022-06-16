package pl.edu.pw.edu.proj.board;

import pl.edu.pw.edu.proj.cell.*;

import java.io.*;
import java.util.*;

public class BoardGenerator {
    private static final String BOARD_PATH = System.getProperty("user.dir") + "\\data\\";
    private final Cell[][] cells;
    public int dim_x;
    public int dim_y;

    /**
     * Constructor for known level of difficulty board
     * @param level difficulty level
     * @param dim_x x dimension
     * @param dim_y y dimension
     * @throws IOException if there is no local maps of selected difficulty
     */
    BoardGenerator(String level,int dim_x,int dim_y) throws IOException {
        cells = makeItCells(readFile(dim_x, dim_y, level));
    }

    /**
     * Constructor for loaded board
     * @param file chosen text file
     * @throws IOException if file does not exist
     */
    BoardGenerator(File file) throws IOException {
        Integer[] dims = findDims(file);
        cells = makeItCells(readFile(dims[0],dims[1],file));
    }

    /**
     * Finds the dimensions from chosen text file
     * @param file that has been chosen
     * @return Integer[2] that contains [0] - x, [1] - y dimensions
     */
    public Integer[] findDims(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        }
        lines.remove("");
        String[] strArray = lines.get(0).split(",");
        dim_x = strArray.length;
        dim_y = lines.size();
        Integer[] dims = new Integer[2];
        dims[0] = dim_x;
        dims[1] = dim_y;
        return dims;
    }
    /**
     * Draw random path to file
     * @param level chosen file
     * @retrun final path to random board
     */
    public String getRandomPathToFile(String level){
        String boardPath = BOARD_PATH + level;
        File dir = new File(boardPath);
        List<String> filesList = Arrays.asList(Objects.requireNonNull(dir.list()));
        Random rand = new Random();
        return boardPath + "\\" + filesList.get(rand.nextInt(filesList.size()));
    }
    /**
     * Read a chosen file and converts it into matrix of Strings
     * @param dim_x dimension in x
     * @param dim_y dimension in y
     * @param level difficulty
     * @retrun matrix of Strings
     */
    public String[][] readFile(int dim_x,int dim_y,String level) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(getRandomPathToFile(level)))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        }
        String[][] map = new String[dim_x][dim_y];
        int y=0;
        for(String s: lines){
            int x=0;
            String[] parts = s.split(",");
            for(String p:parts){
                map[x][y] = p;
                x+=1;
            }
            y+=1;
        }
        return map;
    }

    /**
     * Read a chosen file and converts it into matrix of Strings
     *
     * @param dim_x dimension in x
     * @param dim_y dimension in y
     * @param file chosen file
     * @retrun matrix of Strings
     */
    public String[][] readFile(int dim_x,int dim_y,File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        }
        String[][] map = new String[dim_x][dim_y];
        int y=0;
        for(String s: lines){
            int x=0;
            String[] parts = s.split(",");
            for(String p:parts){
                if(!Objects.equals(p, "")){
                    map[x][y] = p;
                    x+=1;
                }
            }
            y+=1;
        }
        return map;
    }
    /**
     * Convert Strings to Cells from two-dimensional string matrix and add them to two-dimensional cell matrix
     *
     * @param map two-dimensional string matrix
     * @return two-dimensional cell matrix
     */
    public Cell[][] makeItCells(String[][] map){ //making cells from txt
        Cell[][] cell_map = new Cell[map.length][map[0].length];
        for(int x =0;x<map.length;x++){
            for(int y =0;y<map[0].length;y++){
                String string = map[x][y];
                Cell cell;

                if(string.contains("*")){
                    cell = new CellBlocked(x,y);
                    cell_map[x][y] = cell;
                }
                else if(string.contains("/")){
                    String[] tuple = string.split("/");
                    tuple[0] = tuple[0].replace("C","");
                    tuple[1] = tuple[1].replace("R","");
                    int sumInColumn = Integer.parseInt(tuple[0]);
                    int sumInRow = Integer.parseInt(tuple[1]);
                    cell = new CellColumnAndRow(sumInColumn,sumInRow,x,y);
                    cell_map[x][y] = cell;
                }
                else if(string.contains("C")){
                    string = string.replace("C","");
                    int sumInColumn = Integer.parseInt(string);
                    cell = new CellColumn(sumInColumn,x,y);
                    cell_map[x][y] = cell;
                }
                else if(string.contains("R")){
                    string = string.replace("R","");
                    int sumInRow = Integer.parseInt(string);
                    cell = new CellRow(sumInRow,x,y);
                    cell_map[x][y] = cell;
                }
                else if(string.contains("v")){
                    cell = new CellValue(x,y);
                    cell_map[x][y] = cell;
                }
                else if(string.contains("1")||string.contains("2")||string.contains("3")||string.contains("4")
                        ||string.contains("5")||string.contains("6")||string.contains("7")||string.contains("8")
                        ||string.contains("9")||string.contains("0")){
                    cell = new CellValue(x,y);
                    ((CellValue) cell).setValue(Integer.parseInt(string));
                    cell_map[x][y] = cell;
                }
            }
        }
        return cell_map;
    }

    /**
     * Gets two-dimensional cell matrix
     *
     * @return two-dimensional cell matrix
     */
    public Cell[][] getCellMap(){
        return cells;
    }
}
