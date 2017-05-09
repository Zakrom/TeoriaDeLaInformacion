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

}
