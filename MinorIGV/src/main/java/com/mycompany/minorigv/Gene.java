package com.mycompany.minorigv;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author amber/anne
 */
class Gene extends Feature{

private HashMap attributes;

    Gene(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase);
        this.attributes = attributes;
    }

    public Gene() {

    }

    public HashMap getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

//    @Override
//    public String toString() {
//        return "Gene" +Integer.toString(getStart_gene());
//    }
}