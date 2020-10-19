package elements;

import primitives.Point3D;

import java.awt.*;

// base class for lights
public abstract class Light
{
    // color of light
    protected Color _color;

    // full constructor
    public Light(Color color)
    {
        _color = color;
    } // end full constructor

    // default constructor
    public Light()
    {
        this(Color.WHITE);
    } // end default constructor

    // getters
    public Color getColor() { return _color; }

    // get impact of light
    public Color getIntensity()
    {
        return _color;
    } // end method getIntensity
} // end class Light
