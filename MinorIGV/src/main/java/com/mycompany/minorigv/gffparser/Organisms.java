package com.mycompany.minorigv.gffparser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Deze class maakt objecten voor organismes. Ook worden in het object de bijbehorende chromosomen gezet.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class Organisms {
    private String id; //organism id
    private HashMap<String,Chromosome> chromosomes;


    /**
     * De constructor.
     * @param id            Het id van het organisme.
     * @param chromosomes   De chromosomen die in het organisme zitten.
     */
    public Organisms(String id, ArrayList<Chromosome> chromosomes) {
        this.id = id;
        //this.chromosomes = chromosomes;
    }

    public Organisms(String id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Organisms{" +
                "id='" + id + '\'' +
                ", chromosomes=" + chromosomes +
                '}';
    }

    public Organisms() {
        chromosomes = new HashMap<String,Chromosome>();
    }

    /**
     * Het returned het id van het organisme.
     * @return  id is het id van het organisme. Id is een String.
     */
    public String getId() {
        return id;
    }


    /**
     * Het genereerd het id van het organisme.
     * @param id is het id van het organisme.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Het returnd een ArrayList met daarin de chromosomen die zich in het organisme bevinden.
     * @return  chromosomes is een ArrayList met daarin de chromosomen.
     */
    public HashMap<String,Chromosome> getChromosomes() {
        return chromosomes;
    }

    /**
     * Zoekt het chromosoom dat voldoet aan de opgegeven id (die de gebruiker heeft gekozen).
     * @param id       id van het chromosoom die de gebruiker heeft uitgekozen.
     * @return         het chromosoom dat voldoet aan het chromosoom id.
     * @throws Exception
     */
    public Chromosome getChromosome(String id) throws Exception{
        // Loopt over alle chromosoom objecten

        return chromosomes.get(id);

    }

    public void addSequence(String id, String Seq){

        if(chromosomes.containsKey(id)){
            chromosomes.get(id).setSeq(Seq);
        }
        else {
            Chromosome chr = new Chromosome();
            chr.setSeq(Seq);
            chr.setId(Seq);
        }
    }

    public void addChromosome(Chromosome chr){
        this.chromosomes.put(chr.getId(),chr);
}

    /**
     * Het genereerd een ArrayList met daarin de chromosomen die zich in het organisme bevinden.
     * @param chromosomes is een ArrayList met daarin de chromosomen.
     */
    //public void setChromosomes(ArrayList<Chromosome> chromosomes) {
    //    this.chromosomes = chromosomes;
    //}
}
