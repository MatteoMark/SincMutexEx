package nbcercavocali;


/**
 * Classe che memorizza il numero di volta che viene ripetuta una vocale
 *
 * @author orsenigo_giacomo
 */
public class DatiCondivisi {

    /**
     * booleane che indicano se i thread sono in thTerminato o no
     */
    private final boolean[] thTerminato;

    private Schermo schermo;
        
    private Vocali vocali;
    
    /**
     * @brief costruttore
     *
     * Inizializza le vocali, lo schermo e il vettore che indica quali thread sono terminati
     */
    public DatiCondivisi() {
        this.thTerminato = new boolean[Vocali.NUM_VOCALI];
        for (int i = 0; i < thTerminato.length; i++) {
            thTerminato[i] = false;
        }
        this.vocali = new Vocali();
        this.schermo = new Schermo();
    }

    public void resetDatiCondivisi() {
        for (int i = 0; i < thTerminato.length; i++) {
            thTerminato[i] = false;
        }
        this.vocali.reset();
        this.schermo.reset();
    }

    public void scriviSuSchermo(String str) {
        schermo.add(str);
    }
    
    /**
     * @brief controlla se i thread sono terminati
     *
     * @return true se tutti i thread sono terminati
     */
    public boolean sonoFinitiTutti() {
        boolean ris = true;
        for (int i = 0; i < 5; i++) {
            if (!thTerminato[i]) {
                ris = false;
            }
        }
        return ris;
    }

    /**
     * @brief set terminato
     *
     * imposta come terminato il thread corrispondente alla vocale data
     * @param vocale di cui impostare il thread come terminato
     */
    public void setFinito(char vocale) {
        thTerminato[vocali.getIndex(vocale)] = true;
    }

    public String getStringSchermo() {
        return schermo.toString();
    }
    
    public char getVocaleMax() {
        return vocali.getMax();
    }

    public void incNum(char vocale) {
        vocali.incNum(vocale);
    }
    
    public char getVocale(int index) {
        return vocali.getVocale(index);
    }
}
