package pl.edu.pw.edu.proj.board;

import pl.edu.pw.edu.proj.cell.Cell;
import pl.edu.pw.edu.proj.panel.GamePanel;
import pl.edu.pw.edu.proj.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static pl.edu.pw.edu.proj.panel.GamePanel.bImage;

public class Board extends Main {
    /**
     * Panel of all components
     */
    private static JPanel mainPanel;
    /**
     * Panel that contains game/board graphics
     */
    private static JPanel gamePanel;

    public Board() throws IOException {
        JButton btnSave = new JButton();
        btnSave.setFont(mainfont);
        btnSave.setText("Save");
        btnSave.addActionListener(e -> saveToText());

        JButton btnPrint = new JButton();
        btnPrint.setFont(mainfont);
        btnPrint.setText("Print");
        btnPrint.addActionListener(e -> saveImage());

        JButton btnHint = new JButton();
        btnHint.setFont(mainfont);
        btnHint.setText("Hint");
        btnHint.addActionListener(e->showHint());

        JButton btnMenu = new JButton();
        btnMenu.setFont(mainfont);
        btnMenu.setText("Menu");
        btnMenu.addActionListener(e->comeBackToMainMenu());

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1,4));
        btnPanel.setBackground(new Color(128,128,255));
        btnPanel.add(btnPrint);
        btnPanel.add(btnSave);
        btnPanel.add(btnHint);
        btnPanel.add(btnMenu);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128,128,128));
        mainPanel.add(btnPanel,BorderLayout.NORTH);
        mainPanel.add(gamePanel,BorderLayout.CENTER);
        setSize(600,600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        switch (difficulty) {
            case "hard" -> {
                setTitle("Kakuro - Hard");
                generateHardMap();
                add(mainPanel);
            }
            case "medium" -> {
                setTitle("Kakuro - Medium");
                generateMediumMap();
                add(mainPanel);
            }
            case "easy" -> {
                setTitle("Kakuro - Easy");
                generateEasyMap();
                add(mainPanel);
            }
            case "load" ->{
                setTitle("Kakuro - Load");
                generateLoadMap();
                add(mainPanel);
            }
            default -> setTitle("Something is wrong");
        }
    }
    public static void generateHardMap() throws IOException { //13x13
        BoardGenerator boardGen= new BoardGenerator("hard",13,13);
        GamePanel gPanel = new GamePanel(boardGen.getCellMap(),13,13,"hard");
        gamePanel = gPanel;
        mainPanel.add(gPanel);
    }
    public static void generateMediumMap() throws IOException { //8x9
        BoardGenerator boardGen= new BoardGenerator("medium",8,9);
        GamePanel gPanel = new GamePanel(boardGen.getCellMap(),8,9,"medium");
        gamePanel = gPanel;
        mainPanel.add(gPanel);
    }
    public static void generateEasyMap() throws IOException { //5x5
        BoardGenerator boardGen= new BoardGenerator("easy",5,5);
        GamePanel gPanel = new GamePanel(boardGen.getCellMap(),5,5,"easy");
        gamePanel = gPanel;
        mainPanel.add(gPanel);
    }
    public static void generateLoadMap() throws IOException {
       BoardGenerator boardGen= new BoardGenerator(chosenFile);
       GamePanel gPanel = new GamePanel(boardGen.getCellMap(), boardGen.dim_x,boardGen.dim_y,"load");
       gamePanel = gPanel;
       mainPanel.add(gPanel);
    }

    /**
     * Save image to chosen path
     */
    private void saveImage() {
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG Images", "jpg");
        filechooser.setFileFilter(filter);
        int result = filechooser.showSaveDialog(this);
        if(bImage==null){
            bImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
            gamePanel.printAll(bImage.getGraphics());
        }
        if (result == JFileChooser.APPROVE_OPTION) {
            File saveFile = filechooser.getSelectedFile();
            try {
                ImageIO.write(bImage, "jpg", new File((saveFile + ".jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prepares string from cell map get from gamePanel
     */
    private void saveToText(){
        GamePanel gPanel = (GamePanel) gamePanel;
        Cell[][] cells = gPanel.getCellMap();
        BoardSaver bs = new BoardSaver(cells);
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "txt");
        filechooser.setFileFilter(filter);
        int result = filechooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File saveFile = filechooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(saveFile + ".txt");
                fw.write(bs.mapstring);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return to main menu
     */
    public void comeBackToMainMenu(){
        Main main = new Main();
        main.initialize();
        dispose();
    }

    /**
     * Show a hint
     */
    public static void showHint(){
        Runnable r = () -> {
            String html = """
                    <html><h1>Solve Kakuro puzzles</h1><p>In this guide we offer a range of practical tips on how to solve Kakuro puzzles, with difficulties ranging from beginner to expert level.</p>
                    <html><p>To summarize the rules: Kakuro is a puzzle game on a crosswords-like board where digits are used in order to make them sum up to values specified in the 'definition' squares of the board.</p>
                    <html><p>In addition, inside each sum group, each digit can appear once at most. The traditional way to solve a Kakuro puzzle is incremental: by using the existing information on the board,</p>
                    <html><p>you can find with certainty the value of a specific cell which can take only one possible value. Then that value is filled and the process is repeated until all the board cells have been discovered.</p>
                    <html><p>In some situations, there's no specific board cell having just one possibility. In those cases each of the possibilities needs to be explored on its own and eliminated</p>
                    <html><p>through contradictions until only one course of action remains. We're presenting below several methods to make progress in solving the actual puzzle.</p>
                    <html><h2>Unique sums for specific definitions:</h2>
                    <html><p>There are some definitions that can be solved only in a specific way:</p>
                    <html><li>The sum 3 (across two cells) will always be 1 + 2.</li>
                    <html><li>The sum 4 in two cells will always be 1 + 3.</li>
                    <html><li>The sum 17 in two cells will always be 8 + 9.</li>
                    <html><li>The sum 6 in three cells will always be 1 + 2 + 3.</li>
                    <html><li>The sum 24 in three cells will always be 7 + 8 + 9.</li>
                    <html><p>and so on... Usually you can hover on a Kakuro grid over the definition number and a tooltip will appear containing </p>
                    <html><p>all the possibilities of writing that sum with unique digits in the number of available cells.</p>
                    <html><p>The sums that can be written in an unique way are usually low-sums or high-sums which force low/high digits in the answer in order to attain them.</p>
                    <html><p>Having an unique way of writing the sum helps, but keep in mind that all permutations are valid and you still need to figure out which actual permutation to use on the board.</p>
                    <html><h2>Row / column restrictions</h2>
                    <html><p>For instance, there's only one way of writing the sum: 4 = 1 + 3. However we still need to figure out which permutation (1 + 3 or 3 + 1) to use.</p>
                    <html><p>Turns out that the 26 vertical definition helps us: a sum across 4 cells that would contain the digit 1 would be at most 1 + 9 + 8 + 7 = 25. </p>
                    <html><p>Since our sum is 26, it turns out that the digit 1 cannot be a part of the sum. Therefore, the only remaining order for the yellow squares is 3 + 1.</p>
                    <html><h2 style="margin-top:5px">Definitions intersections</h2>
                    <html><p>On the board above, the horizontal yellow squares can be written as 6 = 1 + 5 or 6 = 2 + 4. The vertical yellow sum can only be written as 29 = 5 + 7 + 8 + 9.</p>
                    
                    <html><p>The yellow square found at the intersection of those two sum definitions must contain the same digit, so there must be a common digit present in the horizontal and vertical definitions</p>
                    <html><p>in order to be shared between them. By looking at the possibilities above we can easily figure out that 5 is the only digit which respects this criterion.</p>\040
                    
                    <html><p>This technique works especially well when intersecting a low-sum with a high-sum definition. Low-sum and high-sum definitions are the ones which have a relative low or high sum definition number when </p>
                    <html><p>compared to the number of cells available. Because 6 is relatively low it will force low digits in the sum representation, and 29 will force high digits (in order to attain these sums using the given number of cells).</p>
                    <html><p>Therefore the intersection of low and high digits is likely to contain only one candidate for the actual cell value.</p>
                    """;
            JOptionPane.showMessageDialog(null, String.format(html));
        };
        SwingUtilities.invokeLater(r);
    }
}
