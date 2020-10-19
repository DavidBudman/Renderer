package elements;

import primitives.Point3D;
import primitives.Vector;

import java.awt.*;

// interface for lights with source point
public interface LightSource
{
    // get impact of light on specific point
    public Color getIntensity(Point3D point);

    // get vector from light source to point
    public Vector getL(Point3D point);
} // end interface LightSource
