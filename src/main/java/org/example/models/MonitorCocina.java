package org.example.models;
import org.example.entity.Orden;
import java.util.LinkedList;
import java.util.Queue;

public class MonitorCocina {
    // Buffer para almacenar las órdenes
    private Queue<Orden> bufferOrdenes;
    private Queue<Orden> bufferComidas;

    public MonitorCocina() {
        this.bufferOrdenes = new LinkedList<>(); // Usamos una cola para manejar las órdenes
        this.bufferComidas = new LinkedList<>(); // Cola para almacenar las comidas preparadas
    }

    // Método para que los meseros agreguen una orden
    public synchronized void tomarOrden(Orden orden) {
        bufferOrdenes.add(orden); // Agregar la orden al buffer
        System.out.println("Orden añadida al buffer: " + orden);
        notifyAll(); // Notificar a los cocineros que hay una nueva orden
    }

    // Método para que los cocineros tomen una orden
    public synchronized Orden obtenerOrden() throws InterruptedException {
        while (bufferOrdenes.isEmpty()) {
            System.out.println("No hay órdenes en el buffer, cocinero esperando...");
            wait(); // Esperar hasta que haya órdenes disponibles
        }
        Orden orden = bufferOrdenes.poll(); // Sacar la orden del buffer
        System.out.println("Orden retirada del buffer: " + orden);
        return orden;
    }

    // Método para que los cocineros añadan una comida preparada al buffer
    public synchronized void agregarComida(Orden orden) {
        bufferComidas.add(orden); // Agregar la comida lista al buffer
        System.out.println("Comida lista para servir: " + orden);
        notifyAll(); // Notificar a los meseros que hay comida lista
    }

    // Método para que los meseros tomen una comida lista
    public synchronized Orden obtenerComida() throws InterruptedException {
        while (bufferComidas.isEmpty()) {
            System.out.println("No hay comidas listas, mesero esperando...");
            wait(); // Esperar hasta que haya comidas disponibles
        }
        Orden orden = bufferComidas.poll(); // Sacar la comida del buffer
        System.out.println("Comida retirada del buffer: " + orden);
        return orden;
    }

    // Método para mostrar el estado del buffer de órdenes (opcional)
    public synchronized void mostrarBufferOrdenes() {
        System.out.println("Órdenes en el buffer: " + bufferOrdenes.size());
        for (Orden orden : bufferOrdenes) {
            System.out.println(orden);
        }
    }

    // Método para mostrar el estado del buffer de comidas (opcional)
    public synchronized void mostrarBufferComidas() {
        System.out.println("Comidas en el buffer: " + bufferComidas.size());
        for (Orden orden : bufferComidas) {
            System.out.println(orden);
        }
    }
}

