package org.example;

import org.example.models.MonitorP;
import org.example.threads.Comensal;
import org.example.threads.Mesero;
import  org.example.entity.Mesa;
public class Main{
    public static void main(String[] args) {
        // Crear el monitor de las mesas
        MonitorP monitorMesas = new MonitorP(4); // Restaurante con 4 mesas

        // Crear y lanzar los hilos de los meseros
        Mesero mesero1 = new Mesero(monitorMesas, "Mesero 1");
        Mesero mesero2 = new Mesero(monitorMesas, "Mesero 2");

        mesero1.start();
        mesero2.start();

        // Hilo para simular llegada infinita de comensales
        new Thread(() -> {
            try {
                int comensalId = 1; // Identificador de comensales
                while (true) {
                    Mesa mesa = monitorMesas.asignarMesa(); // Ocupar una mesa
                    System.out.println("Comensal " + comensalId + " ocupa " + mesa);

                    // Simular que el comensal usa la mesa por un tiempo aleatorio
                    int finalComensalId = comensalId;
                    new Thread(() -> {
                        try {
                            Thread.sleep((int) (3000 + Math.random() * 2000)); // 3-5 segundos
                            monitorMesas.liberarMesa(mesa.getNumero()); // Liberar la mesa
                            System.out.println("Comensal " + finalComensalId + " ha dejado " + mesa);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    comensalId++; // Incrementar el identificador para el próximo comensal
                    Thread.sleep(1000); // Simular llegada de nuevos comensales cada 1 segundo
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
