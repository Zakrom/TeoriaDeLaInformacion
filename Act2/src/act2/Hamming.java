package act2;

import java.util.ArrayList;
import java.util.List;

public class Hamming {

    public static void main(String[] args) {
        String s1 = "calcular";
        String s2 = "ralculac";

        if (s1.length() != s2.length()) {
            System.out.println("");
        }

        System.out.println("Distancia de Hamming:\t" + getHammingDistance(s1, s2));

    }

    public static Integer getHammingDistance(String s1, String s2) {

        List<Character> temp1 = new ArrayList<Character>();
        List<Character> temp2 = new ArrayList<Character>();

        int distance = 0;
        char[] arrayS1 = s1.toCharArray();
        char[] arrayS2 = s2.toCharArray();

        for (int i = 0; i < s1.length(); i++) {
            if (arrayS1[i] != arrayS2[i]) {
                distance++;
                if (temp2.contains(arrayS1[i])) {
                    int pos1 = i;
                    int pos2 = temp2.indexOf(arrayS1[i]);
                    distance += pos1 - pos2;
                    temp2.remove(pos2);
                }
                if (temp1.contains(arrayS2[i])) {
                    int pos2 = i;
                    int pos1 = temp1.indexOf(arrayS2[i]);
                    distance += pos2 - pos1;
                    temp1.remove(pos1);
                }

                temp1.add(arrayS1[i]);
                temp2.add(arrayS2[i]);

            }
        }
        return distance;
    }
}
