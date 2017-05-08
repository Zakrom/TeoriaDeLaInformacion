/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package channels;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edgar Valdes
 */
public class Channels {

    static int LENGTH = 5;

    static List<String> muestrasA = new ArrayList<String>();
    static List<String> muestrasB = new ArrayList<String>();

    static Object[] optionsA = {"$", "%", "&", "#", "¡", "@"};
    static Object[] optionsB = {"$$", "%", "&&&", "##", "¡¡¡¡", "@@@@@"};

    public static void main(String[] args) {

        fillList(muestrasA, optionsA);
        fillList(muestrasB, optionsB);

        System.out.println("Muestras A:");
        for (String word : muestrasA) {
            System.out.println(word);
        }
        System.out.println("Muestras B:");
        for (String word : muestrasB) {
            System.out.println(word);
        }

        System.out.println("Muestras A size:" + muestrasA.size());
        System.out.println("Muestras B size:" + muestrasB.size());

    }

    static void permute(Object[] a, int k, PermuteCallback callback, List<String> muestras) {
        int n = a.length;

        int[] indexes = new int[k];
        int total = (int) Math.pow(n, k);

        Object[] snapshot = new Object[k];
        while (total-- > 0) {
            for (int i = 0; i < k; i++) {
                snapshot[i] = a[indexes[i]];
            }

            if (callback.handle(snapshot).length() == LENGTH) {
                muestras.add(callback.handle(snapshot));
            }

            for (int i = 0; i < k; i++) {
                if (indexes[i] >= n - 1) {
                    indexes[i] = 0;
                } else {
                    indexes[i]++;
                    break;
                }
            }
        }
    }

    public static interface PermuteCallback {

        public String handle(Object[] snapshot);
    };

    public static void fillList(List<String> muestras, Object[] options) {

        PermuteCallback callback = new PermuteCallback() {

            @Override
            public String handle(Object[] snapshot) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < snapshot.length; i++) {
                    sb.append(snapshot[i]);
                }
                return sb.toString();
            }
        };
        for (int i = 1; i <= LENGTH; i++) {
            permute(options, i, callback, muestras);
        }
    }
}
