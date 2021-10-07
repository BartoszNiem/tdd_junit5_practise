package pl.qaaacademy.simple;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SimpleJunitTest {
    @Test
    public void VeryComplexTest(){
        Calculator calc = new Calculator();
        assertEquals(calc.add(2,3), 5);

    }
}
