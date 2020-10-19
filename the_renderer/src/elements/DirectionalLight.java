package elements;

import primitives.Point3D;
import primitives.Vector;

import java.awt.*;

// Light source that emits light in specified direction without source point
public class DirectionalLight extends Light implements LightSource
{
    // direction light travels
    private Vector _direction;

    // full constructor
    public DirectionalLight(Color color, Vector direction)
    {
        super(color);
        _direction = direction.normalize();
    } // end full constructor

    // default constructor
    public DirectionalLight()
    {
        this(Color.WHITE, new Vector());
    } // end default constructor

    // getters
    public Vector getDirection() { return _direction; }

    // setters
    public void setDirection(Vector direction) { _direction = direction; }

    // return light impact at point
    public Color getIntensity(Point3D point)
    {
        return _color;
    } // end method getIntensity

    // vector from "light source" to point
    public Vector getL(Point3D point)
    {
        return _direction;
    } // end method getL
} // end class DirectionalLight
