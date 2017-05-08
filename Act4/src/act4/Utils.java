/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Edgar Valdes
 */
public class Utils {

    static HashMap<String, Double> LecturaDeArchivo() {
        HashMap<String, Double> map = new HashMap<String, Double>();

        return null;
    }

    static HashMap<String, Double> LecturaDePalabra() {
        HashMap<String, Double> map = new HashMap<String, Double>();

        String[] args = {};
        System.out.println("Inserte numero de caracteres");
        Scanner reader = new Scanner(System.in);
        int n = 0;
        try {
            n = reader.nextInt();
        } catch (Exception e) {
            System.out.println("Debe insertar un numero entero.");
            Act4.main(args);
        }
        String ca;
        Double f;
        try {
            for (int i = 1; i <= n; i++) {
                System.out.println("inserte caracter");
                ca = reader.next();
                System.out.println("Inserte frecuencia");
                f = reader.nextDouble();
                map.put(ca, f);
            }
        } catch (Exception e) {
            System.out.println("Debe insertar un numero racional y no una operacion.");

            Act4.main(args);
        }
        double totalFreq = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            totalFreq += entry.getValue();
        }

        if (totalFreq < 0.95) {
            System.out.println("Frecuencia total debe ser igual a 1.");
            Act4.main(args);
        }

        return map;
    }

}
