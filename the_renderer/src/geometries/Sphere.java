package geometries;

import primitives.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Sphere class
public class Sphere extends RadialGeometry {
    private Point3D _center; // center of sphere

    // full constructor
    public Sphere(Material material, Color emission, double radius, Point3D pt)
    {
        super(material, emission, radius); // pass radius to parent constructor
        this._center = pt;
    } // end full constructor

    // partial constructor
    public Sphere(double radius, Point3D pt)
    {
        this(new Material(), new Color(128, 128, 128), radius, pt);
    } // end partial constructor

    // default constructor
    public Sphere()
    {
        this(new Material(), new Color(Color.BLACK.getRGB()), 0, new Point3D());
    } // end default constructor

    // copy constructor
    public Sphere(Sphere other)
    {
        this(other.getMaterial(), other.getEmission(), other.getRadius(), other.getCenter());
    } // end copy constructor

    public Point3D getCenter() {
        return new Point3D(_center);
    }

    public void setCenter(Point3D center) {
        this._center = center;
    }

    // get unit vector tangent to the sphere at given point
    public Vector getNormal(Point3D point)
    {
        Vector result;
        // find vector from origin to point
        if (point.equals(Point3D.ORIGIN)) {
            result = new Vector(_center).negative();
        } else {
            result = new Vector(point).subtract(new Vector(_center));
        }

        // convert to unit vector
        result.normalize();

        return result;
    } // end method getNormal

    // return points of intersection with ray
    public List<Point3D> findIntersections(Ray ray)
    {
        // list of intersection points to return
        List<Point3D> result = new ArrayList<Point3D>();

        // radius == 0 then no intersection points
        if (getRadius() == 0) return result;

        // ray originates on sphere
        if (pointOnSphere(ray.getP00()))
        {
            // ray originates on sphere, so include origin point of ray
            result.add(ray.getP00());

            // ray facing away from sphere
            if (getNormal(ray.getP00()).dot(ray.getDirection()).getCoordinate() >= 0)
                return result;

            // ray facing into sphere so add second intersection point
            Vector temp = ray.getDirection().mult(new Vector(ray.getP00(), _center).dot(ray.getDirection()));
            if (ray.getP00().equals(Point3D.ORIGIN)) {
                result.add(temp.mult(new Coordinate(2)).getHead());
            } else {
                result.add(new Vector(ray.getP00()).add(temp.mult(new Coordinate(2))).getHead());
            }

            return result;
        } // end if

        // Ray: P = P0 + tV
        // L = O - P0
        Vector L = new Vector(ray.getP00(), _center);
        Coordinate t_m = new Coordinate(L.dot(ray.getDirection()));
        Coordinate d = L.magnitude().square().subtract(t_m.square()).sqrt();

        Coordinate r = new Coordinate(getRadius());
        // if d > r -> no intersections
        if (d.compareTo(r) == 1) return result;

        Coordinate t_h = r.square().subtract(d.square()).sqrt();
        Coordinate t_1 = t_m.subtract(t_h);
        Coordinate t_2 = t_m.add(t_h);

        // check if one of the points is the ray origin
        if (t_1.compareTo(Coordinate.ZERO) == 0)
        {
            result.add(ray.getP00().add(ray.getDirection().mult(t_2)));
            return result;
        }
        if (t_2.compareTo(Coordinate.ZERO) == 0)
        {
            result.add(ray.getP00().add(ray.getDirection().mult(t_1)));
            return result;
        }

        Point3D P1 = ray.getP00().add(ray.getDirection().mult(t_1));
        Point3D P2 = ray.getP00().add(ray.getDirection().mult(t_2));

        // check if P1 and P2 are the same
        if (P1.equals(P2)) {
            if (t_1.compareTo(Coordinate.ZERO) == 1) result.add(P1);
            return result;
        }

        if (t_1.compareTo(Coordinate.ZERO) == 1) result.add(P1);
        if (t_2.compareTo(Coordinate.ZERO) == 1) result.add(P2);

        // sort list based on closeness to ray head
        result.sort(new Comparator<>() {
            public int compare(Point3D p1, Point3D p2)
            {
                return ray.getP00().distance(p1).compareTo(ray.getP00().distance(p2));
            }
        });

        return result;
    } // end method findIntersections

    // check if point is on sphere
    private boolean pointOnSphere(Point3D point)
    {
        return (new Vector(_center, point).magnitude().compareTo(new Coordinate(getRadius())) == 0);
    } // end method pointOnSphere

} // end class Sphere
