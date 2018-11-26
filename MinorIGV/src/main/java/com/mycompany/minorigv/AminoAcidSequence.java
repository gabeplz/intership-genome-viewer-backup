/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv;

import java.util.List;

/**
 *
 * @author Gebruiker
 */
public class AminoAcidSequence {
 //   private final Strand strand;    
    private final Strand strand;
    private final int start;                // Genomic position for start of sequence.
    private final String sequence; 
    private final Integer codonTableKey;
    
    public AminoAcidSequence(Strand strand, int startPosition, String sequence, Integer codonTableKey) {
        this.strand = strand;
        this.start = startPosition;
        this.sequence = sequence;
        this.codonTableKey = codonTableKey;
    }
    public Strand getStrand() {
        return strand;
    }

    public int getStart() {
        return start;
    }

    public String getSequence() {
        return sequence;
    }

//    public boolean hasNonNullSequence() {
//        return nonNullSequence;
//    }

    public Integer getCodonTableKey() {
        return codonTableKey;
    }

}