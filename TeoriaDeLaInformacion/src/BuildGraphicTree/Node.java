/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuildGraphicTree;

/**
 *
 * @author Erick
 */
public class Node {

    public Node one;
    public Node zero;
    public String code = "";
    public String rama;

    public Node() {
        one = null;
        zero = null;
    }

    public void strgg(int tabs) {
        String tabs2 = "";
        for (int i = 0; i < tabs; i++) {
            tabs2 += " ";
        }
        if (!code.equals("")) {
            System.out.println(tabs2 + "|" + code + "\n");
        }
        if (one != null) {
            System.out.println(tabs2 + "|" + one.rama + "\n");
            one.strgg(tabs + 1);
        }
        if (zero != null) {
            System.out.println(tabs2 + "|" + zero.rama + "\n");
            zero.strgg(tabs + 1);
        }
    }
}
