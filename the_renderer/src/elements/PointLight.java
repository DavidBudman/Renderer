package elements;

import primitives.Point3D;
import primitives.Vector;

import java.awt.*;

// light that spreads equally in all directions
public class PointLight extends Light implements LightSource
{
    protected Point3D _position; // source point of light
    protected double _Kc, _Kl, _Kq; // coefficients of attenuation with distance polynomial

    // full constructor
    public PointLight(Color color, Point3D position, double Kc, double Kl, double Kq)
    {
        super(color);
        _position = position;
        _Kc = Kc;
        _Kl = Kl;
        _Kq = Kq;
    } // end full constructor

    // get impact of light on specific point
    public Color getIntensity(Point3D point)
    {
        // I_L = I_0 / (Kc + Kl*d + Kq(d^2))
        double distance = _position.distance(point).getCoordinate();
        if (distance == 0) return new Color(0, 0, 0);
        double scalar = (1.0 / (_Kc + _Kl * distance + _Kq * distance * distance));
        return scaleColor(scalar, _color);
    } // end method getIntensity

    // scale color
    private Color scaleColor(double scalar, Color color)
    {
        return new Color(
                (float)Math.min(scalar * (color.getRed() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getGreen() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getBlue() / 255.0), 1.0)
        );
    } // end method scaleColor

    // get vector for source light to point
    public Vector getL(Point3D point)
    {
        return new Vector(_position, point).normalize();
    } // end method getL
} // end class PointLight
