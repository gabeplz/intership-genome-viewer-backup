package com.mycompany.minorigv.gui;

import java.util.*;

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

		if(newStop-2 < newStart){
			//TODO error handling
		}
		else if(newStop > this.curChromosome.getSeqTemp().length()-1){
			//TODO error handling
		}
		else if(newStart < 0){
			//TODO error handling
		}
		else {
			this.start = newStart;
			this.stop = newStop;

			changed();
		}
	}

	public void changed(){
		setChanged();
		notifyObservers();
	}

	public Context(){

	}
	
	public Context(String test) {
		
		ArrayList<Chromosome> testList;
		testList = new ArrayList<Chromosome>();

		String seq = "ATCgatcGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCGATCG";

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
	public void addFasta(String path) throws Exception{
		HashMap<String,String> fastaMap = FastaFileReader.getSequences(path);

		if (organism == null){
			organism = new Organisms();
		}

		for(String id : fastaMap.keySet()){
			organism.addSequence(id,fastaMap.get(id));
		}

		changed();

	}

	public int getLength(){
		return stop-start;
	}
	
	private void updateView() {
		CurrentFeatureList = curChromosome.getFeaturesBetween(start, stop);
		
	}

	public void changeChromosome(String id) throws Exception {
		curChromosome = organism.getChromosome(id);

		int stop = (100 < curChromosome.getSeqTemp().length()-1) ? 100 : curChromosome.getSeqTemp().length()-1;
		changeSize(0,stop);
	}
	
	public String getSubSequentie() {
		System.out.println(curChromosome);
		return this.curChromosome.getSeqTemp().substring(start, stop);
		
	}

	public String[] getChromosomeNames(){
		Set<String> namesSet = this.organism.getChromosomes().keySet();
		String[] nameArray = namesSet.toArray(new String[namesSet.size()]);
		Arrays.sort(nameArray);
		return nameArray;
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