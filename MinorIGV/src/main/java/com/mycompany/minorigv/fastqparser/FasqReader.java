package com.mycompany.minorigv.fastqparser;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * deze class bevat slechts één methode die data uit fastaq bestanden haalt
 * deze methode staat in een apparte class omdat het niet thuis hoort in de andere classes
 */
public class FasqReader {
    /**
     * leest het bestand van een pathway in en geeft de data door aan de constructor van Read.
     * de constructor geeft de reads trerug aan parse waar ze in een arraylist worden gezet
     *
     * @param path
     * @return arraylist<Read>
     * @throws IOException, InvalidFileTypeException
     */
    public ArrayList parse(String path) throws IOException, InvalidFileTypeException {
        if(path.endsWith(".txt") == false) {                // check op het juiste bestandstype
            throw new InvalidFileTypeException();
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                ArrayList readList = new ArrayList();       // lijst voor Read objecten
                while ((line = br.readLine()) != null) {
                    String description;
                    String sequence;
                    String qualitySequence;
                                                           // elke contig in een FastaQ file bestaat uit 4 regels.
                    description = line;
                    sequence = br.readLine();
                    br.readLine();                         //in regel 3 staat alleen een scheidings teken.
                    qualitySequence = br.readLine();


                    Read curRead = new Read(description, sequence, qualitySequence);        //call naar Read constructor
                    readList.add(curRead);
                }
                return readList;
            }
        }
    }
}
