package nbcercavocali;

/**
 * La classe gestisce un thread che visualizza continuamento il contenuto dello schermo
 *
 * @author Giacomo Orsenigo
 */
public class ThVisualizza extends Thread {

    private final DatiCondivisi ptrDati;

    public ThVisualizza(DatiCondivisi ptrDati) {
        this.ptrDati = ptrDati;
    }

    @Override
    public void run() {
        int numCar = 0;
        while (!ptrDati.sonoFinitiTutti()) {
            String s = ptrDati.getStringSchermo();
            if ((s != null) && (numCar < s.length())) {
                numCar = s.length();
                System.out.println(s);
            }
            Thread.yield();
            if (isInterrupted()) {
                System.out.println("ThVisualizza interrotto");
                return;
            }
        }
        // System.out.println("ThVisualizza finito");
    }

}
