package com.mycompany.minorigv.gffparser;

public enum FeatureTypes {


    GENE("Gene","Genes","gene"),
    CDS("CDS","CDS","CDS"),
    EXON("Exon","Exonen","exon"),
    MRNA("mRNA","mRNA","mRNA"),
    REGION("Region","Region","region");

    private final String CLASS_NAME;
    private final String READABLE_NAME;
    private final String GFF_NAME;


    FeatureTypes(String className, String name,String gff_name) {
        CLASS_NAME = className;
        READABLE_NAME = name;
        GFF_NAME = gff_name;
    
    }

    public String getClassName(){
        return this.CLASS_NAME;
    }

    public String getNAME(){
        return this.READABLE_NAME;
    }

    public String getGffName(){
        return this.GFF_NAME;
    }


}
