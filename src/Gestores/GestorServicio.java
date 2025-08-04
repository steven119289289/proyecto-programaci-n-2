package Gestores;

import Clases.Servicio;
import java.io.*;
import java.util.ArrayList;

public class GestorServicio {
    private ArrayList<Servicio> listaServicios;
    private final String archivo = "servicios.txt";

    public GestorServicio() {
        listaServicios = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public boolean registrarServicio(Servicio nuevo) {
        for (Servicio s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(nuevo.getCodigo())) {
                return false;
            }
        }
        listaServicios.add(nuevo);
        guardarEnArchivo();
        return true;
    }

    public boolean modificarServicio(String codigo, String nuevaDescripcion, double nuevoCosto) {
        for (Servicio s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                s.setDescripcion(nuevaDescripcion);
                s.setCosto(nuevoCosto);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarServicio(String codigo) {
        for (Servicio s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                listaServicios.remove(s);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Servicio> getListaServicios() {
        return listaServicios;
    }

    public Servicio buscarPorCodigo(String codigo) {
        for (Servicio s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }

    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Servicio s : listaServicios) {
                pw.println(s.getCodigo() + ";" + s.getDescripcion() + ";" + s.getCosto());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar servicios: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String codigo = partes[0];
                    String descripcion = partes[1];
                    double costo = Double.parseDouble(partes[2]);
                    Servicio s = new Servicio(codigo, descripcion, costo);
                    listaServicios.add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró el archivo de servicios.");
        }
    }
    public Servicio buscarPorDescripcion(String descripcion) {
    for (Servicio s : listaServicios) {
        if (s.getDescripcion().equalsIgnoreCase(descripcion)) {
            return s;
        }
    }
    return null;
}

}
