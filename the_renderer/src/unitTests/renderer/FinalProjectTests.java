package renderer;


import elements.*;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FinalProjectTests
{
    @Test
    public void sphereField()
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        scene.setAperture(5);
        scene.setFocalDistance(1500);

        Sphere sphere = new Sphere(30, new Point3D(0.0, 0.0, -2000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0);
        sphere.setKr(0);
        scene.addGeometry(sphere);

        for (int i = 1; i <= 2; i++)
        {
            for (int j = -1; j <= 1; j += 2)
            {
                for (int k = -1; k <= 1; k += 2)
                {
                    Sphere newSphere = new Sphere(30, new Point3D(i * j * 50, i * k * 50, -2000 + i * 50));
                    newSphere.setNShininess(20);
                    newSphere.setEmission(new Color(0, 0, 100));
                    newSphere.setKt(0);
                    newSphere.setKr(0);
                    scene.addGeometry(newSphere);
                }
            }
        }

        scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(0, 0, -1800),
                0.1, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("/sphereField", 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
    }

    @Test
    public void numRaysTest()
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        scene.setAperture(50);
        scene.setFocalDistance(550);
        scene.setNumCameraRays(30);

        Sphere sphere = new Sphere(30, new Point3D(0.0, 0.0, -800));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0);
        sphere.setKr(0);
        scene.addGeometry(sphere);

        for (int i = 1; i <= 2; i++)
        {
            for (int j = -1; j <= 1; j += 2)
            {
                for (int k = -1; k <= 1; k += 2)
                {
                    Sphere newSphere = new Sphere(30, new Point3D(i * j * 50, i * k * 50, -800 + i * 50));
                    newSphere.setNShininess(20);
                    newSphere.setEmission(new Color(0, 0, 100));
                    newSphere.setKt(0);
                    newSphere.setKr(0);
                    scene.addGeometry(newSphere);
                }
            }
        }

        scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(0, 0, -600),
                0.1, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("finalProjectTest", 500, 500, 500, 500);
        Renderer renderer = new Renderer(scene, imageWriter);
        renderer.renderImage();
    }
}
