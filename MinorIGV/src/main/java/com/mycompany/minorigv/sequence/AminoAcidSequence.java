/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv.sequence;

/**
 *
 * @author Stan Wehkamp
 */
public class AminoAcidSequence {
    
    private final Strand strand;             // enum strand duid aan of de sequentie van de positieve of negative strand komt       
    private final String sequence;           // aminozuur sequentie
    private final Integer codonTableKey;     // key value van hasmap met codontabellen 
    
    public AminoAcidSequence(Strand strand, String sequence, Integer codonTableKey) {
        this.strand = strand;
        this.sequence = sequence;
        this.codonTableKey = codonTableKey;
    }

    public Strand getStrand() {
        return strand;
    }

    public String getSequence() {
        return sequence;
    }

    public Integer getCodonTableKey() {
        return codonTableKey;
    }

}