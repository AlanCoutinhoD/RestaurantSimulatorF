package org.example.entity;

public class Orden {
    private int numeroMesa;
    private String estado; // "EN_PROCESO", "LISTA"

    public Orden(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.estado = "EN_PROCESO"; // Inicialmente la orden está en proceso
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
