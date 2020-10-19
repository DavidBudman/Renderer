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

// creates animation of light revolving around sphere
public class Animator
{
    static long numIntersections = 0;
    @Test
    public void renderAnimation()
    {
        /*for (int i = 1; i <= 30; i++)
        {
            numIntersections = 0;
            long startTime = System.currentTimeMillis();
            changeNumRays(i);
            long endTime = System.currentTimeMillis();
            System.out.println(i + " rays: " + numIntersections + " intersections.");
            System.out.println(i + " rays: " + (endTime - startTime) + " milliseconds.");
        }*/

        for (int i = 20; i < 100; i++)
        {
            System.out.println("Rendering image: " + i);
            changeAperture(i);
        }
    }

    @Test
    public void renderLightAnimation()
    {
        for (int i = -10; i < 10; i++)
        {
            moveLight(i);
        }
    }
    public void lightRotatingAroundSphere(int i, int numFrames)
    {
        ImageWriter imageWriter = new ImageWriter("/Sphere/" + i, 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.4, 0.4, 0.4, 0.4, 50);

        geometries.add(new Sphere(
                material,
                new Color(0, 0, 100),
                30,
                new Point3D(0, 0, -100)
        ));


        /*lights.add(new DirectionalLight(
                new Color(Color.GREEN.getRGB()),
                new Vector(new Point3D(
                        new Coordinate(Math.cos(i * 2 * Math.PI / numFrames)),
                        new Coordinate(0),
                        new Coordinate(Math.sin(i * 2 * Math.PI / numFrames))
                ))
        ));*/


        lights.add(new PointLight(
                new Color(255, 20, 255),
                new Point3D(
                        36 * Math.cos(i * 2 * Math.PI / numFrames),
                        8 * Math.cos(8 * i * 2 * Math.PI / numFrames),
                        -100 + 36 * Math.sin(i * 2 * Math.PI / numFrames)
                ),
                0.001, 0.001, 0.00001
        ));

        geometries.add(new Sphere(
                material,
                new Color(100, 0, 0),
                5,
                new Point3D(
                        36 * Math.cos(i * 2 * Math.PI / numFrames),
                        8 * Math.cos(8 * i * 2 * Math.PI / numFrames),
                        -100 + 36 * Math.sin(i * 2 * Math.PI / numFrames)
                )
        ));



        /*
        lights.add(new SpotLight(
                new Color(Color.RED.getRGB()),
                new Point3D(
                        new Coordinate(0),
                        new Coordinate(35),
                        new Coordinate(-65)
                ),
                0.001, 0.001, 0.00001,
                new Vector(new Point3D(
                        new Coordinate(0),
                        new Coordinate(Math.cos(i * 2 * Math.PI / numFrames)),
                        new Coordinate(0)
                ))
        ));*/

        Camera camera = new Camera(
                new Point3D(Point3D.ORIGIN),
                new Vector(new Point3D(0, 1, 0)),
                new Vector(new Point3D(0, 0, -1)),
                new Vector(new Point3D(1, 0, 0))
        );

        Scene scene = new Scene("RenderTest", Color.BLACK, ambientLight, geometries, lights, camera, 500);
        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage(false);
    }

    public void moveViewPlane(int i)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(i * 10);

        Sphere sphere = new Sphere(400, new Point3D(300, 0, -scene.getScreenDistance() - 500));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        ImageWriter imageWriter = new ImageWriter("/ViewPlane/" + i, 500, 500, 500, 500);
        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
    }

    public void moveTriangle(int i)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0);
        sphere.setKr(0);
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270));

        triangle.setP1(new Vector(triangle.getP1()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30 + 2 * i))).getHead());
        triangle.setP2(new Vector(triangle.getP2()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30 + 2 * i))).getHead());
        triangle.setP3(new Vector(triangle.getP3()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30 + 2 * i))).getHead());

        triangle.setEmission(new Color (0, 0, 100));
        triangle.setNShininess(4);
        triangle.setKs(0.6);
        triangle.setKd(0.6);
        triangle.setKt(0);
        triangle.setKr(0);
        scene.addGeometry(triangle);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
                0.1, 0.00001, 0.000005, new Vector(new Point3D(2, 2, -3))));

        ImageWriter imageWriter = new ImageWriter("/MoveTriangle/" + (i + 10), 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
        //render.writeToImage();
    }

    public void moveLight(int i)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0);
        sphere.setKr(0);
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270));

        triangle.setP1(new Vector(triangle.getP1()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30))).getHead());
        triangle.setP2(new Vector(triangle.getP2()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30))).getHead());
        triangle.setP3(new Vector(triangle.getP3()).add(new Vector(new Point3D(2, 2, -3)).normalize().mult(new Coordinate(30))).getHead());

        triangle.setEmission(new Color (0, 0, 100));
        triangle.setNShininess(4);
        triangle.setKs(0.6);
        triangle.setKd(0.6);
        triangle.setKt(0);
        triangle.setKr(0);
        scene.addGeometry(triangle);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100),
                new Point3D(
                    -200 + i * 2,
                    -200 + i * 2,
                    -150 + i * 2
                ),
                0.1, 0.00001, 0.000005, new Vector(new Point3D(2, 2, -3))));

        ImageWriter imageWriter = new ImageWriter("/MoveLight/" + (i + 10), 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
        //render.writeToImage();
    }

    public void changeAperture(int frame)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        scene.setAperture(100 * (frame + 1));
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
                    newSphere.setEmission(new Color(0, 200, 200));
                    newSphere.setKt(0);
                    newSphere.setKr(0);
                    scene.addGeometry(newSphere);
                }
            }
        }

        scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(0, 0, -600),
                0.1, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("/ChangeAperture/" + frame, 500, 500, 500, 500);
        Renderer renderer = new Renderer(scene, imageWriter);
        renderer.renderImage();
    }

    public void changeFocalDistance(int distance)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        scene.setAperture(20);
        scene.setFocalDistance(500 + distance * 5);

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

        /*Plane plane = new Plane(new Point3D(0, 0, -distance - 200), scene.getCamera().getVTo());
        plane.setNShininess(1);
        plane.setEmission(new Color(0, 150, 0));

        plane.setKd(0.1);
        plane.setKs(0);
        plane.setKr(0);
        plane.setKt(0.9);
        scene.addGeometry(plane);*/

        scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(0, 0, -600),
                0.1, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("/ChangeFocalDistance/" + distance, 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
    }

    public void changeNumRays(int numRays)
    {
        Scene scene = new Scene();
        scene.setScreenDistance(200);
        scene.setAperture(2000);
        scene.setFocalDistance(500 + 10 * 5);
        scene.setNumCameraRays(numRays);

        Sphere sphere = new Sphere(30, new Point3D(0.0, 0.0, -800));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0.5);
        sphere.setKr(0.5);
        scene.addGeometry(sphere);

        for (int i = 1; i <= 2; i++)
        {
            for (int j = -1; j <= 1; j += 2)
            {
                for (int k = -1; k <= 1; k += 2)
                {
                    Sphere newSphere = new Sphere(30, new Point3D(i * j * 50, i * k * 50, -800 + i * 50));
                    newSphere.setNShininess(20);
                    newSphere.setEmission(new Color(0, 200, 200));
                    newSphere.setKt(0.5);
                    newSphere.setKr(0.5);
                    scene.addGeometry(newSphere);
                }
            }
        }

        scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(0, 0, -600),
                0.1, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("/ChangeNumCameraRays/" + numRays, 500, 500, 500, 500);
        Renderer renderer = new Renderer(scene, imageWriter);
        renderer.renderImage();
    }
} // end class Animator
