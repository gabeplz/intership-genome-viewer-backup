package com.mycompany.minorigv.gui;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Organisms;
import com.mycompany.minorigv.gffparser.GffReader;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.sequence.CodonTable;
import com.mycompany.minorigv.sequence.TranslationManager;

/**
 * Het object dat de binding realiseert tussen de GUI en de onderliggende Data.
 * @author kahuub
 *
 */
public class Context implements Serializable, PropertyChangeListener {

	private Organisms organism;
	private Chromosome curChromosome;

	private String[] chromosomeNameArray;

	private CodonTable currentCodonTable;

	private Feature[] currentFeatureList;
	private int featStart;
	private int featStop;

	private int start;
	private int stop;
	private ArrayList<String> choiceUser;
	private HashMap<String,String> fastaMap = new HashMap<>();


	private final int DEFAULT_START = 0;
	private final int DEFAULT_STOP = 100;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * Constructor voor testing purposes.
	 * @param organism een Organisms object
	 */
	public Context(Organisms organism) {

		this.organism = organism;
		this.curChromosome = organism.getChromosomes().get(0);
		start = 0;
		stop = 100;
	}

	/**
	 * Wrapper functie voor het aanpassen van de scope op het chromosoom.
	 * @param newStart de nieuwe start,
	 * @param newStop de nieuwe stop
	 */
	public void changeSize(int newStart, int newStop) {
		if(this.organism == null || this.curChromosome == null) {
			return;
		}

		if(newStop-2 < newStart){
			//TODO error handling
		}
		else if(newStop > this.curChromosome.getSeqTemp().length()-1){
			this.setStart(newStart);
			this.setStop(this.curChromosome.getSeqTemp().length()-1);
		}
		else if(newStart < 0){
			//TODO
		}
		else {
			this.setStart(newStart);
			this.setStop(newStop);

		}
		pcs.firePropertyChange("range", null, null); //Fire the range change event
	}

	/**
	 * constructor voor Context ten behoeve van uitbreidbaarheid.
	 */
	public Context(){
        this.addPropertyChangeListener(this);
        this.choiceUser = new ArrayList<String>();
        this.setCurrentCodonTable(1);						//the ncbi standard coding table always has an id of 1
	}


	public void addFasta(String path) throws Exception{

		//Als er geen organism is, maak hem aan. eerste fasta.
		if (organism == null){
			this.setOrganism(new Organisms());
		}
		//er is al een gff ingelezen & een fasta dus we mieteren het weg.
		else if(curChromosome.getFeatures().size() > 0 && curChromosome.getSeqTemp() != null) {
			this.setOrganism(new Organisms());
		}
		//er is al een gff ingelezen & niet een fasta.
		else if (curChromosome.getFeatures().size() > 0 && curChromosome.getSeqTemp() == null) {
			//pass
		}
		//er is geen gff ingelezen maar wel al een fasta.
		else if (curChromosome.getFeatures().size() < 1 && curChromosome.getSeqTemp() != null) {
			this.setOrganism(new Organisms());
		}
		//er is geen gff en geen fasta.
		else if (curChromosome.getFeatures().size() < 1 && curChromosome.getSeqTemp() == null) {
			//voor volledigheid
		}

		fastaMap = FastaFileReader.getSequences(path);

		//loopen over de [header]->Sequentie paren.
		for(String id : fastaMap.keySet()){
			organism.addSequence(id,fastaMap.get(id));
		}

		setChromosomeNames(); //update chromosoom namen
		this.setCurChromosome(organism.getChromosome(this.chromosomeNameArray[0])); //chromosome resetten
		this.updateCurrentFeatureList(); //resetten featureList
		defaultSize(); //defaulten qua size
		pcs.firePropertyChange("fasta", null, null); //fire the fasta event

	}
	public ArrayList<String> getChoicesUser(){
		return choiceUser;
	}

