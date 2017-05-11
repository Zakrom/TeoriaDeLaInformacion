/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

	public static String RESOURCES = System.getProperty("user.dir") + File.separator + "Resources" + File.separator;
	private static HashMap<String, String> map = new HashMap<String, String>();
	public static char[] alphabet = "ACDEFGHIKLMNOPQRSTUVWY".toCharArray();
	private static List<String> fileContent = new ArrayList<String>();
	private static List<String> replacedFileContent = new ArrayList<String>();

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader rdr = readFile();
		// This is the size of the pattern used for substitutions
		fillDefaultMap(2);
		String line;
		Integer size = 0;
		while ((line = rdr.readLine()) != null) {
			fileContent.add(line + "\n");
			size += (line + "\n").length();
		}
		GenAlgorithm gen = new GenAlgorithm(map, fileContent);
		map = gen.getMap(map);
		replacedFileContent = replaceStrings();
		Huffman huffman = new Huffman(replacedFileContent);
		String binFileContent = huffman.getBinString();

		System.out.println("Original size: " + size * 8);
		System.out.println("New Size: " + binFileContent.length());

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
	}

	private static void saveFile(byte[] bytes) {
		try {
			FileOutputStream fos = new FileOutputStream(RESOURCES + "FASTA.wut");
			fos.write(bytes);
			fos.close();
			System.out.println("file saved!!!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
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

	private static void fillDefaultMap(Integer size) {
		for (int i = 0; i < 10; i++) {
			String word = "";
			for (int j = 0; j < size; j++) {
				word = word + Character.toString(alphabet[nextRandomInt(22)]);
			}
			map.put(String.valueOf(i), word);
		}
	}

	private static List<String> replaceStrings() {
		List<String> replacedStrings = new ArrayList<String>();
		for (String line : fileContent) {
			if (!line.startsWith(">")) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					line = line.replaceAll(value, key);
				}
			}
			replacedStrings.add(line);
		}
		return replacedStrings;
	}

}
