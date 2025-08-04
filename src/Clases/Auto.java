package Clases;

public class Auto extends Vehiculo {
    private int numPuertas;

    public Auto(String placa, String marca, String modelo, int año, int numPuertas, Cliente cliente) {
        super(placa, marca, modelo, año, cliente);
        this.numPuertas = numPuertas;
    }

    public int getNumPuertas() { return numPuertas; }
    public void setNumPuertas(int numPuertas) { this.numPuertas = numPuertas; }
}
