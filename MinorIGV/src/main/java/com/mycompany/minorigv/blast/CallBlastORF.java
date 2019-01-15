package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gui.Context;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Amber Janssen Groesbeek en Anne van Ewijk
 * Roept de blastp aan voor de ORFS.
 */
public class CallBlastORF {
    Context cont;
    private String input = "/NAS/minor-g1/non_redundant/blast.input";  //path naar input file
    private String out = "/NAS/minor-g1/non_redundant/";               //path naar output file
    private String fasta = "/NAS/minor-g1/non_redundant/blast.fasta";

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
    public void callBlast(ArrayList<ORF> orfList, String partOutputName) throws IOException {
        cont.saveORFs(orfList, "blastORF");
        BLAST blast = new BLAST();
        String output = out + cont.getOrganism().getId() + partOutputName;
        blast.runBLAST(fasta, output, "blastp", BLAST.blastDB);
//        BLAST.getValuesORF(cont.getCurORFListALL());
    }


}
