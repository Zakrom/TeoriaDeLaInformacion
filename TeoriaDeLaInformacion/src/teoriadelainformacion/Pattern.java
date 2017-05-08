/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

/**
 *
 * @author Edgar Valdes
 */
public class Pattern implements Comparable<Pattern> {

    private String pattern;
    private Integer value;

    public Pattern(String pattern, Integer value) {
        this.pattern = pattern;
        this.value = value;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(Pattern other) {
        if (this.getValue() < other.getValue()) {
            return 1;
        }
        if (this.getValue() > other.getValue()) {
            return -1;
        }
        return 0;
    }
}
