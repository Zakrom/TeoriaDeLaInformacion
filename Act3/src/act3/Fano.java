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
class Fano {

    static List<Caracter> master = new ArrayList<Caracter>();

    static void shannonFano() {
        System.out.println("Inserte numero de caracteres");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int n = 0;
        try {
            n = reader.nextInt();
        } catch (Exception e) {
            System.out.println("Debe insertar un numero entero.");
            String[] args = {};
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
            System.out.println("Debe insertar un numero entero.");
            String[] args = {};
            Act3.main(args);
        }
        double totalFreq = 0;
        for (Caracter c : caracteres) {
            totalFreq += c.getFreq();
        }

        if (totalFreq < 0.95) {
            System.out.println("Frecuencia total debe ser igual a 1.");
        }

        //Ordenar lista
        PriorityQueue<Caracter> queue = new PriorityQueue<Caracter>();
        for (Caracter c : caracteres) {
            queue.offer(c);
        }
        caracteres.clear();
        while (!queue.isEmpty()) {
            caracteres.add(queue.poll());
        }

        //Dividir lista
        List<Caracter> newCaracter = fillTable(caracteres);

        //Mostrar lista
        System.out.println("\t Caracter\t Frecuencia\t Arreglo Binario");
        for (Caracter car : master) {
            System.out.println("\t  " + car.getC() + "\t\t " + car.getFreq() + "\t\t " + car.getBinString());
        }
    }

    private static List<Caracter> fillTable(List<Caracter> caracteres) {

        splitList(caracteres);

        return caracteres;
    }

    private static void splitList(List<Caracter> caracteres) {
        double leftFreq = 0.0;

        //get total percentage in list
        double rightFreq = 0.0;
        for (Caracter c : caracteres) {
            rightFreq += c.getFreq();
        }

        //Comparisons for best splitting option
        double best = 1.0;
        int index = 0;
        for (int i = 0; i < caracteres.size() - 1; i++) {
            Caracter c = caracteres.get(i);
            leftFreq += c.getFreq();
            rightFreq -= c.getFreq();
            System.out.println("Right Freq" + rightFreq + "Left Freq" + leftFreq + "   diff:" + Math.abs(rightFreq - leftFreq));
            if (Math.abs(rightFreq - leftFreq) < best) {
                best = Math.abs(rightFreq - leftFreq);
                index = i;
            } else {
                break;
            }
        }
        List<Caracter> left = new ArrayList<Caracter>();
        List<Caracter> right = new ArrayList<Caracter>();

        for (int j = 0; j < caracteres.size(); j++) {
            if (j <= index) {
                caracteres.get(j).concat("0");
                left.add(caracteres.get(j));
            } else {
                caracteres.get(j).concat("1");
                right.add(caracteres.get(j));
            }
        }

        if (left.size() == 1) {
            master.add(left.get(0));
        } else {
            splitList(left);
        }
        if (right.size() == 1) {
            master.add(right.get(0));
        } else {
            splitList(right);
        }
    }

}
