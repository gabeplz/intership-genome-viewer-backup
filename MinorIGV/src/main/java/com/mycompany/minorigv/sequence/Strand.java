package com.mycompany.minorigv.sequence;

public enum Strand {
    NONE, POSITIVE, NEGATIVE;

    public static Strand fromString(String strandString) {
        return strandString.equals("+") ? POSITIVE : (strandString.equals("-") ? NEGATIVE : NONE);
    }
}
