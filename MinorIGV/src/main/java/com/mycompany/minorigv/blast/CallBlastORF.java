package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gui.Context;

import java.io.IOException;
import java.util.ArrayList;

public class CallBlastORF {
    Context cont = null;

    public CallBlastORF(Context cont){
        this.cont = cont;
    }

    private String fasta = "/NAS/minor-g1/non_redundant/blast.fasta";
    private String out = "/NAS/minor-g1/non_redundant/";


    public void callBlast(ArrayList<ORF> orfList, String partOutputName) throws IOException {
        cont.saveORFs(orfList, "blastORF");
        BLAST blast = new BLAST();
        String output = out + cont.getOrganism().getId() + partOutputName;
        blast.runBLAST(fasta, output, "blastp", BLAST.blastDB);
//        BLAST.getValuesORF(cont.getCurORFListALL());
    }


}
