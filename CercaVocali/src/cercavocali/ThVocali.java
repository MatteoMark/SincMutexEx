/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercavocali;

import java.util.Random;

/**
 * La classe gestisce un thread che ricerca una vocale in una frase e la stampa
 *
 * @author Giacomo Orsenigo
 */
public class ThVocali extends Thread {

    /**
     * puntatore a un oggetto che contiene i dati condivisi
     * 
     * @author Giacomo Orsenigo
     */
    private DatiCondivisi ptrDati;

    /**
     * booleana per decidere se affettuare o no il delay
     *
     * @author Giacomo Orsenigo
     */
    private final boolean delay;

    /**
     * booleana per decidere se effettuare o no lo yield
     *
     * @author Giacomo Orsenigo
     */
    private final boolean yield;

    /**
     * vocale da cercare
     *
     * @author Giacomo Orsenigo
     */
    private final char vocale;

    /**
     * frase in cui cercare la {@link #vocale}
     *
     * @author Giacomo Orsenigo
     */
    private /*static*/ final String frase;

    /**
     * @brief costruttore
     *
     * Inizializza gli attributi {@link #vocale}, {@link #delay},
     * {@link #yield}, {@link #frase} e {@link #ptrDati}
     * @param vocale vocale da cercare
     * @param delay booleana per decidere se affettuare o no il delay
     * @param yield booleana per decidere se effettuare o no lo yield
     * @param frase frase in cui cercare le vocali
     * @param dati puntatore all'oggetto che contiene i dati condivisi
     * @author Giacomo Orsenigo
     */
    public ThVocali(char vocale, boolean delay, boolean yield, String frase, DatiCondivisi dati) {
        this.vocale = vocale;
        this.delay = delay;
        this.yield = yield;
        this.frase = frase;
        this.ptrDati = dati;
    }

    /**
     * @brief cerca le vocali
     *
     * cerca la vocale {@link #vocale} nella stringa {@link #frase} in base agli
     * attributi {@link #delay} e {@link #yield} decide se effettuare o no lo
     * yield e lo sleep. Se trova una vocali la incrementa in {@link Dati#num} e
     * la stampa utilizzanzo la classe {@link Schermo}
     * @author Giacomo Orsenigo
     */
    @Override
    public void run() {
//        ptrDati.setIniziato(vocale);
        try {
            for (int i = 0; i < frase.length(); i++) {
                if (frase.charAt(i) == vocale) {
                    ptrDati.incNum(vocale);
                    ptrDati.getSchermo().getSemaforo().Wait();
                    ptrDati.scriviSuSchermo("trovato: " + vocale);
                    ptrDati.getSchermo().getSemaforo().Signal();
                }

                if (delay) {
                    Random rn = new Random();
                    sleep(rn.nextInt(100));
                }
                if (yield) {
                    yield();
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        ptrDati.setFinito(vocale);
        //System.out.println("Thead " + vocale + " finito");
    }
}
