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

public class PlaneTest {

    Point3D createPoint3D(double x, double y, double z)
    {
        return new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    Plane createPlane(Point3D point, Point3D vec)
    {
        return new Plane(point, new Vector(vec));
    }

    Ray createRay(Point3D point, Point3D vec)
    {
        return new Ray(point, new Vector(vec));
    }

    @Test
    public void testFindIntersections()
    {
        Plane plane = new Plane(
                new Point3D(15, 0, 0),
                new Vector(new Point3D(3, 4, -2))
        );

        Ray ray = new Ray(
                new Point3D(Point3D.ORIGIN),
                new Vector(new Point3D(5,2,4)).normalize()
        );

        System.out.println(plane.findIntersections(ray));
    }

    @Test
    public void testIntersectionPoints()
    {
        // Plane: { Point: (0, 0, -3), Normal: [0, 0, -1] }
        // View Plane: { dim: 3x3, distance: 1 }
        // 9 intersections
        {
            Plane plane = new Plane(
                    createPoint3D(0, 0, -3),
                    new Vector(createPoint3D(0, 0, -1))
            );

            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with plane
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(plane.findIntersections(ray));
                }
            }

            assertEquals(9, result.size());
        }

        // Plane: { Point: (0, 0, -3), Normal: [0, sqrt(2)/2, sqrt(2)/2] }
        // View Plane: { dim: 3x3, distance: 1 }
        // 9 intersections
        {
            Plane plane = new Plane(
                    createPoint3D(0, 0, -3),
                    new Vector(createPoint3D(0, 1, -10)).normalize()
            );

            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with plane
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(plane.findIntersections(ray));
                }
            }

            assertEquals(9, result.size());
        }

        // EP: Ray intersects the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(3, 3, 3), createPoint3D(-1, -2, -3));
            Point3D expected = createPoint3D(2.5, 2, 1.5);
            List<Point3D> intersections = plane.findIntersections(ray);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // EP: Ray does not intersect the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(3, 3, 3), createPoint3D(1, 2, 3));
            List<Point3D> intersections = plane.findIntersections(ray);

            // make sure there are no points of intersection
            assertTrue(intersections.isEmpty());
        }

        // BVA: Ray is parallel to and included in the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(10, -4, 0), createPoint3D(2, 2, -4));
            List<Point3D> intersections = plane.findIntersections(ray);

            // make sure there are no points of intersection
            assertTrue(intersections.isEmpty());
        }

        // BVA: Ray is parallel to and not included in the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(10, -4, 5), createPoint3D(2, 2, -4));
            List<Point3D> intersections = plane.findIntersections(ray);

            // make sure there are no points of intersection
            assertTrue(intersections.isEmpty());
        }

        // BVA: Ray is orthogonal to and P0 is before the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(0, 0, 0), createPoint3D(10, 10, 10));
            List<Point3D> intersections = plane.findIntersections(ray);
            Point3D expected = createPoint3D(2, 2, 2);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // BVA: Ray is orthogonal to and P0 is in the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(10, -4, 0), createPoint3D(10, 10, 10));
            List<Point3D> intersections = plane.findIntersections(ray);
            Point3D expected = createPoint3D(10, -4, 0);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }

        // BVA: Ray is orthogonal to and and P0 is after the plane
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(0, 0, 0), createPoint3D(-10, -10, -10));
            List<Point3D> intersections = plane.findIntersections(ray);

            assertTrue(intersections.isEmpty());
        }

        // BVA: Ray begins at the plane (P0 is in the plane, but not the ray)
        {
            Plane plane = createPlane(createPoint3D(2, 2, 2), createPoint3D(1, 1, 1));
            Ray ray = createRay(createPoint3D(10, -4, 0), createPoint3D(1, 2, 3));
            List<Point3D> intersections = plane.findIntersections(ray);
            Point3D expected = createPoint3D(10, -4, 0);

            // make sure exactly one point of intersection is found
            if (intersections.size() != 1) fail("1 point of intersection expected");

            assertEquals(intersections.get(0), expected);
        }
    }
}