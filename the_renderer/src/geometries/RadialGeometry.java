package geometries;

import primitives.Material;

import java.awt.*;

// RadialGeometry class for geometric figures with radii
public abstract class RadialGeometry extends Geometry {

    private double _radius; // radius of geometric figure

    // full constructor
    public RadialGeometry(Material material, Color emission, double radius)
    {
        super(material, emission);
        this._radius = radius;
    } // end full constructor

    // partial constructor
    public RadialGeometry(double radius)
    {
        this(new Material(), new Color(Color.BLACK.getRGB()), radius);
    } // end partial constructor

    // default constructor
    public RadialGeometry()
    {
        this(new Material(), new Color(Color.BLACK.getRGB()), 0);
    } // end default constructor

    // copy constructor
    public RadialGeometry(RadialGeometry other)
    {
        this(other.getMaterial(), other.getEmission(), other.getRadius());
    } // end copy constructor

    public double getRadius()
    {
        return _radius;
    }

    public void setRadius(double radius)
    {
        this._radius = radius;
    }
} // end class RadialGeometry
