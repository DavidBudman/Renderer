package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import elements.PointLight;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EmissionTest {

    @Test
    public void emissionTest()
    {
        ImageWriter imageWriter = new ImageWriter("EmissionTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 1);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.8, 0.99, 1, 1, 5);

        // top left
        geometries.add(new Triangle(
                material,
                new Color(Color.GREEN.getRGB()),
                new Point3D(-100, 100, -500),
                new Point3D(-100, 0, -500),
                new Point3D(0, 100, -500)
        ));

        // top right
        geometries.add(new Triangle(
                material,
                new Color(0, 0, 0),
                new Point3D(100, 100, -500),
                new Point3D(100, 0, -500),
                new Point3D(0, 100, -500)
        ));

        // bottom left
        geometries.add(new Triangle(
                material,
                new Color(Color.RED.getRGB()),
                new Point3D(-100, -100, -500),
                new Point3D(-100, 0, -500),
                new Point3D(0, -100, -500)
        ));

        // bottom right
        geometries.add(new Triangle(
                material,
                new Color(Color.BLUE.getRGB()),
                new Point3D(100, -100, -500),
                new Point3D(100, 0, -500),
                new Point3D(0, -100, -500)
        ));

        geometries.add(new Sphere(
                material,
                new Color(0, 0, 0),
                50,
                new Point3D(0, 0, -500)
        ));

        Camera camera = new Camera(
                new Point3D(Point3D.ORIGIN),
                new Vector(new Point3D(0, 1, 0)),
                new Vector(new Point3D(0, 0, -1)),
                new Vector(new Point3D(1, 0, 0))
        );

        Scene scene = new Scene("RenderTest", Color.BLACK, ambientLight, geometries, lights, camera, 500);

        Renderer renderer = new Renderer(scene, imageWriter);
        renderer.renderImage(true);
    }
}