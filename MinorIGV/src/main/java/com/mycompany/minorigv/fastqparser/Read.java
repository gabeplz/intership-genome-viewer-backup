package com.mycompany.minorigv.fastqparser;

/**
 * deze class representeerd een contig read
 * bevat de constructor voor Read
 */
public class Read {
   private String description;
   private String sequence;
   private String qualitySequence;

    /**
     * Constructor voor Read
     * @param description begint met een '@' karakter en word gevolgd door een sequentie identificator and een optionele omschrijving
     * @param sequence
     * @param qualitySequence
     */
    public Read(String description, String sequence, String qualitySequence) {
        this.description = description;
        this.sequence = sequence;
        this.qualitySequence = qualitySequence;
    }

    /**
     * getter
     * @return String description
     */
       public String getDescription(){
        return this.description;
    }
    /**
     * getter
     * @return String sequence
     */
    public String getReadSequence(){
        return this.sequence;
    }
    /**
     * getter
     * @return String QualitySequence
     */
    public String getQualitySequence(){
        return this.qualitySequence;
    }
}
