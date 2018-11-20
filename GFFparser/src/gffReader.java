import java.io.*;
import java.util.*;


public class gffReader {

    public String line;
    public String ID;
    public String last_ID;
    //public

//    public HashMap<String, ArrayList<Gene>> chrom_Hashmap = new HashMap<String, ArrayList<Gene>>();
    public static HashMap<String, HashMap<String, ArrayList>> chrom_Hashmap = new HashMap<String, HashMap<String, ArrayList>>();

    public void readData(String path) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<Gene> list = new ArrayList<Gene>();

        while((line = reader.readLine()) != null) if (line.startsWith("##sequence-region")) {
            String[] splited = line.split("\\s+");
            ID = splited[1];
            System.out.println(ID);
        } else if (line.startsWith("#")) {
        } else if (line.startsWith(ID)) {

            if (line.split("\\t")[2].equals("gene")) {
                String[] geneColumns = line.split("\\t");
                Gene genObject = makeGene(geneColumns[1], geneColumns[3], geneColumns[4], geneColumns[5], geneColumns[6], geneColumns[7], geneColumns[8]);
                //System.out.println(ID + ": " + genObject);
                Map<String, ArrayList> gene_hashmap = new HashMap<String, ArrayList>();
                if (!chrom_Hashmap.containsKey(ID)) {
                    chrom_Hashmap.put(ID, null);

                    chrom_Hashmap.put(ID, (HashMap<String, ArrayList>) gene_hashmap);
                    list = new ArrayList<Gene>();
                }
                if (chrom_Hashmap.containsKey(ID)) {
                    list.add(genObject);

                    if (gene_hashmap.containsKey("Gene")) {
                        gene_hashmap.get("Gene").addAll(list);
                    } else {
                        gene_hashmap.put("Gene", list);
                    }
                }
                chrom_Hashmap.put(ID, (HashMap<String, ArrayList>) gene_hashmap);
                last_ID = ID;
            }
        }

        System.out.println(chrom_Hashmap);
    }

    private static Gene makeGene(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        Gene object;
        object = new Gene(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

}
