package pl.edu.pw.edu.proj.panel;

import pl.edu.pw.edu.proj.board.BoardSolver;
import pl.edu.pw.edu.proj.cell.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GamePanel extends JPanel {
    /**
     * Cell map
     */
    private static Cell[][] cells;
    /**
     * Image of board
     */
    public static BufferedImage bImage = null;
    /**
     * Max dimension in y
     */
    public static int dim_x;
    /**
     * Max dimension in y
     */
    public static int dim_y;
    /**
     * Chosen difficulty of a board
     */
    public final String difficulty;
    public GamePanel(Cell[][] celllist, int _dim_x, int _dim_y, String _difficulty) {
        cells = celllist;
        dim_x = _dim_x;
        dim_y = _dim_y;
        difficulty = _difficulty;
        play = true;
        Click click = new Click();
        this.addMouseListener(click);
    }

    /**
     * Getter of cell map
     * @return cell map
     */
    public Cell[][] getCellMap(){
        return cells;
    }
    /**
     * Space between each rectangle on board
     */
    static final int space = 1;
    /**
     * Condition of game running
     */
    static boolean play = true;
    /**
     * Size of a rectangle on board
     */
    static int size;
    /**
     * Move in x dimension to place board in center of window
     */
    static int move;
    /**
     * Font of text in Graphics
     */
    final Font font = new Font("Arial", Font.BOLD, 12);

    /**
     * Prepares a scaled values of size and move depending on a board size
     */
    public void setMoveAndSize(){
        if (Objects.equals(difficulty, "hard")) {
            move = 30;
            size = 40;
        }
        if (Objects.equals(difficulty, "medium")) {
            move = 60;
            size = 55;
        }
        if (Objects.equals(difficulty, "easy")) {
            move = 30;
            size = 104;
        }
        if (Objects.equals(difficulty, "load")) {
            if (dim_x == 5) {
                move = 30;
                size = 104;
            } else if (dim_x == 8 || dim_y == 8) {
                move = 60;
                size = 55;
            } else if (dim_x == 13) {
                move = 30;
                size = 40;
            } else {
                move = 30;
                size = 55;
            }
        }
    }

    /**
     * Graphics maker of board
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        if (play) {
            repaint();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.setColor(Color.LIGHT_GRAY);
            setMoveAndSize();
            for (int i = 0; i < dim_x; i++) {
                for (int j = 0; j < dim_y; j++) {
                    String str;
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);

                    if (Objects.equals(cells[i][j].getType(), "Value")) {
                        CellValue cellv = (CellValue) cells[i][j];
                        str = String.valueOf(cellv.getValue());
                        BoardSolver boardsolver = new BoardSolver(cells);
                        if (str.equals("0")) {
                            str = "";
                            g.setColor(Color.WHITE);
                        } else {
                            if (boardsolver.solve(cellv) == 1) {
                                cellv.setColor(1);
                                cells[i][j] = cellv;
                                g.setColor(Color.GREEN);
                            } else if (boardsolver.solve(cellv) == 0) {
                                cellv.setColor(0);
                                cells[i][j] = cellv;
                                g.setColor(Color.PINK);
                            } else if (boardsolver.solve(cellv) == 2) {
                                cellv.setColor(2);
                                cells[i][j] = cellv;
                                g.setColor(Color.WHITE);
                            }
                        }
                        g.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);
                        g.setFont(new Font("Arial", Font.BOLD, 16));
                        g.setColor(Color.BLACK);
                        g.drawString(str, move + i * size + size * 17 / 40, j * size + size * 5 / 8);

                    } else if (Objects.equals(cells[i][j].getType(), "Column")) {

                        CellColumn cellc = (CellColumn) cells[i][j];
                        str = "" + cellc.getSumInColumn();
                        g.setFont(font);
                        g.setColor(Color.BLACK);
                        g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                        g.drawString(str, move + i * size + size * 13 / 80, j * size + size * 33 / 40);

                    } else if (Objects.equals(cells[i][j].getType(), "Row")) {

                        CellRow cellc = (CellRow) cells[i][j];
                        str = "" + cellc.getSumInRow();
                        g.setFont(font);
                        g.setColor(Color.BLACK);
                        g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                        g.drawString(str, move + i * size + size * 11 / 20, j * size + size * 16 / 40);

                    } else if (Objects.equals(cells[i][j].getType(), "Splited")) {

                        CellColumnAndRow cellcr = (CellColumnAndRow) cells[i][j];
                        String str1 = "" + cellcr.getSumInColumn();
                        String str2 = "" + cellcr.getSumInRow();
                        g.setFont(font);
                        g.setColor(Color.BLACK);
                        g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                        g.drawString(str1, move + i * size + size * 13 / 80, j * size + size * 33 / 40);
                        g.drawString(str2, move + i * size + size * 11 / 20, j * size + size * 16 / 40);
                    }
                }
            }
            repaint();
            endCheck(g);
        }
    }

    /**
     * Prepares image for print after game end
     */
    public void prepareImage(){
        bImage = new BufferedImage(600, 600,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bImage.createGraphics();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 600, 600);

        for (int i = 0; i < dim_x; i++) {
            for (int j = 0; j < dim_y; j++) {
                String str;
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);

                if (Objects.equals(cells[i][j].getType(), "Value")) {
                    CellValue cellv = (CellValue) cells[i][j];
                    str = String.valueOf(cellv.getValue());
                    BoardSolver boardsolver = new BoardSolver(cells);
                    if (str.equals("0")) {
                        str = "";
                        g2d.setColor(Color.WHITE);
                    } else {
                        if (boardsolver.solve(cellv) == 1) {
                            cellv.setColor(1);
                            cells[i][j] = cellv;
                            g2d.setColor(Color.GREEN);
                        } else if (boardsolver.solve(cellv) == 0) {
                            cellv.setColor(1);
                            cells[i][j] = cellv;
                            g2d.setColor(Color.PINK);
                        } else if (boardsolver.solve(cellv) == 2) {
                            cellv.setColor(1);
                            cells[i][j] = cellv;
                            g2d.setColor(Color.WHITE);
                        }
                    }
                    g2d.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);
                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(str, move + i * size + size * 17 / 40, j * size + size * 5 / 8);
                } else if (Objects.equals(cells[i][j].getType(), "Column")) {
                    CellColumn cellc = (CellColumn) cells[i][j];
                    str = "" + cellc.getSumInColumn();
                    g2d.setFont(font);
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g2d.drawString(str, move + i * size + size * 13 / 80, j * size + size * 33 / 40);
                } else if (Objects.equals(cells[i][j].getType(), "Row")) {
                    CellRow cellc = (CellRow) cells[i][j];
                    str = "" + cellc.getSumInRow();
                    g2d.setFont(font);
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g2d.drawString(str, move + i * size + size * 11 / 20, j * size + size * 16 / 40);
                } else if (Objects.equals(cells[i][j].getType(), "Splited")) {
                    CellColumnAndRow cellcr = (CellColumnAndRow) cells[i][j];
                    String str1 = "" + cellcr.getSumInColumn();
                    String str2 = "" + cellcr.getSumInRow();
                    g2d.setFont(font);
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g2d.drawString(str1, move + i * size + size * 13 / 80, j * size + size * 33 / 40);
                    g2d.drawString(str2, move + i * size + size * 11 / 20, j * size + size * 16 / 40);
                }
            }
        }
        repaint();
        g2d.dispose();
    }

    /**
     * Checks if game just have been finished
     * @param g Graphics
     */
    public void endCheck(Graphics g) {
        for (Cell[] cell : cells) {
            for (int y = 0; y < cells[0].length; y++) {
                if (cell[y].getType().equals("Value")) {
                    CellValue cellv = (CellValue) cell[y];
                    if (cellv.getColor() != 1) {
                        return;
                    }
                }
            }
        }
        play = false; //possibility of changing values in cells is gone
        String message = "Congratulation! \nYou have solved board correctly!";
        JOptionPane.showMessageDialog(null, message, "Congratulation", JOptionPane.PLAIN_MESSAGE);
        prepareImage();
        //last draw of board because JOptionPanel spawn in frame which looks terrible
        repaint();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < dim_x; i++) {
            for (int j = 0; j < dim_y; j++) {
                String str;
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);

                if (Objects.equals(cells[i][j].getType(), "Value")) {
                    CellValue cellv = (CellValue) cells[i][j];
                    str = String.valueOf(cellv.getValue());
                    BoardSolver boardsolver = new BoardSolver(cells);
                    if (str.equals("0")) {
                        str = "";
                        g.setColor(Color.WHITE);
                    } else {
                        if (boardsolver.solve(cellv) == 1) {
                            cellv.setColor(1);
                            cells[i][j] = cellv;
                            g.setColor(Color.GREEN);
                        } else if (boardsolver.solve(cellv) == 0) {
                            cellv.setColor(0);
                            cells[i][j] = cellv;
                            g.setColor(Color.PINK);
                        } else if (boardsolver.solve(cellv) == 2) {
                            cellv.setColor(2);
                            cells[i][j] = cellv;
                            g.setColor(Color.WHITE);
                        }
                    }
                    g.fillRect(move + space + i * size, space + j * size, size - 2 * space, size - 2 * space);
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.setColor(Color.BLACK);
                    g.drawString(str, move + i * size + size * 17 / 40, j * size + size * 5 / 8);
                } else if (Objects.equals(cells[i][j].getType(), "Column")) {
                    CellColumn cellc = (CellColumn) cells[i][j];
                    str = "" + cellc.getSumInColumn();
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g.drawString(str, move + i * size + size * 13 / 80, j * size + size * 33 / 40);
                } else if (Objects.equals(cells[i][j].getType(), "Row")) {
                    CellRow cellc = (CellRow) cells[i][j];
                    str = "" + cellc.getSumInRow();
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g.drawString(str, move + i * size + size * 11 / 20, j * size + size * 16 / 40);
                } else if (Objects.equals(cells[i][j].getType(), "Splited")) {
                    CellColumnAndRow cellcr = (CellColumnAndRow) cells[i][j];
                    String str1 = "" + cellcr.getSumInColumn();
                    String str2 = "" + cellcr.getSumInRow();
                    g.setFont(font);
                    g.setColor(Color.BLACK);
                    g.drawLine(move + space + i * size, space + j * size, move + space + size * i + size, space + j * size + size);
                    g.drawString(str1, move + i * size + size * 13 / 80, j * size + size * 33 / 40);
                    g.drawString(str2, move + i * size + size * 11 / 20, j * size + size * 16 / 40);
                }
            }
        }
        repaint();

    }

    /**
     * Position of mouse in x coordinates
     */
    public static int mx;
    /**
     * Position of mouse in y coordinates
     */
    public static int my;
    public static class Click implements MouseListener{
        final NumericFrame numericFrame = new NumericFrame();
        @Override
        public void mouseClicked(MouseEvent e) {
            mx=e.getX();
            my=e.getY();
            if(inBoxX(mx,my)!=-1&&inBoxY(mx,my)!=-1){
                if(Objects.equals(cells[inBoxX(mx, my)][inBoxY(mx, my)].getType(), "Value")){
                    if(play) { //checks if game run
                        numericFrame.setVisible(true);
                        numericFrame.setLocation(mx - 40, my);
                    }
                }
                else{
                    numericFrame.setVisible(false);
                }
            }
            else{
                numericFrame.setVisible(false);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {

        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Class of small frame spawning on click on cell value
     */
    public static class NumericFrame extends JFrame{
        public int output;
        NumericFrame(){
            JButton btn1 = new JButton();
            JButton btn2 = new JButton();
            JButton btn3 = new JButton();
            JButton btn4 = new JButton();
            JButton btn5 = new JButton();
            JButton btn6 = new JButton();
            JButton btn7 = new JButton();
            JButton btn8 = new JButton();
            JButton btn9 = new JButton();
            JButton btnX = new JButton();
            btn1.setText("1");
            btn1.setFont(new Font("Arial", Font.BOLD,15));
            btn1.setMargin(new Insets(0,0,0,0));
            btn2.setText("2");
            btn2.setFont(new Font("Arial", Font.BOLD,15));
            btn2.setMargin(new Insets(0,0,0,0));
            btn3.setText("3");
            btn3.setFont(new Font("Arial", Font.BOLD,15));
            btn3.setMargin(new Insets(0,0,0,0));
            btn4.setText("4");
            btn4.setFont(new Font("Arial", Font.BOLD,15));
            btn4.setMargin(new Insets(0,0,0,0));
            btn5.setText("5");
            btn5.setFont(new Font("Arial", Font.BOLD,15));
            btn5.setMargin(new Insets(0,0,0,0));
            btn6.setText("6");
            btn6.setFont(new Font("Arial", Font.BOLD,15));
            btn6.setMargin(new Insets(0,0,0,0));
            btn7.setText("7");
            btn7.setFont(new Font("Arial", Font.BOLD,15));
            btn7.setMargin(new Insets(0,0,0,0));
            btn8.setText("8");
            btn8.setFont(new Font("Arial", Font.BOLD,15));
            btn8.setMargin(new Insets(0,0,0,0));
            btn9.setText("9");
            btn9.setFont(new Font("Arial", Font.BOLD,15));
            btn9.setMargin(new Insets(0,0,0,0));
            btnX.setText("X");
            btn4.setFont(new Font("Arial", Font.BOLD,15));
            btnX.setMargin(new Insets(0,0,0,0));
            btn1.addActionListener(e -> {
                output = 1;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn2.addActionListener(e -> {
                output = 2;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn3.addActionListener(e -> {
                output = 3;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn4.addActionListener(e -> {
                output = 4;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn5.addActionListener(e -> {
                output = 5;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn6.addActionListener(e -> {
                output = 6;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn7.addActionListener(e -> {
                output = 7;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn8.addActionListener(e -> {
                output = 8;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btn9.addActionListener(e -> {
                output = 9;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            btnX.addActionListener(e -> {
                output = 0;
                CellValue cell;
                cell = (CellValue) cells[inBoxX(mx,my)][inBoxY(mx,my)];
                cell.setValue(output);
                cells[inBoxX(mx,my)][inBoxY(mx,my)] = cell;
                setVisible(false);
            });
            JPanel panel = new JPanel();

            panel.setLayout(new GridLayout(4,3));
            panel.add(btn1);
            panel.add(btn2);
            panel.add(btn3);
            panel.add(btn4);
            panel.add(btn5);
            panel.add(btn6);
            panel.add(btn7);
            panel.add(btn8);
            panel.add(btn9);
            panel.add(new JPanel());
            panel.add(btnX);
            panel.add(new JPanel());
            add(panel);
            this.setUndecorated(true);
            this.setVisible(false);
            this.setSize(80,80);
        }
    }

    /**
     * Checks x dimension of cell depending on where mouse was click
     * @param mx mx
     * @param my my
     * @return number of cell in x dimension
     */
    public static int inBoxX(int mx,int my){
        for (int i =0; i< dim_x;i++){
            for (int j =0; j< dim_y;j++){
                if(mx >= move+space+i*size&& mx< move+i*size+size-space && my >= space+j*size&&my< j*size-space+size){
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * Checks y dimension of cell depending on where mouse was on click
     * @param mx mx
     * @param my my
     * @return number of cell in y dimension
     */
    public static int inBoxY(int mx,int my){
        for (int i =0; i< dim_x;i++){
            for (int j =0; j< dim_y;j++){
                if(mx >= move+space+i*size&& mx< move+i*size+size-space&& my >= space+j*size&&my< j*size-space+size){
                    return j;
                }
            }
        }
        return -1;
    }

}
