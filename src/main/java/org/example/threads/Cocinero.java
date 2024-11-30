package org.example.threads;

import org.example.models.MonitorCocina;
import org.example.interfaces.Interfaces;

public class Cocinero extends Thread {
    private final MonitorCocina monitorCocina;
    private final Interfaces gameLogic;

    public Cocinero(MonitorCocina monitorCocina, Interfaces gameLogic) {
        this.monitorCocina = monitorCocina;
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // El cocinero espera que haya órdenes
                int mesaId = monitorCocina.obtenerOrden();

                if (mesaId != -1) {
                    // El cocinero simula el tiempo de cocina
                    System.out.println("El cocinero está preparando la comida para la mesa " + (mesaId + 1));

                    // Simula el tiempo de preparación de la comida (por ejemplo, 5 segundos)
                    Thread.sleep(5000);  // Ajusta este tiempo según necesites

                    // El cocinero coloca la comida en el buffer
                    monitorCocina.colocarComida(mesaId);  // Coloca la comida en el buffer

                    System.out.println("La comida para la mesa " + (mesaId + 1) + " está lista.");
                } else {
                    // Si no hay órdenes, el cocinero espera
                    System.out.println("El cocinero está esperando órdenes.");
                    Thread.sleep(7000);  // Ajusta el tiempo de espera según lo necesites
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Error en el hilo del cocinero: " + e.getMessage());
        }
    }
}
