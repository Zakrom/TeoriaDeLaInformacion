/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pattern other = (Pattern) obj;
        if (!Objects.equals(this.pattern, other.pattern)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    public boolean containsPattern(Pattern p) {
        if (this.getPattern().contains(p.getPattern())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The pattern: " + pattern + ", has a value of: " + value;
    }

}
