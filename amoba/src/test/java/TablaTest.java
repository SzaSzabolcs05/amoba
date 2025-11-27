import amoba.Jatekos;
import amoba.Tabla;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TablaTest {

    private Tabla tabla;

    @BeforeEach
    void init() {
        tabla = new Tabla();
    }

    // A tábla méret getter tesztelése
    @Test
    void testAlapMeret() {
        assertEquals(15, tabla.getMeret(), "A tábla méretének 15-nek kell lennie");
    }

    // Kezdetben minden cella üres legyen
    @Test
    void testUresATablaKezdetben() {
        for (int i = 0; i < tabla.getMeret(); i++) {
            for (int j = 0; j < tabla.getMeret(); j++) {
                assertNull(tabla.getCella(i, j), "Minden cellának üresnek kell lennie");
            }
        }
    }

    // Bábu elhelyezésének tesztelése és lekérdezése
    @Test
    void testHelyezes() {
        tabla.helyez(3, 4, Jatekos.FEKETE);
        assertEquals(Jatekos.FEKETE, tabla.getCella(3, 4));
    }

    // isEmpty metódus tesztelése, ha a cella üres
    @Test
    void testIsEmptyTrue() {
        assertTrue(tabla.isEmpty(2, 5));
    }

    // isEmpty metódus tesztelése, ha a cella nem üres
    @Test
    void testIsEmptyFalse() {
        tabla.helyez(2, 5, Jatekos.FEHER);
        assertFalse(tabla.isEmpty(2, 5));
    }
}
