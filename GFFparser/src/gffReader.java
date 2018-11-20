import java.io.*;
import java.util.*;


public class gffReader {

    public String line;
    public String ID;

    public void readData(String path) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<Chromosome> chromosomes = new ArrayList<>();

        while ((line = reader.readLine()) != null) if (line.startsWith("##sequence-region")) {
            String[] splited = line.split("\\s+");
            ID = splited[1];
        } else if (line.startsWith("#")) {
        } else if (line.startsWith(ID)) {
            String[] columns = line.split("\\t");

            attributes att = new attributes();


            if (line.split("\\t")[2].equals("gene")) {
                HashMap attribute = att.splitAtt(columns[8]);
                Gene genObject = (Gene) makeGene(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                make(chromosomes, genObject);
            }else if (line.split("\\t")[2].equals("mRNA")) {
                HashMap attribute = att.splitAtt(columns[8]);
                mRNA mRNAObject = makemRNA(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                make(chromosomes, mRNAObject);
            }else if (line.split("\\t")[2].equals("exon")) {
                HashMap attribute = att.splitAtt(columns[8]);
                Exon exonObject = makeExon(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7],attribute);
                make(chromosomes, exonObject);
            }else if (line.split("\\t")[2].equals("CDS")) {
                HashMap attribute = att.splitAtt(columns[8]);
                CDS cdsObject = makeCDS(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                make(chromosomes, cdsObject);
            }
        }

        for( Chromosome chromosome : chromosomes){
            //NC_001133.9
            if(chromosome.getId().equals("NC_001134.8")){
                System.out.println(chromosome);
            }
        }

    }

    private void make(ArrayList<Chromosome> chromosomes, Feature feature) {
        int index = containsID(chromosomes, ID);
        if(index == -1){
            ArrayList<Feature> initialList = new ArrayList<>();
            initialList.add(feature);
            Chromosome chromosome = new Chromosome(ID, initialList);
            chromosomes.add(chromosome);
        }else{
            Chromosome currentChromosome = chromosomes.get(index);
            currentChromosome.getFeatures().add(feature);
        }
    }

    private int containsID(ArrayList<Chromosome> list, String id){
        for( int i=0;i< list.size();i++){
            if(list.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }




    private static Gene makeGene(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        Gene object;
        object = new Gene(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static mRNA makemRNA(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        mRNA object;
        object = new mRNA(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static Exon makeExon(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        Exon object;
        object = new Exon(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static CDS makeCDS(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        CDS object;
        object = new CDS(seqid, start, end, score, strand, phase, attributes);
        return object;
    }


}
