/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act3;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author Edgar Valdes
 */
class Shannon {

    static void shannon() {
        String[] args = {};
        System.out.println("Inserte numero de caracteres");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int n = 0;
        try {
            n = reader.nextInt();
        } catch (Exception e) {
            System.out.println("Debe insertar un numero entero.");
            Act3.main(args);
        }
        List<Caracter> caracteres = new ArrayList<Caracter>();
        String ca;
        Double f;
        try {
            for (int i = 1; i <= n; i++) {
                Caracter caracter = new Caracter();
                System.out.println("inserte caracter");
                ca = reader.next();
                caracter.setC(ca);
                System.out.println("Inserte frecuencia");
                f = reader.nextDouble();
                caracter.setFreq(f);
                caracteres.add(caracter);
            }
        } catch (Exception e) {
            System.out.println("Debe insertar un numero racional y no una operacion.");

            Act3.main(args);
        }
        double totalFreq = 0;
        for (Caracter c : caracteres) {
            totalFreq += c.getFreq();
        }

        if (totalFreq < 0.95) {
            System.out.println("Frecuencia total debe ser igual a 1.");
            Act3.main(args);
        }

        List<Caracter> newCaracter = fillTable(caracteres);

        System.out.println("\t Caracter\t Frecuencia\t Arreglo Binario");
        for (Caracter car : newCaracter) {
            System.out.println("\t  " + car.getC() + "\t\t " + car.getFreq() + "\t\t " + car.getBinString());
        }

    }

    public static List<Caracter> fillTable(List<Caracter> caracteres) {
        PriorityQueue<Caracter> queue = new PriorityQueue<Caracter>();

        for (Caracter c : caracteres) {
            queue.offer(c);
        }

        //PriorityQueue queue = new PriorityQueue(caracteres);
        List<Caracter> newCaracter = new ArrayList<Caracter>();
        double initDiv = 0;
        while (!queue.isEmpty()) {
            double div = initDiv;
            Caracter c = (Caracter) queue.poll();
            //TODO binarizacion
            String binString = "";
            Integer longitud = ((Double) Math.ceil((-(Math.log(c.getFreq()) / Math.log(2))))).intValue();
            do {
                div = div * 2;
                if (div >= 1) {
                    binString = binString + "1";
                    div = div - 1;
                } else {
                    binString = binString + "0";
                }
            } while (binString.length() < longitud);
            c.setBinString(binString);
            newCaracter.add(c);
            initDiv = initDiv + c.getFreq();
        }
        return newCaracter;
    }

}
