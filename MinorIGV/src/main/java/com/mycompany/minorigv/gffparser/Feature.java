package com.mycompany.minorigv.gffparser;

import javax.swing.text.html.parser.Element;
import java.util.HashMap;

/**
 * Features wordt overgeërfd door de classes CDS, Chromosome, Exon, Gene en Region.
 * Verder maakt deze class getters en setters voor id, start, stop, score, strand en phase.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class Feature extends Elements {
    private String id;
//    private String start;
//    private String stop;
    private String score;
//    private String strand;
    private String phase;
    private HashMap attributes;

    /**
     * De constructor.
     * @param seqid     Het ID van het contig/chromosoom waarin de feature aanwezig is.
     * @param start     Start positie van de feature op het chromosoom/contig
     * @param end       Stop positie van de feature op het chromosoom/contig
     * @param score     De score van de feature.
     * @param strand    Of de feature aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase     Het geeft het readingframe aan waarin het feature voorkomt (0,1,2).
     */
    Feature(String seqid, int start, int end, String score, String strand, String phase, HashMap attributes) {
        super(start, end, strand);
        this.id = seqid;
        this.score = score;
        this.attributes = attributes;
        this.phase = phase;
    }

    /**
     * Het returned het id van het chromosoom/contig.
     * @return      id is een String. Het is het id van het chromosoom/contig. Id is een String.
     */
    public String getId() {
        return id;
    }

    /**
     * Het genereerd het id van het chromosoom/contig.
     * @param id   is een String. Het is het id van het chromosoom/contig.
     */
    public void setId(String id) {
        this.id = id;
    }

//    /**
//     * Het returned de start positie van een feature.
//     * @return      start is de positie waar de feature begint. Start is een Integer
//     */
//    public int getStart() {
//        return Integer.parseInt(start);
//    }
//
//    /**
//     * Het genereerd de start positie van een feature.
//     * @param start is de positie waar de feature begint.
//     */
//    public void setStart(String start) {
//        this.start = start;
//    }
//
//    /**
//     * Het returned de stop positie van een feature.
//     * @return      stop is de positie waar de feature eindigd. Stop is een Integer.
//     */
//    public int getStop() {
//        return Integer.parseInt(stop);
//    }
//
//    /**
//     * Het genereerd de stop positie van een feature.
//     * @param stop  is de positie waar de feature eindigd.
//     */
//    public void setStop(String stop) {
//        this.stop = stop;
//    }

    /**
     * Het returned de score van een feature.
     * @return      score is de score van een feature. Score is een String.
     */
    public String getScore() {
        return score;
    }

    /**
     * Het genereerd de score van een feature.
     * @param score is de score van een feature.
     */
    public void setScore(String score) {
        this.score = score;
    }

//    /**
//     * Het returned de strand waarop de feature zich bevindt.
//     * @return      strand zegt of de feature aanwezig is in de strand (+) of complementaire strand (-). Strand is een String.
//     */
//    public String getStrand() {
//        return strand;
//    }
//
//    /**
//     * Het genereerd de strand waarop de feature zich bevindt.
//     * @param strand  zegt of de feature aanwezig is in de strand (+) of complementaire strand (-)
//     */
//    public void setStrand(String strand) {
//        this.strand = strand;
//    }
//
    /**
     * Het returned de phase van de feature.
     * @return      phase geeft het readingframe aan waarin het feature voorkomt (0,1,2). Phase is een String.
     */
    public String getPhase() {
        return phase;
    }

    /**
     * Het genereerd de phase van de feature.
     * @param phase geeft het readingframe aan waarin het feature voorkomt (0,1,2).
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /**
     * Ophalen van de attributen/informatie van een feature.
     *
     * @return     HashMap met als key de omschrijving van de informatie (bijv. name) en
     *             als value de specifieke informatie van de feature (bijv. PAU8).
     */
    public HashMap getAttributes() {
        return attributes;
    }

    /**
     * @param attributes    Het maken van de HashMap met daarin de informatie: als key de omschrijving van de informatie (bijv. name)
     *                      en als value de specifieke informatie van een feature (bijv. PAU8).
     */
    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }
}
