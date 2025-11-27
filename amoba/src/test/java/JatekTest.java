import amoba.Jatek;
import amoba.Jatekos;
import amoba.Tabla;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JatekTest {

    private Jatek jatek;

    @BeforeEach
    void init() {
        jatek = new Jatek();
    }

    // Kezdő játékos fekete tesztelése (mindig feketével kezdődjön a játék)
    @Test
    void testKezdoJatekosFekete() {
        assertEquals(Jatekos.FEKETE, jatek.getAktualisJatekos());
    }

    // Üres tábla tesztelése (kezdetben minden mező üres)
    @Test
    void testUresTabla() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertNull(jatek.getTabla().getCella(i, j));
            }
        }
    }

    // Ha érvénytelen lépést próbálunk megtenni, akkor a lépés visszatérési értéke 0 legyen (olyan mezőre lépés, ami már foglalt)
    @Test
    void testLepesErvenytelen() {
        jatek.lep(0, 0);
        assertEquals(0, jatek.lep(0, 0));
    }

    // Ha sikeres a lépés, akkor a lépés visszatérési értéke 1 legyen
    @Test
    void testLepesSikeres() {
        assertEquals(1, jatek.lep(3, 3));
    }

    // Lépés után a játékos váltson át a másik játékosra
    @Test
    void testJatekosValtas() {
        jatek.lep(1, 1);
        assertEquals(Jatekos.FEHER, jatek.getAktualisJatekos());
        jatek.lep(2, 2);
        assertEquals(Jatekos.FEKETE, jatek.getAktualisJatekos());
    }

    // Passzolás tesztelése
    @Test
    void testPasszEgy() {
        assertFalse(jatek.passz());
        assertEquals(Jatekos.FEHER, jatek.getAktualisJatekos());
    }

    // Döntetlen tesztelése két passzolás után
    @Test
    void testPasszDonto() {
        jatek.passz();
        assertTrue(jatek.passz(), "Két passz → döntetlen");
    }

    // Nyerő ötös vonal tesztelése (fekete játékosra, de mindegyik színre alkalmazható)
    @Test
    void testNyeroOtVonal() {
        // 5 fekete vízszintesen
        for (int i = 0; i < 4; i++) {
            assertEquals(1, jatek.lep(0, i));
            // fehér lépjen máshova
            if (i < 4)
                jatek.lep(10, i);
        }
        int veg = jatek.lep(0, 4); // Ennél a lépésnél fog fekete nyerni
        assertEquals(4, veg);
    }

    // Ez minden játékosra vonatkozik: ha ötösnél több bábú van egy sorban, akkor az a játékos veszít
    @Test
    void testTobbMintOtVesztes() {
        jatek.lep(0, 0); // fekete
        assertEquals(Jatekos.FEHER, jatek.getAktualisJatekos());
        jatek.lep(10, 0); // fehér
        assertEquals(Jatekos.FEKETE, jatek.getAktualisJatekos());
        jatek.lep(0, 1);
        jatek.lep(10, 1);
        jatek.lep(0, 2);
        jatek.lep(10, 2);
        jatek.lep(0, 3);
        jatek.lep(10, 3);
        jatek.lep(0, 5);
        jatek.lep(10, 5);

        int result = jatek.lep(0, 4); // 6 bábu van egymás mellett, így veszít a fekete
        assertEquals(3, result);
        assertEquals(Jatekos.FEKETE, jatek.getAktualisJatekos()); // Mivel nem volt érvényes lépés, és a játék befejeződött így nem váltott játékost
    }

    // A fekete játékos veszít, ha két nyitott hármasa van
    @Test
    void testFeketeVesztesKetszerNyitottHarom() {
        Tabla t = jatek.getTabla();

        // állítsunk be 2 nyitott fekete hármast
        // vízszintes
        t.helyez(5, 5, Jatekos.FEKETE);
        t.helyez(5, 6, Jatekos.FEKETE);
        t.helyez(5, 7, Jatekos.FEKETE);

        // függőleges
        t.helyez(7, 10, Jatekos.FEKETE);
        t.helyez(8, 10, Jatekos.FEKETE);
        t.helyez(9, 10, Jatekos.FEKETE);

        assertTrue(jatek.feketeVesztes());
    }

    // A fekete játékos veszít, ha két nyitott négyese van
    @Test
    void testLepVisszaad2HaFeketeVeszit() {
        Tabla t = jatek.getTabla();
        t.helyez(5, 5, Jatekos.FEKETE);
        t.helyez(5, 6, Jatekos.FEKETE);
        t.helyez(5, 7, Jatekos.FEKETE);
        t.helyez(5, 8, Jatekos.FEKETE);

        t.helyez(10, 10, Jatekos.FEKETE);
        t.helyez(10, 11, Jatekos.FEKETE);
        t.helyez(10, 12, Jatekos.FEKETE);
        t.helyez(10, 13, Jatekos.FEKETE);

        int result = jatek.lep(0, 0);
        assertEquals(2, result);
    }

    //Passzolás után a játékos váltson át a másik játékosra
    @Test
    void testJatekosValtasPassz() {
        assertEquals(Jatekos.FEKETE, jatek.getAktualisJatekos());
        jatek.passz();
        assertEquals(Jatekos.FEHER, jatek.getAktualisJatekos());
    }
}
