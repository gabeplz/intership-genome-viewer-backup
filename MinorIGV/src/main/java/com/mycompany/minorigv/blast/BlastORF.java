package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class BlastORF {
    private static HashMap<String, Iteration> headerIteration = new HashMap<>();

    public void getValuesORF(ArrayList<ORF> curORFList, String output) throws JAXBException, FileNotFoundException, SAXException, ParserConfigurationException {
        /** read the result */
        BLAST b = new BLAST();
        BlastOutput bo = b.parseXML(output);
        HashMap<String, Double> headerEvalue = new HashMap<>();
        ArrayList<String> headerORFsNoHits = new ArrayList<>();


        for(int i = 0; i < curORFList.size(); i++){
            String header = bo.getBlastOutputIterations().getIteration().get(i).getIterationQueryDef();
            String idORF = header.split("\\|")[0];
            headerIteration.put(idORF, bo.getBlastOutputIterations().getIteration().get(i));

            if(!bo.getBlastOutputIterations().getIteration().get(i).getIterationHits().getHit().isEmpty()){
                String evalue = bo.getBlastOutputIterations().getIteration().get(i).getIterationHits().getHit().get(0).getHitHsps().getHsp().get(0).getHspEvalue();

                headerEvalue.put(header, Double.parseDouble(evalue));
            }else{
                headerORFsNoHits.add(header);
            }
        }

        ColorORFs col = new ColorORFs();
        col.evalue(headerEvalue, headerORFsNoHits);

    }



    public HashMap<String, Iteration> getHeaderIteration(){
        return headerIteration;
    }


    public void splitHeader(ArrayList<String> header){


    }

}
