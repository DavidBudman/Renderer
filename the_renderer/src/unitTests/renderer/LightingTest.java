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


public class LightingTest {

    private final String IMAGES_TEST_DIR = "/";

    @Test
	public void emissionTest(){

		Scene scene = new Scene();
		scene.setScreenDistance(50);
		scene.addGeometry(new Sphere(50, new Point3D(0.0, 0.0, -50)));
		
		Triangle triangle = new Triangle(new Point3D( 100, 0, -49),
				 						 new Point3D(  0, 100, -49),
				 						 new Point3D( 100, 100, -49));
		
		Triangle triangle2 = new Triangle(new Point3D( 100, 0, -49),
				 			 			  new Point3D(  0, -100, -49),
				 			 			  new Point3D( 100,-100, -49));
		triangle2.setEmission(new Color (50, 200, 50));
		
		Triangle triangle3 = new Triangle(new Point3D(-100, 0, -49),
				 						  new Point3D(  0, 100, -49),
				 						  new Point3D(-100, 100, -49));
		triangle3.setEmission(new Color (50, 50, 200));
		
		Triangle triangle4 = new Triangle(new Point3D(-100, 0, -49),
				 			 			  new Point3D(  0,  -100, -49),
				 			 			  new Point3D(-100, -100, -49));
		triangle4.setEmission(new Color (200, 50, 50));
		
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		scene.addGeometry(triangle3);
		scene.addGeometry(triangle4);
		
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Emission test", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//render.printGrid(50);
		//render.writeToImage();
	}
	
	
	
	@Test
	public void spotLightTest2(){
		
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
		
		scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
					   0.1, 0.00001, 0.000005, new Vector(new Point3D(2, 2, -3))));
	
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Spot test 2", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);

		renderer.renderImage();
		//renderer.renderImageOriginal(false);
		//render.writeToImage();
	}
	
	
	@Test
	public void spotLightTest(){
		
		Scene scene = new Scene();
		Sphere sphere = new Sphere(800, new Point3D(0.0, 0.0, -1000));
		sphere.setNShininess(20);
		sphere.setEmission(new Color(0, 0, 100));
		scene.addGeometry(sphere);
		scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -100),
					   0, 0.00001, 0.000005, new Vector(new Point3D(2, 2, -3))));
	
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Spot test", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//renderer.writeToImage();
		
	}

	 
	@Test
	public void pointLightTest(){
		
		Scene scene = new Scene();
		Sphere sphere = new Sphere (800, new Point3D(0.0, 0.0, -1000));
		sphere.setNShininess(20);
		sphere.setEmission(new Color(0, 0, 100));
		scene.addGeometry(sphere);
		scene.addLightSource(new PointLight(new Color(255,100,100), new Point3D(-200, -200, -100),
					   0, 0.00001, 0.000005));
	
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Point test", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//renderer.writeToImage();
		
		
	}
	
	@Test
	public void spotLightTest3(){
		
		Scene scene = new Scene();
		
		Triangle triangle = new Triangle(new Point3D(  3500,  3500, -2000),
				 						 new Point3D( -3500, -3500, -1000),
				 						 new Point3D(  3500, -3500, -2000));

		Triangle triangle2 = new Triangle(new Point3D(  3500,  3500, -2000),
				  						  new Point3D( -3500,  3500, -1000),
				  						  new Point3D( -3500, -3500, -1000));
		
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		
		scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100),
					   0, 0.000001, 0.0000005, new Vector(new Point3D(-2, -2, -3))));
	
		
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "ISpot test 3", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//renderer.writeToImage();
		
	}
	
	@Test
	public void pointLightTest2(){
		
		Scene scene = new Scene();
		Sphere sphere = new Sphere(800, new Point3D(0.0, 0.0, -1000));
		sphere.setNShininess(20);
		sphere.setEmission(new Color(0, 0, 100));
		
		Triangle triangle = new Triangle(new Point3D(  3500,  3500, -2000),
				 						 new Point3D( -3500, -3500, -1000),
				 						 new Point3D(  3500, -3500, -2000));

		Triangle triangle2 = new Triangle(new Point3D(  3500,  3500, -2000),
				  						  new Point3D( -3500,  3500, -1000),
				  						  new Point3D( -3500, -3500, -1000));
		
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		
		scene.addLightSource(new PointLight(new Color(255, 100, 100), new Point3D(200, 200, -100),
					   0, 0.000001, 0.0000005));
	
		
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "Point test 2", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//renderer.writeToImage();
		
	}
	
	@Test
	public void shadowTest(){
		
		Scene scene = new Scene();
		Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
		sphere.setNShininess(20);
		sphere.setEmission(new Color(0, 0, 100));
		
		//scene.addGeometry(sphere);
		
		Triangle triangle = new Triangle(new Point3D(  3500,  3500, -2000),
				 						 new Point3D( -3500, -3500, -1000),
				 						 new Point3D(  3500, -3500, -2000));

		Triangle triangle2 = new Triangle(new Point3D(  3500,  3500, -2000),
				  						  new Point3D( -3500,  3500, -1000),
				  						  new Point3D( -3500, -3500, -1000));
		
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);
		
		scene.addLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100),
				   0, 0.000001, 0.0000005, new Vector(new Point3D(-2, -2, -3))));
	
		
		ImageWriter imageWriter = new ImageWriter(IMAGES_TEST_DIR + "shadow test", 500, 500, 500, 500);
		
		Renderer renderer = new Renderer(scene, imageWriter);
		
		renderer.renderImage();
		//renderer.writeToImage();
		
	}

}
