package org.example.models;

import javafx.application.Platform;
import org.example.interfaces.Interfaces;

import java.util.Random;

public class MonitorMesas {
    private boolean[] mesas;  // Representa el estado de cada mesa (true = ocupada, false = libre)
    private final Interfaces gameLogic;
   private boolean hayComensales = false;
    public MonitorMesas(int numMesas, Interfaces gameLogic) {
        this.mesas = new boolean[numMesas];  // Inicializamos las mesas como libres (false)
        this.gameLogic = gameLogic;
    }


    // Método para que el comensal ocupe una mesa
    public synchronized int ocuparMesa() {
        // Si no hay mesas libres, el comensal debe esperar
        while (hayMesasLibres() == false) {
            try {
                System.out.println("NO HAY MESAS DISPONIBLES");
                this.wait();  // El comensal espera hasta que haya una mesa libre
            } catch (InterruptedException e) {
                System.out.println("Error en la espera del comensal: " + e.getMessage());
            }
        }

        // Encontramos la primera mesa libre
        for (int i = 0; i < mesas.length; i++) {
            if (!mesas[i]) {  // Si la mesa está libre
                mesas[i] = true;  // Ocupamos la mesa
                int posicion = i;
                System.out.println("Comensal ocupa la mesa: " + (i));  // Mostramos qué mesa fue ocupada
                Platform.runLater(() -> {
                    gameLogic.CrearComensal(posicion);
                    gameLogic.MoverComensal(posicion,posicion);
                    //gameLogic.MoverMesero(1,1);
                });
                // Despertamos a cualquier hilo que esté esperando una mesa libre
                notifyAll();

                return i;  // Devolvemos el índice de la mesa ocupada
            }
        }

        // Si llegamos aquí, significa que no hay mesas libres
        return -1;  // Retorna -1 si no se pudo ocupar ninguna mesa
    }


    // Método para obtener una mesa con un comensal

    public synchronized int getMesaConComensal() throws InterruptedException {
        Random random = new Random();

        // Intentamos obtener una mesa ocupada de manera aleatoria
        for (int i = 0; i < mesas.length; i++) {
            // Generamos un índice aleatorio entre 0 y mesas.length - 1
            int mesaAleatoria = random.nextInt(mesas.length);

            // Verificamos si la mesa está ocupada
            if (mesas[mesaAleatoria]) {  // Si la mesa está ocupada
                Platform.runLater(() -> {
                    gameLogic.MoverMesero(1,(mesaAleatoria));
                });
                System.out.println("Mesero esta ahora en la mesa " + (mesaAleatoria));  // Mesa con comensal

                return mesaAleatoria;  // Retorna el ID de la mesa ocupada
            }
        }

        // Si no hay comensales en ninguna mesa, retorna -1
        this.wait();
        return -1;
    }


    // Método para liberar una mesa
    public synchronized void liberarMesa(int mesaId) {
        // Liberamos la mesa (la marcamos como libre)
        mesas[mesaId] = false;
        System.out.println("Comensal abandono la mesa" + mesaId );  // Mesa con comensal
        // Actualizamos la interfaz para reflejar que la mesa ha sido desocupada
        Platform.runLater(() -> {
            gameLogic.MoverComensal(mesaId, 5);  // Mueve al comensal fuera de la mesa
        });

        // Notificamos a los hilos que hay una mesa libre para que otro comensal pueda ocuparla
        notifyAll();
    }


    // Método para verificar si hay mesas libres
    private boolean hayMesasLibres() {
        for (boolean mesa : mesas) {
            if (!mesa) {
                return true;  // Hay una mesa libre
            }
        }
        return false;  // No hay mesas libres
    }
}
