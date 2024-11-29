package org.example.threads;

import org.example.entity.Orden;
import org.example.models.MonitorCocina;

public class Cocinero extends Thread {
    private MonitorCocina monitorCocina;

    public Cocinero(MonitorCocina monitorCocina, String nombre) {
        super(nombre);
        this.monitorCocina = monitorCocina;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Tomar una orden del buffer de órdenes
                Orden orden = monitorCocina.obtenerOrden();

                // Simulación de la cocina
                System.out.println(getName() + " está cocinando la orden de la mesa " + orden.getNumeroMesa());
                Thread.sleep(2000); // Simular el tiempo de cocina (2 segundos)

                // Colocar la comida lista en el buffer de comidas
                orden.setEstado("LISTA");
                monitorCocina.agregarComida(orden);
                System.out.println(getName() + " ha cocinado la orden de la mesa " + orden.getNumeroMesa());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
