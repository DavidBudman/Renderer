/*
package geometries;

import primitives.Point3D;
import primitives.Vector;

// Cylinder class
public class Cylinder extends RadialGeometry {

    private Point3D _axisPoint;     // origin point at base of cylinder
    private Vector _axisDirection;  // direction of cylinder

    // full constructor
    Cylinder(double radius, Point3D axisPoint, Vector axisDirection)
    {
        super(radius); // pass radius to parent constructor
        this._axisPoint = axisPoint;
        this._axisDirection = axisDirection;
    } // end full constructor

    // default constructor
    Cylinder()
    {
        super();
        this._axisPoint = new Point3D();
        this._axisDirection = new Vector();
    } // end default constructor

    // copy constructor
    Cylinder(Cylinder other)
    {
        super(other.getRadius());
        this._axisPoint = other.getAxisPoint();
        this._axisDirection = other.getAxisDirection();
    } // end copy constructor

    public Point3D getAxisPoint() {
        return new Point3D(_axisPoint);
    }

    public void setAxisPoint(Point3D axisPoint) {
        this._axisPoint = axisPoint;
    }

    public Vector getAxisDirection() {
        return new Vector(_axisDirection);
    }

    public void setAxisDirection(Vector axisDirection) {
        this._axisDirection = axisDirection;
    }
} // end class Cylinder
*/