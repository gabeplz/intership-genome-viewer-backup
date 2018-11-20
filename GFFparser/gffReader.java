/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GFFparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author amber/anne
 */
public class gffReader {
    public static String path = "/home/users/bxznn/Documents/voorbeeld gff.gff";
    public static String line;
    public static String ID;
    public static ArrayList<String> listChromosoom = new ArrayList<String>();
    public static Map<String, ArrayList> Prob = new HashMap<String, ArrayList>();

    public static void main(String[] args) throws FileNotFoundException, IOException{
        readData(path);
    }

    public static void readData(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));

        while((line = reader.readLine()) != null){

            if(line.startsWith("##sequence-region")){
                String[] splited = line.split("\\s+");
                ID = splited[1];
            } else if(line.startsWith("#")){
            } else if(line.startsWith(ID)){

                if(line.split("\\t")[2].equals("gene")){
                    String[] geneColumns = line.split("\\t");
                    Gene genObject = makeGene(geneColumns[1], geneColumns[3], geneColumns[4], geneColumns[5], geneColumns[6], geneColumns[7], geneColumns[8]);
                    System.out.println(ID + ": " + genObject);
                    ArrayList<Gene> list;
                    if(Prob.containsKey(ID)){
                        list = Prob.get(ID);
                        list.add(genObject);
                    }else{
                        list = new ArrayList<>();
                        list.add(genObject);
                        Prob.put(ID, list);
                    }


                }



            }
        }
        System.out.println(Prob);
    }

    private static Gene makeGene(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        Gene object;
        object = new Gene(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

}