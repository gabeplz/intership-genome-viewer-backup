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
     *leest het bestand van een pathway in en geeft de data door aan de constructor van Read.
     * de constructor geeft de reads trerug aan parse waar ze in een arraylist worden gezet
     * @param path
     * @return arraylist<Read>
     * @throws Exception
     */
    public ArrayList parse(String path)throws Exception {
        BufferedReader r;
        if(path.endsWith(".gz"))            // gebruikt andere buffer voor gz files
        {
            r = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path))));
        }
        else
        {
            r = new BufferedReader(new FileReader(path));
        }

        String line;
        ArrayList readList = new ArrayList();       // lijst voor Read objecten
        while((line=r.readLine())!=null)
        {
            String description;
            String sequence;
            String qualitySequence;
                                        // elke contig in een FastaQ file bestaat uit 4 regels.
            description = line;
            sequence = r.readLine();;
            r.readLine();                       //in regel 3 staat alleen een scheidings teken.
            qualitySequence = r.readLine();;

            Read curRead = new Read(description, sequence, qualitySequence);
            readList.add(curRead);
        }
        r.close();
        return readList;
   }
}
