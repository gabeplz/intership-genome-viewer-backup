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
    /**
     *
     * @param input         Path waar de input file staat
     * @param output        Path naar de output file.
     * @param soortBlast    Welke blast er uitgevoerd moet worden (blastp, blastn, etc.)
     * @param database      De database waartegen geblast moet worden.
     * @throws IOException
     */
    public void runBLAST(String input, String output, String soortBlast, String database) throws IOException {
        try
        {
            // command line argument voor het uitvoeren van blast.
            ProcessBuilder pb = new ProcessBuilder(
                    soortBlast,
                    "-db",database,
                    "-num_threads","8",
                    "-query",input,
                    "-evalue", "1e-5",
                    "-outfmt", "5",
                    "-max_target_seqs", "1",
                    "-out",output
            );

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
     * @param output        Het path naar de gegenereerde output (XML) file.
     * @return
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public BlastOutput parseXML(String output) throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException {

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
        return (BlastOutput) u.unmarshal(source);
    }



}
