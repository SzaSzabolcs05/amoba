package amoba;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private static final int CELL_SIZE = 40;
    private static final int GRID_SIZE = 15;

    private Jatek jatek;
    private BoardPanel boardPanel;

    public GameFrame() {
        jatek = new Jatek();

        setTitle("Amőba 15x15");
        setSize(CELL_SIZE * GRID_SIZE + 50, CELL_SIZE * GRID_SIZE + 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menü gombok
        JPanel topPanel = new JPanel();
        JButton startBtn = new JButton("Start");
        JButton passBtn = new JButton("Passz");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        topPanel.add(startBtn);
        topPanel.add(passBtn);
        topPanel.add(saveBtn);
        topPanel.add(loadBtn);

        add(topPanel, BorderLayout.NORTH);

        // Játék tábla
        boardPanel = new BoardPanel(jatek, this);
        add(boardPanel, BorderLayout.CENTER);

        // Gombok eseményei
        startBtn.addActionListener(e -> {
            jatek = new Jatek(); // új játék
            boardPanel.setJatek(jatek);
        });

        passBtn.addActionListener(e -> {
            boardPanel.repaint();

            if (jatek.passz()) {
                vegeKepernyo("A játék döntetlen!");
            }
        });

        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Játék mentése");
            fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\szabc\\Iskola\\Szasza-BME\\3.felev\\Prog3\\NagyHF\\amoba\\target\\Save"));
            int valasz = fileChooser.showSaveDialog(this);

            if (valasz == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(fileChooser.getSelectedFile());
                    java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);

                    oos.writeObject(jatek);

                    oos.close();
                    fos.close();

                    JOptionPane.showMessageDialog(this, "Sikeres mentés!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hiba mentés közben: " + ex.getMessage());
                }
            }
        });

        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\szabc\\Iskola\\Szasza-BME\\3.felev\\Prog3\\NagyHF\\amoba\\target\\Save"));
            fileChooser.setDialogTitle("Játék betöltése");
            int valasz = fileChooser.showOpenDialog(this);
            
            if (valasz == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.FileInputStream fis = new java.io.FileInputStream(fileChooser.getSelectedFile());
                    java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);

                    jatek = (Jatek) ois.readObject();
                    boardPanel.setJatek(jatek);

                    ois.close();
                    fis.close();

                    JOptionPane.showMessageDialog(this, "Sikeres betöltés!");
                    boardPanel.repaint();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hiba betöltés közben: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    // Döntetlen esetén felugró ablak
    public void vegeKepernyo(String message) {
        Object[] options = {"Új játék", "Kilépés"};
        int choice = JOptionPane.showOptionDialog(
                this,
                message,
                "Játék vége",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            // ugyanaz, mint a Start gomb
            jatek = new Jatek();
            boardPanel.setJatek(jatek);
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}
