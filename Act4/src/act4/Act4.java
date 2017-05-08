/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package act4;

import java.util.Scanner;

/**
 *
 * @author Edgar Valdes
 */
public class Act4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione un programa: "
                + "\n\t1.Codificacion Aritmetica, leyendo de libro\n\t"
                + "2.Codificacion aritmetica con frecuencias y palabra");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Debe seleccionar un numero.");
            Act4.main(args);
        }
        switch (choice) {
            case 1:
                CodificacionAritmetica.completa();
                break;
            case 2:
                CodificacionAritmetica.palabra();
                break;
            default:
                System.out.println("Opcion invalida.");
                Act4.main(args);
        }
        // TODO code application logic here
    }

}
