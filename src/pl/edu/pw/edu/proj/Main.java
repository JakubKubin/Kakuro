package pl.edu.pw.edu.proj;

import pl.edu.pw.edu.proj.board.Board;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    public final Font mainfont = new Font("Arial",Font.BOLD,24);
    private JLabel lbWelcome;
    private JPanel mainPanel,btnPanel;
    public static File chosenFile;
    public static String difficulty;

    public void chooseDiff(){
        mainPanel.removeAll();
        btnPanel.removeAll();

        JButton btnHard = new JButton();
        btnHard.setFont(mainfont);
        btnHard.setText("Hard");

        JButton btnMedium = new JButton();
        btnMedium.setFont(mainfont);
        btnMedium.setText("Medium");

        JButton btnEasy = new JButton();
        btnEasy.setFont(mainfont);
        btnEasy.setText("Easy");

        btnPanel.setLayout(new GridLayout(3,3));
        btnPanel.setBackground(new Color(128,128,255));
        btnPanel.add(new JPanel());
        btnPanel.add(btnHard);
        btnPanel.add(new JPanel());
        btnPanel.add(new JPanel());
        btnPanel.add(btnMedium);
        btnPanel.add(new JPanel());
        btnPanel.add(new JPanel());
        btnPanel.add(btnEasy);
        btnPanel.add(new JPanel());

        btnHard.addActionListener(e -> {
            difficulty="hard";
            try {
                new Board();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        btnMedium.addActionListener(e -> {
            difficulty="medium";
            try {
                new Board();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        btnEasy.addActionListener(e -> {
            difficulty="easy";
            try {
                new Board();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        mainPanel.add(lbWelcome,BorderLayout.NORTH);
        mainPanel.add(btnPanel,BorderLayout.CENTER);

        setSize(601,600);//refreshing screen
        setSize(600,600);

        add(mainPanel);
    }
    public File load(){
        JFileChooser chooser;
        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "txt");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Choose File To Load");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();
        else {
            return null;
        }
    }
    public void initialize(){
        lbWelcome = new JLabel("",SwingConstants.CENTER);
        lbWelcome.setFont(mainfont);
        lbWelcome.setText("Kakuro");

        JButton btnStart = new JButton();
        btnStart.setFont(mainfont);
        btnStart.setText("New Game!");

        JButton btnLoad = new JButton();
        btnLoad.setFont(mainfont);
        btnLoad.setText("Load Map");

        JButton btnClose = new JButton();
        btnClose.setFont(mainfont);
        btnClose.setText("Exit");

        btnPanel = new JPanel();

        btnPanel.setLayout(new GridLayout(3,3));
        btnPanel.setBackground(new Color(128,128,255));
        btnPanel.add(new JPanel());
        btnPanel.add(btnStart);
        btnPanel.add(new JPanel());
        btnPanel.add(new JPanel());
        btnPanel.add(btnLoad);
        btnPanel.add(new JPanel());
        btnPanel.add(new JPanel());
        btnPanel.add(btnClose);
        btnPanel.add(new JPanel());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128,128,128));
        mainPanel.add(lbWelcome,BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.CENTER);

        setTitle("Kakuro");
        setSize(600,600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(mainPanel);

        btnStart.addActionListener(e -> chooseDiff());
        btnClose.addActionListener(e -> System.exit(0));
        btnLoad.addActionListener(e ->{
            File file = load();
            if(file == null){
                String message = "You did not choose any file!";
                difficulty = "";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                chosenFile = file;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    if(br.readLine()==null){
                        String message = "File is empty!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        difficulty = "load";
                        mainPanel.removeAll();
                        btnPanel.removeAll();
                        try {
                            new Board();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    }
                } catch (IOException ex) {
                    String message = "File is empty!";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.initialize();
    }
}
