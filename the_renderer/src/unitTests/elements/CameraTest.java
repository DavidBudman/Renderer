package elements;

import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class CameraTest {

    public Point3D createPoint(double x, double y, double z)
    {
        return new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    @Test
    public void testRaysConstruction() {

        Camera camera = new Camera(
                new Point3D(Point3D.ORIGIN),
                new Vector(createPoint(0, 1, 0)),
                new Vector(createPoint(0, 0, -1)),
                new Vector(createPoint(1, 0, 0))
        );

        // pixel[0, 0]
        {
            Ray result = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 9, 9);
            Ray expected = new Ray(new Point3D(Point3D.ORIGIN),
                    new Vector(createPoint(-3, 3, -1)).normalize());
            assertEquals(expected, result);
        }

        // pixel[1, 1]
        {
            Ray result = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 9, 9);
            Ray expected = new Ray(new Point3D(Point3D.ORIGIN), new Vector(createPoint(0, 0, -1)));
            assertEquals(expected, result);
        }

        // pixel[2, 2]
        {
            Ray result = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 9, 9);
            Ray expected = new Ray(new Point3D(Point3D.ORIGIN),
                    new Vector(createPoint(3, -3, -1)).normalize());
            assertEquals(expected, result);
        }
    }
}