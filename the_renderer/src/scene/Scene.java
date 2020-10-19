package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scene
{
    private String _sceneName;
    private Color _background;
    private AmbientLight _ambientLight;
    private List<Geometry> _geometries;
    private List<LightSource> _lights;
    private Camera _camera;
    private double _screenDistance;

    // full constructor
    public Scene(String sceneName, Color background, AmbientLight ambientLight,
                 List<Geometry> geometries, List<LightSource> lights, Camera camera,
                 double screenDistance)
    {
        _sceneName = sceneName;
        _background = background;
        _ambientLight = ambientLight;
        _geometries = geometries;
        _lights = lights;
        _camera = camera;
        _screenDistance = screenDistance;
    } // end full constructor

    // default constructor
    public Scene()
    {
        this(
                "",
                new Color(0, 0, 0),
                new AmbientLight(),
                new ArrayList<Geometry>(),
                new ArrayList<LightSource>(),
                new Camera(),
                10
        );
    } // end default constructor

    // getters
    public String getSceneName()
    {
        return _sceneName;
    }

    public Color getBackground()
    {
        return new Color(_background.getRGB());
    }

    public AmbientLight getAmbientLight()
    {
        return new AmbientLight(_ambientLight);
    }

    public List<Geometry> getGeometries()
    {
        return new ArrayList<Geometry>(_geometries);
    }

    public Camera getCamera()
    {
        return new Camera(_camera);
    }

    public double getScreenDistance()
    {
        return _screenDistance;
    }

    // setters
    public void setAmbientLight(AmbientLight ambientLight)
    {
        _ambientLight = ambientLight;
    }
    public void setScreenDistance(double screenDistance)
    {
        _screenDistance = screenDistance;
    }
    public void setAperture(double aperture) { _camera.setAperture(aperture); }
    public void setFocalDistance(double focalDistance) { _camera.setFocalDistance(focalDistance); }
    public void setNumCameraRays(int numCameraRays) { _camera.setNumRays(numCameraRays); }

    // add geometry to list of geometries
    public void addGeometry(Geometry geometry)
    {
        _geometries.add(geometry);
    } // end method addGeometry

    // add lightSource to list of lights
    public void addLightSource(LightSource lightSource)
    {
        _lights.add(lightSource);
    } // end method addLightSource

    // get iterator to iterate through list of geometries
    public Iterator<Geometry> getGeometriesIterator()
    {
        return _geometries.iterator();
    } // end method getGeometriesIterator

    // get iterator to iterate through list of lights
    public Iterator<LightSource> getLightsIterator()
    {
        return _lights.iterator();
    } // end method getLightsIterator
} // end class Scene
