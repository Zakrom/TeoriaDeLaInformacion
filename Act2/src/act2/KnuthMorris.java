package act2;

public class KnuthMorris {

    public static void main(String args[]) {
        String pattern = "abac";
        String txt = "ababadabacaabac";
        kmp(pattern, txt);
    }

    static void computeLPS(String pattern, int[] lps) {
        int currMaxLPS = 0;
        lps[0] = 0;

        for (int i = 1; i < pattern.length();) {
            if (pattern.charAt(i) == pattern.charAt(currMaxLPS)) {
                currMaxLPS++;
                lps[i] = currMaxLPS;
                i++;
            } else if (currMaxLPS != 0) {
                currMaxLPS = lps[currMaxLPS - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }

    static void kmp(String pattern, String txt) {
        int lps[] = new int[pattern.length()];
        computeLPS(pattern, lps);

        int txtPos = 0, patternPos = 0;
        while (txtPos < txt.length()) {
            if (pattern.charAt(patternPos) == txt.charAt(txtPos)) {
                patternPos++;
                txtPos++;
            }

            if (patternPos == pattern.length()) {
                System.out.println("Patron encontrado en: " + (txtPos - patternPos));
                patternPos = lps[patternPos - 1];
            } else if (pattern.charAt(patternPos) != txt.charAt(txtPos)) {
                if (patternPos != 0) {
                    patternPos = lps[patternPos - 1];
                } else {
                    txtPos++;
                }
            }
        }
    }

}
