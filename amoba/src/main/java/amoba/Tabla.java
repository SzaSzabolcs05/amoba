package amoba;

public class Tabla {
    private final int meret = 15;
    private Jatekos[][] grid;

    public Tabla() {
        grid = new Jatekos[meret][meret];
    }

    public int getSize() {
        return meret;
    }

    public Jatekos getCell(int sor, int oszlop) {
        return grid[sor][oszlop];
    }

    public boolean isEmpty(int sor, int oszlop) {
        return grid[sor][oszlop] == null;
    }

    public void placeStone(int sor, int oszlop, Jatekos jatekos) {
        grid[sor][oszlop] = jatekos;
    }
}
