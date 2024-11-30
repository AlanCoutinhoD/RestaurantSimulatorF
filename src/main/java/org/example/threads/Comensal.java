package org.example.threads;

import org.example.models.MonitorCocina;
import org.example.models.MonitorMesas;
import org.example.interfaces.Interfaces;

public class Comensal extends Thread {
    private final MonitorMesas monitorMesas;
    private final MonitorCocina monitorCocina;
    private final String nombre;
    private final Interfaces gameLogic;


    public Comensal(MonitorMesas monitorMesas, MonitorCocina monitorCocina, String nombre, Interfaces gameLogic) {
        this.monitorMesas = monitorMesas;
        this.monitorCocina = monitorCocina;
        this.nombre = nombre;
        this.gameLogic = gameLogic;
         // Asume que el comensal ocupa una mesa
    }

    @Override
    public void run() {

        int idMesa = monitorMesas.ocuparMesa();
        monitorCocina.esperarOrden(idMesa);
        monitorCocina.comer(idMesa);
        try {
            Thread.sleep(4000); // tiempo de inactividad esta comiendo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        monitorMesas.liberarMesa(idMesa);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
