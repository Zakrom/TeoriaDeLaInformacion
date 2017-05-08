/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar Valdes
 */
public class TeoriaDeLaInformacion {

    private static String RESOURCES = System.getProperty("user.dir") + "\\Resources";
    private static HashMap<String, String> map = new HashMap<String, String>();
    public static char[] alphabet = "ACDEFGHIKLMNOPQRSTUVWY".toCharArray();
    private static List<String> fileContent = new ArrayList<String>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader rdr = readFile();
        fillDefaultMap(2);

        String line;
        while ((line = rdr.readLine()) != null) {
            fileContent.add(line);
        }

        GenAlgorithm gen = new GenAlgorithm(map, fileContent);

        map = gen.getMap(map);

    }

    private static BufferedReader readFile() {
        FileReader file = null;
        BufferedReader rdr = null;
        try {
            file = new FileReader(RESOURCES + "\\FASTA.txt");
            rdr = new BufferedReader(file);
            return rdr;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TeoriaDeLaInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rdr;
    }

    static Double seed = 8682522807148012.0;
    static Random generator = new Random(seed.longValue());

    public static Integer nextRandomInt(Integer max) {
        Double num = generator.nextDouble() * (max);
        return num.intValue();
    }

    public static Double nextRandomFloat() {
        Double num = generator.nextDouble();
        return num;
    }

    private static void fillDefaultMap(Integer size) {
        for (int i = 0; i < 10; i++) {
            String word = "";
            for (int j = 0; j < size; j++) {
                word = word + Character.toString(alphabet[nextRandomInt(22)]);
            }
            map.put(String.valueOf(i), word);
        }
    }

}
