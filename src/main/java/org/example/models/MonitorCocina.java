package org.example.models;
import org.example.entity.Orden;
import java.util.LinkedList;
import java.util.Queue;

public class MonitorCocina {
    // Buffer para almacenar las órdenes
    private Queue<Orden> bufferOrdenes;

    public MonitorCocina() {
        this.bufferOrdenes = new LinkedList<>(); // Usamos una cola para manejar las órdenes
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

    // Método para mostrar el estado del buffer (opcional)
    public synchronized void mostrarBuffer() {
        System.out.println("Órdenes en el buffer: " + bufferOrdenes.size());
        for (Orden orden : bufferOrdenes) {
            System.out.println(orden);
        }
    }
}

