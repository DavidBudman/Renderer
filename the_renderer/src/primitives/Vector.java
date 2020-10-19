package primitives;

// Vector class in 3D space
public class Vector {

    private Point3D _head; // head of the vector

    // full constructor
    public Vector(Point3D head)
    {
        if (head.equals(Point3D.ORIGIN))
            throw new IllegalArgumentException("Illegal ZERO Vector");
        this._head = head;
    } // end full constructor

    // default constructor
    public Vector()
    {
        this(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(0)));
    } // end default constructor

    // copy constructor
    public Vector(Vector other)
    {
        this(other.getHead());
    } // end copy constructor

    // constructor from two points
    public Vector(Point3D source, Point3D destination)
    {
        this(destination.subtract(source));
    } // end constructor

    public Point3D getHead()
    {
        return new Point3D(_head);
    }

    public void setHead(Point3D head)
    {
        this._head = head;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector)obj;
        return (_head.equals(other._head));
    } // end method equals

    @Override
    public String toString() {
        return String.format("[%s, %s, %s]", _head.getX(), _head.getY(), _head.getZ());
    }

    // vector scaling
    public Vector mult(Coordinate scaleFactor)
    {
        return new Vector(new Point3D(
                _head.getX().mult(scaleFactor),
                _head.getY().mult(scaleFactor),
                _head.getZ().mult(scaleFactor)
        ));
    } // end method mult

    // vector addition
    public Vector add(Vector other)
    {
        return new Vector(new Point3D(
                _head.getX().add(other.getHead().getX()),
                _head.getY().add(other.getHead().getY()),
                _head.getZ().add(other.getHead().getZ())
        ));
    } // end method add

    // vector subtraction
    public Vector subtract(Vector other)
    {
        return new Vector(new Point3D(
                _head.getX().subtract(other.getHead().getX()),
                _head.getY().subtract(other.getHead().getY()),
                _head.getZ().subtract(other.getHead().getZ())
        ));
    } // end method subtract

    // cross product
    public Vector cross(Vector other)
    {
        // calculate x, y, and z components
        Coordinate x = _head.getY().mult(other.getHead().getZ())
                .subtract(_head.getZ().mult(other.getHead().getY()));
        Coordinate y = _head.getZ().mult(other.getHead().getX())
                .subtract(_head.getX().mult(other.getHead().getZ()));
        Coordinate z = _head.getX().mult(other.getHead().getY())
                .subtract(_head.getY().mult(other.getHead().getX()));

        return new Vector(new Point3D(x, y, z));
    } // end method cross

    // dot product
    public Coordinate dot(Vector other)
    {
        Coordinate x = _head.getX().mult(other.getHead().getX());
        Coordinate y = _head.getY().mult(other.getHead().getY());
        Coordinate z = _head.getZ().mult(other.getHead().getZ());

        return x.add(y).add(z);
    } // end method dot

    // vector magnitude
    public Coordinate magnitude()
    {
        Coordinate aSquared = _head.getX().mult(_head.getX());
        Coordinate bSquared = _head.getY().mult(_head.getY());
        Coordinate cSquared = _head.getZ().mult(_head.getZ());

        return aSquared.add(bSquared).add(cSquared).sqrt();
    } // end method magnitude

    // reduce vector to unit vector in same direction
    public Vector normalize()
    {
        Coordinate magnitude = magnitude();
        _head.setX(_head.getX().div(magnitude));
        _head.setY(_head.getY().div(magnitude));
        _head.setZ(_head.getZ().div(magnitude));

        return new Vector(this);
    } // end method normalize

    // reverse direction of vector
    public Vector negative()
    {
        return mult(new Coordinate(-1));
    } // end method negative

    // returns whether or not two vectors are orthogonal
    public boolean isOrthogonal(Vector other)
    {
        // dot product is 0
        return dot(other).equals(Coordinate.ZERO);
    } // end method isOrthogonal
} // end class Vector
