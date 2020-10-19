package primitives;

import util.Util;

// Coordinate class representing a single value
public class Coordinate implements Comparable<Coordinate> {

    private double _coordinate; // coordinate value

    // static variables
    public static final Coordinate ZERO = new Coordinate(0);
    public static final Coordinate ONE = new Coordinate(1);

    // full constructor
    public Coordinate(double coordinate)
    {
        this._coordinate = Util.isZero(coordinate) ? 0.0 : coordinate;
    } // end full constructor

    // default constructor
    public Coordinate()
    {
        this._coordinate = 0;
    } // end default constructor

    // copy constructor
    public Coordinate(Coordinate other)
    {
        this._coordinate = other._coordinate;
    } // end copy constructor

    public double getCoordinate()
    {
        return _coordinate;
    }

    public void setCoordinate(double coordinate)
    {
        this._coordinate = coordinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate)obj;
        return (Util.subtract(_coordinate, other._coordinate) == 0.0);
    } // end method equals

    @Override
    public String toString() {
        return "" + _coordinate;
    } // end method toString

    // subtract two coordinates
    public Coordinate subtract(Coordinate other)
    {
        return new Coordinate(Util.subtract(_coordinate, other._coordinate));
    } // end method subtract

    // add two coordinates
    public Coordinate add(Coordinate other)
    {
        return new Coordinate(Util.add(_coordinate, other._coordinate));
    } // end method add

    // multiply coordinate and double
    public Coordinate mult(double num)
    {
        return new Coordinate(Util.mult(_coordinate, num));
    } // end method mult

    // multiply two coordinates
    public Coordinate mult(Coordinate other)
    {
        return new Coordinate(Util.mult(_coordinate, other._coordinate));
    } // end method mult

    // divide two coordinates
    public Coordinate div(Coordinate other)
    {
        return new Coordinate(Util.div(_coordinate, other._coordinate));
    } // end method div

    // sqrt
    public Coordinate sqrt()
    {
        return new Coordinate(Math.sqrt(_coordinate));
    } // end method sqrt

    // square
    public Coordinate square()
    {
        return new Coordinate(_coordinate * _coordinate);
    } // end method square

    // Returns -1 if this < other, 0 if this == other, and 1 if this > other
    public int compareTo(Coordinate other)
    {
        if (equals(other)) return 0;
        if (_coordinate < other._coordinate) return -1;
        return 1;
    } // end method compareTo

    // Returns 1 if value is negative and 0 otherwise
    public int sign()
    {
        return _coordinate < 0 ? 1 : 0;
    } // end method sign
} // end class Coordinate
