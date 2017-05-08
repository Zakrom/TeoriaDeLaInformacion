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
class CodificacionAritmetica {

    public static HashMap<String, Double> map = new HashMap<String, Double>();

    static void completa() {
        map = Utils.LecturaDeArchivo();
    }

    static void palabra() {
        map = Utils.LecturaDeArchivo();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Forme una palabra");
        String word = scanner.next();

        String validateWord = word;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            validateWord.replaceAll(entry.getKey(), "");
        }

        if (validateWord.length() != 0) {
            throw new RuntimeException("alabra Invalida");
        }
    }

}
