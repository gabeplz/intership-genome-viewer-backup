package com.mycompany.minorigv.blast;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Huub
 * Class voor het aanroepen van BLAST via de commandline.
 */
public class BLAST {

    private static ProcessBuilder pb;
    /**
     * Runnen van de BLAST
     *
     * @param input         Path waar de input file staat
     * @param output        Path naar de output file.
     * @param blastType     Welke blast er uitgevoerd moet worden (blastp, blastn, etc.)
     * @param database      De database waartegen geblast moet worden.
     * @throws IOException
     */
    public static void runBLAST(String input, String output, String blastType, String database, String sortBlast) throws IOException {
        try
        {
            if(sortBlast.equals("NCBI")){
                pb = blastCommandNCBI(blastType, database, input, output);
            }else{
                pb = blastCommandDiamond(blastType, database, input, output);
            }


            // Checken of de huidige instellingen al een keer gebruikt zijn, zo niet: blasten.
            if(new File(output).exists() == false){
                Process proc = pb.start();      // Starten van de blast
                if(proc.waitFor()!=0) throw new RuntimeException("error occured");
            }

            parseXML(output);

        }
        catch(Exception err)
        {
            new File(output).delete();
            err.printStackTrace();
            throw new RuntimeException(err);
        }
        finally
        {

        }
    }

    /**
     * Parsen van de output file (XML) van de blast.
     *
     * @param output        Het path naar de gegenereerde output (XML) file.
     * @return
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public static BlastOutput parseXML(String output) throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException {

        SAXSource source;
        Unmarshaller u;
        JAXBContext jc = JAXBContext.newInstance("com.mycompany.minorigv.blast");

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        spf.setFeature("http://xml.org/sax/features/validation", false);

        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(
                new FileReader(output));
        source = new SAXSource(xmlReader, inputSource);

        u = jc.createUnmarshaller();
        BlastOutput bo = (BlastOutput) u.unmarshal(source);
        return bo;
    }

    /**
     * Command line argument voor het uitvoeren van blast via NCBI.
     * @param input         Path waar de input file staat
     * @param output        Path naar de output file.
     * @param blastType     Welke blast er uitgevoerd moet worden (blastp, blastn, etc.)
     * @param database      De database waartegen geblast moet worden.
     * @return
     */
    public static ProcessBuilder blastCommandNCBI(String blastType, String database, String input, String output){
        pb = new ProcessBuilder(
                blastType,
                "-db",database,              //database
                "-query",input,              //input
                "-evalue", "1e-5",           //Cutoff e-value
                "-num_threads","8",          //Hoeveelheid threads
                "-outfmt", "5",              //soort output XML
                "-max_target_seqs", "1",     //Aantal hits per ORF
                "-out",output                //output
        );
        return pb;
    }

    /**
     * Command line argument voor het uitvoeren van blast via Diamond.
     * @param input         Path waar de input file staat
     * @param output        Path naar de output file.
     * @param blastType    Welke blast er uitgevoerd moet worden (blastp, blastn, etc.)
     * @param database      De database waartegen geblast moet worden.
     * @return
     */
    public static ProcessBuilder blastCommandDiamond(String blastType, String database, String input, String output){
        pb = new ProcessBuilder(
                "diamond",blastType,
                "-d",database,              //database
                "-q",input,                 //input
                "-e", "1e-5",               //Cutoff e-value
                "-p","8",                   //Hoeveelheid threads
                "-f", "5",                  //soort output XML
                "-k", "1",                  //Aantal hits per ORF
                "-o",output                 //output
        );
        return pb;
    }



}
