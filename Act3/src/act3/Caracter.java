/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act3;

import java.util.Comparator;

/**
 *
 * @author Edgar Valdes
 */
public class Caracter implements Comparable<Caracter> {

    String c;
    Double freq;
    String binString;

    public Caracter() {
        this.binString = "";
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Double getFreq() {
        return freq;
    }

    public void setFreq(Double freq) {
        this.freq = freq;
    }

    public String getBinString() {
        return binString;
    }

    public void setBinString(String binString) {
        this.binString = binString;
    }

    public void concat(String bin) {
        this.binString = this.binString + bin;
    }

    @Override
    public int compareTo(Caracter other) {
        if (this.getFreq() < other.getFreq()) {
            return 1;
        }
        if (this.getFreq() > other.getFreq()) {
            return -1;
        }
        return 0;
    }

}
