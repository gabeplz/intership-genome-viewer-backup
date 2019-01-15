package com.mycompany.minorigv.fastqparser;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Stan Wehkamp
 * Deze class bevat slechts één methode die data uit FASTQ bestanden haalt
 * Deze methode staat in een aparte class omdat hij niet thuis hoort in de andere classes
 */
public class FastqReader {

    /**
     * Leest het bestand van een pathway in en geeft de data door aan de constructor van Read.
     * De constructor geeft de reads terug aan parse waar ze in een arraylist worden gezet.
     *
     * @param path path van de file.
     * @return ArrayList<Read> een ArrayList met hierin de read objecten.
     * @throws IOException, InvalidFileTypeException
     */
    public ArrayList<Read> parse(String path) throws IOException, InvalidFileTypeException {
        if(!path.endsWith(".txt")) {                // check op het juiste bestandstype
            throw new InvalidFileTypeException();
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                ArrayList<Read> readList = new ArrayList<>();       // lijst voor Read objecten
                while ((line = br.readLine()) != null) {

                    String description;
                    String sequence;
                    String qualitySequence;
                    // elke contig in een FASTQ file bestaat uit 4 regels.

                    description = line;
                    sequence = br.readLine();
                    br.readLine();                         //in regel 3 staat alleen een scheidingsteken.
                    qualitySequence = br.readLine();

                    Read curRead = new Read(description, sequence, qualitySequence);        //maken read object.
                    readList.add(curRead);
                }
                return readList;
            }
        }
    }
}