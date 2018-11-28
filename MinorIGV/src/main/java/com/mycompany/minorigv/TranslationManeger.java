/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2007-2015 Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.minorigv;

import static java.nio.file.Files.list;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jrobinso, Stan Wehkamp
 * 
 * start method passes arguments needed for the constructors for CodonTable, AminoAcidSequence and to the own class method getaminoacied. 
 * contains static classes to translate amino acids and make reverse complemantary sequence 
 */
public class TranslationManeger {
    
    public static final String[] BASE_SEQUENCES = {"TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",      //hardcoded as the codon order is permanent in the format of the files
                                                   "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
                                                   "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"};
    
    public static final String TEST_AMINOVOLGORDE = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";
    public static final String TEST_AMINOSTARTS =  "---M---------------M---------------M----------------------------";      // TEST_AMINOVOLGORDE, TEST_AMINOSTARTS will be read from a file in the future
    
    private LinkedHashMap<Integer, CodonTabel> allCodonTabels = new LinkedHashMap<Integer, CodonTabel>();
   /**
    * returns a array containing 6 AminoacidSeqeance objects
    * @param chrom
    * @return 
    */ 
   public static AminoAcidSequence[] start(chromosometest chrom) {
        String[] namen = {"chromosome1","kaas","baas"};                             //namen wil be read in from a file in the future
        CodonTabel numer1 = CodonTabel.build(1, namen, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS); 
        
        
        
        String sequence = chrom.getRefsequence();
        
        int start = 0;                      // currently overly complicated way to give the following variables the values:
        int mod = start % 3;                // n1 = 0, n2 = 1, n2 = 2
        int n1 = normalize3(3 - mod);;      // in future start may be based on the reference frame and location on chromosome
        int n2 = normalize3(n1 + 1);
        int n3 = normalize3(n2 + 1);


        
        String AminoAcidsP1 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n1) , numer1);  // 6 amino acid strings are made for eacht reading frame
        String AminoAcidsP2 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n2) , numer1);
        String AminoAcidsP3 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n3) , numer1);
        

        final int len = sequence.length();
        String AminoAcidsN1 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n1) , numer1); // enum negative will cause a reverse complemantary sequence to be created and used
        String AminoAcidsN2 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n2) , numer1);
        String AminoAcidsN3 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n3) , numer1);
 
             
        
   
  
        AminoAcidSequence RF1 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP1, numer1.getKey()); 
        AminoAcidSequence RF2 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP2, numer1.getKey());
        AminoAcidSequence RF3 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP3, numer1.getKey());
        AminoAcidSequence RF4 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN1, numer1.getKey());
        AminoAcidSequence RF5 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN2, numer1.getKey());
        AminoAcidSequence RF6 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN3, numer1.getKey());
        
         AminoAcidSequence[] TranslatedReadingFrames = {RF1, RF2, RF3, RF4, RF5, RF6}; 
         
 
       
       return TranslatedReadingFrames;
   }

 
     private static int normalize3(int n) {
        return n == 3 ? 0 : n;
    }  
    /**
     * returns a string of aminoacids using a given nucleotide sequence and codon table. the enum strand will lead to the sequence being used as
     * is or for a reverse complamentary sequence to be created and used
     * 
     * @param direction
     * @param sequence
     * @param huidigeTabel
     * @return 
     */
    static String getAminoAcids(Strand direction, String sequence, CodonTabel huidigeTabel) {
        // in de toekomst vervang codontabel met key in codontabel hashmap
        
        // Sequence must be divisible by 3. It is the responsibility of the
        // calling program to send a sequence properly aligned.
        int readLength = sequence.length() / 3;
        List<String> acids = new ArrayList<String>(readLength);

        if(direction == Strand.NEGATIVE) {                                      //waneer enum negative is word een reverse complementare sequentie aangemaakt
            sequence = TranslationManeger.getReverseComplement(sequence);       // het houd de volgorde aan van nieuwe leesrichting atg aaa ccg -> cgg ttt cat
        }
        
        for (int i = 0; i <= sequence.length() - 3; i += 3) {                   // gebruikt codons als keys om de aminozuren uit een hashmap te halen
            String codon = sequence.substring(i, i + 3).toUpperCase();
            String aa = huidigeTabel.getCodonMap().get(codon);
            acids.add(aa);
        }

        if(direction == Strand.NEGATIVE) {            // aminozuur sequentie word omgedraaid voor visualisatie doeleinden 
            Collections.reverse(acids);               // de output is dus achterstevoren met de volgorde waarin ze in een biologische omgeving getransleerd zouden worden
        }
        
        
        String listString = String.join("", acids); // turn arraylist into string
   
        return listString;
    }
   
   /**
    * creates a reverse complemantary strand of nucleotides. the order of the amino acids is equal to the order 
    * at wich the nucleotides would be encouterd following the reading frame.
    * so not alligned to the original sequence
    * in: atgaaaccg -> out:ccgtttcat
    * @param sequence
    * @return 
    */ 
   public static String getReverseComplement(String sequence) {        
        char[] complement = new char[sequence.length()];
        int jj = complement.length;
        for (int ii = 0; ii < sequence.length(); ii++) {
            char c = sequence.charAt(ii);
            jj--;
            switch (c) {
                case 'T':
                    complement[jj] = 'A';
                    break;
                case 'A':
                    complement[jj] = 'T';
                    break;
                case 'C':
                    complement[jj] = 'G';
                    break;
                case 'G':
                    complement[jj] = 'C';
                    break;
                case 't':
                    complement[jj] = 'a';
                    break;
                case 'a':
                    complement[jj] = 't';
                    break;
                case 'c':
                    complement[jj] = 'g';
                    break;
                case 'g':
                    complement[jj] = 'c';
                    break;
                default:
                    complement[jj] = c;
            }
        } 
        return new String(complement);
    }
}
