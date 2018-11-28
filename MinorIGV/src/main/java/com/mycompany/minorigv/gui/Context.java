package com.mycompany.minorigv.gui;

import java.util.ArrayList;
import java.util.Observable;

import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Organisms;

public class Context extends Observable {

	private Organisms organism;
	private Chromosome curChromosome;
	private ArrayList<Feature> CurrentFeatureList;
	private int start;
	private int stop;
	
	public Context(Organisms organism) {
		this.organism = organism;
		this.curChromosome = organism.getChromosomes().get(0);
		start = 0;
		stop = 100;
	}
	
	public void changeSize(int newStart, int newStop) {
		this.start = newStart;
		this.stop = newStop;
		changed();
	}

	public void changed(){
		setChanged();
		notifyObservers();
	}
	
	public Context(String test) {
		
		ArrayList<Chromosome> testList;
		testList = new ArrayList<Chromosome>();

		String seq = "ATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCG";

		Organisms org = new Organisms("piet");
		org.addSequence("jan",seq);
		this.organism = org;
		try {
			this.curChromosome = organism.getChromosome("jan");
		}catch (Exception e){
			System.out.println("oeps");
		}
		start = 0;
		stop = 50;
	}

	public int getLength(){
		return stop-start;
	}
	
	private void updateView() {
		CurrentFeatureList = curChromosome.getFeaturesBetween(start, stop);
		
	}

	public void changeChromosome(String id) throws Exception {
		curChromosome = organism.getChromosome(id);
	}
	
	public String getSubSequentie() {
		System.out.println(curChromosome);
		return this.curChromosome.getSeqTemp().substring(start, stop);
		
	}

	public Organisms getOrganism() {
		return organism;
	}

	public Chromosome getCurChromosome() {
		return curChromosome;
	}

	public ArrayList<Feature> getCurrentFeatureList() {
		return CurrentFeatureList;
	}

	public int getStart() {
		return start;
	}

	public int getStop() {
		return stop;
	}


		
}