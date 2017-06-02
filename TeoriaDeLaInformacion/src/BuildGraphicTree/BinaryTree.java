/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuildGraphicTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;
import teoriadelainformacion.HuffmanResult;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Erick
 */
public class BinaryTree {

    public static void main(String[] args) throws IOException {
        List<HuffmanResult> strings = new ArrayList<HuffmanResult>();

        Properties properties = teoriadelainformacion.TeoriaDeLaInformacion.loadProperties("characters.properties");

        for (Entry<Object, Object> entry : properties.entrySet()) {
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();

            String binVal = v.substring(0, v.lastIndexOf(","));
            String freq = v.substring(v.lastIndexOf(",") + 1, v.length());
            byte[] bytes = DatatypeConverter.parseHexBinary(k);
            String realKey = new String(bytes);
            HuffmanResult hr;
            hr = new HuffmanResult();
            hr.setValue(realKey.charAt(0));
            hr.setFreq(Integer.parseInt(freq));
            hr.setBinArray(binVal);

            strings.add(hr);

        }

        Node root = new Node();
        for (HuffmanResult s : strings) {
            createBinaryTree(root, s.getBinArray(), s.getValue(), s.getFreq());
        }
        root.strgg(0);
    }

    public static void createBinaryTree(Node root, String str, Character ch, Integer s) {
        if (str.length() < 1) {
            return;
        }
        boolean leaf = str.length() == 1;
        String a = str.substring(0, 1);
        if (a.equals("1")) {
            if (root.one == null) {
                Node n = new Node();
                n.rama = a;
                if (leaf) {
                    n.code = ch.toString();
                    root.one = n;
                    System.out.println("Leaf : " + ch);
                } else {
                    root.one = n;
                    createBinaryTree(n, str.substring(1), ch, s);
                }
            } else {
                createBinaryTree(root.one, str.substring(1), ch, s);
            }
        }
        if (a.equals("0")) {
            if (root.zero == null) {
                Node n = new Node();
                n.rama = a;
                if (leaf) {
                    n.code = ch.toString();
                    root.zero = n;
                    System.out.println("Leaf : " + ch);
                } else {
                    root.zero = n;
                    createBinaryTree(n, str.substring(1), ch, s);
                }
            } else {
                createBinaryTree(root.zero, str.substring(1), ch, s);
            }
        }
    }
}
