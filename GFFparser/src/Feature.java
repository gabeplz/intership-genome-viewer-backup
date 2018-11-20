public class Feature {
    private String id;
    private String start_gene;
    private String stop_gene;
    private String score;
    private String strand;
    private String phase;

    Feature(String seqid, String start, String end, String score, String strand, String phase) {
        this.id = seqid;
        this.start_gene = start;
        this.stop_gene = end;
        this.score = score;
        this.strand = strand;
        this.phase = phase;
    }

    public Feature() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStart_gene() {
        return Integer.parseInt(start_gene);
    }

    public void setStart_gene(String start_gene) {
        this.start_gene = start_gene;
    }

    public int getStop_gene() {
        return Integer.parseInt(stop_gene);
    }

    public void setStop_gene(String stop_gene) {
        this.stop_gene = stop_gene;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
