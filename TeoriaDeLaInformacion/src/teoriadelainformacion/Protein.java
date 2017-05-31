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
public enum Protein {
    A("a", "Alanine"),
    B("b", "Aspartic Acid or Arsparagine"),
    C("c", "Cysteine"),
    D("d", "Aspartic Acid"),
    E("e", "Glutamic Acid"),
    F("f", "Phenylalanine"),
    G("g", "Glycine"),
    H("h", "Histidine"),
    I("i", "Isoleucine"),
    J("j", "Leucine or Isoleucine"),
    K("k", "Lysine"),
    L("l", "Leucine"),
    M("m", "Methionine"),
    N("n", "Asparagine"),
    O("o", "Pyrrolysine"),
    P("p", "Proline"),
    Q("q", "Glutamine"),
    R("r", "Arginine"),
    S("s", "Serine"),
    T("t", "Threonine"),
    U("u", "Selenocysteine"),
    V("v", "Valine"),
    W("w", "Tryptophan"),
    X("x", "Any Amino Acid"),
    Y("y", "Tyrosine"),
    Z("z", "Glutamic Acid or Glutamine");

    private String code;

    private String protein;

    Protein(String code, String protein) {
        this.code = code;
        this.protein = protein;
    }

    public static Protein getByCode(String code) {
        for (Protein protein : Protein.values()) {
            if (protein.code.equals(code)) {
                return protein;
            }
        }
        return null;
    }

    public String getProtein() {
        return protein;
    }

    public String getCode() {
        return code;
    }

}
