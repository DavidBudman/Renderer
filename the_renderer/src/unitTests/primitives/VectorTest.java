package primitives;

import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;
import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void testAdd()
    {
        // generic case
        {
            Vector a = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector b = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expectedResult = new Vector(
                    new Point3D(new Coordinate(5), new Coordinate(7), new Coordinate(9))
            );
            assertEquals("Addition Failed", expectedResult, a.add(b));
        }

        // Addition with negative results in 0
        {
            Vector a = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector c = new Vector(new Point3D(new Coordinate(-1), new Coordinate(-2), new Coordinate(-3)));

            try {
                a.add(c);
                fail("Didn't throw IllegalArgumentException for ZERO vector");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void testSubtract()
    {
// positive values
        {

            Vector a = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector b = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expectedResult = new Vector(
                    new Point3D(new Coordinate(5), new Coordinate(7), new Coordinate(9))
            );
            assertEquals("Addition Failed", expectedResult, a.add(b));
        }

        // Addition with negative results in 0
        {
            Vector a = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector c = new Vector(new Point3D(new Coordinate(-1), new Coordinate(-2), new Coordinate(-3)));

            try {
                a.add(c);
                fail("Didn't throw IllegalArgumentException for ZERO vector");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void testScaling()
    {

        // scale by 0
        {
            Vector vector = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            try
            {
                vector.mult(Coordinate.ZERO);
                fail("Didn't catch creation of ZERO vector");
            } catch (IllegalArgumentException e)
            {
                assertTrue(true);
            }
        }

        // scale by 1
        {
            Vector vector = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            assertEquals("scale by 1", vector, vector.mult(Coordinate.ONE));
        }

        // scale by -1
        {
            Vector vector = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expected = new Vector(new Point3D(new Coordinate(-4), new Coordinate(-5), new Coordinate(-6)));
            assertEquals("scale by -1", expected, vector.mult(new Coordinate(-1)));
        }

        // scale by 2
        {
            Vector vector = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expected = new Vector(new Point3D(new Coordinate(8), new Coordinate(10), new Coordinate(12)));
            assertEquals("scale by 2", expected, vector.mult(new Coordinate(2)));
        }

        // scale by 1/2
        {
            Vector vector = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expected = new Vector(new Point3D(new Coordinate(2), new Coordinate(2.5), new Coordinate(3)));
            assertEquals("scale by 1/2", expected, vector.mult(new Coordinate(0.5)));
        }
    }

    @Test
    public void testDotProduct()
    {
        // dot product results in 0
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(1)));
            Vector vec2 = new Vector(new Point3D(new Coordinate(-1), new Coordinate(1), new Coordinate(-1)));
            assertEquals(vec1.dot(vec2), Coordinate.ZERO);
        }

        // general case
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(1)));
            Vector vec2 = new Vector(new Point3D(new Coordinate(4), new Coordinate(3), new Coordinate(-5)));
            assertEquals(vec1.dot(vec2), new Coordinate(5));
        }
    }

    @Test
    public void testMagnitude()
    {
        // unit vector
        {
            Vector vector = new Vector(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));
            assertEquals("Incorrect magnitude", Coordinate.ONE, vector.magnitude());
        }

        // (1, 1, 1)
        {
            Vector vector = new Vector(new Point3D(new Coordinate(1), new Coordinate(1), new Coordinate(1)));
            assertEquals("Incorrect magnitude", new Coordinate(Math.sqrt(3)), vector.magnitude());
        }
    }

    @Test
    public void testNormalize()
    {
        // generic
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(1)));
            assertEquals(vec1.normalize().magnitude(), Coordinate.ONE);
        }

        // already unit vector
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(Math.sqrt(3) / 2), new Coordinate(1 / 2), new Coordinate(0)));
            assertEquals(vec1.normalize().magnitude(), Coordinate.ONE);
        }
    }

    @Test
    public void testCrossProduct()
    {
        // Colinear vectors results in ZERO vector
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector vec2 = new Vector(new Point3D(new Coordinate(3), new Coordinate(6), new Coordinate(9)));
            try {
                vec1.cross(vec2);
                fail("Didn't throw exception for ZERO vector");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }

        // generic
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector vec2 = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector expected = new Vector(new Point3D(new Coordinate(-3), new Coordinate(6), new Coordinate(-3)));
            assertEquals(vec1.cross(vec2), expected);
        }

        // ensure result is normal to two vectors
        {
            Vector vec1 = new Vector(new Point3D(new Coordinate(1), new Coordinate(2), new Coordinate(3)));
            Vector vec2 = new Vector(new Point3D(new Coordinate(4), new Coordinate(5), new Coordinate(6)));
            Vector result = vec1.cross(vec2);
            assertTrue(result.dot(vec1).equals(Coordinate.ZERO) && result.dot(vec2).equals(Coordinate.ZERO));
        }
    }
}