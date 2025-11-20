package amoba;

public class Jatek {
    private Tabla tabla;
    private Jatekos aktualisJatekos;
    private int passzolasokSzama = 0;

    public Jatek() {
        tabla = new Tabla();
        aktualisJatekos = Jatekos.FEKETE;
    }

    public Tabla getTabla() {
        return tabla;
    }

    public Jatekos getAktualisJatekos() {
        return aktualisJatekos;
    }

    public boolean lep(int sor, int oszlop) {
        if (tabla.isEmpty(sor, oszlop)) {
            tabla.placeStone(sor, oszlop, aktualisJatekos);
            aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
            return true;
        }
        return false;
    }

    public boolean dontetlen() {
        passzolasokSzama++;
        aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
        if(passzolasokSzama >= 2) {
            return true;
        }
        return false;
    }
}
