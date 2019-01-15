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

/**
 * Class voor het aanroepen van BLAST via de commandline.
 */
public class BLAST {

    public static final String blastDB = "/NAS/minor-g1/non_redundant/nr"; // roep nr dan aan

    public void runBLAST(String input, String output, String soortBlast, String database){

        try
        {
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

            if(new File(output).exists() == false){
                Process proc = pb.start();
                if(proc.waitFor()!=0) throw new RuntimeException("error occured");
            }else{
            }

            parseXML(output);

        }
        catch(Exception err)
        {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
        finally
        {

        }
    }


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


//    public void getValuesORF(ArrayList<ORF> curORFList) throws JAXBException {
//        /** read the result */
//
//        BlastOutput bo = parseXML();
//        HashMap<String, Double> headerEvalue = new HashMap<>();
//        ArrayList<String> headerORFsNoHits = new ArrayList<>();
//
//
//        for(int i = 0; i < curORFList.size(); i++){
//            String header = bo.getBlastOutputIterations().getIteration().get(i).getIterationQueryDef();
//            headerIteration.put(header, bo.getBlastOutputIterations().getIteration().get(i));
//
//            if(!bo.getBlastOutputIterations().getIteration().get(i).getIterationHits().getHit().isEmpty()){
//                String evalue = bo.getBlastOutputIterations().getIteration().get(i).getIterationHits().getHit().get(0).getHitHsps().getHsp().get(0).getHspEvalue();
//
//                headerEvalue.put(header, Double.parseDouble(evalue));
//            }else{
//                headerORFsNoHits.add(header);
//                System.out.println("GEEN HITS");
//            }
//        }
//
//        ColorORFs col = new ColorORFs();
//        col.evalue(headerEvalue, headerORFsNoHits);
//
//    }
//
//
//
//    public HashMap<String, Iteration> getHeaderIteration(){
//        return headerIteration;
//    }
//
//
//    public void splitHeader(ArrayList<String> header){
//
//
//    }


}
