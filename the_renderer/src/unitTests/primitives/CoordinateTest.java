package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    @Test
    public void divTest()
    {
        Coordinate numerator = new Coordinate(1);
        Coordinate denominator = new Coordinate(1232038.3710993805);
        Coordinate result = numerator.div(denominator);
        double res = 1 / 1232038.3710993805;
        assertEquals(result, new Coordinate(1 / 1232038.3710993805));
    }
}