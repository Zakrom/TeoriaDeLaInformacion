/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Intervalos {

    static void intervalosMultiplicativa() {
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
        Double mult;
        try {
            for (int i = 1; i <= n; i++) {
                Caracter caracter = new Caracter();
                System.out.println("inserte caracter:");
                ca = reader.next();
                caracter.setC(ca);
                System.out.println("Inserte frecuencia:");
                f = reader.nextDouble();
                System.out.println("Inserte valor por el que se multiplicara dicha frecuencia:");
                mult = reader.nextDouble();
                caracter.setFreq(f * mult);
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
        for (Caracter c : caracteres) {
            c.setFreq(c.getFreq() / totalFreq);
            System.out.println(c.getFreq());
        }
        List<Caracter> newCaracter = Shannon.fillTable(caracteres);

        System.out.println("\t Caracter\t Frecuencia\t Arreglo Binario");
        for (Caracter car : newCaracter) {
            System.out.println("\t  " + car.getC() + "\t\t " + car.getFreq() + "\t\t " + car.getBinString());
        }

    }

    static void intervalosNaturaleza() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
