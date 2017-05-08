/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act3;

import java.util.Scanner;

/**
 *
 * @author Edgar Valdes
 */
public class Act3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione un programa: "
                + "\n\t1.Metodo de Shannon.\n\t"
                + "2.Metodo de Shannon-Fano\n\t"
                + "3.Metodo de Shannon-Fano-Elias\n\t"
                + "4.Expansion Binaria de Neal Coblitz\n\t"
                + "5.Codificacion por Intervalos con Variacion Multiplicativa\n\t"
                + "6.Codificacion por Intervalos con Rastros de la Naturaleza ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Debe seleccionar un numero.");
            Act3.main(args);
        }
        switch (choice) {
            case 1:
                Shannon.shannon();
                break;
            case 2:
                Fano.shannonFano();
                break;
            case 3:
                Elias.shannonFanoElias();
                break;
            case 4:
                //nielCoblitz
                break;
            case 5:
                Intervalos.intervalosMultiplicativa();
                break;
            case 6:
                Intervalos.intervalosNaturaleza();
                break;
            default:
                System.out.println("Opcion invalida.");
                Act3.main(args);
        }
    }

}
