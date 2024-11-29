package org.example.models;

import org.example.entity.Mesa;

import java.util.ArrayList;
import java.util.List;


import org.example.entity.Mesa;
import java.util.ArrayList;
import java.util.List;

public class MonitorP {
    // Lista de mesas
    private List<Mesa> mesas;

    public MonitorP(int cantidadMesas) {
        mesas = new ArrayList<>();
        for (int i = 0; i < cantidadMesas; i++) {
            mesas.add(new Mesa(i + 1)); // Las mesas se numeran desde 1
        }
    }

    // Método para asignar una mesa a un comensal
    public synchronized Mesa asignarMesa() throws InterruptedException {
        while (true) {
            for (Mesa mesa : mesas) {
                if (!mesa.isOcupado()) {
                    mesa.ocupar();
                    System.out.println("Comensal asignado a la mesa " + mesa.getNumero());
                    return mesa;
                }
            }
            // Si todas las mesas están ocupadas, esperar
            System.out.println("No hay mesas disponibles. Comensal esperando...");
            wait();
        }
    }

    // Método para liberar una mesa
    public synchronized void liberarMesa(int numeroMesa) {
        mesas.get(numeroMesa - 1).liberar();
        System.out.println("Mesa " + numeroMesa + " ha sido liberada.");
        notifyAll(); // Notificar a otros hilos que hay una mesa disponible
    }

    // Método para obtener una mesa que no ha sido atendida
    public synchronized Mesa obtenerMesaSinAtender() throws InterruptedException {
        while (true) {
            for (Mesa mesa : mesas) {
                if (mesa.isOcupado() && !mesa.isAtendido()) {
                    System.out.println("Mesero necesita atender la mesa " + mesa.getNumero());
                    return mesa;
                }
            }
            // Si no hay mesas sin atender, esperar
            System.out.println("No hay mesas para atender. Mesero esperando...");
            wait();
        }
    }

    // Método para marcar una mesa como atendida
    public synchronized void atenderMesa(int numeroMesa) {
        mesas.get(numeroMesa - 1).atender();
        System.out.println("Mesa " + numeroMesa + " ha sido atendida.");
        notifyAll(); // Notificar a otros hilos que la mesa ha sido atendida
    }

    // Método para mostrar el estado actual de todas las mesas
    public synchronized void mostrarMesas() {
        System.out.println("Estado de las mesas:");
        for (Mesa mesa : mesas) {
            System.out.println(mesa);
        }
    }
}
