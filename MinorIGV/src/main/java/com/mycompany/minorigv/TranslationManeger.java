/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Gebruiker
 */
public class TranslationManeger {
    
    public static final String[] BASE_SEQUENCES = {"TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
                                                   "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
                                                   "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"};
    
    public static final String TEST_AMINOVOLGORDE = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";
    public static final String TEST_AMINOSTARTS = "---M---------------M---------------M----------------------------";
    
    private LinkedHashMap<Integer, CodonTabel> allCodonTabels = new LinkedHashMap<Integer, CodonTabel>();
    
   public static AminoAcidSequence[] start(chromosometest chrom) {
        String[] namen = {"chromosome1","kaas","baas"};
        CodonTabel numer1 = CodonTabel.build(1, namen, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS);
        
        
        
        String sequence = chrom.getRefsequence();
        
        int start = 0;
        int mod = start % 3;
//        System.out.println(mod);
        int n1 = normalize3(3 - mod);
//        System.out.println(n1);
        int n2 = normalize3(n1 + 1);
//        System.out.println(n2);
        int n3 = normalize3(n2 + 1);
//        System.out.println(n3);
        //List
        String AminoAcidsP1 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n1) , numer1);
        String AminoAcidsP2 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n2) , numer1);
        String AminoAcidsP3 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n3) , numer1);
        
//        System.out.println(AminoAcidsP1);
//        System.out.println(AminoAcidsP2);
//        System.out.println(AminoAcidsP3);

        final int len = sequence.length();
        String AminoAcidsN1 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n1) , numer1);
        String AminoAcidsN2 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n2) , numer1);
        String AminoAcidsN3 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n3) , numer1);
 
        
//        System.out.println(AminoAcidsN2);
//        System.out.println(AminoAcidsN3);        
        
     //   List<String> AminoAcidsN = TranslationManeger.getAminoAcids(Strand.NEGATIVE, chrom.getRefsequence(), numer1);
       
  //     AminoAcidSequence TranslatedReadingFrames = {
  
        AminoAcidSequence RF1 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP1, numer1.getKey());
        AminoAcidSequence RF2 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP2, numer1.getKey());
        AminoAcidSequence RF3 = new AminoAcidSequence(Strand.POSITIVE, 1, AminoAcidsP3, numer1.getKey());
        AminoAcidSequence RF4 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN1, numer1.getKey());
        AminoAcidSequence RF5 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN2, numer1.getKey());
        AminoAcidSequence RF6 = new AminoAcidSequence(Strand.NEGATIVE, 1, AminoAcidsN3, numer1.getKey());
        
         AminoAcidSequence[] TranslatedReadingFrames = {RF1, RF2, RF3, RF4, RF5, RF6};
         
  // };
       
//   System.out.println(CodonTabel.build(1, namen, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS).getCodonMap().entrySet());
       return TranslatedReadingFrames;
   }

 
     private static int normalize3(int n) {
        return n == 3 ? 0 : n;
    }  
   
    static String getAminoAcids(Strand direction, String sequence, CodonTabel huidigeTabel) {
        // in de toekomst vervang codontabel met key in codontabel hashmap
        
        // Sequence must be divisible by 3. It is the responsibility of the
        // calling program to send a sequence properly aligned.
        int readLength = sequence.length() / 3;
        List<String> acids = new ArrayList<String>(readLength);

        if(direction == Strand.NEGATIVE) {
            sequence = TranslationManeger.getReverseComplement(sequence);
        }

        for (int i = 0; i <= sequence.length() - 3; i += 3) {
            String codon = sequence.substring(i, i + 3).toUpperCase();
            String aa = huidigeTabel.getCodonMap().get(codon);
            acids.add(aa);
        }

        if(direction == Strand.NEGATIVE) {
            Collections.reverse(acids);
        }
    //    acids 
        
       
        String listString = String.join("", acids);
        

        return listString;
    }
   
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