package org.example.models;

import org.example.entity.Mesa;

public class MonitorP {
    private Mesa[] mesas;
    private int nC = 0; // Número de comensales en el restaurante

    public MonitorP(int cantidadMesas) {
        mesas = new Mesa[cantidadMesas];
        for (int i = 0; i < cantidadMesas; i++) {
            mesas[i] = new Mesa(i + 1); // Las mesas se numeran desde 1
        }
    }

    // Método para ocupar una mesa
    public synchronized void ocuparMesa() {
        while (true) {
            // Buscar una mesa libre
            for (Mesa mesa : mesas) {
                if (!mesa.isOcupado()) { // Si la mesa está libre
                    mesa.ocupar(); // Ocupar la mesa
                    nC++; // Incrementar el número de comensales
                    System.out.println("Comensal asignado a " + mesa);
                    return; // Salir del método, ya que se ha ocupado una mesa
                }
            }

            // Si no hay mesas libres, esperar hasta que se libere una
            try {
                System.out.println("Todas las mesas están ocupadas. Comensal espera.");
                this.wait(); // Espera a que se notifique la liberación de una mesa
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido mientras esperaba.");
            }
        }
    }

    // Método para liberar una mesa
    public synchronized void liberarMesa(int numeroMesa) {
        if (numeroMesa < 1 || numeroMesa > mesas.length) {
            System.out.println("Número de mesa inválido.");
            return;
        }

        Mesa mesa = mesas[numeroMesa - 1]; // Encontrar la mesa según el número

        if (mesa.isOcupado()) { // Si la mesa está ocupada
            mesa.liberar(); // Liberar la mesa
            if (nC > 0) { // Verificar que haya comensales antes de disminuir
                nC--; // Reducir el número de comensales en el restaurante
            }
            System.out.println("Mesa " + numeroMesa + " ha sido liberada. Total comensales: " + nC);
            // Notificar a los hilos en espera (comensales esperando por mesas)
            notifyAll();
        } else {
            System.out.println("La mesa " + numeroMesa + " ya está libre.");
        }
    }

    // Clase interna para representar una mesa


}
