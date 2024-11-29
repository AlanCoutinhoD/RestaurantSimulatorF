package org.example;

import org.example.models.MonitorCocina;
import org.example.models.MonitorP;
import org.example.threads.Cocinero;
import org.example.threads.Comensal;
import org.example.threads.Mesero;

public class Main {
    public static void main(String[] args) {
        int cantidadMesas = 4;      // Número total de mesas en el restaurante


        // Crear los monitores
        MonitorP monitorMesas = new MonitorP(cantidadMesas);
        MonitorCocina monitorCocina = new MonitorCocina();

        // Crear y lanzar un hilo de mesero
        new Mesero(monitorMesas, monitorCocina, "Mesero 1").start();

        // Crear y lanzar un hilo de cocinero
        new Cocinero(monitorCocina, "Cocinero 1").start();

        // Generar comensales de forma infinita
        int contadorComensales = 1; // Contador para nombrar a los comensales
        while (true) {
            new Comensal(monitorMesas, monitorCocina, "Comensal " + contadorComensales++).start();
            try {
                Thread.sleep(1000); // Simular la llegada de comensales cada 1 segundo
            } catch (InterruptedException e) {
                System.out.println("Error en la llegada de comensales: " + e.getMessage());
            }
        }
    }
}

