package org.example.threads;

import org.example.entity.Orden;
import org.example.models.MonitorCocina;
import org.example.models.MonitorP;

public class Mesero extends Thread {
    private MonitorP monitorMesas;
    private MonitorCocina monitorCocina;

    public Mesero(MonitorP monitorMesas, MonitorCocina monitorCocina, String name) {
        super(name);
        this.monitorMesas = monitorMesas;
        this.monitorCocina = monitorCocina;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Buscar una mesa ocupada pero no atendida
                System.out.println(getName() + ": Buscando una mesa ocupada pero no atendida...");
                var mesa = monitorMesas.obtenerMesaSinAtender();

                if (mesa != null) {
                    System.out.println(getName() + ": Atendiendo a la mesa " + mesa.getNumero());

                    // Generar una orden para la cocina
                    Orden orden = new Orden(mesa.getNumero());
                    System.out.println(getName() + ": Generando la orden para la mesa " + mesa.getNumero());
                    monitorCocina.tomarOrden(orden);

                    // Marcar la mesa como atendida
                    monitorMesas.atenderMesa(mesa.getNumero());
                    System.out.println(getName() + ": Mesa " + mesa.getNumero() + " ha sido marcada como atendida.");
                } else {
                    System.out.println(getName() + ": No hay mesas para atender en este momento.");
                }

                Thread.sleep(500); // Simular tiempo de atención
            } catch (InterruptedException e) {
                System.out.println(getName() + " fue interrumpido: " + e.getMessage());
            }
        }
    }
}
