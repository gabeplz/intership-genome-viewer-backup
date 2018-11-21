package com.mycompany.minorigv;
import java.io.*;
import java.util.ArrayList;

public class FastaFileReader {

    //String pad = "C:\\Users\\Tim Kuijpers\\Desktop\\Minor_project\\git\\Minor-IGV\\MinorIGV\\resources";


    public static ArrayList<String> getSequences(String pad) throws IOException{

        BufferedReader f_reader = new BufferedReader(new FileReader(pad));
        ArrayList<String> CH_list = new ArrayList<>();

        String regel = f_reader.readLine();

        while(regel != null){
            String header = regel;
            regel = f_reader.readLine();

            StringBuilder bob = new StringBuilder();

            while(regel != null && !regel.startsWith(">")){
                regel = regel.trim();
                bob.append(regel);
                regel = f_reader.readLine();
            }

            System.out.println(header);
            CH_list.add(bob.toString());


        }
        System.out.println(CH_list);
        return CH_list;
    }
/*
    public static void main(String[] args) {
        String pad = "C:\\Users\\Tim Kuijpers\\Desktop\\Minor_project\\git\\Minor-IGV\\MinorIGV\\resources\\GCF_000146045.2_R64_genomic.fna";

        try {
            System.out.println(getSequences(pad).get(0).length());
        } catch(Exception e){
            System.out.println(e.toString());
        }


    }

*/

}