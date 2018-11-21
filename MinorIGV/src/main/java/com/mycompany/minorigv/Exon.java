package com.mycompany.minorigv;
import java.util.HashMap;

public class Exon extends Feature{
    private HashMap attributes;

    Exon(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase);
        this.attributes = attributes;
    }

    public HashMap getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }


}
