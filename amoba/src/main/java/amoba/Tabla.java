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

    //Tábla kiíratása konzolra
    public void printTabla() {
        System.out.print("  ");
        for (int i = 0; i < meret; i++) {
            System.out.printf("%2d", i);
        }
        System.out.println();
        for (int i = 0; i < meret; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < meret; j++) {
                char jel;
                if (grid[i][j] == Jatekos.FEKETE) {
                    jel = '⚫';
                } else if (grid[i][j] == Jatekos.FEHER) {
                    jel = '⚪';
                } else {
                    jel = '•';
                }
                System.out.print(jel + " ");
            }
            System.out.println();
        }
    }
}
