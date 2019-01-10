package com.mycompany.minorigv.sequence;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class BlastORF {

    private String fasta = "/NAS/minor-g1/non_redundant/blast.fasta";
    private String out = "/NAS/minor-g1/non_redundant/out.xml";
    private String blastDB = "/NAS/minor-g1/non_redundant/nr"; // roep nr dan aan
    public void run() throws IOException{
       // File fasta=File.createTempFile("blast", ".fa");
        File blast=File.createTempFile("blast", ".xml");
        try
        {


            ProcessBuilder pb = new ProcessBuilder(
                    "blastp",
                    "-db",blastDB,
                    "-num_threads","8",
                    "-query",fasta,
                    "-evalue", "1e-5",
                    "-out",out
            );

            System.out.println(pb.command());


            Process proc = pb.start();
            if(proc.waitFor()!=0) throw new RuntimeException("error occured");
            JAXBContext jc = JAXBContext.newInstance("blast");

            Unmarshaller u = jc.createUnmarshaller();
            u.setSchema(null);



            /** read the result */

           // return  (blast.BlastOutput)u.unmarshal(blast);

        }
        catch(Exception err)
        {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
        finally
        {
            blast.delete();
            //fasta.delete();
        }
    }
}
