/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nbthreadsemaphore;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scuola
 */
public class ThLock extends Thread {

    private DatiCondivisi ptdDati;

    public ThLock(DatiCondivisi ptdDati) {
        this.ptdDati = ptdDati;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                ptdDati.waitNum();
                System.out.println(currentThread().getName() + ": Locks acquired >> " + ptdDati.availablePermits());
                ptdDati.incNum();
                ptdDati.signalNum();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThLock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
