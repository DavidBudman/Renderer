package primitives;

// Ray class for ray in 3D space
public class Ray {
    private Point3D _p00; // point ray begins at
    private Vector _direction; // direction of the ray

    // full constructor
    public Ray(Point3D pt, Vector vec)
    {
        this._p00 = pt;
        this._direction = vec.normalize();
    } // end full constructor

    //default constructor
    public Ray()
    {
        this(new Point3D(), new Vector());
    } // end default constructor

    // copy constructor
    public Ray(Ray other)
    {
        this(other.getP00(), other.getDirection());
    }

    public Point3D getP00() {
        return _p00;
    }

    public void setP00(Point3D p00) {
        this._p00 = p00;
    }

    public Vector getDirection() {
        return _direction;
    }

    public void setDirection(Vector direction) {
        this._direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return (_p00.equals(other._p00) && _direction.equals(other._direction));
    } // end method equals

    @Override
    public String toString() {
        return String.format("{P00: %s, direction: %s}", _p00.toString(), _direction.toString());
    } // end method toString
} // end class Ray
