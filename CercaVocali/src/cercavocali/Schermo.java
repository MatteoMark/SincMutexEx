/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercavocali;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author giaco 
 */
public class Schermo {

    /**
     * coda di stringhe usata dai thread per scrivere su schermo
     *
     * @author Giacomo Orsenigo
     */
    private Queue<String> buffer;

    private final Semaforo semaforo;

    public Schermo() {
        this.buffer = new ArrayDeque();
        this.semaforo = new Semaforo(1);
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public Queue<String> getBuffer() {
        return buffer;
    }
   
    public void add(String str) {
        buffer.add(str);
    }
    
    @Override
    public String toString() {
        return buffer.toString();
    }
}
