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



import com.mycompany.minorigv.gui.CodonPanel;
import com.mycompany.minorigv.gui.Context;

import java.util.*;
import java.io.*;


/**
 * @author jrobinso. Stan Wehkamp
 * singleton class that call on helper functions to build the codontabels
 * also translates nucleotides sequences to aminoacids.
 * takes the codon table to be used from context
 * the codontables in context are pulled from the allCodonTables in this class
 */
public class TranslationManager {
    private static final String[] BASE_SEQUENCES = {    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",      //hardcoded aangezien de codon volgorde altijd gelijk is in het dataformat voor codontabellen
                                                        "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
                                                        "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"};
    public static final String TEST_AMINOVOLGORDE =     "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";     // TEST_AMINOVOLGORDE, TEST_AMINOSTARTS worden in de toekomst uit een file gegenereed
    public static final String TEST_AMINOSTARTS =       "---M---------------M---------------M----------------------------";

    static final String DEFAULT_CODON_TABLE_PATH = "CodonTabels.txt";

    private LinkedHashMap<Integer, CodonTable> allCodonTables = new LinkedHashMap<Integer, CodonTable>();

    private static TranslationManager instance;

    private TranslationManager(){    }                  //leave this here

    public static TranslationManager getInstance() {
        if (instance == null) {
            try {
                TranslationManager newInstance = new TranslationManager();
                newInstance.loadCodonTabels(DEFAULT_CODON_TABLE_PATH);
                instance = newInstance;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      return instance;
    }

    /**
     *loads file from recource map and parses the contents to CodonTable.build.
     * which returns the codon table back to this method which puts it in the hashmap allCodonTables
     * @param codonTablesPath
     * @throws FileNotFoundException
     * @throws IOException
     */
	void loadCodonTabels(String codonTablesPath) throws FileNotFoundException, IOException {
        
        ClassLoader classLoader = getClass().getClassLoader();
        String file = java.net.URLDecoder.decode(classLoader.getResource(DEFAULT_CODON_TABLE_PATH).getFile(),"UTF-8");

        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line = fileReader.readLine();
        while (line != null) {
            // huidige line bevat de opende brace
            line = fileReader.readLine();

            //huidige line bevat de alle namen van de tabel
            String[] tabelNamen = line.substring(line.lastIndexOf("[")+1, line.lastIndexOf("]")).replaceAll("\"", "").split(",");
            line = fileReader.readLine();

            //huidige line bevat het tabel id
            Integer key = Integer.valueOf(line.substring(line.lastIndexOf(":") + 1, line.length() -1 ));
            line = fileReader.readLine();

            //huidige line bevat aminozuren in een specifieke volgorde om alle mogelijke codons te representeren
            String aminoNormal = line.substring(line.lastIndexOf(":" )+2, line.length() -2);
            line = fileReader.readLine();

            //huidige line bevat alternative aminozuren in een specifieke volgorde om alle mogelijke codons te representeren
            String aminoStarts = line.substring(line.lastIndexOf(":" )+2, line.length() -1);
            line = fileReader.readLine();

            //huidige line bevat de sluitende brace
            line = fileReader.readLine();

            CodonTable curTable = CodonTable.build(key, tabelNamen, BASE_SEQUENCES, aminoNormal, aminoStarts);
            allCodonTables.put(curTable.getKey(), curTable);
        }
    }

    /**
     * getter voor 1 codontable uit allCodonTables
     * gebruikt een Integer als key
     * @param key
     * @return Codontable
     */
    public CodonTable getCodonTable (Integer key){
        return allCodonTables.get(key);
    }

    /**
     * getter voor alle codontabellen uit allCodonTables
     * @return collection van allCodonTables
     */
    public Collection<CodonTable> getAllCodonTables() {
        return Collections.unmodifiableCollection(allCodonTables.values());
    }

     /**
     * retuns een string van aminozuren gegenereed van een nucleotide sequentie en een codontabel. 
     * 
     * @param direction de enum strand zort ervoor dat de sequentie word gebruikt zoal het is of gereversed word waneer enum NEGATIVE is
     * @param sequence string de een nucleotide sequentie voorsteld
     * @param huidigeTabel object CodonTable
     * @return string waar elk karakter een aminozuur voorsteld
     **/
    public String getAminoAcids(Strand direction, String sequence, CodonTable huidigeTabel) {
        // sequentie moet deelbaar zijn door 3
        // het aanroepende programma is verandwoordelijk om een uitgelijnde sequentie mee te geven.
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

        //if (direction == Strand.NEGATIVE) {     // aminozuur sequentie word omgedraaid voor visualisatie doeleinden
        //    Collections.reverse(acids);         // de output is dus achterstevoren met de volgorde waarin ze in een biologische omgeving getransleerd zouden worden
        //}

        String listString = String.join("", acids);     // veranderd de arraylist in een string
        return listString;
    }
    
    
/**
 * zet een codon om naar een string aminozuur
 * @param codon string bestaand uit 3 karakters. dient als key voor CodonMap
 * @param table object CodonTable bevat de CodonMap.
 * @return string van 1 karakter dat een aminozuur voorsteld
 */
    public static String toAA(String codon, CodonTable table) {
    	return table.getCodonMap().get(codon);
    }
}
