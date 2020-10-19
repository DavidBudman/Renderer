package geometries;

import primitives.*;

import java.awt.*;
import java.util.List;

// Triangle class in 3D space
public class Triangle extends Geometry {

    // points of the triangle
    private Point3D _p1;
    private Point3D _p2;
    private Point3D _p3;

    // full constructor
    public Triangle(Material material, Color emission, Point3D p1, Point3D p2, Point3D p3)
    {
        super(material, emission);
        this._p1 = p1;
        this._p2 = p2;
        this._p3 = p3;
    } // end full constructor

    // partial constructor
    public Triangle(Point3D p1, Point3D p2, Point3D p3)
    {
        this(new Material(), new Color(128, 128, 128), p1, p2, p3);
    }

    // default constructor
    public Triangle()
    {
        this(
                new Material(),
                new Color(128, 128, 128),
                new Point3D(), new Point3D(), new Point3D()
        );
    } // end default constructor

    // copy constructor
    public Triangle(Triangle other)
    {
        this(
                other.getMaterial(),
                other.getEmission(),
                other.getP1(), other.getP2(), other.getP3()
        );
    } // end copy constructor

    public Point3D getP1() {
        return new Point3D(_p1);
    }

    public void setP1(Point3D p1) {
        this._p1 = p1;
    }

    public Point3D getP2() {
        return new Point3D(_p2);
    }

    public void setP2(Point3D p2) {
        this._p2 = p2;
    }

    public Point3D getP3() {
        return new Point3D(_p3);
    }

    public void setP3(Point3D p3) {
        this._p3 = p3;
    }

    public Vector getNormal(Point3D point)
    {
        Vector v1 = new Vector(_p2.subtract(_p1)).normalize();
        Vector v2 = new Vector(_p3.subtract(_p1)).normalize();
        Vector normal = v1.cross(v2);
        normal.normalize();

        return normal;
    } // end method getNormal

    // return points of intersection with ray
    public List<Point3D> findIntersections(Ray ray)
    {
        // create plane that triangle is on
        Plane plane = new Plane(this);
        List<Point3D> result = plane.findIntersections(ray);

        // check if intersection points is empty
        if (result.isEmpty()) return result;

        // check if point is on the triangle
        if (containsPoint(ray.getP00())) return result;

        // Ray begins on the plane, but not on the triangle
        if (plane.containsPoint(ray.getP00()))
        {
            result.clear();
            return result;
        }

        // remove point if it is not on the triangle
        Vector v1 = new Vector(_p1.subtract(ray.getP00())).normalize();
        Vector v2 = new Vector(_p2.subtract(ray.getP00())).normalize();
        Vector v3 = new Vector(_p3.subtract(ray.getP00())).normalize();

        // TODO: Delete this
        if (
                v1.cross(v2).magnitude().compareTo(Coordinate.ZERO) == 0 ||
                        v2.cross(v3).magnitude().compareTo(Coordinate.ZERO) == 0 ||
                        v3.cross(v1).magnitude().compareTo(Coordinate.ZERO) == 0
        ) return result;

        Vector N1 = v1.cross(v2).normalize();
        Vector N2 = v2.cross(v3).normalize();
        Vector N3 = v3.cross(v1).normalize();

        Vector vec = new Vector(ray.getP00(), result.get(0));
        int sign1 = vec.dot(N1).sign();
        int sign2 = vec.dot(N2).sign();
        int sign3 = vec.dot(N3).sign();

        if (!((sign1 == sign2) && (sign2 == sign3))) result.clear();

        return result;
    } // end method findIntersections

    // returns the area of the triangle
    public Coordinate area()
    {
        try
        {
            // point is a corner of the triangle
            Vector v1 = new Vector(_p1, _p2);
            Vector v2 = new Vector(_p1, _p3);
            v1.dot(v2); // check for linear dependence
            return new Coordinate(0.5).mult(v1.cross(v2).magnitude());
        } catch (IllegalArgumentException e)
        {
            return new Coordinate(0);
        }
    } // end method area

    // Returns whether or not point is on the triangle
    public boolean containsPoint(Point3D point)
    {
        // create three triangles containing point
        // point is in triangle if the sum of the areas is equal to the area of the original triangle
        Triangle tri1 = new Triangle(_p1, _p2, point);
        Triangle tri2 = new Triangle(_p2, _p3, point);
        Triangle tri3 = new Triangle(_p1, _p3, point);
        return tri1.area().add(tri2.area()).add(tri3.area()).equals(area());
    } // end method containsPoint

} // end class Triangle
