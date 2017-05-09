/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

public class Leaf extends Tree {

    char value;

    public Leaf(int freq, char value) {
        super(freq);
        this.value = value;
    }

}
