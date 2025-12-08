package amoba;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private GameFrame gameFrame;

    public MainMenu(GameFrame gf) {
        this.gameFrame = gf;
        
        // A főmenü megjelenítésekor középen legyenek a gombok
        // GridBagLayout használata, hogy a gombok középre kerüljenek és egymás alatt helyezkedjenek el
        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Térköz a gombok között
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Gombok kitöltik a rendelkezésre álló helyet vízszintesen
        gbc.gridx = 0;
        
        // Gombok létrehozása
        
        // 1. Indítás gomb
        JButton startBtn = new JButton("Indítás");
        startBtn.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 0;
        add(startBtn, gbc);
        
        // 2. Betöltés gomb
        JButton loadBtn = new JButton("Betöltés");
        loadBtn.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 1;
        add(loadBtn, gbc);

        // 3. Kilépés gomb
        JButton exitBtn = new JButton("Kilépés");
        exitBtn.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 2;
        add(exitBtn, gbc);
        
        // Indítás: Megjeleníti a játékpanelt és elindít egy új játékot
        startBtn.addActionListener(e -> {
            gameFrame.showGame();
        });
        
        // Betöltés: Elindítja a betöltési párbeszédablakot
        loadBtn.addActionListener(e -> {
            gameFrame.loadGame(); // A GameFrame kezeli a fájlválasztót és a betöltést
        });
        
        // Kilépés: Bezárja az alkalmazást
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
    }
}