	/**
	 * Functie voor het toevoegen van een GFF File aan het organism/aanmaken van
	 * @param path
	 * @throws Exception
	 */
	public void addGFF(String path) throws Exception {

		//als er nog geen organism object is.
		if (organism == null){
			this.setOrganism(new Organisms());
		}
		//er is al een gff ingelezen & een fasta dus we mieteren het weg.
		else if(curChromosome.getFeatures().size() > 0 && curChromosome.getSeqTemp() != null) { //
			this.setOrganism(new Organisms());
		}
		//er is al een gff ingelezen & niet een fasta.
		else if (curChromosome.getFeatures().size() > 0 && curChromosome.getSeqTemp() == null) {
		    this.setOrganism(new Organisms());
		}
		//er is geen gff ingelezen maar wel al een fasta.
		else if (curChromosome.getFeatures().size() == 0 && curChromosome.getSeqTemp() != null) {
			//pass
		}
		//er is geen gff en geen fasta.
		else if (curChromosome.getFeatures().size() == 0 && curChromosome.getSeqTemp() == null) {
			//voor volledigheid
		}

		GffReader.readData(organism, path);
		setChromosomeNames();
		this.setCurChromosome(organism.getChromosome(this.chromosomeNameArray[0])); //chromosome resetten
		this.updateCurrentFeatureList(); //resetten featureList
		defaultSize(); //defaulten qua size
		pcs.firePropertyChange("gff", null, null); //fire het gff event


	}

	/**
	 * de lengte van de subsequentie.
	 * @return de lengte van de subsequentie.
	 */
	public int getLength(){
		return stop-start;
	}

	/**
	 * Functie die de volledige lengte van de sequentie van het huidige chromosoom retourneert.
	 * @return De lengte van de sequentie van het huidige chromosoom.
	 */
	public int getFullLenght() {
		return curChromosome.getSeqTemp().length();
	}


	private void updateCurrentFeatureList() {
	    this.featStart = start;
	    this.featStop = stop;
	    ArrayList<Feature> featList = Chromosome.filterFeatures(curChromosome.getFeaturesBetween(featStart,featStop),this.choiceUser);
		this.currentFeatureList = featList.toArray(new Feature[featList.size()]);

		pcs.firePropertyChange("currentFeatureList",null,null);

	}

	/**
	 * Functie die het Chromosoom wisselt.
	 * @param id het String ID om het chromosoom te kiezen.
	 * @throws Exception
	 */
	public void changeChromosome(String id) throws Exception {
		this.setCurChromosome(organism.getChromosome(id));

		if(this.stop > this.curChromosome.getSeqTemp().length()-1) defaultSize();
		pcs.firePropertyChange("chromosome", null, null);
	}

	/**
	 * Functie die bij het switchen van Chromosomen de start en stop default naar 0-100 of 0-lengte adhv welke kleiner is.
	 */
	private void defaultSize() {

		int stop = (DEFAULT_STOP < curChromosome.getSeqTemp().length()-1) ? DEFAULT_STOP : curChromosome.getSeqTemp().length()-1;
		changeSize(DEFAULT_START,stop);

	}

	/**
	 * Functie voor het verkrijgen van de huidig geselecteerde subsequentie.
	 * @return de huidige subsequentie
	 */
	public String getSubSequentie() throws NullPointerException {
		return this.curChromosome.getSeqTemp().substring(start, stop);

	}

	/**
	 * functie voor het verkrijgen van de huidige chromosoom namen als String[].
	 * @return String[] met hierin gesorteerd de chromosoom namen.
	 */
	public String[] getChromosomeNames(){
		return this.chromosomeNameArray;
	}

	/**
	 * Functie die de chromosoom namen synchroniseert met het organism.
	 */
	public void setChromosomeNames() {
		String[] oldValue = this.chromosomeNameArray;
		Set<String> namesSet = this.organism.getChromosomes().keySet();
		String[] nameArray = namesSet.toArray(new String[namesSet.size()]);
		Arrays.sort(nameArray);
		this.chromosomeNameArray = nameArray;

		pcs.firePropertyChange("chromosomeNameArray", oldValue, this.chromosomeNameArray); //vuur het chromosomeNameArray event af.

	}

	public Organisms getOrganism() {
		return organism;
	}

	public Feature[] getCurrentFeatureList() {

        if (currentFeatureList == null || featStart != start || featStop != stop) {
            updateCurrentFeatureList();
        }
        return this.currentFeatureList;
    }

