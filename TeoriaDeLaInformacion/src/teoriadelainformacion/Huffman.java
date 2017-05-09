package teoriadelainformacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Huffman {

	static double entropia = 0;
	static double infMut = 0;
	static double compress = 0;
	// static HashMap<Character, String> result;
	List<HuffmanResult> results = new ArrayList<HuffmanResult>();
	final int R = 256;// Todos los caracteres ASCII
	HashMap<Character, Integer> map = new HashMap<Character, Integer>();

	char[] wordArray;
	int[] charFreqs;

	public Huffman(List<String> replacedFileContent) {
		getFreqs(replacedFileContent);
		wordArray = new char[map.size()];
		charFreqs = new int[map.size()];

		int arrayPlacement = 0;
		for (Entry<Character, Integer> entry : map.entrySet()) {
			Character key = entry.getKey();
			Integer value = entry.getValue();
			wordArray[arrayPlacement] = key;
			charFreqs[arrayPlacement] = value;
			arrayPlacement++;
		}
		System.out.println("Key: " + wordArray.toString() + " \nValue: " + charFreqs.toString());

		HuffmanJ.run(wordArray, charFreqs);
	}

	private void getFreqs(List<String> replacedFileContent) {
		for (String line : replacedFileContent) {
			for (int i = 0; i < line.length(); i++) {
				Character key = line.charAt(i);
				if (map.containsKey(key)) {
					map.put(key, map.get(key) + 1);
				} else {
					map.put(key, 1);
				}
			}
		}
	}

}
