package org.example.interfaces;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Interfaces {

    private final Map<Integer, Entity> comensales = new HashMap<>();  // Para gestionar comensales por su ID
    private Entity waiter; // El mesero (punto amarillo)
    private Entity mesa1,mesa2,mesa3,mesa4;
    private Point2D waiterPosition,mesa1Position,mesa2Position,mesa3Position,mesa4Position;


    public void CrearDivisiones() {
        // Crear los bordes del restaurante usando Rectángulos

        // Rectángulo superior (línea horizontal)
        FXGL.entityBuilder()
                .at(150, 50)  // Posición inicial
                .view(new Rectangle(500, 5, Color.BLACK))  // Un rectángulo delgado para la línea superior
                .buildAndAttach();

        // Rectángulo izquierdo (línea vertical)
        FXGL.entityBuilder()
                .at(650, 50)  // Posición inicial
                .view(new Rectangle(5, 500, Color.BLACK))  // Un rectángulo delgado para la línea izquierda
                .buildAndAttach();

        // Rectángulo derecho (línea vertical)
        FXGL.entityBuilder()
                .at(150, 50)  // Posición de la línea derecha
                .view(new Rectangle(5, 500, Color.BLACK))  // Un rectángulo delgado para la línea derecha
                .buildAndAttach();

        // Rectángulo inferior (línea horizontal)
        FXGL.entityBuilder()
                .at(150, 550)  // Posición de la línea inferior
                .view(new Rectangle(500, 5, Color.BLACK))  // Un rectángulo delgado para la línea inferior
                .buildAndAttach();
    }


    // Crear las mesas y la cocina
    public void CrearMesas() {
        // Cargar la textura de la mesa
        Texture mesaTexture = FXGL.getAssetLoader().loadTexture("mesa.png");
        Texture mesa2Texture = FXGL.getAssetLoader().loadTexture("mesa.png");
        Texture mesa3Texture = FXGL.getAssetLoader().loadTexture("mesa.png");
        Texture mesa4Texture = FXGL.getAssetLoader().loadTexture("mesa.png");


        // Crear Mesa 1
        mesa1 = FXGL.entityBuilder()
                .at(150, 120)  // Posición de la mesa 1
                .view(mesaTexture)  // Asignar la imagen escalada
                .buildAndAttach();
        mesa1Position = new Point2D(200, 400);
        mesa1.setScaleX(0.030);
        mesa1.setScaleY(0.030);


        // Crear Mesa 2
        mesa2 = FXGL.entityBuilder()
                .at(150, 200)  // Posición de la mesa 2
                .view(mesa2Texture)  // Asignar la imagen escalada
                .buildAndAttach();
        mesa2.setScaleX(0.030);
        mesa2.setScaleY(0.030);
        waiterPosition = new Point2D(200, 200);


        // Crear Mesa 3
        mesa3 = FXGL.entityBuilder()
                .at(150, 300)  // Posición de la mesa 3
                .view(mesa3Texture)  // Asignar la imagen escalada
                .buildAndAttach();
        mesa3.setScaleX(0.030);
        mesa3.setScaleY(0.030);
        waiterPosition = new Point2D(100, 500);
        // Crear Mesa 4


        mesa4 = FXGL.entityBuilder()
                .at(150, 400)  // Posición de la mesa 4
                .view(mesa4Texture)  // Asignar la imagen escalada
                .buildAndAttach();
        mesa4.setScaleX(0.030);
        mesa4.setScaleY(0.030);
        waiterPosition = new Point2D(100, 500);
    }

    // Crear la cocina
    public void CrearCocina() {
        // Cargar la imagen para el mesero
        Texture cocineroTexture= FXGL.getAssetLoader().loadTexture("cocinero.png");
        Texture meseroTexture = FXGL.getAssetLoader().loadTexture("mesero.png");
        waiter = FXGL.entityBuilder()
                .at(300, 450)
                .view(meseroTexture)
                .buildAndAttach();
        waiter.setScaleX(0.2);
        waiter.setScaleY(0.2);
        waiterPosition = new Point2D(100, 500);

        Entity cocinero = FXGL.entityBuilder()
                .at(480, 450)
                .view(cocineroTexture)
                .buildAndAttach();
        cocinero.setScaleX(0.05);
        cocinero.setScaleY(0.05);
    }

    // Crear un nuevo comensal (usando una imagen) con un ID específico
    public void CrearComensal(int id) {
        if (comensales.size() < 4) {  // Aseguramos que no haya más de 4 comensales
            // Cargar la imagen para el comensal
            Texture clienteTexture = FXGL.getAssetLoader().loadTexture("comensal.png");

// Carga la imagen (debe estar en la carpeta de recursos)

            Entity comensal = FXGL.entityBuilder()
                    .at(50, 300)  // Siempre comienza en la entrada
                    .view(clienteTexture)
                    .buildAndAttach();

            comensal.setScaleX(0.050);
            comensal.setScaleY(0.050);
            comensales.put(id, comensal);  // Guardamos el comensal en el mapa usando su ID
        }
    }

    // Mover comensal a la mesa o la entrada
    public void MoverComensal(int id, int mesaId) {
        Entity comensal = comensales.get(id);

        if (comensal != null) {

            Point2D destino = new Point2D(0, 0);  // Por defecto va a la entrada

            // Cambiar la posición dependiendo de la mesa a la que va
            switch (mesaId) {
                case 6:
                    destino = new Point2D(50, 300);  // Regreso a la entrada
                    break;
                case 0:
                    destino = new Point2D(200, 100);  // Mesa 1
                    break;
                case 1:
                    destino = new Point2D(200, 200);  // Mesa 2
                    break;
                case 2:
                    destino = new Point2D(200, 300);  // Mesa 3
                    break;

                case 3:
                    destino = new Point2D(200, 400);  // Mesa 4
                    break;
            }

            // Mover el comensal a la nueva posición de manera inmediata
            comensal.setPosition(destino);

            // Si regresa a la entrada, el comensal desaparece
            if (mesaId == 5) {

                System.out.println("Borrado" + id);  // Mesa con comensal
                FXGL.runOnce(() -> comensales.remove(id), Duration.seconds(0));  // Eliminar comensal después de moverse
            }
        }
    }

    // Mover al mesero
    public void MoverMesero(int id, int mesaId) {
        Entity mesero = waiter;

            Point2D destino = new Point2D(100, 800);

            // Cambiar la posición dependiendo de la mesa o la cocina a la que va
            switch (mesaId) {
                case 4:
                    destino = new Point2D(400, 440);  // Cocina
                    break;
                case 0:
                    destino = new Point2D(250, 100);  // Mesa 1
                    break;
                case 1:
                    destino = new Point2D(250, 200);  // Mesa 2
                    break;
                case 2:
                    destino = new Point2D(250, 300);  // Mesa 3
                    break;
                case 3:
                    destino = new Point2D(250, 400);  // Mesa 4
                    break;
            }

            // Mover el mesero a la nueva posición
            mesero.setPosition(destino);


            if (mesaId == 0) {
                FXGL.runOnce(() -> {

                    //mesero.removeFromWorld();  // Eliminar mesero de la escena cuando regrese a la cocina
                }, Duration.seconds(0)); // Inmediatamente después de moverse a la cocina
            }

    }

}
