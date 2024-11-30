package org.example.models;

import javafx.application.Platform;
import org.example.interfaces.Interfaces;
import java.util.LinkedList;
import java.util.Queue;

public class MonitorCocina {
    private final Queue<Integer> bufferOrdenes;  // Buffer de las órdenes
    private final Queue<Integer> bufferComida;   // Buffer de la comida preparada
    private final Interfaces gameLogic;
    private boolean servido = false;
    public MonitorCocina(Interfaces gameLogic) {
        this.bufferOrdenes = new LinkedList<>();
        this.bufferComida = new LinkedList<>();
        this.gameLogic = gameLogic;
    }

    // Método para agregar la orden al buffer de la cocina
    public synchronized void agregarOrden(int mesaId) {
        Platform.runLater(() -> {
            gameLogic.MoverMesero(1, 4);  // Mueve al comensal fuera de la mesa
        });
        bufferOrdenes.add(mesaId);  // Se agrega la orden al buffer
        System.out.println("Mesero dejando orden en la cocina, Orden agregada para la mesa " + (mesaId));
        notifyAll();
    }

    // Método para obtener una orden de la cocina (el cocinero lo llama para tomar la orden)
    public synchronized int obtenerOrden() {
        // Espera hasta que haya una orden en el buffer
        while (bufferOrdenes.isEmpty()) {
            try {
                System.out.println("El cocinero esta sin nada que cocinar: ");
                wait();  // El cocinero espera hasta que haya una orden
            } catch (InterruptedException e) {
                System.out.println("Error esperando orden: " + e.getMessage());
            }
        }

        // Toma una orden del buffer (la primer orden)
        return bufferOrdenes.remove();  // El cocinero toma la orden de la mesa
    }

    // Método para colocar la comida en el buffer (cuando el cocinero la prepara)
    public synchronized void colocarComida(int mesaId) {
        // Colocamos la comida en el buffer
        bufferComida.add(mesaId);
        System.out.println("Comida colocada en el buffer para la mesa " + (mesaId));
        // Notificamos que la comida está lista
        notifyAll();
    }

    // Método para recibir la comida cuando esté lista (el mesero lo llama para servir la comida)
    public synchronized void recibirComida(int mesaId) {
        // Espera hasta que haya comida lista en el buffer
        while (bufferComida.isEmpty()) {
            try {
                wait();  // El mesero espera hasta que haya comida
            } catch (InterruptedException e) {
                System.out.println("Error esperando comida: " + e.getMessage());
            }
        }

        // El mesero "sirve" la comida desde el buffer
        bufferComida.remove();  // Simulamos que el mesero está entregando la comida
        System.out.println("Comida servida para la mesa " + (mesaId));
        Platform.runLater(() -> {
            gameLogic.MoverMesero(1,mesaId);
        });
        servido = true;

        // Ahora el comensal puede continuar, ya que la comida está lista
    }

    public synchronized void esperarOrden(int mesaId) {
        while (!servido) {
            try {
                System.out.println("La mesa " + mesaId + "Esta esperando su comida");

                this.wait();  // El hilo espera hasta que haya comida para la mesa indicada
            } catch (InterruptedException e) {
                System.out.println("Error en la espera de la orden: " + e.getMessage());
            }
        }

        // Cuando la orden esté en el buffer, sale del bucle
        System.out.println("La comida de la mesa " + mesaId + " está lista.");

    }

    public synchronized void comer(int idMesa) {

        System.out.println("El comensal de la mesa " + (idMesa) + "Esta comiendo");
        servido = false;

    }
}
