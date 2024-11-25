package org.example.threads;
import org.example.models.MonitorP;
public class Comensal extends Thread{
    private MonitorP monitorP;


    public Comensal(MonitorP monitorP, String name) {
        super(name);
        this.monitorP = monitorP;
    }


    @Override

    public void run() {
        monitorP.ocuparMesa();
        // System.out.println(Thread.currentThread().getName() + "Ocupando mesa ");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }


}
