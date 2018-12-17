package com.mycompany.minorigv.sequence;

import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.ORF;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Het vinden van Open Reading Frames (ORFs) in de sequentie van een chromosoom/contig. De informatie van een ORF
 * wordt opgeslagen in een object.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek.
 */
public class findORF {

    /**
     * Het vinden van het ORF in drie verschillende reading frames (Template strand). Elk ORF wordt opgeslagen in een object.
     *
     * @param id    Het id van het chromosoom.
     * @param seq   De sequentie van het chromosoom.
     * @return  listORF is een Arraylist met ORF objecten.
     */
    public static ArrayList<ORF> searchORF(String id, String seq, int startUser, int stopUser, int lengthORFUser) {
        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(?=((ATG)(.{3})*?(TAG|TGA|TAA)))", Pattern.CASE_INSENSITIVE); //TODO koppel codontabel
        Matcher match = patroon.matcher(seq);
        int idORF = 0;
        ArrayList<ORF> listORF = new ArrayList<>();
        // Informatie ophalen van het ORF.
        while(match.find()){
            int start = match.start();                                  // Positie startcodon
            int stop = match.start() + match.group(1).length();         // Positie stopcodon
            idORF++;

            int readingframe = ORF.calcFrame(start, Strand.POSITIVE, seq.length());

            String id_ORF = "ORF" + idORF +"_T";
            Strand strand = Strand.POSITIVE;

            int lengthORF = stop - start;

            if(lengthORF >= lengthORFUser){
                ORF ORF_Object = new ORF(start, stop, readingframe, id_ORF, strand, lengthORF);
                listORF.add(ORF_Object);
            }

        }
        return listORF;
    }

    /**
     * Het vinden van het ORF in drie verschillende reading frames (Template strand). Elk ORF wordt opgeslagen in een object.
     *
     * @param id        Het id van het chromosoom.
     * @param seq       De sequentie van het chromosoom.
     * @param bevestiging
     * @return listORF is een Arraylist met ORF objecten.
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static ArrayList<ORF> searchORF(String id, String seq, String bevestiging, int startUser, int stopUser, int lengthORFUser) {
        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(?=((ATG)(.{3})*?(TAG|TGA|TAA)))", Pattern.CASE_INSENSITIVE); //TODO koppel codontabel
        Matcher match = patroon.matcher(seq);
        int idORF = 0;
        ArrayList<ORF> listORF = new ArrayList<>();

        while(match.find()){
            // -1 : index laatste letter.   - 1: tot ipv t/m
            int stop = (seq.length() -1) - (match.start() - 1);   // Positie van het startcodon op de orginele sequentie (+)
            int start = stop - match.group(1).length();           // Positie van het stopcodon op de orginele sequentie (+)
            idORF++;

            int readingframe = ORF.calcFrame(stop, Strand.NEGATIVE, seq.length());

            String id_ORF = "ORF" + idORF +"_C";
            Strand strand = Strand.NEGATIVE;
            int lengthORF = stop - start;

            if(lengthORF >= lengthORFUser){
                ORF ORF_Object = new ORF(start, stop, readingframe, id_ORF, strand, lengthORF);
                listORF.add(ORF_Object);
            }


        }
        return listORF;
    }
}