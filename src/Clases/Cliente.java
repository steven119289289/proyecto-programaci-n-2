package Clases;

import java.util.ArrayList;

public class Cliente extends Persona {
    private String idCliente;
    private ArrayList<Vehiculo> vehiculos;

    public Cliente(String idCliente, String nombre, String telefono, String direccion) {
        super(nombre, telefono, direccion);
        this.idCliente = idCliente;
        this.vehiculos = new ArrayList<>();
    }

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    public ArrayList<Vehiculo> getVehiculos() { return vehiculos; }

    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }
@Override
public String toString() {
    return this.getIdCliente() + " - " + this.getNombre();  
}

    
    @Override
    public String getIdentificador() {
        return idCliente;
    }
    
}
