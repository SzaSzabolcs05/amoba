package amoba;
import java.io.Serializable;
import java.util.ArrayList;

public class Tabla implements Serializable{
    private final int meret = 15;
    private ArrayList<ArrayList<Jatekos>> grid;

    public Tabla() {
        grid = new ArrayList<>();

        // 15x15-ös rács feltöltése null értékekkel
        for (int sor = 0; sor < meret; sor++) {
            ArrayList<Jatekos> sorLista = new ArrayList<>();

            for (int oszlop = 0; oszlop < meret; oszlop++) {
                sorLista.add(null);   // üres cella
            }

            grid.add(sorLista);
        }
    }

    public int getMeret() {
        return meret;
    }

    public ArrayList<ArrayList<Jatekos>> getGrid() {
        return grid;
    }

    // Cella lekérdezése
    public Jatekos getCella(int sor, int oszlop) {
        return grid.get(sor).get(oszlop);
    }

    // Ellenőrzi, hogy a megadott cella üres-e
    public boolean isEmpty(int sor, int oszlop) {
        return grid.get(sor).get(oszlop) == null;
    }

    // Bábú helyezése a táblára
    public void helyez(int sor, int oszlop, Jatekos jatekos) {
        grid.get(sor).set(oszlop, jatekos);
    }
}
