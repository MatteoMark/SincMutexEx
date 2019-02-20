/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nbthreadsemaphore;

// https://stackoverflow.com/questions/53622681/java-thread-synchronization-with-semaphores
// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Semaphore.html#acquireUninterruptibly()

/**
 *
 * @author giodabg
 */
public class NBThreadSemaphore {

    public static void main(String[] args) {
        DatiCondivisi datiC = new DatiCondivisi();
        ThLock th1 = new ThLock(datiC);
        ThLock th2 = new ThLock(datiC);
        th1.start();
        th2.start();
    }
}
