package amoba;

public class Tabla {
    private final int meret = 15;
    private Jatekos[][] grid;

    public Tabla() {
        grid = new Jatekos[meret][meret];
    }

    public int getMeret() {
        return meret;
    }

    public Jatekos[][] getGrid() {
        return grid;
    }

    //Cella lekérdezése
    public Jatekos getCella(int sor, int oszlop) {
        return grid[sor][oszlop];
    }

    //Ellenőrzi, hogy egy cella üres-e
    public boolean isEmpty(int sor, int oszlop) {
        return grid[sor][oszlop] == null;
    }

    //Bábu helyezése a táblára
    public void helyez(int sor, int oszlop, Jatekos jatekos) {
        grid[sor][oszlop] = jatekos;
    }
}
