package org.example.threads;
import org.example.entity.Mesa;
import org.example.models.MonitorP;
public class Comensal extends Thread {
    private MonitorP monitorMesas;
    private int idComensal;

    public Comensal(MonitorP monitorMesas, int idComensal) {
        this.monitorMesas = monitorMesas;
        this.idComensal = idComensal;
    }

    @Override
    public void run() {
        try {
            // El comensal intenta ocupar una mesa
            Mesa mesa = monitorMesas.asignarMesa();
            System.out.println("Comensal " + idComensal + " ocupa " + mesa);

            // Simular que el comensal está comiendo (por un tiempo aleatorio)
            Thread.sleep((int) (3000 + Math.random() * 2000)); // 3-5 segundos comiendo

            // Después de comer, libera la mesa
            monitorMesas.liberarMesa(mesa.getNumero());
            System.out.println("Comensal " + idComensal + " ha dejado " + mesa);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
