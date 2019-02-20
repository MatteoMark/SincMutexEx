package nbcercavocali;

/**
 * Classe che memorizza il numero di volta che viene ripetuta ogni vocale
 *
 * @author giodabg
 */
public class Vocali {

    private static final char[] VOCALI = {'a', 'e', 'i', 'o', 'u'};
    public static int NUM_VOCALI = 5;
    /**
     * vettore che contiene il numero di volte che si ripete ogni vocale
     */
    private final int[] num;

    public Vocali(int[] num) {
        this.num = num;
    }

    public Vocali() {
        this.num = new int[VOCALI.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = 0;
        }
    }

    public synchronized int getNum(char Vocale) {
        return num[getIndex(Vocale)];
    }

    public synchronized void incNum(char Vocale) {
        num[getIndex(Vocale)]++;
    }

    /**
     * @brief restituisce l'indice dell'elemento del vettore in cui è presenta
     * la vocale
     *
     * @param vocale da ricercare nel vettore VOCALI
     * @return indice del vettore VOCALI in cui si trova la vocale
     */
    public synchronized int getIndex(char vocale) {
        for (int i = 0; i < VOCALI.length; i++) {
            if (vocale == VOCALI[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Vocale non trovata");
    }

    /**
     * @brief trova la vocale che si ripete più volte
     *
     * @return vocale che si ripete più volte
     */
    public synchronized char getMax() {
        int max = -1;
        int indexMax = 0;
        for (int i = 0; i < num.length; i++) {
            if (num[i] > max) {
                indexMax = i;
                max = num[i];
            }
        }
        return VOCALI[indexMax];
    }

    /**
     * @brief restituisce la vocale corrispondente a index
     *
     * @param index indice del vettore VOCALI
     * @return vocale corrispondente
     */
    public synchronized char getVocale(int index) {
        return VOCALI[index];
    }

    /**
     * @brief riporta a zero il numero delle vocali presenti in num
     *
     */
    public void reset() {
        for (int i = 0; i < num.length; i++) {
            num[i] = 0;
        }
    }

}
