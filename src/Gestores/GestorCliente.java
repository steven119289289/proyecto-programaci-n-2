package Gestores;

import Clases.Cliente;
import java.io.*;
import java.util.ArrayList;

public class GestorCliente {
    private ArrayList<Cliente> listaClientes;
    private final String archivo = "clientes.txt";

    public GestorCliente() {
        listaClientes = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public boolean registrarCliente(Cliente nuevo) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(nuevo.getIdCliente())) {
                return false;
            }
        }
        listaClientes.add(nuevo);
        guardarEnArchivo();
        return true;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public boolean modificarCliente(String idCliente, String nuevoNombre, String nuevoTelefono, String nuevaDireccion) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(idCliente)) {
                c.setNombre(nuevoNombre);
                c.setTelefono(nuevoTelefono);
                c.setDireccion(nuevaDireccion);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCliente(String idCliente) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(idCliente)) {
                listaClientes.remove(c);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public Cliente buscarClientePorId(String idCliente) {
        for (Cliente c : listaClientes) {
            if (c.getIdCliente().equalsIgnoreCase(idCliente)) {
                return c;
            }
        }
        return null;
    }

    private void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    String id = partes[0];
                    String nombre = partes[1];
                    String telefono = partes[2];
                    String direccion = partes[3];
                    Cliente c = new Cliente(id, nombre, telefono, direccion);
                    listaClientes.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado o vacío, se creará uno nuevo al guardar.");
        }
    }

    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Cliente c : listaClientes) {
                pw.println(c.getIdCliente() + ";" + c.getNombre() + ";" +
                           c.getTelefono() + ";" + c.getDireccion());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los clientes: " + e.getMessage());
        }
    }
    public Cliente buscarClientePorNombre(String nombre) {
    for (Cliente c : listaClientes) {
        if (c.getNombre().equalsIgnoreCase(nombre)) {
            return c;
        }
    }
    return null;
}

}
