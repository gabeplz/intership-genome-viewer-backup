import java.util.ArrayList;

public class InformationUser {


    public void getInfo(ArrayList<String> allContigs, ArrayList<Chromosome> chromosomes){
        Gene gen = new Gene();

        for( Chromosome chromosome : chromosomes){
            String contigID = allContigs.get(1);
            if(chromosome.getId().equals(contigID)){
//                chromosome.getFeatures();
                System.out.println(chromosome);
                ArrayList lijst = chromosome.getFeatures();
                for(Object s : lijst){
                    System.out.println(s.getStart_gene());
                    String klas = s.getClass().toString();
                    if("class Gene".equals(klas)){
                        System.out.println(gen.getId(s));
                    }

                }
            }
        }
    }
}
