
/**
 *
 * @author amber/anne
 */
class Gene extends Feature{

private String attributes;

    Gene(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
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
        return "Gene" +Integer.toString(getStart_gene());
    }
}