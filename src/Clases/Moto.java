package Clases;

public class Moto extends Vehiculo {
    private String tipoMoto;

    public Moto(String placa, String marca, String modelo, int año, String tipoMoto, Cliente cliente) {
        super(placa, marca, modelo, año, cliente);
        this.tipoMoto = tipoMoto;
    }

    public String getTipoMoto() { return tipoMoto; }
    public void setTipoMoto(String tipoMoto) { this.tipoMoto = tipoMoto; }
}
