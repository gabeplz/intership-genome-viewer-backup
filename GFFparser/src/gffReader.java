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
            
            if (line.split("\\t")[2].equals("gene")) {
                String[] geneColumns = line.split("\\t");
                Gene genObject = makeGene(geneColumns[1], geneColumns[3], geneColumns[4], geneColumns[5], geneColumns[6], geneColumns[7], geneColumns[8]);
                make(chromosomes, genObject);
            }else if (line.split("\\t")[2].equals("mRNA")) {
                String[] mRNAColumns = line.split("\\t");
                mRNA mRNAObject = makemRNA(mRNAColumns[1], mRNAColumns[3], mRNAColumns[4], mRNAColumns[5], mRNAColumns[6], mRNAColumns[7], mRNAColumns[8]);
                make(chromosomes, mRNAObject);
            }else if (line.split("\\t")[2].equals("exon")) {
                String[] exonColumns = line.split("\\t");
                Exon exonObject = makeExon(exonColumns[1], exonColumns[3], exonColumns[4], exonColumns[5], exonColumns[6], exonColumns[7], exonColumns[8]);
                make(chromosomes, exonObject);
            }else if (line.split("\\t")[2].equals("CDS")) {
                String[] cdsColumns = line.split("\\t");
                CDS cdsObject = makeCDS(cdsColumns[1], cdsColumns[3], cdsColumns[4], cdsColumns[5], cdsColumns[6], cdsColumns[7], cdsColumns[8]);
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




    private static Gene makeGene(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        Gene object;
        object = new Gene(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static mRNA makemRNA(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        mRNA object;
        object = new mRNA(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static Exon makeExon(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        Exon object;
        object = new Exon(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    private static CDS makeCDS(String seqid, String start, String end, String score, String strand, String phase, String attributes) {
        CDS object;
        object = new CDS(seqid, start, end, score, strand, phase, attributes);
        return object;
    }


}
