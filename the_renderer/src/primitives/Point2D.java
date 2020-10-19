package primitives;

// Point class in 2D space
public class Point2D {

    private Coordinate _x; // x coordinate
    private Coordinate _y; // y coordinate

    // full constructor from coordinates
    public Point2D(Coordinate x, Coordinate y)
    {
        this._x = x;
        this._y = y;
    } // end full constructor

    // full constructor from doubles
    public Point2D(double x, double y) {
        this(new Coordinate(x), new Coordinate(y));
    } // end full constructor

    // default constructor
    public Point2D()
    {
        this(new Coordinate(0), new Coordinate(0));
    } // end default constructor

    // copy constructor
    public Point2D(Point2D other)
    {
        this(other.getX(), other.getY());
    } // end copy constructor

    public Coordinate getX()
    {
        return new Coordinate(_x);
    }

    public void setX(Coordinate x)
    {
        this._x = x;
    }

    public Coordinate getY()
    {
        return new Coordinate(_y);
    }

    public void setY(Coordinate y)
    {
        this._y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point2D)) return false;
        Point2D other = (Point2D)obj;
        return (getX().equals(other.getX())) && (getY().equals(other.getY()));
    } // end method equals
} // end class Point2D
