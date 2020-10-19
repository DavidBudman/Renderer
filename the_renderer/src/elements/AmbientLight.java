package elements;

import primitives.Point3D;

import java.awt.*;

public class AmbientLight extends Light
{
    private double _Ka;

    // full constructor
    public AmbientLight(Color color, double Ka)
    {
        super(color);
        _Ka = Ka;
    } // end full constructor

    // default constructor
    public AmbientLight()
    {
        this(new Color(0, 0, 0), 0);
    } // end default constructor

    // copy constructor
    public AmbientLight(AmbientLight other)
    {
        this(other._color, other._Ka);
    } // end copy constructor

    // return color of ambient lighting
    @Override
    public Color getIntensity()
    {
        // scale r, g, and b by Ka
        return new Color(
                (int)(_color.getRed() * _Ka),
                (int)(_color.getGreen() * _Ka),
                (int)(_color.getBlue() * _Ka)
        );
    } // end method getIntensity()
} // end class AmbientLight
