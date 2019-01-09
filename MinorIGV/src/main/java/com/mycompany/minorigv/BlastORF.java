package com.mycompany.minorigv;


import com.mycompany.minorigv.gui.Context;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;

public class BlastORF {
    private static final String BLAST_OUTPUT_FILE = "blastOutput.xml";
    private static String path;
    private static String SEQUENCE = "ATACCTACTCTTTCGCATATCCCACTATACCGCCtattaatatatctatCCAATCCTCATCCCAACGccatataatatacatttATAAACCTCTTTTATCcatctatatatattacCTATCTACCCCATAAATTACACacttcaacttcttcacAGCTTAAACTTCGCTATTATATCTCTACCATTTCAATTATCGGCTTTAACATAAATCAAATCCAATATCATTCTCATATTTCACTCTCAATATCGCATTTTAATTACTCCATCTATATTATATTCTATTCTATTCTATATCTCATAATAACACCATAGCTCCAAACGCCATACGCccttttcaattcaaatctCCATTAAATATACACTTCCATTTAACTTCTTCGCCGGCCAAATTTCAACCATCCCAACGCCATATCATATACTAAACCCCTTTTATCCGCATTTccatatatttatataattccATTAATTCTTACTTCAACCCATTCACCGCTTTAAACACTAATTATCTCGCTATTATCATACAATTTCCAACTACGCGatctaaattaaattcaatatcattttccaCAATTCACTtatttttccaataaacCTCTTCATATTAATTAACTTATTTCACATTATATAGCCCATATTCctcaatatttcatttcattataCACCATACCTCCCTTTCAATCTCTTCATTCCAACCTTCAAACATAACTCAATGCGCCAACCTAACAACCCTCTAACATTAACTCGCcatttatatttacaaaCTTCAACATATACTCACCTTTTCTATTACTCCTTCCCATTATTCTATTCTTAACCCATTCATACACTCAATTCAACCTTTTTCAACCCTTAAATTTAAACATCTCTCCATCTCCATCCCAACGGCctattctttcaatttcatttatttttcaacacACCgctaaattcattataaaCTTCATATTTTACTCCAACTTCtcccatttttcaattcccCTTAATCCTCCCATATTCAACTCAATTCCACATCTTATCATTTTCCTTCTCTTACCTCATCTCAAATACTACTACTTCTGCTAACTCTACTTTCAACtccaaataaataaatctaatCTAATAAacatcatttttcaatttaaatttCCTTTCTATAAATTAATCAGCCCCACCAATTCTTCTCCGGCCCTCTTACTTATATATAAACACTACCAATTCAACATTCCAATTTCATTACTCACAAACCTACTCCATCAAAATACATTATAATTCTtaactatatatatcataTCACgccaattcattaatttaaaCTATCCCTACACCGCTAATATCACCAACCGCCTCACTTTATCTTCTCATTCTTCATATTCTATTTCCGCTCCTCATTCCAACCTCCCTAACtcaaacaaattaatactccattttatttacaatatCAACGCACTTTATCTAAATCTCCTCCATACCCGCTTACTACATCACCTCTCATTTCCCACCacttatcattattcacGCCTCATCCTTTTCCCATCGGCTATTTATTTCAGCTCCATAACAATAACATCAGCATAATTTCTACAATCCATAACGCTTAAATAACGCATACCAACTTATACCCCTTAACTTTAATATTCCATCCCGCTTCAACCTTATCCTTCTCCCCTTCTCCCCCCCTTCATACACCTCAATATTCTATAAACTATCGGCCacatattaatttatacGACACCCATTCTATCGGAATATCATCCTCGCTTCACTAAAACACGCCATCCACGACATTCTCGCACGATCGCCAACTTCTTATCATCACATGACCAGCGTTATGAGCATTGCTATTTATGCCAACTTCTCTCCCCGATATACCACCCCATATACGCCTATCCGAAGAGGAAGCTACCCATTTCATCTACTAATACTCTTCTCTACACTATGCTGCCCTTTTTCTGgctcaaataattttttttatattttctatatatttaacaattttctgagatttttccaataatataattatctATTCTTATATATTCCCGACTAATCTTCATCCAGATAATCCAGCTCCAGATAATCCAGATCATCTACCACAGGATCATTCTTAGGGATCTTGGGCCCTTTCCGAGCAACGCTTTTAGGGGAATCTGACGGAGCAAAGAACCTTCCCAACGGCGATGCCTGCAAACTCTCCGTTATTCTTTGGCGGAGATGCTGGGGAGAAAGCAAGGACCGGCGCGGCGTCTCGGGAAACGAGGGAGGCGATCCTAAAGAGCGCGCGCTGGGAGGCTGCTTTGGCTGCTTTTGCACGCGCGTTGGGTGGGAAGACCCAGGAACGGCCAGGCTTTGCGTGCTCGATTGATAGATAAGGGGGTGCACCCGCCTGGCACTCAGATTCGGCGTTGGGGGACCCGATGGCATGGCTCGAGGACTTGAGGGGCCTGAGGGGCCTGATGGGCTTTCTCTACGGAATAACCGTCCACTCGTTGCAGACAGATATAGATGGACGTCTTTGACTTTTAGGGTCCTGACTGGGGTGACAGGAGAAGCTAGGGCTGcactttctttttctcGTTTCTCCTCTGCCCGTTTTCGTTCCCTTTTCTCTCTCTTCTCTCTTTTCCttatcttttctttctcctCTCTTTTTCTGATCTTTTCCTCTCTTTTCCTGATATCTTCCTCTCTTTTACTGATCCTTTCTTTCTCCTTCCTCTTCCTTTCCATTTTTCTCTCTTTTCTTTCCTTTTTTTCTCGACTATGGCGTTTTCTCGCTCCCAGAAGCACTTCTCGAGACACATGCGCATCATATAACGGCGCGCCTAGATATGCATCTTGCGGAGGTAAAGCAAACCCAGAAGATACAGGAGCGTGGTGGTAGGGAGGTGAGAAGGGAGGACGATAACCTGGGAGCTGGTGAACATAAGGGGACGAAGCGTGGTTTGGGGAGAGGCTCGAATCGTTCCACGAAACAGACACTGGAGGGGCGTCGGGAGCTGAAACAGGGACTGAACGAGAAGGAGCTGGAGGTTCAGAAGGTGGAACAGCAGAAGGTTCAAGCGGAGGAACAGGCGTAGGATCAGAGCCTACAGCCCTATTATAATCTATGCGAGGAATTTCCATAAGCATATCCGAAATAGAATCAACCTCGTCATCTGCATAAACAGGATTCCCGCCAATATCATCACGCAAGGGTTCAACTTCCTCAACCTCTTCATTGACAGGGCTAGCCGGGTCAATTGAAACATCAGATGCCACGTCATCCACATCACCCGGCTCACCCTCCGGCTCAACAACCTCACCCTCGAAACCGATAAACTTCCGACGCAACCCCAGCCGATGCCAGACCTGGCCCAAATGACGGGACCGGAGCACATCGGCGGCGTTGACTGATGGCGGATAATTCGCAATCGTCCCGTAATGCTCCGTGGACGTGGCCACCGAATGGCCCAGGGTCCGCGCAATGGTCTCGTAAACCAGACGGTACATGTCGAGATCTGCTGTCACGCTTTTGGTGTAATGGAACTCGATGCACCGCTTGAGGATCTGGCGCACCTCACAGAACTTATACTCGCGCTGGTTATTCATGCCGTACGTGAACCAATACAGGACTCCTCTCCGGATCTTGTCCGGCAGCATCGCATCAAACTGGTACAACTTCTCACTGCTCTCCACACGCGACAGCTCCACGTACATCCGCCTGAAGACGAGCACATACCACAAAAACAACGACGACAATTCAGGACCGATTTCCCGGATGATGGGGTCTTTCGAATTGGCCGACTTGTTATAGTTATAGCAAAACCCAAACGTGGGGCCAAGAAAGTACACACCCCGATACTCCTTGGACGTGCCCTCCACCGATAATCGGTCGATCTCAGTTGACCGTGCCGGCAAGCACAGGATATGGAGGAGCGTGATGAGGATGAGCGACAAGTTGGTGTGTGCTTTCCGGAACAGGTCGATGTTGTTGTTATGGATCGAGAGCAATCGGTGCTTATATCGCGAATACAGCGGTACTCTCACGCACTCCGTAAACGAAACCATCCCGTCGCTGAGCCGAGCGTTGTCAACGGAGTAGTCCAATTCTTCACCATTCGACAAATCCTGGATGGTTTCCTGTGCAAGGTCGTACAACCGGCCGAATATCTTGCTGATCATATTCGTGTGGCCCACAACCACGTTGTCGCTGTTGAAGATGTCTCCCGTGGAAAAGTTGAGGCGAAAGAGATTACCCTCGCTTCGAGCCAACGATAGCCCGAGATATGTATAATGGGTGCGCACCTGGGCCAATATGCGGTACAGACCGGTGTCCACAACAGACGGATCCTCAGCGTCTCGAAGCGATACGCGGCGGGTGCGCGCCCGTTTGATGATATCCAACAAAACACACGTCTTGGCCACATAGCATATTCCGCTCAACGACCGGCAATACTGCTTGGTCGGATACTTAGCCCCTAGGTACCCGAGGTACACCACAAACGCATTTTCGTTTGACGtattttgttcttcttcttcttcggcATTATTGTTGACGCCATAGACCGGATAGTACAAACTGGCCAAAGACTCAGCAGCTGTTTGCTGCTGCGAGGACGACATTGTTGTGTTCATGGCCTCCAAATACACCTCTGGGGTTTCATTGTTGTCGTCGCCGTCCTCATCCAAGATACCCGCGGCTATGGCATCACTCTCAATATCGATATCTggctgctgctgctgctcATCCCCCGCCACAGAAAGGTTTTCGTCGAACTCCTTTCTTATGCAGTGGTCCATGATCCAATGCACGAGCTGGTCGATGTCGCGAAGGGACGACTCGGTAAACGTCTGGTCCAAGATCCGCGTGCGCTCCTCGGCCGTGATGAACTGCGAGTAGCCTGTCAAATTGTGATAATCACGGGAATTGACCTCCAACACAACTTCATCGTCTCTATACTTATCAAGCATCTCAGTGAGCGCCAAGGAGGCCGCAGGTTTGGGTCCATACATCAATACCATGTATACTAGCTCGGCCATGTACTTGCTGTACTGCACGCACGTCAGGTATGTCTGGACTGCGCGGAAATACGTAGATGGGCGGCGGTTAAAGTTATTGGAGGCGCGCCCGGTGTTATTGGCGGCGCCTTTGAAAAGTCGCACCACTTCGAGTCCCGCAAGATTGACATGGTTGATGTTGGCGCTGAAAAGTGTCATGGCACCCACCAAGAGCTGGGCTATC";
    Context cont;
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
       blasten();
       parser();
    }

    public static void blasten(){
        NCBIQBlastService service = new NCBIQBlastService();
//        if (args.length == 1) {
//            service = new NCBIQBlastService(args[0]);
//        } else {
//            service = new NCBIQBlastService();
//        }

        NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
        props.setBlastProgram(BlastProgramEnum.blastn);
        props.setBlastDatabase("nr");
        props.setAlignmentOption(ENTREZ_QUERY, " fungi[Organism]");

        NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
        outputProps.setDescriptionNumber(10);

        String rid = null;
        FileWriter writer = null;
        BufferedReader reader = null;

        try {
            rid = service.sendAlignmentRequest(SEQUENCE, props);

            while (!service.isReady(rid)) {

                System.out.println("Waiting for results. Sleeping for 5 seconds");
                Thread.sleep(5000);

            }

            // read results when they are ready
            InputStream in = service.getAlignmentResults(rid, outputProps);
            reader = new BufferedReader(new InputStreamReader(in));

            File f = new File(BLAST_OUTPUT_FILE);
            path = f.getAbsolutePath();
            System.out.println("Saving query results in file " + f.getAbsolutePath());
            writer = new FileWriter(f);

            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line + System.getProperty("line.separator"));
                //     System.out.println("line "+line);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
            IOUtils.close(reader);
            service.sendDeleteRequest(rid);
        }
    }

    public static void parser() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse (new File("blastOutput.xml"));

        doc.getDocumentElement().normalize();
        NodeList hit = doc.getElementsByTagName("Hit");
        PrintWriter writer = new PrintWriter("hits.txt");


        for(int s=0; s < hit.getLength() ; s++){
            Node root = hit.item(s);
            if(root.getNodeType() == Node.ELEMENT_NODE) {
                Element child = (Element) root;

                NodeList hit_num_lijst = child.getElementsByTagName("Hit_num");
                Element hit_num = (Element)hit_num_lijst.item(0);

                NodeList hitnum = hit_num.getChildNodes();
                writer.println("Hit nummer: "+((Node)hitnum.item(0)).getNodeValue().trim());

                //----
                NodeList accessie_lijst = child.getElementsByTagName("Hit_accession");
                Element accessie = (Element)accessie_lijst.item(0);

                NodeList accession = accessie.getChildNodes();
                writer.println("Accessiecode: "+ ((Node)accession.item(0)).getNodeValue().trim());

                //----
                NodeList score_lijst = child.getElementsByTagName("Hsp_bit-score");
                Element score = (Element)score_lijst.item(0);

                NodeList score1 = score.getChildNodes();
                writer.println("Score: "+ ((Node)score1.item(0)).getNodeValue().trim());

                //----
                NodeList eVal_lijst = child.getElementsByTagName("Hsp_evalue");
                Element eval = (Element)eVal_lijst.item(0);

                NodeList evValue = eval.getChildNodes();
                writer.println("E-value: "+ ((Node)evValue.item(0)).getNodeValue().trim());

                writer.println();
            }
        }
        writer.close();
    }
}
