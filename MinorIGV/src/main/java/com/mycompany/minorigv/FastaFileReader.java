package com.mycompany.minorigv;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.sequence.findORF;
import com.mycompany.minorigv.sequence.makeCompStrand;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Het inlezen van een fasta bestand met chromosoom/contig sequenties. Het ID van het chromosoom/contig wordt
 * samen met de sequentie opgeslagen in het Chromosoom object.
 *
 * @author Anne van Ewijk, Huub Goltstein en Amber Janssen Groesbeek
 */
public class FastaFileReader {

    /**
     * Het inlezen van een fasta file en elke sequentie van chromosoon/contig opslaan in het object Chromosoom
     * met het ID van het chromosoom/contig.
     *
     * @param pad           Pad van het fasta bestand
     * @return CH_list is een HashMap met daarin als key het chromosoom id en als value de sequentie van het chromosoom.
     * @throws IOException  Input/output exceptie
     */
    public static HashMap<String,String> getSequences(String pad) throws IOException{

        BufferedReader f_reader = new BufferedReader(new FileReader(pad));
        HashMap<String,String> CH_list = new HashMap<String, String>();
        String regel = f_reader.readLine();

        // Loopen over de headers
        while(regel != null) {
            String header = regel;
            // ID uit fasta header halen
            String id = header.split("\\s+")[0].replace(">", "");

            regel = f_reader.readLine();

            StringBuilder chromosoomSeq = new StringBuilder();

            // Loopen over de sequentie van een header
            while (regel != null && !regel.startsWith(">")) {
                regel = regel.trim();
                chromosoomSeq.append(regel);
                regel = f_reader.readLine();
            }

            CH_list.put(id,chromosoomSeq.toString().toUpperCase());
        }
        return CH_list;
    }
}