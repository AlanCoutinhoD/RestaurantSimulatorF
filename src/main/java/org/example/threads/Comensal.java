package org.example.threads;

import org.example.models.MonitorCocina;
import org.example.models.MonitorP;

public class Comensal extends Thread {
    private MonitorP monitorMesas;
    private MonitorCocina monitorCocina;

    public Comensal(MonitorP monitorMesas, MonitorCocina monitorCocina, String name) {
        super(name);
        this.monitorMesas = monitorMesas;
        this.monitorCocina = monitorCocina;
    }

    @Override
    public void run() {
        try {
            // Intentar ocupar una mesa
            var mesa = monitorMesas.asignarMesa();
            System.out.println(getName() + " ocupa " + mesa);

            // Simular tiempo comiendo
            Thread.sleep((long) (Math.random() * 5000 + 2000));

            // Liberar la mesa después de comer
            monitorMesas.liberarMesa(mesa.getNumero());
            System.out.println(getName() + " terminó y liberó " + mesa);
        } catch (InterruptedException e) {
            System.out.println(getName() + " fue interrumpido: " + e.getMessage());
        }
    }
}
