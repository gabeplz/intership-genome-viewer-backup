package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gui.Context;
import com.mycompany.minorigv.gui.Paths;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Amber Janssen Groesbeek en Anne van Ewijk
 * Roept de blastp aan voor de ORFS.
 */
public class CallBlastORF {
    Context cont;

    /**
     * Constructor.
     * @param cont
     */
    public CallBlastORF(Context cont){
        this.cont = cont;
    }

    /**
     * Aanroepen van de blastp waarbij ORFs worden geblast.
     * @param orfList           Alle ORFs of de ORFs tussen twee waardes.
     * @param partOutputName    Stuk naam voor output bestand.
     * @throws IOException
     */
    public void callBlast(ArrayList<ORF> orfList, String partOutputName) throws IOException, JAXBException, ParserConfigurationException, SAXException {
        cont.saveORFs(orfList, "blastORF");
        BLAST BLAST = new BLAST();
        String output = nameXML(cont.getPath(Paths.OUTPUT_ORF), partOutputName);
        BLAST.runBLAST(nameInput(cont.getPath(Paths.SAVE_BLAST_ORF)), output, "blastp", cont.getPath(Paths.NR));

        BlastORF b = new BlastORF();
        b.getValuesORF(orfList, output);
    }

    public String nameXML(String out , String partOutputName){
        String output = out + cont.getOrganism().getId() + partOutputName;
        return output;
    }

    public String nameInput(String input){
        return input + "blastORF.fasta";
    }



}
