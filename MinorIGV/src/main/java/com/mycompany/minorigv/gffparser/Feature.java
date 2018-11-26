package com.mycompany.minorigv.gffparser;

/**
 * Features wordt overgeërfd door de classes CDS, Chromosome, Exon, Gene en Region.
 * Verder maakt deze class getters en setters voor id, start, stop, score, strand en phase.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class Feature {
    private String id;
    private String start;
    private String stop;
    private String score;
    private String strand;
    private String phase;

    /**
     * De constructor.
     * @param seqid     Het ID van het contig/chromosoom waarin de feature aanwezig is.
     * @param start     Start positie van de feature op het genoom
     * @param end       Stop positie van de feature op het genoom
     * @param score     De score van de feature.
     * @param strand    Of de feature aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase     Het geeft het readingframe aan waarin het feature voorkomt (0,1,2).
     */
    Feature(String seqid, String start, String end, String score, String strand, String phase) {
        this.id = seqid;
        this.start = start;
        this.stop = end;
        this.score = score;
        this.strand = strand;
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

    /**
     * Het returned de start positie van een feature.
     * @return      start is de positie waar de feature begint. Start is een Integer
     */
    public int getStart() {
        return Integer.parseInt(start);
    }

    /**
     * Het genereerd de start positie van een feature.
     * @param start is de positie waar de feature begint.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Het returned de stop positie van een feature.
     * @return      stop is de positie waar de feature eindigd. Stop is een Integer.
     */
    public int getStop() {
        return Integer.parseInt(stop);
    }

    /**
     * Het genereerd de stop positie van een feature.
     * @param stop  is de positie waar de feature eindigd.
     */
    public void setStop(String stop) {
        this.stop = stop;
    }

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

    /**
     * Het returned de strand waarop de feature zich bevindt.
     * @return      strand zegt of de feature aanwezig is in de strand (+) of complementaire strand (-). Strand is een String.
     */
    public String getStrand() {
        return strand;
    }

    /**
     * Het genereerd de strand waarop de feature zich bevindt.
     * @param strand  zegt of de feature aanwezig is in de strand (+) of complementaire strand (-)
     */
    public void setStrand(String strand) {
        this.strand = strand;
    }

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
     * Het retured hoe het object wordt geprint. Wanneer het object nu wordt geprint
     * wordt niet de locatie geprint, maar print het op de manier hoe het hier is aangegeven.
     * @return het object in een string.
     */
//    @Override
//    public String toString() {
//        return  this.getClass().toString() +
//                "    start_gene='" + start + '\'' +
//                ", stop_gene='" + stop + '\'' +
//                ", strand='" + strand + '\'' +
//                '}';
//    }


}
