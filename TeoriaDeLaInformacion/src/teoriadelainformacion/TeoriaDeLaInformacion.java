/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeoriaDeLaInformacion {

    public static String RESOURCES = System.getProperty("user.dir") + File.separator + "Resources" + File.separator;
    private static HashMap<String, String> replacedChars = new HashMap<String, String>();
    public static char[] alphabet = "ACDEFGHIKLMNOPQRSTUVWY".toCharArray();
    private static List<String> fileContent = new ArrayList<String>();
    private static List<String> replacedFileContent = new ArrayList<String>();

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione un programa: "
                + "\n\t1.Compresi\u00f3n.\n\t"
                + "2.Decompresi\u00f3n.\n\t"
                + "3.Obtener cadenas de Amino\u00e1cidos completas.(En Ingl\u00e9s)\n\t"
                + "4. Todos los pasos");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Debe seleccionar un numero.");
            TeoriaDeLaInformacion.main(args);
        }
        //Menu de opciones
        switch (choice) {
            case 1:
                compress();
                break;
            case 2:
                decompress();
                break;
            case 3:
                getProteins();
                break;
            case 4:
                compress();
                decompress();
                getProteins();
                break;
            default:
                System.out.println("Opcion invalida.");
                TeoriaDeLaInformacion.main(args);
        }
    }

    private static void replaceChars() {
        for (int i = 0; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            if (!line.startsWith(">")) {
                for (Entry<String, String> entry : replacedChars.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
                fileContent.set(i, line);
            }
        }
    }

    private static String parseBytes(byte[] bytes) {
        String content = "";
        for (int i = 0; i < bytes.length; i++) {
            String bits = Integer.toBinaryString(bytes[i]);
            bits = String.format("%8s", bits).replace(' ', '0');
            bits = bits.substring(bits.length() - 8, bits.length());
            content = content + bits;
        }
        content = content.substring(0, content.length() - 1);
        content = content.substring(0, content.lastIndexOf("1"));
        return content;
    }

    private static byte[] readWutFile() throws IOException {
        InputStream in = new FileInputStream(RESOURCES + "FASTA.wut");
        long fileSize = new File(RESOURCES + "FASTA.wut").length();
        byte[] allBytes = new byte[(int) fileSize];
        in.read(allBytes);
        return allBytes;
    }

    private static void decompress() throws IOException {
        byte[] bytesArray = readWutFile();
        String bytes = parseBytes(bytesArray);
        Huffman huffman = new Huffman(bytes);
        fileContent = huffman.getFileContent();
        Properties properties = loadProperties("replacements.properties");
        replacedChars = fillMap(properties);
        replaceChars();
        writeFile("FASTA2.txt", fileContent);
    }

    private static void getProteins() throws IOException {
        BufferedReader rdr = readFile();
        String line;
        String newLine;
        List<String> newLines = new ArrayList<String>();
        while ((line = rdr.readLine()) != null) {
            if (!line.startsWith(">")) {
                newLine = replaceLineProteins(line);
                newLines.add(newLine);
            } else {
                newLines.add(line);
                if (newLines.size() > 1) {
                    String noComma = newLines.get(newLines.size() - 3).substring(0, newLines.get(newLines.size() - 3).length() - 1);
                    newLines.set(newLines.size() - 3, noComma);
                }
            }
        }
        writeFile("FullReplacement.txt", newLines);
    }

    private static void compress() throws IOException {
        BufferedReader rdr = readFile();
        // This is the size of the pattern used for substitutions

        String line;
        Integer size = 0;
        while ((line = rdr.readLine()) != null) {
            fileContent.add(line + "\n");
            size += (line + "\n").length();
        }

        GenAlgorithm gen = new GenAlgorithm(fileContent);

        for (int i = 10; i > 1; i--) {
            if (i == 10) {
                replacedChars = gen.makeMap(i);
            } else {
                gen.makeMap(i);
            }
        }
        DecimalFormat formatter = new DecimalFormat("#0.0000");

        replacedFileContent = replaceStrings();

        Integer afterReplaceSize = 0;

        for (String s : replacedFileContent) {
            afterReplaceSize += s.length();
        }
        Double substitution = 100.0 - ((100.0 / size) * afterReplaceSize);
        System.out.println("Porcentaje de archivo sustituido:" + formatter.format(substitution));

        Huffman huffman = new Huffman(replacedFileContent);
        String binFileContent = huffman.getBinString();

        System.out.println("Tama\u00f1o original: " + size * 8);
        System.out.println("Nuevo tama\u00f1o: " + binFileContent.length());
        Double compressionRatio = size * 8.0 / binFileContent.length() / 10;

        System.out.println("Radio de compresion = " + formatter.format(compressionRatio * 10));
        // ensure (n mod 8 bits) == 0
        StringBuilder bldr = new StringBuilder();
        if (binFileContent.length() % 8 == 0) {
            bldr.append("10000001");
        } else if (binFileContent.length() % 8 == 7) {
            bldr.append("100000001");
        } else {
            int lastSize = 8 - (binFileContent.length() % 8);
            for (int i = 0; i < lastSize; i++) {
                if (i == 0 || i == lastSize - 1) {
                    bldr.append("1");
                } else {
                    bldr.append("0");
                }
            }
        }
        binFileContent = binFileContent.concat(bldr.toString());
        byte[] bytes = getByteByString(binFileContent);
        saveFile(bytes);
        saveReplacements();
    }

    private static void saveFile(byte[] bytes) {
        try {
            FileOutputStream fos = new FileOutputStream(RESOURCES + "FASTA.wut");
            fos.write(bytes);
            fos.close();
            System.out.println("Archivo guardado!!!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName, List<String> newFileContent) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(RESOURCES + fileName, "UTF-8");
        for (String line : newFileContent) {
            writer.println(line);
        }
        writer.close();
    }

    private static void saveReplacements() throws IOException {
        Properties prop = new Properties();
        FileOutputStream fos = new FileOutputStream(TeoriaDeLaInformacion.RESOURCES + "replacements.properties");

        for (Entry<String, String> entry : replacedChars.entrySet()) {
            String key = entry.getKey().toString();
            String val = entry.getValue();
            prop.setProperty(key, val);
        }
        prop.store(fos, "Stored replacements");
        fos.close();
    }

    private static byte[] getByteByString(String binFileContent) {
        int SPLIT_SIZE = 8;

        int index = 0;
        int position = 0;

        byte[] resultByteArray = new byte[binFileContent.length() / SPLIT_SIZE];
        StringBuilder builder = new StringBuilder(binFileContent);

        while (index < builder.length()) {
            String byteString = builder.substring(index, Math.min(index + SPLIT_SIZE, builder.length()));
            Integer byteAsInt = Integer.parseInt(byteString, 2);
            resultByteArray[position] = byteAsInt.byteValue();
            index += SPLIT_SIZE;
            position++;
        }
        return resultByteArray;
    }

    private static BufferedReader readFile() {
        FileReader file = null;
        BufferedReader rdr = null;
        try {
            file = new FileReader(RESOURCES + "FASTA.txt");
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

    private static List<String> replaceStrings() {
        List<String> replacedStrings = new ArrayList<String>();
        for (String line : fileContent) {
            if (!line.startsWith(">")) {
                for (Map.Entry<String, String> entry : replacedChars.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    line = line.replaceAll(value, key);
                }
            }
            replacedStrings.add(line);
        }
        return replacedStrings;
    }

    public static Properties loadProperties(String fileName) throws IOException {
        // TODO Auto-generated method stub
        Properties properties = new Properties();
        InputStream in = new FileInputStream(RESOURCES + fileName);
        properties.load(in);
        return properties;
    }

    private static HashMap<String, String> fillMap(Properties properties) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            map.put(key, val);
        }
        return map;
    }

    private static String replaceLineProteins(String line) {
        String newLine = "";

        for (int i = 0; i < line.length(); i++) {
            if (Protein.getByCode(Character.toString(line.charAt(i)).toLowerCase()) != null) {
                newLine = newLine + Protein.getByCode(Character.toString(line.charAt(i)).toLowerCase()).getProtein() + ",";
            }
        }
        return newLine;
    }

}
