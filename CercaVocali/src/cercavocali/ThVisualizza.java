/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercavocali;

/**
 *
 * @author giaco & giodabg
 */
public class ThVisualizza extends Thread {

    private final DatiCondivisi ptrDati;

    public ThVisualizza(DatiCondivisi ptrDati) {
        this.ptrDati = ptrDati;
    }

    @Override
    public void run() {
        final Schermo schermo = ptrDati.getSchermo();
        int numCar = 0;
        while (!ptrDati.sonoFinitiTutti()) {
            schermo.getSemaforo().Wait();
            String s = ptrDati.getStringSchermo();
            if ((s != null) && (numCar < s.length())) {
                numCar = s.length();
                System.out.println(s);
            }
            schermo.getSemaforo().Signal();
            Thread.yield();
            if (isInterrupted()) {
                return;
            }
        }
    }

}
