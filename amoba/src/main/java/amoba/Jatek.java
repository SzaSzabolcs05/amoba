package amoba;

import java.io.Serializable;

public class Jatek implements Serializable {
    private Tabla tabla;
    private Jatekos aktualisJatekos;
    private int passzolasokSzama = 0;
    private static final long serialVersionUID = 1L;

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
            passzolasokSzama = 0;
            tabla.helyez(sor, oszlop, aktualisJatekos);
            aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
            return true;
        }
        return false;
    }

    public boolean passz() {
        passzolasokSzama++;
        if(passzolasokSzama >= 2) {
            return true;
        }
        aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
        return false;
    }

    public int countEgySorba(int sor, int oszlop) {
        //Egy soron belül hány darab azonos színű bábu van
        int countViz = 1;
        int i = 1;
        // jobbra
        while (sor + i < tabla.getMeret() && tabla.getCella(sor + i, oszlop) == aktualisJatekos) {
            countViz++;
            i++;
        }
        // balra
        i = 1;
        while (sor - i >= 0 && tabla.getCella(sor - i, oszlop) == aktualisJatekos) {
            countViz++;
            i++;
        }

        //Egy oszlopon belül hány darab azonos színű bábu van
        int countFug = 1;
        i = 1;
        // lefelé
        while (oszlop + i < tabla.getMeret() && tabla.getCella(sor, oszlop + i) == aktualisJatekos) {
            countFug++;
            i++;
        }
        // felfelé
        i = 1;
        while (oszlop - i >= 0 && tabla.getCella(sor, oszlop - i) == aktualisJatekos) {
            countFug++;
            i++;
        }

        // (\) Átlósan hány darab azonos színű bábu van
        int countAtl1 = 1;
        i = 1;
        while (sor + i < tabla.getMeret() && oszlop + i < tabla.getMeret() && tabla.getCella(sor + i, oszlop + i) == aktualisJatekos) {
            countAtl1++;
            i++;
        }
        i = 1;
        while (sor - i >= 0 && oszlop - i >= 0 && tabla.getCella(sor - i, oszlop - i) == aktualisJatekos) {
            countAtl1++;
            i++;
        }

        // (/) Átlósan hány darab azonos színű bábu van
        int countAtl2 = 1;
        i = 1;
        while (sor + i < tabla.getMeret() && oszlop - i >= 0 && tabla.getCella(sor + i, oszlop - i) == aktualisJatekos) {
            countAtl2++;
            i++;
        }
        i = 1;
        while (sor - i >= 0 && oszlop + i < tabla.getMeret() && tabla.getCella(sor - i, oszlop + i) == aktualisJatekos) {
            countAtl2++;
            i++;
        }

        return Math.max(Math.max(countViz, countFug), Math.max(countAtl1, countAtl2));
    }

    public boolean feketeVesztes(Jatekos j){
        int count3 = 0;
        int count4 = 0;

        // irányok: vízszintes, függőleges, átlós \, átlós /
        // Egy tömbbe tettem, hogy szépen lehessen iterálni rajtuk és ne kelljen külön ciklusokat írni
        int[][] irany = {
            {1, 0}, {0, 1}, {1, 1}, {1, -1}
        };

        for (int sor = 0; sor < tabla.getMeret(); sor++) {
            for (int oszlop = 0; oszlop < tabla.getMeret(); oszlop++) {

                for (int[] d : irany) {
                    int dx = d[0];
                    int dy = d[1];

                    if (isOpenThree(sor, oszlop, dx, dy, j)) {
                        count3++;
                    }else if(isOpenFour(sor, oszlop, dx, dy, j)){
                        count4++;
                    }
                }
            }
        }
        if(count4 >=2 || count3 >=2){
            return true;
        }

        return false;
    }

    // Megmondja, hogy egy adott pozíciótól (ami az első bábú helye) az nyitott hármasban van-e
    // pl. [., X, X, X, .] ez az első x-re azt fogja mondani, hogy nyitott hármasban van, viszont bármelyik másik x-re már nem
    // Ahol megszámoljuk a darabszámot ott kell figyelni, hogy úgy hívjuk meg, ezt a függvényt, hogy az elsőt érintse (a többi lényegtelen)
    // dx és dy az irányt jelöli (pl. függőleges: dx=1, dy=0, tehát a dx-et lehet léptetni, míg dy-t nem)
    private boolean isOpenThree(int sor, int oszlop, int dx, int dy, Jatekos j) {
        int m = tabla.getMeret();

        // Ellenőrzi, hogy a [sor−dx , oszlop−dy] üres-e (tehát a bábútól egy lépéssel vissza)
        int ax = sor - dx;
        int ay = oszlop - dy;
        if ((ax>=0 && ax < m && ay >= 0 && ay < m) && tabla.getCella(ax, ay) != null)
            return false;

        // Ellenőrzi, hogy a [sor + 3*dx, oszlop + 3*dy] üres-e (tehát az első bábútól hárommal előre)
        int bx = sor + 3 * dx;
        int by = oszlop + 3 * dy;
        if ((bx>=0 && bx < m && by >= 0 && by < m) && tabla.getCella(bx, by) != null)
            return false;

        // Ellenőrzi, hogy a három bábú az azonos-e
        for (int i = 0; i < 3; i++) {
            int x = sor + i * dx;
            int y = oszlop + i * dy;

            if (!((x>=0 && x < m && y >= 0 && y < m)) || tabla.getCella(x, y) != j)
                return false;
        }
        return true;
    }

    // Ugyanazt vizsgálja, mint az isOpenThree, csak négyesre
    private boolean isOpenFour(int sor, int oszlop, int dx, int dy, Jatekos j) {
        int m = tabla.getMeret();
        int ax = sor - dx;
        int ay = oszlop - dy;

        if ((ax>=0 && ax < m && ay >= 0 && ay < m) && tabla.getCella(ax, ay) != null)
            return false;

        int bx = sor + 4 * dx;
        int by = oszlop + 4 * dy;
        if ((bx>=0 && bx < m && by >= 0 && by < m) && tabla.getCella(bx, by) != null)
            return false;

        for (int i = 0; i < 4; i++) {
            int x = sor + i * dx;
            int y = oszlop + i * dy;

            if (!((x>=0 && x < m && y >= 0 && y < m)) || tabla.getCella(x, y) != j)
                return false;
        }
        return true;
    }
}
