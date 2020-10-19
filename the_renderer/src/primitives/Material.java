package primitives;

// Material class
public class Material
{
    private double _Kd; // diffuse coefficient
    private double _Ks; // specular coefficient
    private double _Kr; // reflected coefficient
    private double _Kt; // refracted coefficient
    private int _nShininess; // sharpness of specular light

    // full constructor
    public Material(double Kd, double Ks, double Kr, double Kt, int nShininess)
    {
        _Kd = Kd;
        _Ks = Ks;
        _Kr = Kr;
        _Kt = Kt;
        _nShininess = nShininess;
    } // end full constructor

    // default constructor
    public Material() { this(0.5, 0.5, 0.5, 0.5, 1); }

    // copy constructor
    public Material(Material other)
    {
        this(other._Kd, other._Ks, other._Kr, other._Kt, other._nShininess);
    }

    // getters
    public double getKd() { return _Kd; }
    public double getKs() { return _Ks; }
    public double getKr() { return _Kr; }
    public double getKt() { return _Kt; }
    public int getNShininess() { return _nShininess; }

    // setters
    public void setKd(double Kd) { _Kd = Kd; }
    public void setKs(double Ks) { _Ks = Ks; }
    public void setKr(double Kr) { _Kr = Kr; }
    public void setKt(double Kt) { _Kt = Kt; }
    public void setNShininess(int nShininess) { _nShininess = nShininess; }
} // end class Material
