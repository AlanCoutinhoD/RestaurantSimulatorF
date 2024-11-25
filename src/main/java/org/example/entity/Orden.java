package org.example.entity;

public class Orden {
        private int numeroMesa;   // Número de mesa de donde proviene la orden
        private String estado;    // Estado de la orden (en proceso, listo, etc.)
        private boolean atendido; // true: atendido, false: no atendido
        // Constructor para inicializar la orden
        public Orden(int numeroMesa) {
            this.numeroMesa = numeroMesa;
            this.estado = "EN PROCESO";  // Estado inicial
        }

        // Getter para obtener el número de la mesa
        public int getNumeroMesa() {
            return numeroMesa;
        }

        // Getter para obtener el estado de la orden
        public String getEstado() {
            return estado;
        }

        // Setter para modificar el estado de la orden
        public void setEstado(String estado) {
            this.estado = estado;
        }

    public synchronized void atender() {
        atendido = true;
    }

        // Representación en forma de cadena para imprimir la orden
        @Override
        public String toString() {
            return "Orden [Mesa: " + numeroMesa + ", Estado: " + estado + "]";
        }
    }

