package com.mycompany.minorigv.sequence;

import com.mycompany.minorigv.blast.BlastOutput;
import com.mycompany.minorigv.blast.ObjectFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

public class BlastORF {

    private String fasta = "/NAS/minor-g1/non_redundant/blast.fasta";
    private String out = "/NAS/minor-g1/non_redundant/results.out";
    private String blastDB = "/NAS/minor-g1/non_redundant/nr"; // roep nr dan aan
    public void run() throws IOException{
       // File fasta=File.createTempFile("blast", ".fa");
        File blast=File.createTempFile("blast", ".xml");
        try
        {
/*

            ProcessBuilder pb = new ProcessBuilder(
                    "blastp",
                    "-db",blastDB,
                    "-num_threads","8",
                    "-query",fasta,
                    "-evalue", "1e-5",
                    "-outfmt", "5",
                    "-max_target_seqs", "1",
                    "-out",out
            );

            System.out.println(pb.command());


            Process proc = pb.start();
            if(proc.waitFor()!=0) throw new RuntimeException("error occured");
*/
            JAXBContext jc = JAXBContext.newInstance("com.mycompany.minorigv.blast");

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            spf.setFeature("http://xml.org/sax/features/validation", false);

            XMLReader xmlReader = spf.newSAXParser().getXMLReader();
            InputSource inputSource = new InputSource(
                    new FileReader(out));
            SAXSource source = new SAXSource(xmlReader, inputSource);

            Unmarshaller u = jc.createUnmarshaller();

            /** read the result */

            BlastOutput bo = (BlastOutput) u.unmarshal(source);
            System.out.println(bo.getBlastOutputIterations().getIteration().get(3).getIterationHits().getHit().get(0).getHitHsps().getHsp().get(0).getHspEvalue());

        }
        catch(Exception err)
        {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
        finally
        {
            //blast.delete();
            //fasta.delete();
        }
    }
}
