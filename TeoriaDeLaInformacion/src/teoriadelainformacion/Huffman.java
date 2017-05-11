package teoriadelainformacion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

public class Huffman {

	static double entropia = 0;
	static double infMut = 0;
	static double compress = 0;
	// static HashMap<Character, String> result;
	List<HuffmanResult> results = new ArrayList<HuffmanResult>();
	final int R = 256;// Todos los caracteres ASCII
	private static String binFileContent;

	//
	HashMap<Character, Integer> freqs = new HashMap<Character, Integer>();
	HashMap<Character, String> binArrays = new HashMap<Character, String>();

	char[] wordArray;
	int[] charFreqs;

	public Huffman(List<String> replacedFileContent) throws IOException {
		getFreqs(replacedFileContent);
		wordArray = new char[freqs.size()];
		charFreqs = new int[freqs.size()];

		// Generate an array with characters and their frequencies
		int arrayPlacement = 0;
		for (Entry<Character, Integer> entry : freqs.entrySet()) {
			Character key = entry.getKey();
			Integer value = entry.getValue();
			wordArray[arrayPlacement] = key;
			charFreqs[arrayPlacement] = value;
			arrayPlacement++;
		}
		// Run huffman an generate a map with replacements

		for (HuffmanResult val : HuffmanJ.run(wordArray, charFreqs)) {
			binArrays.put(val.getValue(), val.getBinArray());
		}
		fillBinString(replacedFileContent);
		saveBinArrays();
	}

	private void saveBinArrays() throws IOException {
		Properties prop = new Properties();
		FileOutputStream fos = new FileOutputStream(TeoriaDeLaInformacion.RESOURCES + "characters.properties");

		for (Entry<Character, String> entry : binArrays.entrySet()) {
			String key = entry.getKey().toString();
			String val = entry.getValue();
			byte[] bytes = key.getBytes("UTF-8");
			String hexString = DatatypeConverter.printHexBinary(bytes);
			prop.setProperty(hexString, val);
		}
		prop.store(fos, "Generated tree,stored and needed for decompression");
		fos.close();

	}

	private void getFreqs(List<String> replacedFileContent) {
		for (String line : replacedFileContent) {
			for (int i = 0; i < line.length(); i++) {
				Character key = line.charAt(i);
				if (freqs.containsKey(key)) {
					freqs.put(key, freqs.get(key) + 1);
				} else {
					freqs.put(key, 1);
				}
			}
		}
	}

	public void fillBinString(List<String> replacedFileContent) {
		binFileContent = "";
		StringBuilder builder = new StringBuilder();

		for (String line : replacedFileContent) {
			for (int i = 0; i < line.length(); i++) {
				builder.append(binArrays.get(line.charAt(i)));
			}
		}
		binFileContent = builder.toString();
	}

	public String getBinString() {
		return binFileContent;
	}

}
