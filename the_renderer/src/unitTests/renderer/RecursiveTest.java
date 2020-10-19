package renderer;

import java.awt.Color;


import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Renderer;
import scene.Scene;

public class RecursiveTest {

    final String IMAGES_TEST_DIR = "/";

    @Test
    public void recursiveTest(){

        Scene scene = new Scene();
        scene.setScreenDistance(300);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0.5);
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(250, new Point3D(0.0, 0.0, -1000));
        sphere2.setNShininess(20);
        sphere2.setEmission(new Color(100, 20, 20));
        sphere2.setKt(0);
        scene.addGeometry(sphere2);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
                0.1, 0.00001, 0.000005, new Vector(new Point3D(2, 2, -3))));

        ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Recursive Test", 500, 500, 500, 500);

        Renderer render = new Renderer(scene, imageWriter);

        render.renderImage();
        //render.writeToImage();

    }


    @Test
    public void recursiveTest2(){

        Scene scene = new Scene();
        scene.setScreenDistance(300);

        Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0.5);
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000));
        sphere2.setNShininess(20);
        sphere2.setEmission(new Color(100, 20, 20));
        sphere2.setKt(0);
        scene.addGeometry(sphere2);

        Triangle triangle = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D(  200,  200, -375));

        Triangle triangle2 = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D( -1500, -1500, -1500));

        triangle.setEmission(new Color(20, 20, 20));
        triangle2.setEmission(new Color(20, 20, 20));
        triangle.setKr(1);
        triangle2.setKr(0.5);
        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),
                0, 0.00001, 0.000005, new Vector(new Point3D(-2, -2, -3))));

        ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR +"Recursive Test 2", 500, 500, 500, 500);

        Renderer render = new Renderer(scene, imageWriter);

        render.renderImage();
        //render.writeToImage();

    }

    @Test
    public void recursiveTest3(){

        Scene scene = new Scene();
        scene.setScreenDistance(300);

        Sphere sphere = new Sphere(300, new Point3D(0, 0, -1000));
        sphere.setNShininess(20);
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setKt(0.5);
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(150, new Point3D(0, 0, -1000));
        sphere2.setNShininess(20);
        sphere2.setEmission(new Color(100, 20, 20));
        sphere2.setKt(0);
        scene.addGeometry(sphere2);

        Triangle triangle = new Triangle(new Point3D(  2000, -1000, -1500),
                new Point3D( -1000,  2000, -1500),
                new Point3D(  700,  700, -375));

        Triangle triangle2 = new Triangle(new Point3D(  2000, -1000, -1500),
                new Point3D( -1000,  2000, -1500),
                new Point3D( -1000, -1000, -1500));

        triangle.setEmission(new Color(20, 20, 20));
        triangle2.setEmission(new Color(20, 20, 20));
        triangle.setKr(1);
        triangle2.setKr(0.5);
        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        scene.addLightSource(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),
                 0, 0.00001, 0.000005, new Vector(new Point3D(-2, -2, -3))));

        ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Recursive Test 3", 500, 500, 500, 500);

        Renderer render = new Renderer(scene, imageWriter);

        render.renderImage();
        //render.writeToImage();

    }

}
