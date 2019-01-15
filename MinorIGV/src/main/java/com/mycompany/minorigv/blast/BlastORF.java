package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.HashMap;

public class BlastORF {
//    HashMap<String, Iteration> headerIteration = new HashMap<>();
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
