package teoriadelainformacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanJ {

    public static List<String> newFileContent = new ArrayList<String>();
    static double entropia = 0;
    static double infMut = 0;
    double compress = 0;
    // static HashMap<Character, String> result;
    static List<HuffmanResult> results = new ArrayList<HuffmanResult>();

    final int R = 256;// Todos los caracteres ASCII

    public static double getCompressionRate() {
        double sum = 0;

        for (HuffmanResult obj : results) {
            sum += obj.freq * obj.binArray.length() / 100.0;
        }

        return 8 / sum;
    }

    public int[] generateFreqs(String word) {
        int[] x = new int[R];

        for (char c : word.toCharArray()) {
            x[c] = 0;
        }

        for (char c : word.toCharArray()) {
            x[c] += 1;
        }

        return x;
    }

    // halp, no me convence usar 2 listas para un nuevo string
    public char[] removeDuplicates(String s) {
        String temp2 = "";// string sin duplicados

        List<Character> listC = new ArrayList<Character>();
        for (char c : s.toCharArray()) {
            listC.add(c);
        }

        List<Character> temp = new ArrayList<Character>();
        for (Character c : listC) {
            if (!temp.contains(c)) {
                temp.add(c);
            }
        }

        String noRep = "";
        for (Character c : temp) {
            noRep = noRep + c.toString();
        }
        System.out.println(noRep);
        return noRep.toCharArray();
    }

    public static Tree buildTree(int[] charFreqs, char[] usedChars) {
        PriorityQueue<Tree> trees = new PriorityQueue<Tree>();

        // crear arboles
        for (int i = 0; i < usedChars.length; i++) {
            if (charFreqs[i] > 0) {
                trees.offer(new Leaf(charFreqs[i], usedChars[i]));
            }
        }

        // unir nodos
        while (trees.size() > 1) {
            // traer arboles peor evaluados
            Tree left = trees.poll();
            Tree right = trees.poll();

            // unir como nuev arbol y regresar a lista
            trees.offer(new Node(left, right));
        }

        // regresa arbol generado
        return trees.peek();
    }

    public static void getCodes(Tree tree, StringBuffer compressed) {

        if (tree instanceof Leaf) {
            Leaf leaf = (Leaf) tree;
            infMut += (Math.log((double) leaf.frequency / 100.0) / Math.log(2.0));
            entropia += (Math.log((double) leaf.frequency / 100.0) / Math.log(2.0)) * (leaf.frequency / 100.0);
            // Agregar a tabla hash e imprimir prefijo si es hoja
            results.add(new HuffmanResult(leaf.value, leaf.frequency, compressed.toString()));
            // result.put(leaf.value, compressed.toString());

            //AQUI
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + compressed);
            newFileContent.add(leaf.value + "\t" + leaf.frequency + "\t" + compressed);
        } else if (tree instanceof Node) {
            Node node = (Node) tree;

            int nodeDif = node.right.frequency - node.left.frequency;

            infMut += Math.log((double) nodeDif / 100) / Math.log(2.0);
            entropia += (Math.log((double) nodeDif / 100.0) / Math.log(2.0)) * ((double) nodeDif / 100.0);
            // recorre a por la izquierda agregando un 0
            compressed.append('0');
            getCodes(node.left, compressed);
            compressed.deleteCharAt(compressed.length() - 1);

            // recorre por la derecha agregando un 1
            compressed.append('1');
            getCodes(node.right, compressed);
            compressed.deleteCharAt(compressed.length() - 1);
        }

    }

    public HuffmanJ() {
    }

    public static List<HuffmanResult> run(char[] wordArray, int[] charFreqs) {
        if (charFreqs.length != wordArray.length) {
            System.out.println("Arreglo de caracteres y arreglo de frecuencias no tienen el mismo tamaño");
        } else {
            Tree tree = buildTree(charFreqs, wordArray);

            //ESTE TAMBIEN
            System.out.println("SIMBOLO\tFRECUENCIA\tCODIGO");
            newFileContent.add("SIMBOLO\tFRECUENCIA\tCODIGO");
            getCodes(tree, new StringBuffer());

            try {
                TeoriaDeLaInformacion.writeFile("TablaDeCodificacion.txt", newFileContent);
            } catch (IOException e) {
                System.out.println("File context not found.\n" + e.getMessage());
            }
            entropia = entropia * (-1);
            infMut = infMut * (-1);
        }

        return results;
    }
}
