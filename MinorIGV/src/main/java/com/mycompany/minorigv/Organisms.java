package com.mycompany.minorigv;

import java.util.ArrayList;

public class Organisms {
    private String id; //organism id
    private ArrayList<Chromosome> chromosomes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public Organisms(String id, ArrayList<Chromosome> chromosomes) {
        this.id = id;
        this.chromosomes = chromosomes;
    }
}