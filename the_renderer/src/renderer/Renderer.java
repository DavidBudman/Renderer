package renderer;

import elements.LightSource;
import geometries.Geometry;
import geometries.Triangle;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
import java.util.*;
import java.util.List;

// Renderer class used to render scenes using ImageWriter
public class Renderer
{
    private Scene _scene; // the scene to render
    private ImageWriter _imageWriter; // used to write images to file
    private final int RECURSION_LEVEL = 2;

    // full constructor
    public Renderer(Scene scene, ImageWriter imageWriter)
    {
        _scene = scene;
        _imageWriter = imageWriter;
    } // end full constructor

    // default constructor
    public Renderer(String name)
    {
        this(
                new Scene(),
                new ImageWriter(name, 500, 500, 500, 500)
        );
    } // end default constructor

    // render the image
    public void renderImage(boolean showGrid)
    {
        int pixelSize = _imageWriter.getWidth() / _imageWriter.getNx();
        double aperture = _scene.getCamera().getAperture();
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        double screenDistance = _scene.getScreenDistance();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        int numCameraRays = _scene.getCamera().getNumRays();

        for (int row = 0; row < _imageWriter.getNy(); row++)
        {
            //if (row % 10 == 0) System.out.println("Row: " + row);
            for (int col = 0; col < _imageWriter.getNx(); col++) {
                Point3D viewPlanePoint = _scene.getCamera().pixelToPoint(Nx, Ny, col, row, screenDistance, width, height);
                Point3D focalPlanePoint = viewPlanePoint.add(_scene.getCamera()._vTo.mult(new Coordinate(_scene.getCamera().getFocalDistance())));
                List<Ray> rays = generateCameraRaysCircle(viewPlanePoint, focalPlanePoint, aperture, numCameraRays);
                List<Color> colors = new ArrayList<Color>();
                for (Ray ray : rays)
                {
                    Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                    if (intersectionPoints.isEmpty()) {
                        colors.add(_scene.getBackground());
                    } else {
                        Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
                        colors.add(calcColor(closestPoint, ray));
                    }
                }
                printSquare(col * pixelSize, row * pixelSize, pixelSize, averageColor(colors));
            } // end col for
        } // end row for

        if (showGrid) printGrid(50);
        _imageWriter.writeToImage();
    } // end method renderImage

    /*
     * returns list of numRays camera rays
     * origin of each ray on circle on X/Y plane with center: viewPlanePoint and radius: aperture
     * each ray passes through focalPlanePoint
     */
    public List<Ray> generateCameraRaysCircle(Point3D viewPlanePoint, Point3D focalPlanePoint,
                                              double aperture, int numRays)
    {
        // list of rays
        List<Ray> rayList = new ArrayList<Ray>();

        for (double theta = 2 * Math.PI / numRays; theta <= 2 * Math.PI; theta += 2 * Math.PI / numRays) {
            // move rayOrigin to be on circle with origin: viewPlanePoint, radius: aperture
            Vector radius = new Vector(new Point3D(Math.cos(theta), Math.sin(theta), 0));
            radius = radius.mult(new Coordinate(aperture));
            Point3D rayOrigin = viewPlanePoint.add(radius);

            // add to list ray originating at rayOrigin and passing through focalPlanePoint
            rayList.add(new Ray(rayOrigin, new Vector(rayOrigin, focalPlanePoint)));
        }

        return rayList;
    }

