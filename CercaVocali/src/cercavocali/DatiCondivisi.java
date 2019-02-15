/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercavocali;

/**
 * Classe che memorizza il numero di volta che viene ripetuta una vocale
 *
 * @author orsenigo_giacomo
 */
public class DatiCondivisi {

    private final char[] VOCALI;
    /**
     * vettore che contiene il numero di volte che si ripete ogni vocale
     */
    private final int[] num;
    /**
     * booleane che indicano se i thread sono in esecuzione o no
     */
    private final boolean[] esecuzione;

    private Schermo schermo;
    

    /**
     * @param caratteri
     * @brief costruttore
     *
     * Inizializza il vettore {@link #num}
     */
    public DatiCondivisi(char[] caratteri) {
        this.esecuzione = new boolean[5];
        this.num = new int[5];
        for (int i = 0; i < num.length; i++) {
            num[i] = 0;
            esecuzione[i] = true;
        }
        this.schermo = new Schermo();
        VOCALI = caratteri;
    }

    public Schermo getSchermo() {
        return schermo;
    }

    public void scriviSuSchermo(String str) {
        schermo.add(str);
        // System.out.println("scriviSchermo(): "+str);
    }
    private int getIndex(char vocale) {
        for (int i = 0; i < VOCALI.length; i++) {
            if (vocale == VOCALI[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Vocale non trovata");
    }

    public int getNum(char Vocale) {
        return num[getIndex(Vocale)];
    }

    public void incNum(char Vocale) {
        num[getIndex(Vocale)]++;
    }

    /**
     * @brief calcola il massimo
     *
     * trova la vocale che si ripete più volte
     * @return vocale che si ripete più volte
     */
    public char getMax() {
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
     * @brief controlla se i thread sono terminati
     *
     * @return true se tutti i thread sono terminati
     */
    public boolean sonoFinitiTutti() {
        boolean ris = true;
        for (int i = 0; i < 5; i++) {
            if (esecuzione[i]) {
                ris = false;
            }
        }
        return ris;
    }

    /**
     * @brief set terminato
     *
     * imposta come terminato il thread corrispondente alla vocale data
     * @param vocale vocale di cui impostare il thread come terminato
     */
    public void setFinito(char vocale) {
        esecuzione[getIndex(vocale)] = false;
    }

    
//    public void setIniziato(char vocale) {
//        System.out.println("vocale: " + vocale);
//        switch (vocale) {
//            case 'a':
//                esecuzione[0] = true;
//                break;
//            case 'e':
//                esecuzione[1] = true;
//                break;
//            case 'i':
//                esecuzione[2] = true;
//                break;
//            case 'o':
//                esecuzione[3] = true;
//                break;
//            case 'u':
//                esecuzione[4] = true;
//                break;
//        }
//    }

    public String getStringSchermo() {
        return schermo.toString();
    }
}
