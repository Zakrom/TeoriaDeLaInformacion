package teoriadelainformacion;

public class HuffmanResult {

	public int freq;
	public char value;
	public String binArray;

	HuffmanResult(char value, int freq, String binArray) {
		this.freq = freq;
		this.value = value;
		this.binArray = binArray;
	}

	@Override
	public String toString() {
		return "HuffmanResult{" + "freq=" + freq + ", value=" + value + ", binArray=" + binArray + '}';
	}

	/**
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * @param freq
	 *            the freq to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * @return the value
	 */
	public char getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(char value) {
		this.value = value;
	}

	/**
	 * @return the binArray
	 */
	public String getBinArray() {
		return binArray;
	}

	/**
	 * @param binArray
	 *            the binArray to set
	 */
	public void setBinArray(String binArray) {
		this.binArray = binArray;
	}

}