    public List<Ray> generateCameraRaysSquare(Point3D viewPlanePoint, Point3D focalPlanePoint, double aperture)
    {
        List<Ray> result = new ArrayList<Ray>();

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                Point3D point = new Point3D(viewPlanePoint);
                point.setX(point.getX().add(new Coordinate(i * aperture)));
                point.setY(point.getY().add(new Coordinate(j * aperture)));
                result.add(new Ray(point, new Vector(point, focalPlanePoint)));
            } // end j for
        } // end i for

        return result;
    }

    // render the image
    public void renderImageOriginal(boolean showGrid)
    {
        int pixelSize = _imageWriter.getWidth() / _imageWriter.getNx();

        for (int row = 0; row < _imageWriter.getNy(); row++)
        {
            for (int col = 0; col < _imageWriter.getNx(); col++)
            {
                Ray ray = _scene.getCamera().constructRayThroughPixel(
                        _imageWriter.getNx(), _imageWriter.getNy(),
                        col, row, _scene.getScreenDistance(),
                        _imageWriter.getWidth(), _imageWriter.getHeight());
                Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                if (intersectionPoints.isEmpty())
                {
                    printSquare(col * pixelSize, row * pixelSize, pixelSize, _scene.getBackground());
                }
                else
                {
                    Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
                    printSquare(col * pixelSize, row * pixelSize, pixelSize, calcColor(closestPoint, ray));
                }
            } // end col for
        } // end row for

        if (showGrid) printGrid(50);
        _imageWriter.writeToImage();
    } // end method renderImageOriginal

    // default renderImage
    public void renderImage()
    {
        renderImage(false);
    } // end method renderImage

    // prints size x size square of pixels starting at point (x, y) with given color
    private void printSquare(int x, int y, int size, Color color)
    {
        for (int row = y; row < y + size; row++)
        {
            for (int column = x; column < x + size; column++)
            {
                _imageWriter.writePixel(column, row, color);
            }
        }
    } // end method printSquare

    // print grid over image with squares of size "interval" pixels
    private void printGrid(int interval)
    {
        // draw grid rows
        for (int i = 0; i < _imageWriter.getHeight() / interval; i++)
        {
            for (int j = 0; j < _imageWriter.getWidth(); j++)
            {
                _imageWriter.writePixel(i * interval, j, Color.WHITE);
                _imageWriter.writePixel(i * interval + (interval - 1), j, Color.WHITE);
            }
        }

        // draw grid columns
        for (int i = 0; i < _imageWriter.getWidth() / interval; i++)
        {
            for (int j = 0; j < _imageWriter.getHeight(); j++)
            {
                _imageWriter.writePixel(j, i * interval, Color.WHITE);
                _imageWriter.writePixel(j, i * interval + (interval - 1), Color.WHITE);
            }
        }
    } // end method printGrid

    // returns average color of list of colors
    private Color averageColor(List<Color> colors)
    {
        // initialize color to 0s
        double r = 0, g = 0, b = 0;

        // sum color components
        for (Color color : colors)
        {
            r += color.getRed();
            g += color.getGreen();
            b += color.getBlue();
        }

        // average color components
        r /= colors.size();
        g /= colors.size();
        b /= colors.size();

        // return averaged color
        return new Color((int)r, (int)g, (int)b);
    }

    // getters
    public Scene getScene()
    {
        return _scene;
    } // does not return a copy

    // get all the intersections in the scene from a ray
    private Map<Geometry, List<Point3D>> getSceneRayIntersections(Ray ray)
    {
        // iterator for iterating over the geometries
        Iterator<Geometry> geometries = _scene.getGeometriesIterator();

        // resulting map of geometries and the list of intersections through the geometries
        Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();

        // iterate through geometries and add intersections to intersectionPoints
        while (geometries.hasNext())
        {
            Geometry geometry = geometries.next();
            List<Point3D> geometryIntersectionPoints = geometry.findIntersections(ray);
            if (!geometryIntersectionPoints.isEmpty())
            {
                intersectionPoints.put(geometry, geometryIntersectionPoints);
            } // end if
        } // end while

        return intersectionPoints;
    } // end method getSceneRayIntersections

    // get closest <Geometry, Point3D> pair from Map of geometries and points
    private Map<Geometry, Point3D> getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints, Point3D excludedPoint)
    {
        Coordinate distance = new Coordinate(Double.MAX_VALUE);
        Point3D P0 = _scene.getCamera().getP0();
        Map<Geometry, Point3D> minDistancePoint = new HashMap<Geometry, Point3D>();

        // iterate through points and pick point with smallest distance to camera
        for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
        {
            for (Point3D point : entry.getValue()) {
                if (excludedPoint != null && pointsEqual(point, excludedPoint)) continue;
                //if (point.equals(excludedPoint)) continue;
                if (P0.distance(point).compareTo(distance) == -1) {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(), new Point3D(point));
                    distance = P0.distance(point);
                } // end if
            }
        } // end for

        return minDistancePoint;
    } // end method getClosestPoint

    // default getClosestPoint
    private Map<Geometry, Point3D> getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints)
    {
        return getClosestPoint(intersectionPoints, null);
    } // end method getClosestPoint

    // find closest geometry intersection not including ray head
    private Map<Geometry, Point3D> findClosestIntersection(Ray ray)
    {
        return getClosestPoint(getSceneRayIntersections(ray), ray.getP00());
    } // end method findClosestIntersection

    // calculate color at given point given geometry
    private Color calcColor(Geometry geometry, Point3D point, Ray inRay, int level)
    {
        // base case for recursive rays
        if (level == RECURSION_LEVEL) return new Color(0, 0, 0);

        Color ambientLight = _scene.getAmbientLight().getIntensity();
        Color emissionLight = geometry.getEmission();
        Color diffuseLight = new Color(0, 0, 0);
        Color specularLight = new Color(0, 0, 0);

        // add diffuse and specular lights for every light
        Iterator<LightSource> lights = _scene.getLightsIterator();
        while (lights.hasNext()) {
            LightSource light = lights.next();

            if (occluded(light, point, geometry)) continue;

            diffuseLight = addColors(diffuseLight, calcDiffuseLight(
                    geometry.getMaterial().getKd(),
                    geometry.getNormal(point),
                    light.getL(point).negative(),
                    light.getIntensity(point)
            ));
            specularLight = addColors(specularLight, calcSpecularLight(
                    geometry.getMaterial().getKs(),
                    new Vector(point, _scene.getCamera().getP0()).normalize(),
                    geometry.getNormal(point),
                    light.getL(point),
                    geometry.getMaterial().getNShininess(),
                    light.getIntensity(point)
            ));
        } // end while

        Color reflectedLight = calcReflectedLight(geometry, point, inRay, level);

        // calculate refracted light
        Ray refractedRay = constructRefractedRay(point, inRay);
        Map<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);
        Color refractedLight;
        if (refractedEntry.isEmpty()) {
            refractedLight = new Color(0, 0, 0);
        } else {
            Color refractedColor = calcColor(refractedEntry, refractedRay, level + 1);
            double Kt = ((Geometry)refractedEntry.keySet().toArray()[0]).getMaterial().getKt();
            refractedLight = scaleColor(Kt, refractedColor);
        }

        // put colors in a list
        List<Color> colors = new ArrayList<Color>();
        colors.add(ambientLight);
        colors.add(emissionLight);
        colors.add(diffuseLight);
        colors.add(specularLight);
        colors.add(reflectedLight);
        colors.add(refractedLight);

        // return the sum of the colors in the list
        return addColors(colors);
    } // end calcColor

    // calculate color at point given geometry
    private Color calcColor(Map<Geometry, Point3D> geometryPoint3DMap, Ray inRay, int level)
    {
        if (geometryPoint3DMap.isEmpty()) return new Color(0, 0, 0);
        Geometry geometry = (Geometry)geometryPoint3DMap.keySet().toArray()[0];
        return calcColor((Geometry)geometryPoint3DMap.keySet().toArray()[0],
                (Point3D)geometryPoint3DMap.values().toArray()[0],
                inRay, level);
    } // end method calcColor

    // default calcColor 1
    private Color calcColor(Geometry geometry, Point3D point, Ray inRay)
    {
        return calcColor(geometry, point, inRay, 0);
    } // end method calcColor

    // default calcColor 2
    private Color calcColor(Map<Geometry, Point3D> geometryPoint3DMap, Ray inRay)
    {
        return calcColor(geometryPoint3DMap, inRay, 0);
    }

    private Color calcReflectedLight(Geometry geometry, Point3D point, Ray inRay, int level)
    {
        // calculate reflected light
        Ray reflectedRay = constructReflectedRay(geometry.getNormal(point), point, inRay);

        // no reflection ray
        if (reflectedRay == null) return new Color(0, 0, 0);

        Map<Geometry, Point3D> reflectedEntry = findClosestIntersection(reflectedRay);

        // no reflection intersection
        if (reflectedEntry.isEmpty()) return new Color(0, 0, 0);

        Color reflectedColor = calcColor(reflectedEntry, reflectedRay, level + 1);
        double Kr = ((Geometry) reflectedEntry.keySet().toArray()[0]).getMaterial().getKr();

        return scaleColor(Kr, reflectedColor);
}

    // check if point is occluded
    private boolean occluded(LightSource lightSource, Point3D point, Geometry geometry)
    {
        // if ray is on the wrong side of face, don't show light
        // if ((N*L) * (N*C) <= 0) return true
        Vector normal = geometry.getNormal(point);
        Vector L = lightSource.getL(point).negative();
        Coordinate normalDotL = geometry.getNormal(point).dot(lightSource.getL(point).negative());
        Coordinate normalDotCamera = geometry.getNormal(point).dot(new Vector(point, _scene.getCamera().getP0()));
        Coordinate product = normalDotL.mult(normalDotCamera);

        /*if (geometry.getNormal(point).dot(lightSource.getL(point).negative())
                //.mult(geometry.getNormal(point).dot(new Vector(_scene.getCamera().getP0(), point)))
                .mult(geometry.getNormal(point).dot(new Vector(point, _scene.getCamera().getP0())))
                .compareTo(Coordinate.ZERO) != 1)
        {
            return true;
        }*/
        if (product.compareTo(Coordinate.ZERO) != 1) return true;

        // don't allow light to travel through geometries
        Ray ray = new Ray(point, lightSource.getL(point).negative());
        Map<Geometry, List<Point3D>> sceneIntersections = getSceneRayIntersections(ray);
        for (Map.Entry<Geometry, List<Point3D>> entry : sceneIntersections.entrySet()) {
            Geometry g = entry.getKey();
            // check if this point of intersection is the head of the ray
            if (g.equals(geometry) && entry.getValue().size() == 1) continue;
            if (g.getMaterial().getKt() == 0) return true;
        }

        return false;
    } // end method occluded

    // calculate diffuse light at point = Kd(N * L)I_L
    private Color calcDiffuseLight(double Kd, Vector normal, Vector L, Color lightIntensity)
    {
        double dot = normal.dot(L).getCoordinate();
        double scalar = Kd * dot;
        return scaleColor(scalar, lightIntensity);
    } // end method calcDiffuseLight

    // calculate specular light at point = Ks((V * R)^n)I_L
    private Color calcSpecularLight(double Ks, Vector V, Vector normal, Vector L, int nShininess, Color lightIntensity)
    {
        // R = L - 2(L * N)N
        Vector R = L.subtract(normal.mult(L.dot(normal).mult(2)));

        double dot = V.dot(R).getCoordinate();
        return scaleColor(Ks * Math.pow(dot, nShininess), lightIntensity);
    } // end method calcSpecularLight

    // adds all the colors in inputted list
    private Color addColors(List<Color> colors)
    {
        int red = 0;
        int green = 0;
        int blue = 0;

        // add red, green, and blue values across list of colors
        for (Color color : colors)
        {
            red += color.getRed();
            green += color.getGreen();
            blue += color.getBlue();
        }

        return new Color(
                Math.min(red, 255),
                Math.min(green, 255),
                Math.min(blue, 255)
        );
    } // end method addColors

    // add two colors
    private Color addColors(Color color1, Color color2)
    {
        List<Color> colors = new ArrayList<Color>();
        colors.add(color1);
        colors.add(color2);
        return addColors(colors);
    } // end method addColors

    // scale color
    private Color scaleColor(double scalar, Color color)
    {
        if (scalar < 0) {
            scalar = 0;
        }
        return new Color(
                (float)Math.min(scalar * (color.getRed() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getGreen() / 255.0), 1.0),
                (float)Math.min(scalar * (color.getBlue() / 255.0), 1.0)
        );
    } // end method scaleColor

    // construct ray reflected from inRay
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay)
    {
        // R = D - 2(D * N)N
        Vector D = inRay.getDirection();

        // reflection is orthogonal to normal so no reflection
        if (D.dot(normal).equals(Coordinate.ZERO)) return null;

        Vector R = D.subtract(normal.mult(D.dot(normal).mult(2)));
        return new Ray(point, R);
    } // end method constructReflectedRay

    // construct ray refracted from inRay
    private Ray constructRefractedRay(Point3D point, Ray inRay)
    {
        return new Ray(point, inRay.getDirection());
    } // end method constructRefractedRay

    private boolean pointsEqual(Point3D p1, Point3D p2)
    {
        double EPS = 0.001;
        return
                Math.abs(p1.getX().getCoordinate() - p2.getX().getCoordinate()) < EPS
                && Math.abs(p1.getY().getCoordinate() - p2.getY().getCoordinate()) < EPS
                && Math.abs(p1.getZ().getCoordinate() - p2.getZ().getCoordinate()) < EPS;
    }


} // end class Renderer
