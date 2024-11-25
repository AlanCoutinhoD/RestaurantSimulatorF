package org.example.entity;


public class Mesa {
    private int numero;
    private boolean ocupado;
    private boolean atendido;

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

    public synchronized boolean isAtendido() { // <-- Verifica que este método esté aquí
        return atendido;
    }

    public int getNumero() {
        return numero; // Devuelve el número de la mesa
    }

    public synchronized void atender() {
        atendido = true;
    }
    @Override
    public String toString() {
        return "Mesa " + numero + " [Ocupado: " + (ocupado ? "Sí" : "No") + "]";
    }


}
