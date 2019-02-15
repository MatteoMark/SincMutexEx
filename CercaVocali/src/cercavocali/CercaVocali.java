/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercavocali;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author orsenigo_giacomo & giodabg
 */
public class CercaVocali {

    /**
     * MAX_TIME = costante che indica il tempo in millisecondi entro cui
     * l'utente deve rispondere
     */
    public static final int TIME_USER = 3000;
    public static final char[] VOCALI = {'a', 'e', 'i', 'o', 'u'};

    /**
     * Scanner per leggere da tastiera
     */
    private static final Scanner SCAN = new Scanner(System.in);

    /**
     * BufferedReader per leggere una vocale in modo non bloccante
     */
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private static DatiCondivisi datiCondivisi = new DatiCondivisi(VOCALI);

    /**
     * @brief entry point
     *
     * Chiede all'utente una frase e la vocale più presente crea 5 tread di
     * classe {@link ThVocali} con le 5 vocali, li avvia e aspetta che abbiano
     * terminato l'esecuzione per vedere se l'utente ha vinto
     * @param args The command line arguments
     */
    public static void main(String[] args) {

        do {
            try {
                String frase = null;
                char vocInserita = ' ';

                System.out.println("Vuoi utilizzare il delay? [S/N]");
                boolean delay = YesNoInput();
                System.out.println("Vuoi utilizzare lo yield? [S/N]");
                boolean yield = YesNoInput();

                System.out.println("Inserisci la frase");
                frase = SCAN.nextLine();

                System.out.println("Quale vocale compare più volte? (Hai 3 secondi per rispondere)");

                //avvio il thread che stampa su console i risultati
                final ThVisualizza vis = new ThVisualizza(datiCondivisi);
                vis.start();

                //creo e avvio i thread
                final ThVocali[] thVocali = new ThVocali[5];
                for (int i = 0; i < thVocali.length; i++) {
                    thVocali[i] = new ThVocali(VOCALI[i], delay, yield, frase, datiCondivisi);
                    thVocali[i].start();
                }

                vocInserita = leggiVocale();

                //aspetto che tutti abbiano finito
                for (ThVocali th : thVocali) {
                    th.join();
                }
                // compreso il thred per la visualizzazione
                vis.join();

                //controllo scelta utente
                char vocVincente = datiCondivisi.getMax();
                if (vocInserita == vocVincente) {
                    System.out.println("Hai indovinato!");
                } else {
                    System.out.println("Non hai indovinato! La vocale giusta era " + vocVincente);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(CercaVocali.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                System.out.println("Tempo scaduto!");
            }
            System.out.println("Vuoi ricominciare? [S/N]");
        } while (YesNoInput());
        System.out.println("Ciao! Alla prossima.");
    }

    /**
     * @brief legge una vocale
     *
     * legge una vocale da {@link #reader} se viene inserita entro 3 secondi
     * @return vocale letta
     * @throws IllegalArgumentException se non viene inserita una vocale
     * @throws TimeoutException se non viene inserito niente entro 3 secondi
     * @author Giacomo Orsenigo
     */
    private static char leggiVocale() throws TimeoutException {
        char ris = ' ';
        try {

            int i = 0;
            while (READER.ready()) { //svuoto il buffer
                READER.read();
            }
            while (!READER.ready() && i < TIME_USER) {
                Thread.sleep(1);
                i++;
            }
            if (READER.ready()) {
                ris = (char) READER.read();
            } else {
                throw new TimeoutException("Tempo scaduto!!");
            }

            if (ris != 'a' && ris != 'e' && ris != 'i' && ris != 'o' && ris != 'u') {
                throw new IllegalArgumentException("Non hai inserito una vocale");
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(CercaVocali.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;

    }

    private static boolean YesNoInput() {
        boolean letto = false;
        boolean continua = false;
        do {
            String s = SCAN.nextLine().toLowerCase();
            if (s.equals("s") || s.equals("si")) {
                letto = true;
                continua = true;
            } else if (s.equals("n") || s.equals("no")) {
                letto = true;
                continua = false;
            }
            else
                System.out.print("inserire [S/N] ");

        } while (!letto);
        return continua;
    }

}
