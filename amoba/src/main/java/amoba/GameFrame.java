package amoba;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private static final int CELL_SIZE = 40;
    private static final int GRID_SIZE = 15;
    private static final int MENU_WIDTH = 200; 
    private static final int MENU_HEIGHT = 200;

    private Jatek jatek;
    private BoardPanel boardPanel;
    private MainMenu mainMenu; 
    
    private JPanel topPanel;
    private JButton newGameBtn;
    private JButton passBtn;
    private JButton saveBtn;
    private JButton loadBtn;

    public GameFrame() {
        setTitle("Amőba 15x15");
        setSize(MENU_WIDTH, MENU_HEIGHT); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        jatek = new Jatek();
        boardPanel = new BoardPanel(jatek, this);
        mainMenu = new MainMenu(this); 
        setupGameMenuButtons();

        // A játék elindítása főmenüvel kezdődik
        showMainMenu();
        setVisible(true);
    }
    
    // Játék menü gombjainak beállítása (Új Játék, Passz, Mentés, Betöltés)
    private void setupGameMenuButtons() {
        topPanel = new JPanel();

        newGameBtn = new JButton("Új Játék");
        passBtn = new JButton("Passz");
        saveBtn = new JButton("Mentés");
        loadBtn = new JButton("Betöltés");

        topPanel.add(newGameBtn);
        topPanel.add(passBtn);
        topPanel.add(saveBtn);
        topPanel.add(loadBtn);
        
        // Új Játék gomb eseménye
        newGameBtn.addActionListener(e -> {
            startNewGame();
        });
        
        passBtn.addActionListener(e -> {
            boardPanel.repaint();

            if (jatek.passz()) {
                vegeKepernyo("A játék döntetlen!");
            }
        });

        saveBtn.addActionListener(e -> saveGame());
        loadBtn.addActionListener(e -> loadGame());
    }
    
    // Segéd függvény az új játék indításához
    public void startNewGame() {
        jatek = new Jatek();
        boardPanel.setJatek(jatek);
    }

    // A Játékpanel + játékon belüli menü megjelenítése
    public void showGame() {
        getContentPane().removeAll(); 
        setSize(CELL_SIZE * GRID_SIZE + 50, CELL_SIZE * GRID_SIZE + 100);
        setLocationRelativeTo(null);
        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    // Főmenü megjelenítése
    public void showMainMenu() {
        getContentPane().removeAll();
        setSize(MENU_WIDTH, MENU_HEIGHT);
        setLocationRelativeTo(null);
        add(mainMenu, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    
    // Segéd függvény a játék mentéséhez
    private void saveGame() {
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
    }
    
    // Segéd függvény a játék betöltéséhez
    public void loadGame() {
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

                showGame(); 
                JOptionPane.showMessageDialog(this, "Sikeres betöltés!");
                boardPanel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hiba betöltés közben: " + ex.getMessage());
                showMainMenu(); 
            }
        }
    }

    // Miután vége a játéknak, megjelenít ezt az ablakot
    public void vegeKepernyo(String message) {
        Object[] options = {"Új játék", "Főmenü", "Kilépés"}; 
        int choice = JOptionPane.showOptionDialog(
                this,
                message,
                "Játék vége",
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) { // Új játék
            startNewGame();
        } else if (choice == JOptionPane.NO_OPTION) { // Főmenü
            showMainMenu();
        } else if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) { // Kilépés
            System.exit(0);
        }
    }
}
