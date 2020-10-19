package elements;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {

    public Point3D _P0; // origin point of camera
    public Vector _vUp; // up orientation vector
    public Vector _vTo; // vector pointing towards the scene
    public Vector _vRight; // right orientation vector
    public double _focalDistance; // distance between view plane and focal plane
    public double _aperture; // size of aperture window (corners are heads of focal rays)
    public int _numRays; // number of rays for supersampling

    // full constructor
    public Camera(Point3D P0, Vector vUp, Vector vTo, Vector vRight,
                  double focalDistance, double aperture, int numRays)
    {
        if (aperture == 0) throw new IllegalArgumentException("Aperture cannot be 0!");
        _P0 = P0;
        _vUp = vUp;
        _vTo = vTo;
        _vRight = vRight;
        _focalDistance = focalDistance;
        _aperture = aperture;
        _numRays = numRays;
    } // end full constructor

    public Camera(Point3D P0, Vector vUp, Vector vTo, Vector vRight)
    {
        this(P0, vUp, vTo, vRight, 300, 1, 30);
    }

    // default constructor
    public Camera()
    {
        this(
                new Point3D(Point3D.ORIGIN),
                new Vector(new Point3D(0, 1, 0)),
                new Vector(new Point3D(0, 0, -1)),
                new Vector(new Point3D(1, 0, 0))
        );
    } // end default constructor

    // copy constructor
    public Camera(Camera other)
    {
        this(other._P0, other._vUp, other._vTo, other._vRight, other._focalDistance, other._aperture, other._numRays);
    } // end copy constructor

    // getters
    public Point3D getP0()
    {
        return new Point3D(_P0);
    }
    public Vector getVTo() { return new Vector(_vTo); }
    public double getAperture() { return _aperture; }
    public double getFocalDistance() { return _focalDistance; }
    public int getNumRays() { return _numRays; }

    // setters
    public void setAperture(double aperture) { _aperture = aperture; }
    public void setFocalDistance(double focalDistance) { _focalDistance = focalDistance; }
    public void setNumRays(int numRays) { _numRays = numRays; }

    // construct ray from camera origin through center of pixel[x][y]
    public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y, double screenDistance,
                                        double screenWidth, double screenHeight)
    {
        Vector P_C = _vTo.mult(new Coordinate(screenDistance));

        double R_x = screenWidth / Nx;
        double R_y = screenHeight / Ny;

        Coordinate xScale = new Coordinate((x - (Nx / 2.0)) * R_x + (R_x / 2));
        Coordinate yScale = new Coordinate((y - (Ny / 2.0)) * R_y + (R_y / 2));

        Vector P = P_C;

        if (!xScale.equals(Coordinate.ZERO)) P = P.add(_vRight.mult(xScale));
        if (!yScale.equals(Coordinate.ZERO)) P = P.subtract(_vUp.mult(yScale));

        // turn P into unit vector
        P.normalize();

        return new Ray(new Point3D(Point3D.ORIGIN), P);
    } // end method constructRayThroughPixel

    public Point3D pixelToPoint(int Nx, int Ny, double x, double y, double screenDistance,
                                double screenWidth, double screenHeight)
    {
        Vector P_C = _vTo.mult(new Coordinate(screenDistance));

        double R_x = screenWidth / Nx;
        double R_y = screenHeight / Ny;

        Coordinate xScale = new Coordinate((x - (Nx / 2.0)) * R_x + (R_x / 2));
        Coordinate yScale = new Coordinate((y - (Ny / 2.0)) * R_y + (R_y / 2));

        Vector P = P_C;

        if (!xScale.equals(Coordinate.ZERO)) P = P.add(_vRight.mult(xScale));
        if (!yScale.equals(Coordinate.ZERO)) P = P.subtract(_vUp.mult(yScale));

        return P.getHead();
    }
} // end class Camera
