package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends GameApplication {

    private Entity redPoint; // Punto rojo principal
    private Point2D targetPosition; // Posición de destino a donde se mueve el punto rojo
    private boolean isMoving = false; // Indicador de si el punto está en movimiento

    // Los 4 puntos azules donde los puntos rojos deben ir
    private List<Point2D> bluePoints = new ArrayList<>();
    private List<Entity> bluePointEntities = new ArrayList<>();
    private List<Boolean> pointOccupied = new ArrayList<>(); // Para verificar si un punto azul está ocupado

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Puntos Azules y Puntos Rojos");
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        // Crear el primer punto rojo en la posición inicial (100, 300)
        redPoint = FXGL.entityBuilder()
                .at(100, 300) // Posición inicial
                .view(new Circle(20, Color.RED)) // Círculo rojo
                .buildAndAttach();

        // Definir los 4 puntos azules en el lado derecho de la pantalla
        bluePoints.add(new Point2D(600, 100));
        bluePoints.add(new Point2D(600, 200));
        bluePoints.add(new Point2D(600, 300));
        bluePoints.add(new Point2D(600, 400));

        // Inicializar el estado de ocupación de los puntos (todos vacíos al inicio)
        for (int i = 0; i < bluePoints.size(); i++) {
            pointOccupied.add(false); // Todos los puntos están vacíos al principio
        }

        // Dibujar los puntos azules en el lado derecho de la pantalla
        for (Point2D point : bluePoints) {
            Entity bluePointEntity = FXGL.entityBuilder()
                    .at(point.getX(), point.getY()) // Colocar el círculo en la posición del punto
                    .view(new Circle(10, Color.BLUE)) // Círculo azul para marcar los puntos
                    .buildAndAttach();
            bluePointEntities.add(bluePointEntity); // Guardar la entidad para poder acceder a ella
        }

        // Iniciar el proceso de mover el punto rojo hacia un punto azul
        moveToRandomBluePoint();
    }

    private void moveToRandomBluePoint() {
        // Buscar puntos azules vacíos
        List<Integer> availablePoints = new ArrayList<>();
        for (int i = 0; i < pointOccupied.size(); i++) {
            if (!pointOccupied.get(i)) {
                availablePoints.add(i); // Agregar el índice de los puntos vacíos
            }
        }

        if (availablePoints.size() > 0) {
            // Elegir un punto azul vacío aleatorio
            Random rand = new Random();
            int randomIndex = availablePoints.get(rand.nextInt(availablePoints.size()));
            targetPosition = bluePoints.get(randomIndex);
            pointOccupied.set(randomIndex, true); // Marcar el punto azul como ocupado

            // Iniciar el movimiento hacia el punto azul seleccionado
            isMoving = true;
        } else {
            // Si todos los puntos están ocupados, el punto rojo regresa a la posición inicial
            System.out.println("Todos los puntos están ocupados. Esperando...");
            targetPosition = new Point2D(100, 300); // Regresar al inicio
            isMoving = true; // Iniciar el movimiento de regreso
        }
    }

    @Override
    protected void onUpdate(double tpf) {
        if (isMoving) {
            // Obtener la posición actual del punto rojo
            double redPointX = redPoint.getX();
            double redPointY = redPoint.getY();

            // Calcular el movimiento hacia la posición de destino
            double deltaX = targetPosition.getX() - redPointX;
            double deltaY = targetPosition.getY() - redPointY;

            // Si el punto rojo no ha llegado a su destino, moverlo
            if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
                // Movimiento gradual hacia el destino
                redPoint.translate(deltaX * 0.05, deltaY * 0.05); // Factor de multiplicación para un movimiento suave
            } else {
                // Si el punto rojo ha llegado al destino, detener el movimiento
                isMoving = false;
                System.out.println("Punto rojo llegó al punto azul: " + targetPosition);

                // Generar un nuevo punto rojo que irá a otro punto azul
                generateNewRedPoint();
                // Mover el nuevo punto rojo a otro punto azul disponible
                moveToRandomBluePoint();
            }
        }
    }

    private void generateNewRedPoint() {
        // Crear un nuevo punto rojo en la posición inicial (100, 300)
        Entity newRedPoint = FXGL.entityBuilder()
                .at(100, 300) // Posición inicial
                .view(new Circle(20, Color.RED)) // Círculo rojo
                .buildAndAttach();

        redPoint = newRedPoint; // Actualizar la referencia al nuevo punto rojo
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación
    }
}
