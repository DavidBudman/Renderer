package primitives;

// Point class in 3D space
public class Point3D extends Point2D {

    private Coordinate _z; // z coordinate

    // static (0, 0, 0) Point
    public static final Point3D ORIGIN = new Point3D();

    // full constructor from coordinates
    public Point3D(Coordinate x, Coordinate y, Coordinate z)
    {
        super(x, y);
        this._z = z;
    } // end full constructor

    // full constructor from doubles
    public Point3D(double x, double y, double z)
    {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    } // end full constructor

    // default constructor
    public Point3D()
    {
        this(0, 0, 0);
    } // end default constructor

    // copy constructor
    public Point3D(Point3D other)
    {
        this(other.getX(), other.getY(), other.getZ());
    } // end copy constructor

    public Coordinate getZ()
    {
        return new Coordinate(_z);
    }

    public void setZ(Coordinate z)
    {
        this._z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D)obj;
        return (getX().equals(other.getX()) &&
                (getY().equals(other.getY()))) &&
                (getZ().equals(other.getZ()));
    } // end method equals

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", getX().toString(), getY().toString(), _z.toString());
    }

    // distance between points (sqrt((x1-x2)^2 + (y2-y1)^2 + (z2-z1)^2))
    public Coordinate distance(Point3D other)
    {
        Point3D difference = subtract(other);
        Coordinate sum = difference.getX().square()
                .add(difference.getY().square())
                .add(difference.getZ().square());

        return sum.sqrt();
    } // end method distance

    // point addition (x1+x2, y1+y2, z1+z2)
    public Point3D add(Vector vector)
    {
        return new Point3D(
                getX().add(vector.getHead().getX()),
                getY().add(vector.getHead().getY()),
                getZ().add(vector.getHead().getZ())
        );
    } // end method add

    // point subtraction (x1-x2, y1-y2, z1-z2)
    public Point3D subtract(Point3D other)
    {
        return new Point3D(
                getX().subtract(other.getX()),
                getY().subtract(other.getY()),
                getZ().subtract(other.getZ())
        );
    } // end method subtract

} // end class Point3D
