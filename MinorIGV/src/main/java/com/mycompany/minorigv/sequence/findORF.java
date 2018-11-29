package com.mycompany.minorigv.sequence;

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
    public static ArrayList<ORF> searchORF(String id, String seq) {
        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(ATG)(.{3})*?(TAG|TGA|TAA)", Pattern.CASE_INSENSITIVE);
        Matcher match = patroon.matcher(seq);
        int idORF = 0;
        ArrayList<ORF> listORF = new ArrayList<>();
        // Informatie ophalen van het ORF.
        while(match.find()){
            int start = match.start();      // Positie startcodon
            int stop = match.end();         // Positie stopcodon
            idORF++;
            int readingframe = Integer.MIN_VALUE;

            if((start % 3) == 0){
                readingframe = +1;
            }else if((start % 3) == 1){
                readingframe = +2;
            }else if((start % 3) == 2){
                readingframe = +3;
            }else{
                System.out.println("Geen ORF gevonden.");
            }

            // Positie van de start aminozuur en stop aminozuur bepalen.
            int aaStart = (int) Math.ceil(start/3.0);
            int aaStop = (int) Math.ceil(stop/3.0);

            String id_ORF = "ORF" + idORF +"_T";

            ORF ORF_Object = new ORF(start, stop, readingframe, id_ORF, aaStart, aaStop);
            listORF.add(ORF_Object);
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
    public static ArrayList<ORF> searchORF(String id, String seq, String bevestiging) throws FileNotFoundException, UnsupportedEncodingException {
        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(ATG)(.{3})*?(TAG|TGA|TAA)", Pattern.CASE_INSENSITIVE);
        Matcher match = patroon.matcher(seq);
        int idORF = 0;
        ArrayList<ORF> listORF = new ArrayList<>();

        while(match.find()){
            int start = seq.length() - match.start();   // Positie van het startcodon op de orginele sequentie (+)
            int stop = seq.length() - match.end();      // Positie van het stopcodon op de orginele sequentie (+)
            int readingframe;
            idORF++;

            if((start % 3) == 0){
                readingframe = -1;
            }else if((start % 3) == 1){
                readingframe = -2;
            }else if((start % 3) == 2){
                readingframe = -3;
            }else{
                readingframe = Integer.MIN_VALUE;
                System.out.println("Geen ORF gevonden.");
            }

            // Positie van de start aminozuur en stop aminozuur bepalen.
            int aaStart = (int) Math.ceil(start/3.0);
            int aaStop = (int) Math.ceil(stop/3.0);

            String id_ORF = "ORF" + idORF +"_C";

            ORF ORF_Object = new ORF(start, stop, readingframe, id_ORF, aaStart, aaStop);
            listORF.add(ORF_Object);

        }
        return listORF;
    }
}