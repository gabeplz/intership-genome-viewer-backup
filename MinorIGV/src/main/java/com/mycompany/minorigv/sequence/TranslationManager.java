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
package com.mycompany.minorigv.sequence;

import java.util.*;

import com.mycompany.minorigv.gffparser.main;


/**
 * @author jrobinso. Stan Wehkamp
 */
public class TranslationManager {

    public static final String[] BASE_SEQUENCES = {	"TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",      //hardcoded aangezien de codon volgorde altijd gelijk is in het dataformat voor codontabellen
    							"TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    							"TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"};

    public static final String TEST_AMINOVOLGORDE = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";     // TEST_AMINOVOLGORDE, TEST_AMINOSTARTS worden in de toekomst uit een file gegenereed
    public static final String TEST_AMINOSTARTS = 	"---M---------------M---------------M----------------------------";

    private LinkedHashMap<Integer, CodonTabel> allCodonTabels = new LinkedHashMap<Integer, CodonTabel>();

    /**
     * bouwt de standaard codon tabel.
     * @return object CodonTabel 
     */
    public static CodonTabel buildDefault() {
		return CodonTabel.build(1,new String[]{"hoi","hai"}, BASE_SEQUENCES, TEST_AMINOVOLGORDE, TEST_AMINOSTARTS);
		
	}
 /**
 *
 * @author jrobinso, Stan Wehkamp
 * 
 * returns een hashmap
 * 
 * start method passes arguments die nodig zijn voor de constructors van CodonTable, AminoAcidSequence en naar de methode getaminoacid binnen de class
 * contains static classes to translate amino acids and make reverse complemantary sequence 
 * 
 * @param sequence
 * @param comp_sequence
 * @return hashmap
 */
    public static HashMap<String, Object> start(String sequence, String comp_sequence) {
        // namen = namen van codontabel
        String[] namen = {"chromosome1", "kaas", "baas"};
        // maak codontabel aan
        CodonTabel numer1 = buildDefault();

        int start = 0;                          // huidig onodig gecompliceerde maneer om de volgede variabelen de volgende waardes te geven:
        int mod = start % 3;                    // n1 = 0, n2 = 1, n2 = 2
        int n1 = normalize3(3 - mod);           // in de toekomst word start gebaseerd op het refrenceframe en locatie op de refsequentie
        int n2 = normalize3(n1 + 1);
        int n3 = normalize3(n2 + 1);
        //List
        String AminoAcidsP1 = getAminoAcids(Strand.POSITIVE, sequence.substring(n1), numer1);       // 6 aminozuur sequenties worden gemaakt voor de 6 reading frames
        String AminoAcidsP2 = getAminoAcids(Strand.POSITIVE, sequence.substring(n2), numer1);
        String AminoAcidsP3 = getAminoAcids(Strand.POSITIVE, sequence.substring(n3), numer1);

        final int len = comp_sequence.length();
        String AminoAcidsN1 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n1), numer1);
        String AminoAcidsN2 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n2), numer1);
        String AminoAcidsN3 = getAminoAcids(Strand.NEGATIVE, comp_sequence.substring(n3), numer1);


        AminoAcidSequence RF1 = new AminoAcidSequence(Strand.POSITIVE, AminoAcidsP1, numer1.getKey()); //de enum negative zorgt ervoor dat de sequentie word gereversed voor translatie begint
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

    /**
     * 
     * @param n int
     * @return 0 of n
     */
    private static int normalize3(int n) {
        return n == 3 ? 0 : n;
    }
    /**
     * tijdelijke main
     * @param args 
     */
    public static void main(String[] args) {
		String seq = "GGTCTCTCTGGTTAGACCAGATCTGAGCCTGGGAGCTCTCTGGCTAACTAGGGAACCCACTGCTTAAGCC";
		CodonTabel tabel = buildDefault();
		System.out.println(getAminoAcids(Strand.NEGATIVE,seq,tabel));
	}

     /**
     * retuns een string van aminozuren gegenereed van een nucleotide sequentie en een codontabel. 
     * 
     * @param direction de enum strand zort ervoor dat de sequentie word gebruikt zoal het is of gereversed word waneer enum NEGATIVE is
     * @param sequence string de een nucleotide sequentie voorsteld
     * @param huidigeTabel object CodonTabel
     * @return string waar elk karakter een aminozuur voorsteld
     */
    static String getAminoAcids(Strand direction, String sequence, CodonTabel huidigeTabel) {
        // in de toekomst vervang codontabel met key in codontabel hashmap

        // Sequence must be divisible by 3. It is the responsibility of the
        // calling program to send a sequence properly aligned.
        int readLength = sequence.length()/3;
        List<String> acids = new ArrayList<String>(readLength);
        
        if (direction == Strand.NEGATIVE) {
            sequence = makeCompStrand.getReverseComplement(sequence);
        }
        
        for (int i = 0; i <= sequence.length() - 3; i += 3) {
            String codon = sequence.substring(i, i + 3).toUpperCase();
            String aa = toAA(codon, huidigeTabel);
            acids.add(aa);
        }

        if (direction == Strand.NEGATIVE) {     // aminozuur sequentie word omgedraaid voor visualisatie doeleinden 
            Collections.reverse(acids);         // de output is dus achterstevoren met de volgorde waarin ze in een biologische omgeving getransleerd zouden worden     
        }

        String listString = String.join("", acids);     // veranderd de arraylist in een string
        return listString;
    }
    
    
/**
 * zet een codon om naar een string aminozuur
 * @param codon string bestaand uit 3 karakters. dient als key voor CodonMap
 * @param table object CodonTabel bevat de CodonMap.
 * @return string van 1 karakter dat een aminozuur voorsteld
 */
    public static String toAA(String codon,CodonTabel table) {
    	return table.getCodonMap().get(codon);
    }
}
