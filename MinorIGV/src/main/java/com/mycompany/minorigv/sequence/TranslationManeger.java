/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv.sequence;

import java.util.*;


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
    
   public static HashMap<String, Object> start(String sequence) {
       // namen = namen van codontabel
        String[] namen = {"chromosome1","kaas","baas"};
        // maak codontabel aan
        CodonTabel numer1 = CodonTabel.build(1, namen, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS);

        int start = 0;
        int mod = start % 3;
        int n1 = normalize3(3 - mod);
        int n2 = normalize3(n1 + 1);
        int n3 = normalize3(n2 + 1);
        //List
        String AminoAcidsP1 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n1) , numer1);
        String AminoAcidsP2 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n2) , numer1);
        String AminoAcidsP3 = TranslationManeger.getAminoAcids(Strand.POSITIVE, sequence.substring(n3) , numer1);

        final int len = sequence.length();
        String AminoAcidsN1 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n1) , numer1);
        String AminoAcidsN2 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n2) , numer1);
        String AminoAcidsN3 = TranslationManeger.getAminoAcids(Strand.NEGATIVE, sequence.substring(0, len - n3) , numer1);


        AminoAcidSequence RF1 = new AminoAcidSequence(Strand.POSITIVE, AminoAcidsP1, numer1.getKey());
        AminoAcidSequence RF2 = new AminoAcidSequence(Strand.POSITIVE, AminoAcidsP2, numer1.getKey());
        AminoAcidSequence RF3 = new AminoAcidSequence(Strand.POSITIVE, AminoAcidsP3, numer1.getKey());
        AminoAcidSequence RF4 = new AminoAcidSequence(Strand.NEGATIVE, AminoAcidsN1, numer1.getKey());
        AminoAcidSequence RF5 = new AminoAcidSequence(Strand.NEGATIVE, AminoAcidsN2, numer1.getKey());
        AminoAcidSequence RF6 = new AminoAcidSequence(Strand.NEGATIVE, AminoAcidsN3, numer1.getKey());

        HashMap<String, Object> readingframes = new HashMap<String, Object>();
        readingframes.put("RF1", RF1);
        readingframes.put("RF2", RF2);
        readingframes.put("RF3", RF3);
        readingframes.put("RF4", RF4);
        readingframes.put("RF5", RF5);
        readingframes.put("RF6", RF6);




        return readingframes;
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
        makeCompStrand makeComp = new makeCompStrand();

        if(direction == Strand.NEGATIVE) {
            sequence = makeComp.getReverseComplement(sequence);
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
   
//   public static String getReverseComplement(String sequence) {
//        char[] complement = new char[sequence.length()];
//        int jj = complement.length;
//        for (int ii = 0; ii < sequence.length(); ii++) {
//            char c = sequence.charAt(ii);
//            jj--;
//            switch (c) {
//                case 'T':
//                    complement[jj] = 'A';
//                    break;
//                case 'A':
//                    complement[jj] = 'T';
//                    break;
//                case 'C':
//                    complement[jj] = 'G';
//                    break;
//                case 'G':
//                    complement[jj] = 'C';
//                    break;
//                default:
//                    complement[jj] = c;
//            }
//        }
//        return new String(complement);
//    }
}
