package amoba;

import java.io.Serializable;

public class Jatek implements Serializable {
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

    // Bábu léptet és közben ellenőrzi, hogy nyert-e valaki vagy érvénytelen lépés történt
    public int lep(int sor, int oszlop) {
        if (tabla.isEmpty(sor, oszlop)) {
            passzolasokSzama = 0;
            tabla.helyez(sor, oszlop, aktualisJatekos);

            // Ellenőrizzük, hogy a fekete játékos veszített-e
            if(aktualisJatekos == Jatekos.FEKETE){
                if(feketeVesztes()){
                    return 2; // A fekete játékos veszített
                }
            }
            if(otnelTobb(sor, oszlop, aktualisJatekos)){
                return 3; // A lépő játékos veszített (fekete is és fehér is)
            }
            if(vanOt(sor, oszlop, aktualisJatekos)){
                return 4; // A lépő játékos nyert
            }

            aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
            return 1;
        }
        return 0;
    }

    // Passzolás kezelése, visszatér true-val, ha döntetlen a játék
    public boolean passz() {
        passzolasokSzama++;
        if(passzolasokSzama >= 2) {
            return true;
        }
        aktualisJatekos = (aktualisJatekos == Jatekos.FEKETE) ? Jatekos.FEHER : Jatekos.FEKETE;
        return false;
    }

    // Azt vizsgálja, hogy a fekete játékos veszített-e
    public boolean feketeVesztes(){
        int count3 = 0;
        int count4 = 0;

        // irányok: vízszintes, függőleges, átlós \, átlós /
        // Egy tömbbe tettem, hogy szépen lehessen iterálni rajtuk és ne kelljen külön ciklusokat írni
        int[][] irany = {
            {1, 0}, {0, 1}, {1, 1}, {1, -1}
        };

        for (int sor = 0; sor < tabla.getMeret(); sor++) {
            for (int oszlop = 0; oszlop < tabla.getMeret(); oszlop++) {
                if (tabla.getCella(sor, oszlop) != Jatekos.FEKETE) continue; // Csak a fekete bábukat vizsgáljuk, ha fehér vagy üres akkor nézi a következőt

                for (int[] d : irany) { // Egy adott bábutól megnézi az összes irányba
                    int dx = d[0];
                    int dy = d[1];

                    // Azt számoljuk meg, hogy hány darab van (nyitott hármas és négyes)
                    if (isOpenThree(sor, oszlop, dx, dy, Jatekos.FEKETE)) {
                        count3++;
                    }else if(isOpenFour(sor, oszlop, dx, dy, Jatekos.FEKETE)){
                        count4++;
                    }
                }
            }
        }
        // Ha true-val tér vissza, akkor a fekete játékos veszített
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

        // Ellenőrzi, hogy a [sor−dx , oszlop−dy] üres-e (tehát a bábutól egy lépéssel vissza)
        int ax = sor - dx;
        int ay = oszlop - dy;
        if ((ax>=0 && ax < m && ay >= 0 && ay < m) && tabla.getCella(ax, ay) != null)
            return false;

        // Ellenőrzi, hogy a [sor + 3*dx, oszlop + 3*dy] üres-e (tehát az első bábutól hárommal előre)
        int bx = sor + 3 * dx;
        int by = oszlop + 3 * dy;
        if ((bx>=0 && bx < m && by >= 0 && by < m) && tabla.getCella(bx, by) != null)
            return false;

        // Ellenőrzi, hogy a három bábu az azonos-e (az előzőek között vizsgált részen)
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

    private boolean otnelTobb(int sor, int oszlop, Jatekos j){
        return hosszIranyban(sor, oszlop, j, 1, 0) > 5 ||    // vízszintes
            hosszIranyban(sor, oszlop, j, 0, 1) > 5 ||    // függőleges
            hosszIranyban(sor, oszlop, j, 1, 1) > 5 ||    // átló \
            hosszIranyban(sor, oszlop, j, 1, -1) > 5;     // átló /
    }

    private boolean vanOt(int sor, int oszlop, Jatekos j) {
        return hosszIranyban(sor, oszlop, j, 1, 0) == 5 ||    // vízszintes
            hosszIranyban(sor, oszlop, j, 0, 1) == 5 ||    // függőleges
            hosszIranyban(sor, oszlop, j, 1, 1) == 5 ||    // átló \
            hosszIranyban(sor, oszlop, j, 1, -1) == 5;     // átló /
    }

    private int hosszIranyban(int sor, int oszlop, Jatekos j, int dx, int dy) {
        int count = 1;
        int x = sor + dx, y = oszlop + dy;

        while (x >= 0 && x < tabla.getMeret() && y >= 0 && y < tabla.getMeret()
                && tabla.getCella(x, y) == j) {
            count++;
            x += dx; y += dy;
        }

        x = sor - dx; y = oszlop - dy;
        while (x >= 0 && x < tabla.getMeret() && y >= 0 && y < tabla.getMeret()
                && tabla.getCella(x, y) == j) {
            count++;
            x -= dx; y -= dy;
        }

        return count;
    }
}
