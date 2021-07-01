import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertTest {

    @Test
    public void test(){
        assertTrue(true);
        assertFalse(false);

        assertEquals(1, 1);

        assertEquals(0.51234, 0.512, 0.001);
        assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;

        assertEquals(Integer.valueOf(i), i2);
        assertEquals(i, i2.intValue());

        assertEquals("bola", "bola");
        //assertEquals("bola", "Bola");
        assertTrue("bola".equalsIgnoreCase("Bola"));
        assertTrue("bola".startsWith("bo"));

        Usuario usuario1 = new Usuario("Usuario 1");
        Usuario usuario2 = new Usuario("Usuario 1");
        Usuario usuario3 = null;

        assertEquals(usuario1, usuario2);
        assertNotSame(usuario1, usuario2);
        assertSame(usuario2, usuario2);

        assertNull(usuario3);
        assertNotNull(usuario1);

    }
}
