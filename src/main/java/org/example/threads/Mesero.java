package org.example.threads;
import org.example.models.MonitorCocina;
import  org.example.models.MonitorP;
import  org.example.entity.Orden;
import  org.example.entity.Mesa;
public class Mesero extends Thread {
    private final MonitorP monitorMesas;

    public Mesero(MonitorP monitorMesas, String nombre) {
        super(nombre); // Configurar el nombre del thread
        this.monitorMesas = monitorMesas;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Obtener una mesa que necesita atención
                Mesa mesa = monitorMesas.obtenerMesaSinAtender();

                // Atender la mesa
                System.out.println(getName() + " está atendiendo a la mesa " + mesa.getNumero());
                Thread.sleep(2000); // Simular el tiempo que tarda en atender al cliente

                // Marcar la mesa como atendida
                monitorMesas.atenderMesa(mesa.getNumero());
                System.out.println(getName() + " ha terminado de atender a la mesa " + mesa.getNumero());

                // Simular un breve descanso del mesero
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " ha sido interrumpido.");
        }
    }
}

