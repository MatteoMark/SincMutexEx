package nbcercavocali;

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
public class NBCercaVocali {

    /**
     * TIME_USER = costante che indica il tempo in millisecondi entro cui
     * l'utente deve rispondere
     */
    public static final int TIME_USER = 3000;

    /**
     * Scanner per leggere da tastiera
     */
    private static final Scanner SCAN = new Scanner(System.in);

    /**
     * BufferedReader per leggere una vocale in modo non bloccante
     */
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    /**
     * datiCondivisi per memorizzare i dati da condividere tra tutti i thread e il main
     */
    private static DatiCondivisi datiCondivisi = new DatiCondivisi();

    /**
     * @brief entry point
     *
     * Chiede all'utente una frase e la vocale più presente
     * crea 5 tread di classe {@link ThVocali} con le 5 vocali, 
     * li avvia e aspetta che abbiano
     * terminato l'esecuzione per vedere se l'utente ha vinto
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {

        boolean continua = true;
        do {
            try {
                String frase = null;
                char vocInserita = ' ';

                System.out.println("Vuoi utilizzare il delay? [S/N]");
                boolean delay = (LeggiSiNo() == 's');
                System.out.println("Vuoi utilizzare lo yield? [S/N]");
                boolean yield = (LeggiSiNo() == 's');

                System.out.println("Inserisci la frase");
                frase = SCAN.nextLine();

                System.out.println("Quale vocale compare più volte? (Hai 3 secondi per rispondere)");

                //avvio il thread che stampa su console i risultati
                final ThVisualizza vis = new ThVisualizza(datiCondivisi);
                vis.start();

                //creo e avvio i thread
                final ThVocali[] thVocali = new ThVocali[Vocali.NUM_VOCALI];
                for (int i = 0; i < thVocali.length; i++) {
                    thVocali[i] = new ThVocali(datiCondivisi.getVocale(i), delay, yield, frase, datiCondivisi);
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
                char vocVincente = datiCondivisi.getVocaleMax();
                if (vocInserita == vocVincente) {
                    System.out.println("Hai indovinato!");
                } else {
                    System.out.println("Non hai indovinato! La vocale giusta era " + vocVincente);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(NBCercaVocali.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                System.out.println("Tempo scaduto!");
            }
            System.out.println("Vuoi ricominciare? [S/N]");
            if (LeggiSiNo() == 's') {
                datiCondivisi.resetDatiCondivisi();
            }
            else
                continua = false;
                    
        } while (continua);
        System.out.println("Ciao! Alla prossima.");
    }

    /**
     * @brief legge una vocale
     *
     * legge una vocale da {@link #reader} se viene inserita entro 3 secondi
     * @return vocale letta
     * @throws TimeoutException se non viene inserito niente entro 3 secondi
     */
    private static char leggiVocale() throws TimeoutException {
        char ris = ' ';
        boolean error = false;
        try {

            int i = 0;
            do {
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
                    throw new TimeoutException();
                }

                error = (ris != 'a' && ris != 'e' && ris != 'i' && ris != 'o' && ris != 'u');
                if (error)
                    System.out.print("Devi inseerire una vocale: ");
            } while (error);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(NBCercaVocali.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;

    }

    private static char LeggiSiNo() {
        char letto = ' ';
        boolean continua = true;
        do {
            String s = SCAN.nextLine().toLowerCase();
            if (s.equals("s") || s.equals("si")) {
                letto = 's';
                continua = false;
            } else if (s.equals("n") || s.equals("no")) {
                letto = 'n';
                continua = false;
            } else {
                System.out.print("inserire [S/N] ");
            }

        } while (continua);
        return letto;
    }

}
