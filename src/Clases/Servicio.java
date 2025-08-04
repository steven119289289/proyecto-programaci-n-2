package Clases;

public class Servicio {
    private String codigo;
    private String descripcion;
    private double costo;

    public Servicio(String codigo, String descripcion, double costo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public double getCosto() { return costo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCosto(double costo) { this.costo = costo; }
 
    @Override
public String toString() {
    return codigo + " - " + descripcion;
}

}

