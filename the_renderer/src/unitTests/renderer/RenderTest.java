package renderer;


import elements.*;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderTest
{
    @Test
    public void spherePointLightTest()
    {
        ImageWriter imageWriter = new ImageWriter("SpherePointLightTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.8, 1, 1, 1, 50);

        geometries.add(new Sphere(
                material,
                new Color(0, 0, 100),
                30,
                new Point3D(0, 0, -100)
        ));


//        lights.add(new PointLight(
//                new Color(255, 20, 255),
//                new Point3D(-30, -30, -65),
//                0.001, 0.001, 0.00001
//        ));

        lights.add(new PointLight(
                new Color(255, 20, 255),
                new Point3D(-5, -5, -65),
                0.001, 0.001, 0.00001
        ));


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

    @Test
    public void sphereSpotLightTest()
    {
        ImageWriter imageWriter = new ImageWriter("SphereSpotLightTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.75, 1, 1, 1, 50);

        geometries.add(new Sphere(
                material,
                new Color(0, 0, 100),
                30,
                new Point3D(0, 0, -100)
        ));

//        lights.add(new SpotLight(
//                new Color(255, 20, 255),
//                new Point3D(-30, -30, -65),
//                0.001, 0.001, 0.00001,
//                new Vector(new Point3D(1, 1, -1)).normalize()
//        ));

        lights.add(new SpotLight(
                new Color(255, 20, 255),
                new Point3D(-5, -5, -65),
                0.001, 0.001, 0.00001,
                new Vector(new Point3D(0, 0, -1)).normalize()
        ));


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

    @Test
    public void sphereOnPlaneTest()
    {
        ImageWriter imageWriter = new ImageWriter("SphereOnPlaneTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.8, 0.99, 1, 1, 50);

        geometries.add(new Plane(
                material,
                new Color(20, 20, 20),
                new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-100)),
                new Vector(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(1)))
        ));

        geometries.add(new Sphere(
                material,
                new Color(0, 0, 100),
                30,
                new Point3D(0, 0, -100)
        ));

        /*
        lights.add(new DirectionalLight(
                new Color(Color.GREEN.getRGB()),
                new Vector(new Point3D(
                        new Coordinate(-1),
                        new Coordinate(0),
                        new Coordinate(0)
                ))
        ));
        */

        lights.add(new PointLight(
                new Color(255, 20, 255),
                new Point3D(
                        new Coordinate(0),
                        new Coordinate(0),
                        new Coordinate(-65)
                ),
                0.001, 0.001, 0.00001
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
                        new Coordinate(-1),
                        new Coordinate(0)
                ))
        ));
        */
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

    @Test
    public void planeTest()
    {
        ImageWriter imageWriter = new ImageWriter("PointLightTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(20, 20, 20), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.3, 5, 1, 1, 75);

        geometries.add(new Triangle(
                material,
                new Color(20, 20, 20),
                new Point3D(new Coordinate(-10), new Coordinate(-10), new Coordinate(-25)), // bottom left
                new Point3D(new Coordinate(30), new Coordinate(30), new Coordinate(-100)), // top right
                new Point3D(new Coordinate(-30), new Coordinate(30), new Coordinate(-100)) // top left
        ));

        geometries.add(new Triangle(
                material,
                new Color(20, 20, 20),
                new Point3D(new Coordinate(-10), new Coordinate(-10), new Coordinate(-25)),
                new Point3D(new Coordinate(10), new Coordinate(-10), new Coordinate(-25)),
                new Point3D(new Coordinate(30), new Coordinate(30), new Coordinate(-100))
        ));


//        geometries.add(new Sphere(
//                material,
//                new Color(255, 0, 0),
//                3,
//                new Point3D(0, 6, -40)
//        ));

        lights.add(new PointLight(
                new Color(255, 200, 200),
                new Point3D(0, 5, -42),
                0.01, 0.001, 0.0001
        ));


//        lights.add(new SpotLight(
//                new Color(255, 200, 200),
//                new Point3D(0, 5, -42),
//                0.01, 0.001, 0.0001,
//                new Vector(new Point3D(0, -0.88235, -0.47058))
//        ));

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

    @Test
    public void closerViewPlaneTest()
    {
        ImageWriter imageWriter = new ImageWriter("closerViewPlaneTest", 500, 500, 500, 500);
        AmbientLight ambientLight = new AmbientLight(new Color(Color.DARK_GRAY.getRGB()), 0.25);
        List<Geometry> geometries = new ArrayList<Geometry>();
        List<LightSource> lights = new ArrayList<LightSource>();
        Material material = new Material(0.8, 1, 1, 1, 50);

//        geometries.add(new Sphere(
//                material,
//                new Color(0, 0, 100),
//                10,
//                new Point3D(0, 0, -50)
//        ));

        // left wall
        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, 250, -100),
                new Point3D(-250, -250, -100),
                new Point3D(-250, 250, -400)
        ));

        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, -250, -100),
                new Point3D(-250, -250, -400),
                new Point3D(-250, 250, -400)
        ));

        // bottom wall
        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, -250, -100),
                new Point3D(250, -250, -100),
                new Point3D(-250, -250, -400)
        ));

        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(250, -250, -100),
                new Point3D(250, -250, -400),
                new Point3D(-250, -250, -400)
        ));

        // right wall
        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(250, -250, -100),
                new Point3D(250, 250, -100),
                new Point3D(250, 250, -400)
        ));

        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(250, -250, -100),
                new Point3D(250, 250, -400),
                new Point3D(250, -250, -400)
        ));

        // top wall
        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, 250, -100),
                new Point3D(-250, 250, -400),
                new Point3D(250, 250, -100)
        ));

        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, 250, -400),
                new Point3D(250, 250, -400),
                new Point3D(250, 250, -100)
        ));

        // back wall
        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, -250, -400),
                new Point3D(250, 250, -400),
                new Point3D(-250, 250, -400)
        ));

        geometries.add(new Triangle(
                material,
                new Color(200, 200, 200),
                new Point3D(-250, -250, -400),
                new Point3D(250, -250, -400),
                new Point3D(250, 250, -400)
        ));

        lights.add(new DirectionalLight(
                new Color(0, 0, 255),
                new Vector(new Point3D(-1, 0, 0))
        ));

        Camera camera = new Camera(
                new Point3D(Point3D.ORIGIN),
                new Vector(new Point3D(0, 1, 0)),
                new Vector(new Point3D(0, 0, -1)),
                new Vector(new Point3D(1, 0, 0))
        );

        Scene scene = new Scene("RenderTest", Color.BLACK, ambientLight, geometries, lights, camera, 100);
        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage(false);
    }

    @Test
    public void occlusionTest()
    {
        Material material = new Material(0.8, 1, 1, 1, 50);
        Renderer renderer = new Renderer("occlusionTest");
        Scene scene = renderer.getScene();
        Point3D triangleCenter = new Point3D(-1501, -1501, -1098);

        scene.addGeometry(new Sphere(
                material,
                new Color(128, 128, 128),
                2000,
                new Point3D(0, 0, -2600)
        ));
        /*scene.addGeometry(new Sphere(
                material,
                new Color(255, 0, 0),
                100,
                triangleCenter
        ));*/

        double epsilon = 100;
        scene.addGeometry(new Triangle(
                new Point3D(-1501 + epsilon, -1501 - epsilon, -1098 + epsilon),
                new Point3D(-1501 - epsilon, -1501 + epsilon, -1098 + epsilon),
                new Point3D(-1501 - epsilon, -1501 - epsilon, -1098 - epsilon)
        ));

        scene.addLightSource(new DirectionalLight(
                new Color(255, 0, 255),
                new Vector(new Point3D(1, 1, -1)).normalize()
        ));
        scene.addLightSource(new PointLight(
                new Color(255, 0, 0),
                new Point3D(0, 0, -595),
                0.1, 0.0001, 0.00001
        ));

        renderer.renderImage();
    }

    @Test
    public void refractionTest()
    {
        Scene scene = new Scene();


        Sphere outerSphere = new Sphere(800, new Point3D(0.0, 0.0, -1000));
        outerSphere.setNShininess(20);
        outerSphere.setEmission(new Color(0, 0, 100));
        outerSphere.setKd(0.7);
        outerSphere.setKs(0.3);
        outerSphere.setKr(0);
        outerSphere.setKt(1);
        scene.addGeometry(outerSphere);

        Sphere innerSphere = new Sphere(500, new Point3D(0, 0, -1000));
        innerSphere.setNShininess(20);
        innerSphere.setEmission(new Color(255, 100, 100));
        outerSphere.setKd(0.7);
        outerSphere.setKs(0.3);
        innerSphere.setKr(0.8);
        innerSphere.setKt(0.2);
        scene.addGeometry(innerSphere);

        scene.addLightSource(new PointLight(new Color(255, 0, 0), new Point3D(-150, -150, -150),
                0, 0.00001, 0.000001));


        ImageWriter imageWriter = new ImageWriter("Refraction Test", 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
    }

    @Test
    public void refractionTestMirror()
    {
        Scene scene = new Scene();
        //scene.setScreenDistance(1000);

        Triangle triangle = new Triangle(new Point3D(  3500,  3500, -2000),
                new Point3D( -3500, -3500, -1000),
                //new Point3D(  3500, -3500, -2000));
                new Point3D(3000, -3000, -1500));
        triangle.setKr(1);
        triangle.setKt(0);

        Triangle triangle2 = new Triangle(new Point3D(  3500,  3500, -2000),
                new Point3D( -2000,  3500, -100),
                new Point3D( -3500, -3500, -1000));
        triangle.setKr(1);
        triangle.setKt(0);

        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        Sphere sphere = new Sphere(200, new Point3D(500, 0, -800));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKr(0.9);
        sphere.setKt(0);
        scene.addGeometry(sphere);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100),
                0, 0.000001, 0.0000005, new Vector(new Point3D(-2, -2, -3))));

        ImageWriter imageWriter = new ImageWriter("Refraction Test Mirror", 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();

    }

    @Test
    public void refractionTestSmaller()
    {
        Scene scene = new Scene();
        scene.setScreenDistance(1000);


        //Sphere outerSphere = new Sphere(200, new Point3D(0.0, 0.0, -1000 - 200));
        Sphere outerSphere = new Sphere(100, new Point3D(0, 0, -700));
        outerSphere.setNShininess(20);
        outerSphere.setEmission(new Color(0, 0, 150));
        outerSphere.setKd(1);
        outerSphere.setKs(0);
        outerSphere.setKr(0.7);
        outerSphere.setKt(0.7);
        scene.addGeometry(outerSphere);

        Sphere innerSphere = new Sphere(40, new Point3D(0, 0, -700));
        innerSphere.setNShininess(20);
        innerSphere.setEmission(new Color(255, 100, 100));
        outerSphere.setKd(1);
        outerSphere.setKs(0.3);
        innerSphere.setKr(0.9);
        innerSphere.setKt(0.5);
        //scene.addGeometry(innerSphere);

        scene.addLightSource(new PointLight(new Color(255, 255, 255), new Point3D(-50, -50, -600),
                //0.000001, 0.0000001, 0.0000000001));
                0, 0, 0.000000001));


        ImageWriter imageWriter = new ImageWriter("Refraction Test Smaller", 500, 500, 500, 500);

        Renderer renderer = new Renderer(scene, imageWriter);

        renderer.renderImage();
    }

    @Test
    public void refractionPlaneTest() {
        Scene scene = new Scene();
        scene.setScreenDistance(1000);
        Plane plane = new Plane(new Point3D(0, 0, -1100), new Vector(new Point3D(0, 0, 1)));
        plane.setNShininess(5);
        plane.setEmission(new Color(0, 0, 100));
        plane.setKr(0.8);
        plane.setKt(0);
        scene.addGeometry(plane);

        Sphere sphere = new Sphere(50, new Point3D(200, 0, -1000));
        sphere.setNShininess(5);
        sphere.setEmission(new Color(0, 255, 0));
        sphere.setKt(0);
        scene.addGeometry(sphere);

        //scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(-200, 0, -1000),
        //0, 0.0001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("Refraction Plane Test", 500, 500, 500, 500);
        Renderer renderer = new Renderer(scene, imageWriter);
        renderer.renderImage();
    }


}
