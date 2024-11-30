package org.example.threads;

import org.example.models.MonitorCocina;
import org.example.models.MonitorMesas;

public class Mesero extends Thread {
    private final MonitorMesas monitorMesas;  // Monitor de las mesas
    private final MonitorCocina monitorCocina;  // Monitor de la cocina

    public Mesero(MonitorMesas monitorMesas, MonitorCocina monitorCocina, String name) {
        super(name);
        this.monitorMesas = monitorMesas;
        this.monitorCocina = monitorCocina;

    }


    @Override
    public void run() {
        try {
            while (true) {  // Mantener el hilo corriendo de manera indefinida
                // Intentamos obtener una mesa ocupada
                int mesaId = monitorMesas.getMesaConComensal();


                if (mesaId != -1) {
                    Thread.sleep(4000); // tiempo de espera de la orden
                    // Agrega la orden a la cocina
                    monitorCocina.agregarOrden(mesaId);  // Puede ser el ID de la mesa

                    // Espera a que la comida esté lista
                    monitorCocina.recibirComida(mesaId);  // Puede ser el ID de la mesa también

                    System.out.println("La comida ha sido servida en la mesa " + (mesaId + 1));
                    Thread.sleep(4000);
                } else {
                    // Si no hay mesas ocupadas, el mesero espera un tiempo antes de intentar de nuevo
                    System.out.println("No hay comensales. El mesero está esperando.");
                }




            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error en el hilo del mesero: " + e.getMessage());
        }
    }
}