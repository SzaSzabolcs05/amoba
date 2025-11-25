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
        boardPanel = new BoardPanel(jatek);
        add(boardPanel, BorderLayout.CENTER);

        // Gombok eseményei
        startBtn.addActionListener(e -> {
            jatek = new Jatek(); // új játék
            boardPanel.setJatek(jatek);
        });

        passBtn.addActionListener(e -> {
            boardPanel.repaint();

            if (jatek.passz()) {
                dontetlenKepernyo();
            }
        });

        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Save még nincs implementálva");
        });

        loadBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Load még nincs implementálva");
        });

        setVisible(true);
    }

    // Döntetlen esetén felugró ablak
    private void dontetlenKepernyo() {
        Object[] options = {"Új játék", "Kilépés"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "A játék döntetlen!",
                "Döntetlen",
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
