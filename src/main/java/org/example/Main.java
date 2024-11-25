package org.example;

import org.example.models.MonitorP;
import org.example.threads.Comensal;
public class Main {

    public static void main(String[] args) {
        MonitorP monitorP = new MonitorP(4);




        Thread comensales = new Thread(() -> {
            int comensalesCount = 1; // Contador de comensales
            while (true) {
                try {
                    monitorP.ocuparMesa(); // Comensal intenta ocupar una mesa
                    System.out.println("Comensal " + comensalesCount + " llegó al restaurante.");
                    comensalesCount++; // Incrementar el contador de comensales
                    Thread.sleep(1000); // Simula tiempo entre llegadas
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Hilo para liberar mesas periódicamente
        Thread liberarMesas = new Thread(() -> {
            int liberacionCount = 1; // Contador de mesas a liberar
            while (true) {
                try {
                    Thread.sleep(5000); // Simula el tiempo que los comensales están ocupando la mesa
                    monitorP.liberarMesa((liberacionCount % 4) + 1); // Liberar una mesa cíclicamente
                    liberacionCount++; // Incrementar el contador de liberación de mesas
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        comensales.start(); // Iniciar el hilo de comensales
        liberarMesas.start(); // Iniciar el hilo de liberar mesas
    }
}







