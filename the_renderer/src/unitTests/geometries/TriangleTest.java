package geometries;

import elements.Camera;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest {

    Point3D createPoint3D(double x, double y, double z)
    {
        return new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    Ray createRay(Point3D point, Point3D vec)
    {
        return new Ray(point, new Vector(vec));
    }

    @Test
    public void testContainsPoint()
    {
        Triangle triangle = new Triangle(
                new Point3D(5, 2, 5),
                new Point3D(-3, 3, 5),
                new Point3D(4, -4, 4)
        );
        Point3D point = new Point3D(0.9, 1.9, 4.9);
        System.out.println(triangle.containsPoint(point));
    }

    @Test
    public void testIntersectionPoints()
    {
        // Triangle: { Point1: (0, 1, -2), Point2: (1, -1, -2) , Point3: (-1, -1, -2) }
        // View Plane: { dim: 3x3, distance: 1 }
        // 1 intersection
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 1, -2),
                    createPoint3D(1, -1, -2),
                    createPoint3D(-1, -1, -2)
            );

            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with triangle
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(tri.findIntersections(ray));
                }
            }

            assertEquals(1, result.size());
        }

        // Triangle: { Point1: (0, 10, -2), Point2: (1, -1, -2) , Point3: (-1, -1, -2) }
        // View Plane: { dim: 3x3, distance: 1 }
        // 2 intersections
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 10, -2),
                    createPoint3D(1, -1, -2),
                    createPoint3D(-1, -1, -2)
            );

            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with triangle
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(tri.findIntersections(ray));
                }
            }

            assertEquals(2, result.size());
        }

        // EP: Ray does not intersect with the plane containing the triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(1, 2, 3), createPoint3D(1, 1, 1));

            List<Point3D> intersections = new Plane(tri).findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }

        // EP: Ray intersects the triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(5, 2.5, 3), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(5, 2.5, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // EP: Ray does not intersect the triangle (off the side)
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(11, 2.5, 3), createPoint3D(0, 0, -1));

            List<Point3D> intersections = tri.findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }

        // EP: Ray does not intersect the triangle (off the corner)
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(5, 6, 3), createPoint3D(0, 0, -1));

            List<Point3D> intersections = tri.findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }

        // VBA: Ray begins on edge of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(5, 0, 0), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(5, 0, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // VBA: Ray begins on corner of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(10, 0, 0), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(10, 0, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // VBA: Ray begins on extended edge of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(11, 0, 0), createPoint3D(0, 0, -1));

            List<Point3D> intersections = tri.findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }

        // VBA: Ray begins before edge of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(5, 0, 3), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(5, 0, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // VBA: Ray begins before corner of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(0, 0, 3), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(0, 0, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // VBA: Ray begins before extended edge of triangle
        {
            Triangle tri = new Triangle(
                    createPoint3D(0, 0, 0),
                    createPoint3D(5, 5, 0),
                    createPoint3D(10, 0, 0)
            );
            Ray ray = createRay(createPoint3D(11, 0, 3), createPoint3D(0, 0, -1));
            Point3D expected = createPoint3D(10, 0, 0);

            List<Point3D> intersections = tri.findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }
    }
}