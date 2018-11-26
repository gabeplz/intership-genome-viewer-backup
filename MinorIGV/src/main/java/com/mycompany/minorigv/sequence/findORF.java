package com.mycompany.minorigv.sequence;

import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.ORF;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    public int readingframe;
    public static int idORF;
    public static ArrayList<ORF> listORF = new ArrayList<>();

    public static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("D:\\0000 HAN\\00 Minor\\Project\\orf.fasta", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Het vinden van het ORF in drie verschillende reading frames. Elk ORF wordt opgeslagen in een object.
     *
     * @param seq   Elk opgeslagen chromosoom sequentie wordt een voor een meegegeven aan searchORF.
     */
    public ArrayList<ORF> searchORF(String id, String seq) {

        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(ATG)(.{3})*?(TAG|TGA|TAA)", Pattern.CASE_INSENSITIVE);
        Matcher match = patroon.matcher(seq);

        // Informatie ophalen van het ORF.
        while(match.find()){
            int start = match.start();      // Positie startcodon
            int stop = match.end();         // Positie stopcodon

            idORF++;

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

            ORF ORF_Object = makeORF(start, stop, readingframe, idORF, aaStart, aaStop, match.group());
            listORF.add(ORF_Object);
            writer.println(">ORF"+ idORF +" RF:"+ readingframe +" start:"+start + " stop:"+stop);
            writer.println(match.group());
        }
        writer.close();
        return listORF;
    }

    public ArrayList<ORF> searchORF(String id, String seq, String bevestiging) throws FileNotFoundException, UnsupportedEncodingException {


        // Patroon voor een ORF. Wordt gekeken waar dit ORF op de chromosoom/contig sequentie ligt.
        Pattern patroon = Pattern.compile("(ATG)(.{3})*?(TAG|TGA|TAA)", Pattern.CASE_INSENSITIVE);
        Matcher match = patroon.matcher(seq);

        while(match.find()){
            int start = seq.length() - match.start() -3;   // Positie van het startcodon op de orginele sequentie (+)
            int stop = seq.length() - match.end() -3;      // Positie van het stopcodon op de orginele sequentie (+)

            idORF++;

            if((start % 3) == 0){
                readingframe = -1;
            }else if((start % 3) == 1){
                readingframe = -2;
            }else if((start % 3) == 2){
                readingframe = -3;
            }else{
                System.out.println("Geen ORF gevonden.");
            }

            // Positie van de start aminozuur en stop aminozuur bepalen.
            int aaStart = (int) Math.ceil(start/3.0);
            int aaStop = (int) Math.ceil(stop/3.0);

            ORF ORF_Object = makeORF(start, stop, readingframe, idORF, aaStart, aaStop, match.group());
            listORF.add(ORF_Object);
            writer.println(">ORF"+ idORF +" RF:"+ readingframe +" start:"+start + " stop:"+stop);
            writer.println(match.group());
        }
        writer.close();
        return listORF;
    }

    private static ORF makeORF(int start, int stop, int readingframe, int idORF, int aaStart, int aaStop, String DNA_ORF) {
        ORF object;
        object = new ORF(start, stop, readingframe, idORF, aaStart, aaStop, DNA_ORF);
        return object;
    }
}