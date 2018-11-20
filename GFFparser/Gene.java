/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GFFparser;

/**
 *
 * @author amber/anne
 */
class Gene {
    private String id;
    private String start_gene;
    private String stop_gene;
    private String score;
    private String strand;
    private String phase;
    private String attributes;

    Gene(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        this.id = seqid;
        this.start_gene = start;
        this.stop_gene = end;
        this.score = score;
        this.strand = strand;
        this.phase = phase;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public String getStart_gene() {
        return start_gene;
    }

    public String getStop_gene() {
        return stop_gene;
    }

    public String getScore() {
        return score;
    }

    public String getStrand() {
        return strand;
    }

    public String getPhase() {
        return phase;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStart_gene(String start_gene) {
        this.start_gene = start_gene;
    }

    public void setStop_gene(String stop_gene) {
        this.stop_gene = stop_gene;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

}