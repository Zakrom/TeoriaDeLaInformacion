/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act2;

/**
 *
 * @author Edgar Valdes
 */
public class Tree implements Comparable<Tree> {

    public int frequency;

    public Tree(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Tree other) {
        return this.frequency - other.frequency;
    }
}
