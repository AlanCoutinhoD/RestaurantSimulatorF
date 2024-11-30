package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import org.example.interfaces.Interfaces;
import org.example.models.MonitorCocina;
import org.example.models.MonitorMesas;
import org.example.threads.Cocinero;
import org.example.threads.Comensal;
import org.example.threads.Mesero;

import javafx.application.Platform;

public class Main extends GameApplication {

    private Interfaces gameLogic; // Instancia de la interfaz gráfica
    private MonitorMesas monitorMesas;
    private MonitorCocina monitorCocina;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Restaurant Simulator");
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        // Inicializar la lógica de la interfaz
        gameLogic = new Interfaces();
        gameLogic.CrearMesas();
        gameLogic.CrearCocina();
        gameLogic.CrearDivisiones();

        // Inicializar los monitores
        monitorMesas = new MonitorMesas(4, gameLogic); // Asumimos que hay 4 mesas
        monitorCocina = new MonitorCocina(gameLogic);

        // Crear y lanzar un hilo de mesero
        new Mesero(monitorMesas, monitorCocina, "Mesero").start();

        // Crear y lanzar un hilo de cocinero
        new Cocinero(monitorCocina,gameLogic).start();

        // Crear comensales y lanzar los hilos
        int contadorComensales = 1;

        Runnable comensalCreacion = () -> {
            while (true) {
                // Espera 1 segundo antes de crear el siguiente comensal
                try {
                    Thread.sleep(1000);  // Espera 1 segundo entre comensales
                    Platform.runLater(() -> {
                        new Comensal(monitorMesas, monitorCocina, "Comensal ", gameLogic).start();

                    });
                } catch (InterruptedException e) {
                    System.out.println("Error en la llegada de comensales: " + e.getMessage());
                }
            }
        };

        // Iniciar el hilo para la creación de comensales
        new Thread(comensalCreacion).start();
    }

    public static void main(String[] args) {
        launch(args); // Lanzar la aplicación de FXGL
    }
}
