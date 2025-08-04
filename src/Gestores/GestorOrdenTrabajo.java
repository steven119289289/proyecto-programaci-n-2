package Gestores;

import Clases.*;
import java.io.*;
import java.util.ArrayList;

public class GestorOrdenTrabajo {
    private ArrayList<OrdenTrabajo> listaOrdenes;
    private final String archivo = "ordenes.txt";

    private GestorCliente gestorCliente;
    private GestorVehiculo gestorVehiculo;
    private GestorServicio gestorServicio;

    public GestorOrdenTrabajo(GestorCliente gc, GestorVehiculo gv, GestorServicio gs) {
        this.listaOrdenes = new ArrayList<>();
        this.gestorCliente = gc;
        this.gestorVehiculo = gv;
        this.gestorServicio = gs;

        File f = new File(archivo);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear el archivo de órdenes.");
            }
        }

        cargarDesdeArchivo();
    }

    public boolean registrarOrden(OrdenTrabajo nueva) {
        for (OrdenTrabajo ot : listaOrdenes) {
            if (ot.getIdOrden().equalsIgnoreCase(nueva.getIdOrden())) {
                return false;
            }
        }
        listaOrdenes.add(nueva);
        guardarEnArchivo();
        return true;
    }

    public boolean cerrarOrden(String idOrden) {
        for (OrdenTrabajo ot : listaOrdenes) {
            if (ot.getIdOrden().equalsIgnoreCase(idOrden)) {
                ot.cerrarOrden();
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean cambiarFechaEntrega(String idOrden, String nuevaFecha) {
        for (OrdenTrabajo ot : listaOrdenes) {
            if (ot.getIdOrden().equalsIgnoreCase(idOrden)) {
                ot.setFechaEntrega(nuevaFecha);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public ArrayList<OrdenTrabajo> getListaOrdenes() {
        return listaOrdenes;
    }
    public boolean eliminarOrden(String idOrden) {
    for (OrdenTrabajo ot : listaOrdenes) {
        if (ot.getIdOrden().equalsIgnoreCase(idOrden)) {
            listaOrdenes.remove(ot);
            guardarEnArchivo();
            return true;
        }
    }
    return false;
}
    public OrdenTrabajo buscarPorId(String idOrden) {
        for (OrdenTrabajo ot : listaOrdenes) {
            if (ot.getIdOrden().equalsIgnoreCase(idOrden)) {
                return ot;
            }
        }
        return null;
    }

    public void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (OrdenTrabajo ot : listaOrdenes) {
                StringBuilder linea = new StringBuilder();
                linea.append(ot.getIdOrden()).append(";")
                     .append(ot.getCliente().getIdCliente()).append(";")
                     .append(ot.getVehiculo().getPlaca()).append(";")
                     .append(ot.getFechaIngreso()).append(";")
                     .append(ot.getFechaEntrega()).append(";")
                     .append(ot.isCerrada()).append(";");

                ArrayList<Servicio> servicios = ot.getServicios();
                for (int i = 0; i < servicios.size(); i++) {
                    linea.append(servicios.get(i).getCodigo());
                    if (i < servicios.size() - 1) {
                        linea.append(",");
                    }
                }

                pw.println(linea.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar órdenes: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        listaOrdenes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 7) {
                    String idOrden = partes[0];
                    String idCliente = partes[1];
                    String placaVehiculo = partes[2];
                    String fechaIngreso = partes[3];
                    String fechaEntrega = partes[4];
                    boolean cerrada = Boolean.parseBoolean(partes[5]);
                    String[] codigosServicios = partes[6].split(",");

                    Cliente cliente = gestorCliente.buscarClientePorId(idCliente);
                    Vehiculo vehiculo = gestorVehiculo.buscarVehiculoPorPlaca(placaVehiculo);
                    ArrayList<Servicio> servicios = new ArrayList<>();

                    for (String codigo : codigosServicios) {
                        Servicio s = gestorServicio.buscarPorCodigo(codigo);
                        if (s != null) {
                            servicios.add(s);
                        }
                    }

                    if (cliente != null && vehiculo != null) {
                        OrdenTrabajo orden = new OrdenTrabajo(idOrden, cliente, vehiculo, fechaIngreso, fechaEntrega);
                        for (Servicio s : servicios) {
                            orden.agregarServicio(s);
                        }
                        if (cerrada) {
                            orden.cerrarOrden();
                        }
                        listaOrdenes.add(orden);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de órdenes: " + e.getMessage());
        }
    }
}
