package geometries;

import primitives.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Plane class defined by point and normal vector
public class Plane extends Geometry {

    private Point3D _p; // point on plane
    private Vector _normal; // normal of the plane

    // full constructor
    public Plane(Material material, Color emission, Point3D pt, Vector vec)
    {
        super(material, emission);
        this._p = pt;
        this._normal = vec;
    } // end full constructor

    // partial constructor
    public Plane(Point3D pt, Vector vec)
    {
        this(new Material(), new Color(Color.BLACK.getRGB()), pt, vec);
    } // end partial constructor

    //default constructor
    public Plane()
    {
        this(new Material(), new Color(Color.BLACK.getRGB()), new Point3D(), new Vector());
    } //end default constructor

    //copy constructor
    public Plane(Plane other)
    {
        this(
                other.getMaterial(),
                other.getEmission(),
                other.getP(),
                other.getNormal()
        );
    } // end copy constructor

    // construct Plane from Triangle
    public Plane(Triangle triangle)
    {
        this(
                triangle.getMaterial(),
                triangle.getEmission(),
                triangle.getP1(),
                triangle.getNormal(Point3D.ORIGIN)
        );
    } // end constructor

    public Point3D getP() {
        return new Point3D(_p);
    }

    public void setP(Point3D p) {
        this._p = p;
    }

    public Vector getNormal() {
        return new Vector(_normal);
    }

    public Vector getNormal(Point3D point)
    {
        return getNormal();
    }

    public void setNormal(Vector normal) {
        this._normal = normal;
    }

    // returns whether or not given point is on plane
    public boolean containsPoint(Point3D point)
    {
        // point is _p
        if (_p.equals(point)) return true;

        // vector formed between point and _p is orthogonal to the plane's normal
        Vector vec = new Vector(_p.subtract(point));

        // if vec is orthogonal to _normal, point is on the plane
        return (vec.isOrthogonal(_normal));
    } // end method containsPoint

    // return points of intersection with ray
    public List<Point3D> findIntersections(Ray ray)
    {
        List<Point3D> result = new ArrayList<Point3D>();

        // Ray and Plane are parallel so no points of intersection
        if (ray.getDirection().isOrthogonal(_normal)) return result;

        // Ray begins on plane and is not parallel
        if (containsPoint(ray.getP00()))
        {
            result.add(ray.getP00());
            return result;
        }

        // P: point of intersection between ray and plane
        // ray: P = P0 + tV
        // t = (-N dot (P0 - Q0)) / (N dot V)
        Coordinate numerator = _normal.mult(new Coordinate(-1)).dot(new Vector(ray.getP00().subtract(_p)));
        Coordinate denominator = _normal.dot(ray.getDirection());
        Coordinate t = numerator.div(denominator);
        Point3D P = ray.getP00().add(ray.getDirection().mult(t));

        // Ray points away from plane and never intersects
        if (t.compareTo(Coordinate.ZERO) == -1) return result;

        // Ray intersects once
        result.add(P);

        return result;
    } // end method findIntersections
} // end class plane