	/**
	 * Functie voor het krijgen van alle Features van het huidige chromosoom.
	 * @return een array met alle huidige features.
	 */
	public ArrayList<Feature> getWholeFeatureList() {

		return this.curChromosome.getFeatures();
	}

	public void setChoiceUser(ArrayList<String> choices){
		this.choiceUser = choices;
	}

	public int getStart() {
		return start;
	}

	public int getStop() {
		return stop;
	}


	public void setOrganism(Organisms organism) {
		Organisms oldValue = this.organism;
		this.organism = organism;
		pcs.firePropertyChange("organism", oldValue, this.organism); //organism event
	}

	public void setCurChromosome(Chromosome curChromosome) {
		Chromosome oldValue = this.curChromosome;
		this.curChromosome = curChromosome;
		pcs.firePropertyChange("chromosome", oldValue, this.curChromosome); //chromosome event
	}

	public Chromosome getCurChromosome() {
		return curChromosome;
	}

	private void setStart(int start) {
		this.start = start;
	}

	private void setStop(int stop) {
		assert stop <= this.getFullLenght()-1;
		this.stop = stop;
	}

	public CodonTable getCurrentCodonTable() {
		return currentCodonTable;
	}

	public void setCurrentCodonTable(Integer key) {
		CodonTable oldValue = this.currentCodonTable;
		this.currentCodonTable = TranslationManager.getInstance().getCodonTable(key);
		pcs.firePropertyChange("CodonTable", oldValue, currentCodonTable);
	}

	/**
	 * encapsulatie van de property change support
	 * @param listener het element dat geinformeerd wil worden
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * encapsulatie van de property change support
	 * @param listener het element dat geinformeerd wil worden
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	/**
	 * encapsulatie van de property change support
	 * @param topic het topic waarop geluisterd moet worden.
	 * @param listener het element dat geinformeerd wil worden
	 */
	public void addPropertyChangeListener(String topic, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(topic, listener);
	}

	/**
	 * encapsulatie van de property change support
	 * @param topic het topic waarop geluisterd moet worden.
	 * @param listener het element dat geinformeerd wil worden
	 */
	public void removePropertyChangeListener(String topic, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(topic,listener);
	}

	/**
	 * Dit object luistert ook naar zichzelf om hulper objecten te updaten.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String name = evt.getPropertyName();
		if(name.equals("fasta") ||
				name.equals("gff") ||
				name.equals("organism")
				)
		{this.setChromosomeNames();}
		if(name.equals("range")){
		    this.updateCurrentFeatureList();
        }

	}

	public String getSequentie() {
		return curChromosome.getSeqTemp();
	}

	/**
	 *
	 * @return een ArrayList met ORFs tussen een bepaalde start en stop.
	 */
    public ArrayList<ORF> getCurORFListBetween() {
        return curChromosome.getORFsBetween(start, stop);
	}

	/**
	 * Zoekt naar alle ORFs die liggen op het chromosoom/contig met minimaal een bepaalde lengte die is ingevoerd door de gebruiker.
	 * @param lenghtORF		de lengte die het ORF minimaal mag hebben, ingevoerd door de gebruiker.
	 */
	public void setCurORFListALL(int lenghtORF){
		curChromosome.setListORF(lenghtORF);
	}

	/**
	 *
	 * @return een ArrayList met ORFs van het hele chromosoom/contig.
	 */
	public ArrayList<ORF> getCurORFListALL(){
		return curChromosome.getListORF();
	}

	/**
	 * Wegschrijven ORFs in fasta bestand.
	 * @param curORFList
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void saveORFs(ArrayList<ORF> curORFList) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("orf.fasta", "UTF-8");

		for(ORF o: curORFList){
			// Header
			writer.println(">" + o.getIdORF() + "|RF: " + o.getReadingframe() + "|start: " + o.getStart() + "|stop: " + o.getStop() + "|strand: " + o.getStrand());
			// Sequentie in nt.
			String subSeq = curChromosome.getSeqTemp().substring(o.getStart(), o.getStop());
			writer.println(subSeq);
		}
		writer.close();
	}

}