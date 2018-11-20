public class mRNA extends Feature{

    private String attributes;

    mRNA(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        super(seqid, start, end, score, strand, phase);
        this.attributes = attributes;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "mRNA" + Integer.toString(getStart_gene());
    }
}
