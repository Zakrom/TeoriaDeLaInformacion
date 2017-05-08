package act2;

import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {

    private final int R;
    private final int[] right;     // tabla de saltos
    private final String pat;

    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < pat.length(); j++) {
            right[pat.charAt(j)] = j;
        }
    }

    //regresa N si no encuentra nada
    public ArrayList<Integer> search(String txt) {
        int M = pat.length();
        int N = txt.length();
        ArrayList<Integer> newArrayInt = new ArrayList<Integer>();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                newArrayInt.add(i);    // se agrega
                skip++;
            }
        }
        return newArrayInt;
    }

    public static void main(String[] args) {
        String pat = "abcegnkaojfioaabcddacabccs";
        String txt = "abc aabda";

        BoyerMoore boyermoore1 = new BoyerMoore(pat);

        ArrayList<Integer> offset = boyermoore1.search(txt);

        for (int i : offset) {
            System.out.println("Patron encontrado en: " + i);
        }
    }

}
