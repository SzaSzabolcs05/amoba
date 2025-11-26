package amoba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private static final int CELL_SIZE = 40;
    private static final int GRID_SIZE = 15;

    private Jatek jatek;

    //Setter új játék kezdéséhez
    public void setJatek(Jatek jatek) {
        this.jatek = jatek;
        repaint();
    }

    public BoardPanel(Jatek jatek) {
        this.jatek = jatek;
        setPreferredSize(new Dimension(CELL_SIZE * GRID_SIZE, CELL_SIZE * GRID_SIZE));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int totalSize = CELL_SIZE * GRID_SIZE;
                int offsetX = (getWidth() - totalSize) / 2;
                int offsetY = (getHeight() - totalSize) / 2;

                int col = (e.getX() - offsetX) / CELL_SIZE;
                int row = (e.getY() - offsetY) / CELL_SIZE;

                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    int result = BoardPanel.this.jatek.lep(row, col);
                    if (result == 1) {
                        repaint();
                    }else if(result == 0){
                        JOptionPane.showMessageDialog(BoardPanel.this, "Érvénytelen lépés!");
                    }else if(result == 2){
                        JOptionPane.showMessageDialog(BoardPanel.this, "Érvénytelen lépés! A fehér játékos nyert!");
                        repaint();
                    }else if(result == 3){
                        if(BoardPanel.this.jatek.getAktualisJatekos() == Jatekos.FEKETE){
                            JOptionPane.showMessageDialog(BoardPanel.this, "Öt bábúnál több van egy sorban! A fehér játékos nyert!");
                        }else{
                            JOptionPane.showMessageDialog(BoardPanel.this, "Öt bábúnál több van egy sorban! A fekete játékos nyert!");
                        }
                        repaint();
                    }else if(result == 4){
                        if(BoardPanel.this.jatek.getAktualisJatekos() == Jatekos.FEKETE){
                            JOptionPane.showMessageDialog(BoardPanel.this, "Öt egy sorban! A fekete játékos nyert!");
                        }else{
                            JOptionPane.showMessageDialog(BoardPanel.this, "Öt egy sorban! A fehér játékos nyert!");
                        }
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(222, 184, 135)); // Világosbarna háttér
        g.fillRect(0, 0, getWidth(), getHeight());

        // Középre igazítás
        int totalSize = CELL_SIZE * GRID_SIZE;
        int offsetX = (getWidth() - totalSize) / 2;
        int offsetY = (getHeight() - totalSize) / 2;

        // Rács
        g.setColor(Color.BLACK);
        for (int i = 0; i <= GRID_SIZE; i++) {
            // Vízszintes vonalak
            g.drawLine(offsetX, offsetY + i * CELL_SIZE,
                    offsetX + totalSize, offsetY + i * CELL_SIZE);
            // Függőleges vonalak
            g.drawLine(offsetX + i * CELL_SIZE, offsetY,
                    offsetX + i * CELL_SIZE, offsetY + totalSize);
        }

        // Bábuk kirajzolása
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Jatekos cella = jatek.getTabla().getCella(row, col);
                if (cella != null) {
                    if (cella == Jatekos.FEKETE) g.setColor(Color.BLACK);
                    else g.setColor(Color.WHITE);

                    int padding = 5;
                    int x = offsetX + col * CELL_SIZE + padding;
                    int y = offsetY + row * CELL_SIZE + padding;
                    g.fillOval(x, y, CELL_SIZE - 2 * padding, CELL_SIZE - 2 * padding);

                    if (cella == Jatekos.FEHER) {
                        g.setColor(Color.BLACK);
                        g.drawOval(x, y, CELL_SIZE - 2 * padding, CELL_SIZE - 2 * padding);
                    }
                }
            }
        }
    }
}
