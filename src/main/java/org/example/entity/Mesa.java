package org.example.entity;


public class Mesa {
    private int numero;
    private boolean ocupado;

    public Mesa(int numero) {
        this.numero = numero;
        this.ocupado = false; // Al principio está libre
    }

    public synchronized boolean isOcupado() {
        return ocupado;
    }

    public synchronized void ocupar() {
        this.ocupado = true; // Marca la mesa como ocupada
    }

    public synchronized void liberar() {
        this.ocupado = false; // Marca la mesa como libre
    }

    @Override
    public String toString() {
        return "Mesa " + numero + " [Ocupado: " + (ocupado ? "Sí" : "No") + "]";
    }
}
