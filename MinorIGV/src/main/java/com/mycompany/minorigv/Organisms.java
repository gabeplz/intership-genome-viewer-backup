package com.mycompany.minorigv;

import java.util.ArrayList;


/**
 * Deze class maakt objecten voor organismes. Ook worden in het object de bijbehorende chromosomen gezet.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class Organisms {
    private String id; //organism id
    private ArrayList<Chromosome> chromosomes;


    /**
     * De constructor.
     * @param id            Het id van het organisme.
     * @param chromosomes   De chromosomen die in het organisme zitten.
     */
    public Organisms(String id, ArrayList<Chromosome> chromosomes) {
        this.id = id;
        this.chromosomes = chromosomes;
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
    public ArrayList<Chromosome> getChromosomes() {
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
        for (Chromosome chr : chromosomes){
            // Zoekt het chromosoom die voldoet aan het id, die de gebruiker heeft gekozen.
            if(chr.getId().equals(id)){
                return chr;
            }
        }
        throw new Exception();
    }

    /**
     * Het genereerd een ArrayList met daarin de chromosomen die zich in het organisme bevinden.
     * @param chromosomes is een ArrayList met daarin de chromosomen.
     */
    public void setChromosomes(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }
}
