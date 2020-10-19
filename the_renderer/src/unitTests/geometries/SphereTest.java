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

public class SphereTest {

    Point3D createPoint3D(double x, double y, double z)
    {
        return new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    Ray createRay(Point3D point, Point3D vec)
    {
        return new Ray(point, new Vector(vec));
    }

    @Test
    public void testFindIntersections()
    {
        Sphere sphere = new Sphere(180, new Point3D(200, 50, 0));
        Ray ray = new Ray(new Point3D(Point3D.ORIGIN), new Vector(new Point3D(100, 80, 150)).normalize());
        System.out.println(sphere.findIntersections(ray));
    }

    @Test
    public void testIntersectionPoints()
    {
        // Sphere: { center: (0,0,-3), radius: 1 }
        // View Plane: { dim: 3x3, distance: 1 }
        // 2 intersections
        {
            Sphere sphere = new Sphere(1, createPoint3D(0, 0, -3));

            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with sphere
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(sphere.findIntersections(ray));
                }
            }

            assertEquals(2, result.size());
        }

        // Sphere: { center: (0,0,-2), radius: 3 }
        // View Plane: { dim: 3x3, distance: 1 }
        // 9 intersections
        {
            Sphere sphere = new Sphere(3, createPoint3D(0, 0, -2));
            Camera camera = new Camera(
                    createPoint3D(0, 0, 0),
                    new Vector(createPoint3D(0, 1, 0)),
                    new Vector(createPoint3D(0, 0, -1)),
                    new Vector(createPoint3D(1, 0, 0))
            );

            // create ray through each pixel and calculate intersections with sphere
            List<Point3D> result = new ArrayList<Point3D>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 9, 9);
                    result.addAll(sphere.findIntersections(ray));
                }
            }

            assertEquals(9, result.size());
        }

        // EP: 2 intersections (before)
        {
            Sphere sphere = new Sphere(1, createPoint3D(0, 0, 0));
            Ray ray = createRay(createPoint3D(-1, 0, 0.5), createPoint3D(1, 0, 0));

            List<Point3D> expected = new ArrayList<Point3D>();
            expected.add(createPoint3D(-Math.sqrt(3)/2, 0, 0.5));
            expected.add(createPoint3D(Math.sqrt(3)/2, 0, 0.5));

            List result = sphere.findIntersections(ray);
            assertEquals(expected, result);
        }

        // EP: 0 intersections (tail does not intersect)
        // EP: 0 intersections (tail intersects)
        // EP: 1 intersection (in)
        // BVA: 1 intersection (on, tail intersects)
        // BVA: 2 intersections (on)
        // BVA: 1 intersection (center)
        // BVA: 1 intersection (on, tail intersects origin)
        // BVA: 2 intersections (on, body intersects origin)
        // BVA: 0 intersections (after, tail intersects twice and origin)
        // BVA: 2 intersections (before, body intersects origin)
        // BVA: 1 intersection (before, tangent)
        // BVA: 1 intersection (on, tangent)
        // BVA: 0 intersections (after, tangent)
        // BVA: 0 intersections (offset, perpendicular to normal)
    }
}