package com.mycompany.minorigv.gffparser;
import com.mycompany.minorigv.FastaFileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class main {
    public static String path_gff;
    public static String path_fasta;
    public static void main(String[] args) throws Exception {
        String ncseq = new String("https://stackoverflow.com/questions/tagged/regex\n");
        String pattern = "(https?|ftp)://([^/\r\n]+)(/[^\r\n]*)?";
       // MotifFinder.gogo(ncseq, pattern);
    }
        /**
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        path_gff = classLoader.getResource("voorbeeldgff.gff").getFile();
        path_fasta = classLoader.getResource("GCF_000146045.2_R64_genomic.fna").getFile();

        gffReader lees = new gffReader();
        Organisms org = lees.readData(null,path_gff);

        HashMap<String,String> fastaMap = FastaFileReader.getSequences(path_fasta);

        for(String id : fastaMap.keySet()){
            org.addSequence(id,fastaMap.get(id));

        }


    }
}
**/
    }
