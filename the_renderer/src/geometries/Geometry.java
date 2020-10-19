package geometries;

import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.List;

public abstract class Geometry {

    protected Material _material; // material of the geometry
    protected Color _emission; // emission color of the geometry

    // full constructor
    public Geometry(Material material, Color emission)
    {
        _material = material;
        _emission = emission;
    } // end full constructor

    // default constructor
    public Geometry()
    {
        this(new Material(), new Color(255, 255, 255));
    } // end default constructor

    // getters
    public Material getMaterial() { return new Material(_material); }
    public Color getEmission() { return new Color(_emission.getRGB()); }

    // setters
    public void setKd(double Kd) { _material.setKd(Kd); }
    public void setKs(double Ks) { _material.setKs(Ks); }
    public void setKr(double Kr) { _material.setKr(Kr); }
    public void setKt(double Kt) { _material.setKt(Kt); }
    public void setNShininess(int nShininess) { _material.setNShininess(nShininess); }
    public void setEmission(Color emission)
    {
        _emission = emission;
    }

    // unit vector perpendicular to geometry at specific point
    public abstract Vector getNormal(Point3D point);

    // points of intersection between geometry and ray
    public abstract List<Point3D> findIntersections(Ray ray);
} // end class Geometry
