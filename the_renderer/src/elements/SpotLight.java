package elements;

import primitives.Point3D;
import primitives.Vector;

import java.awt.*;

// Point light with focused direction
public class SpotLight extends PointLight
{
    // direction of spotlight
    private Vector _direction;

    // full constructor
    public SpotLight(Color color, Point3D position, double Kc, double Kl, double Kq, Vector direction)
    {
        super(color, position, Kc, Kl, Kq);
        _direction = direction.normalize();
    } // end full constructor

    // get direction of spotlight
    public Vector getD()
    {
        return _direction;
    }

    // get impact of light on specific point
    public Color getIntensity(Point3D point)
    {
        // I_L = [I_0(D * L)] / (Kc + Kld + Kq(d^2))
        double distance = _position.distance(point).getCoordinate();
        double dot = _direction.dot(getL(point)).getCoordinate();
        if (dot < 0) return new Color(Color.BLACK.getRGB());

        double scalar = dot / (_Kc + _Kl * distance + _Kq * distance * distance);
        return scaleColor(scalar, _color);
    } // end method getIntensity

    // scale color
    private Color scaleColor(double scalar, Color color)
    {
        Color temp = new Color(
                (float)Math.min(scalar * (color.getRed() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getGreen() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getBlue() / 255.0), 1.0)
        );
        return temp;
    } // end method scaleColor
} // end class SpotLight
