/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv.sequence;

import com.mycompany.minorigv.gffparser.Chromosome;

import java.util.*;


/**
 * @author Gebruiker
 */
public class TranslationManeger {

    public static final String[] BASE_SEQUENCES = {"TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
            "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
            "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"};

    public static final String TEST_AMINOVOLGORDE = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";
    public static final String TEST_AMINOSTARTS = "---M---------------M---------------M----------------------------";

    private LinkedHashMap<Integer, CodonTabel> allCodonTabels = new LinkedHashMap<Integer, CodonTabel>();

    public static HashMap<String, Object> start(String sequence, String comp_sequence) {
        // namen = namen van codontabel
        String[] namen = {"chromosome1", "kaas", "baas"};
        // maak codontabel aan
        CodonTabel numer1 = CodonTabel.build(1, namen, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS);

        int start = 0;
        int mod = start % 3;
        int n1 = normalize3(3 - mod);
        int n2 = normalize3(n1 + 1);
        int n3 = normalize3(n2 + 1);
        //List
        String AminoAcidsP1 = getAminoAcids(Strand.POSITIVE, sequence.substring(n1), numer1);
        String AminoAcidsP2 = getAminoAcids(Strand.POSITIVE, sequence.substring(n2), numer1);
        String AminoAcidsP3 = getAminoAcids(Strand.POSITIVE, sequence.substring(n3), numer1);

        final int len = comp_sequence.length();
        String AminoAcidsN1 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n1), numer1);
        String AminoAcidsN2 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n2), numer1);
        String AminoAcidsN3 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n3), numer1);


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

        if (direction == Strand.NEGATIVE) {
            sequence = new StringBuilder(sequence).reverse().toString();
        }
        
        for (int i = 0; i <= sequence.length() - 3; i += 3) {
            String codon = sequence.substring(i, i + 3).toUpperCase();
            String aa = huidigeTabel.getCodonMap().get(codon);
            acids.add(aa);
        }

        if (direction == Strand.NEGATIVE) {
            Collections.reverse(acids);
        }

        String listString = String.join("", acids);
        return listString;
    }
}
