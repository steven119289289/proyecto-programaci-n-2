package Clases;

import java.util.ArrayList;

public class OrdenTrabajo {
    private String idOrden;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ArrayList<Servicio> servicios;
    private String fechaIngreso;
    private String fechaEntrega;
    private boolean cerrada;

    public OrdenTrabajo(String idOrden, Cliente cliente, Vehiculo vehiculo, String fechaIngreso, String fechaEntrega) {
        this.idOrden = idOrden;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaIngreso = fechaIngreso;
        this.fechaEntrega = fechaEntrega;
        this.servicios = new ArrayList<>();
        this.cerrada = false;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public boolean isCerrada() {
        return cerrada;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void cerrarOrden() {
        this.cerrada = true;
    }

    public void agregarServicio(Servicio servicio) {
        if (servicio != null) {
            servicios.add(servicio);
        }
    }

    public double calcularCostoTotal() {
        double total = 0;
        for (Servicio s : servicios) {
            total += s.getCosto();
        }
        return total;
    }
}
