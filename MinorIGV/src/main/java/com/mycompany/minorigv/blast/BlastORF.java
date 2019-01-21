package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.BlastedORF;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gffparser.Organisms;
import com.mycompany.minorigv.gui.Context;
import com.mycompany.minorigv.gui.Paths;
import com.mycompany.minorigv.sequence.Strand;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class BlastORF {

    Context cont;

    public BlastORF(){

    }

    /**
     * Constructor.
     * @param cont
     */
    public BlastORF(Context cont){
        this.cont = cont;
    }

    /**
     * Aanroepen van de blastp waarbij ORFs worden geblast.
     * @param orfList           Alle ORFs of de ORFs tussen twee waardes.
     * @param partOutputName    Stuk naam voor output bestand.
     * @throws IOException
     */
    public String callBlast(ArrayList<ORF> orfList, String partOutputName) throws IOException, JAXBException, ParserConfigurationException, SAXException {
        cont.saveORFs(orfList, "blastORF");

        String output = nameXML(cont.getPath(Paths.OUTPUT_ORF), partOutputName);
        BLAST.runBLAST(nameInput(cont.getPath(Paths.SAVE_BLAST_ORF)), output, "blastp", cont.getPath(Paths.NR));

        return output;

    }

    public String nameXML(String out , String partOutputName){

        String[] nameFasta = cont.getNameFasta().split("////");
        String nameFile = nameFasta[nameFasta.length-1];
        String[] splitNameFile = nameFile.split("_");
        String codeFasta = splitNameFile[0] + "_"+ splitNameFile[1];
        String output = out + codeFasta + partOutputName;
        return output;
    }

    public String nameInput(String input){
        return input + "blastORF.fasta";
    }

    public void parseBlastResults(BlastOutput bo){

        Organisms org = cont.getOrganism();
        for(Chromosome chrom : org.getChromosomes().values()){
            ArrayList<ORF> orfList = new ArrayList<>(0);

            for (Iteration iter : bo.getBlastOutputIterations().getIteration()){
                orfList.add(makeORF(iter));

            }
            chrom.setListORF(orfList);
        }
    }


    public ORF makeORF(Iteration iter) {

        ORF orf = null;

        String header = iter.iterationQueryDef;
        String[] informationHeader = header.split("\\|");

        String id = (informationHeader[0]);
        int RF = Integer.parseInt(informationHeader[1].split(":")[1]);
        int startORF = Integer.parseInt(informationHeader[2].split(":")[1]);
        int stopORF = Integer.parseInt(informationHeader[3].split(":")[1]);
        String strandORF = informationHeader[4].split(":")[1];
        String chromName = informationHeader[5].split(":")[1];
        Strand strandORFenum = null;
        if (strandORF.equals("POSITIVE")) {
            strandORFenum = Strand.POSITIVE;
        } else if (strandORF.equals("NEGATIVE")) {
            strandORFenum = Strand.NEGATIVE;
        }

        orf = new BlastedORF(startORF, stopORF, RF, id, strandORFenum, stopORF - startORF, chromName,iter);

        return orf;
    }


}