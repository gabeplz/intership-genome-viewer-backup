package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gui.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        BLAST BLAST = new BLAST();
        String output = out + cont.getOrganism().getId() + partOutputName;
        BLAST.runBLAST(fasta, output, "blastp");
//        BLAST.getValuesORF(cont.getCurORFListALL());
    }


}
