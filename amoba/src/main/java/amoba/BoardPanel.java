package amoba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private static final int CELL_SIZE = 40; // Cella mérete pixelben
    private static final int GRID_SIZE = 15; // Rács mérete 15x15

    private Jatek jatek;
    private GameFrame gameFrame;

    //Setter új játék kezdéséhez
    public void setJatek(Jatek jatek) {
        this.jatek = jatek; // Játék objektum beállítása új játékhoz
        repaint();
    }

    // Konstruktor
    public BoardPanel(Jatek jatek, GameFrame gf) {
        this.jatek = jatek; // Játék objektum beállítása új játékhoz
        this.gameFrame = gf; // GameFrame referencia beállítása
        setPreferredSize(new Dimension(CELL_SIZE * GRID_SIZE, CELL_SIZE * GRID_SIZE));  // Panel mérete

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int totalSize = CELL_SIZE * GRID_SIZE;
                int offsetX = (getWidth() - totalSize) / 2; // Középre igazítás X tengelyen
                int offsetY = (getHeight() - totalSize) / 2;    // Középre igazítás Y tengelyen

                int col = (e.getX() - offsetX) / CELL_SIZE; // Oszlop számítása
                int row = (e.getY() - offsetY) / CELL_SIZE; // Sor számítása

                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    int result = BoardPanel.this.jatek.lep(row, col);   // Lépés végrehajtása
                    if (result == 1) {
                        repaint();  // Sikeres lépés, újrarajzolás
                    }else if(result == 0){
                        // Érvénytelen lépés üzenet, de nem történik semmi, újra léphet
                        JOptionPane.showMessageDialog(BoardPanel.this, "Érvénytelen lépés!");
                    }else if(result == 2){
                        // Vége a játéknak, üzenetet küldünk a GameFrame-nek
                        gameFrame.vegeKepernyo("Illegális lépés! A fehér játékos nyert!");
                        repaint();
                    }else if(result == 3){
                        // Megnézzük, ki volt az aktuális játékos, és annak megfelelően küldünk üzenetet
                        // Ötnél több bábú egy sorban
                        if(BoardPanel.this.jatek.getAktualisJatekos() == Jatekos.FEKETE){
                            gameFrame.vegeKepernyo("Öt bábúnál több van egy sorban! A fehér játékos nyert!");
                        }else{
                            gameFrame.vegeKepernyo("Öt bábúnál több van egy sorban! A fekete játékos nyert!");
                        }
                        repaint();
                    }else if(result == 4){
                        // Megnézzük, ki volt az aktuális játékos, és annak megfelelően küldünk üzenetet
                        // Öt egy sorban
                        if(BoardPanel.this.jatek.getAktualisJatekos() == Jatekos.FEKETE){
                            gameFrame.vegeKepernyo("Öt egy sorban! A fekete játékos nyert!");
                        }else{
                            gameFrame.vegeKepernyo("Öt egy sorban! A fehér játékos nyert!");
                        }
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    // Játék tábla és bábuk kirajzolása
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Háttér
        g.setColor(new Color(222, 184, 135)); // Világosbarna háttér
        g.fillRect(0, 0, getWidth(), getHeight());

        // Középre igazítás
        int totalSize = CELL_SIZE * GRID_SIZE;
        int offsetX = (getWidth() - totalSize) / 2;
        int offsetY = (getHeight() - totalSize) / 2;

        // Rács
        g.setColor(Color.BLACK);
        for (int i = 0; i <= GRID_SIZE; i++) {
            // Vízszintes vonalak rajzolása
            g.drawLine(offsetX, offsetY + i * CELL_SIZE,
                    offsetX + totalSize, offsetY + i * CELL_SIZE);
            // Függőleges vonalak rajzolása
            g.drawLine(offsetX + i * CELL_SIZE, offsetY,
                    offsetX + i * CELL_SIZE, offsetY + totalSize);
        }

        // Bábuk kirajzolása
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Jatekos cella = jatek.getTabla().getCella(row, col);
                // Ha van bábu a cellában, kirajzoljuk
                if (cella != null) {
                    if (cella == Jatekos.FEKETE) g.setColor(Color.BLACK);
                    else g.setColor(Color.WHITE);

                    // 5 px hely a cella szélétől
                    int hely = 5;
                    // Melyik cellába rajzolunk és ha van offset hozzáadjuk + helyet hagyunk a cella szélén
                    int x = offsetX + col * CELL_SIZE + hely;
                    int y = offsetY + row * CELL_SIZE + hely;
                    // Ovális rajzolása
                    g.fillOval(x, y, CELL_SIZE - 2 * hely, CELL_SIZE - 2 * hely);

                    if (cella == Jatekos.FEHER) {
                        g.setColor(Color.BLACK);
                        g.drawOval(x, y, CELL_SIZE - 2 * hely, CELL_SIZE - 2 * hely);
                    }
                }
            }
        }
    }
}
