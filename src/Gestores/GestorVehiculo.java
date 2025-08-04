package Gestores;

import Clases.*;
import java.io.*;
import java.util.ArrayList;

public class GestorVehiculo {
    private ArrayList<Vehiculo> listaVehiculos;
    private final String archivo = "vehiculos.txt";

    public GestorVehiculo() {
        listaVehiculos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public boolean registrarVehiculo(Vehiculo nuevo) {
        for (Vehiculo v : listaVehiculos) {
            if (v.getPlaca().equalsIgnoreCase(nuevo.getPlaca())) {
                return false; 
            }
        }
        listaVehiculos.add(nuevo);
        guardarEnArchivo();
        return true;
    }

    public ArrayList<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

private void cargarDesdeArchivo() {
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(";");
            if (partes.length >= 6) {
                String tipo = partes[0]; 
                String placa = partes[1];
                String marca = partes[2];
                String modelo = partes[3];
                int año = Integer.parseInt(partes[4]);
                String idCliente = partes[5];

                GestorCliente gestorTemp = new GestorCliente();
                Cliente cliente = gestorTemp.buscarClientePorId(idCliente);

                if (cliente == null) {
                    System.out.println("Cliente con ID " + idCliente + " no encontrado. Vehículo omitido.");
                    continue;
                }

                if (tipo.equals("Auto") && partes.length == 7) {
                    int numPuertas = Integer.parseInt(partes[6]);
                    listaVehiculos.add(new Auto(placa, marca, modelo, año, numPuertas, cliente));
                } else if (tipo.equals("Moto") && partes.length == 7) {
                    String tipoMoto = partes[6];
                    listaVehiculos.add(new Moto(placa, marca, modelo, año, tipoMoto, cliente));
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Archivo de vehículos no encontrado. Se creará al guardar.");
    } catch (NumberFormatException e) {
        System.out.println("Error al convertir número al cargar vehículo: " + e.getMessage());
    }
}
    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Vehiculo v : listaVehiculos) {
                StringBuilder sb = new StringBuilder();
                if (v instanceof Auto a) {
                    sb.append("Auto;").append(a.getPlaca()).append(";").append(a.getMarca()).append(";")
                      .append(a.getModelo()).append(";").append(a.getAño()).append(";")
                      .append(a.getCliente().getIdCliente()).append(";").append(a.getNumPuertas());
                } else if (v instanceof Moto m) {
                    sb.append("Moto;").append(m.getPlaca()).append(";").append(m.getMarca()).append(";")
                      .append(m.getModelo()).append(";").append(m.getAño()).append(";")
                      .append(m.getCliente().getIdCliente()).append(";").append(m.getTipoMoto());
                }
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar vehículos: " + e.getMessage());
        }
    }

    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        for (Vehiculo v : listaVehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

public boolean eliminarVehiculo(String placa) {
    for (Vehiculo v : listaVehiculos) {
        if (v.getPlaca().equalsIgnoreCase(placa)) {
            listaVehiculos.remove(v);
            guardarEnArchivo();
            return true;
        }
    }
    return false;
}


 public boolean modificarVehiculo(String placa, String nuevaMarca, String nuevoModelo, int nuevoAño, String infoExtra) {
    for (Vehiculo v : listaVehiculos) {
        if (v.getPlaca().equalsIgnoreCase(placa)) {
            v.setMarca(nuevaMarca);
            v.setModelo(nuevoModelo);
            v.setAño(nuevoAño);
            if (v instanceof Auto a) {
                a.setNumPuertas(Integer.parseInt(infoExtra));
            } else if (v instanceof Moto m) {
                m.setTipoMoto(infoExtra);
            }
            guardarEnArchivo();
            return true;
        }
    }
    return false;
 }
}