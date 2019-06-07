package com.mycompany.minorigv.gui;


import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;

import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.blast.BLAST;
import com.mycompany.minorigv.blast.BlastOutput;
import com.mycompany.minorigv.fastqparser.FastqReader;
import com.mycompany.minorigv.fastqparser.InvalidFileTypeException;
import com.mycompany.minorigv.fastqparser.Read;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Organisms;
import com.mycompany.minorigv.gffparser.GffReader;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.motif.MotifAlignment;
import com.mycompany.minorigv.motif.PositionScoreMatrix;
import com.mycompany.minorigv.sequence.CodonTable;
import com.mycompany.minorigv.sequence.MakeCompStrand;
import com.mycompany.minorigv.sequence.Strand;
import com.mycompany.minorigv.sequence.TranslationManager;


import javax.swing.*;

/**
 * Het object dat de binding realiseert tussen de GUI en de onderliggende Data.
 * @author kahuub
 *
 */
public class Context implements Serializable, PropertyChangeListener {

	private Properties applicationProps;

	private Organisms organism;
	private Chromosome curChromosome;

	private String[] chromosomeNameArray;

	private CodonTable currentCodonTable;

	private ReadCoverage readCoverage_fw;
    private ReadCoverage readCoverage_rv;
    private boolean graphBool;
    private boolean graphBoolMotif;


    private ArrayList<SamRead> currentSamReads = new ArrayList<>();
    private HashMap<String,int[][]> barCoverageMap;
    private int coverageReadsPerPixel = 1;
    private int pixelHeightReads = 3 ;
    private int pixelSpaceBetweenReads = 7;
    //private Dimension area = new Dimension(0,0);

	private HashMap<String,ArrayList<SamRead>> readMap;
	private HashMap<Rectangle,String> insertTooltipsMap;

    private ArrayList<Read> currentReads;

	private Feature[] currentFeatureList;
	private int featStart;
	private int featStop;

	private int start;
	private int stop;

	private ArrayList<String> choiceUser;
	private HashMap<String,String> fastaMap = new HashMap<>();
	private String nameFastaFile;

	private ArrayList<PositionScoreMatrix> matrixes = new ArrayList<PositionScoreMatrix>();
	private ArrayList<PositionScoreMatrix> matrixesForSearch = new ArrayList<PositionScoreMatrix>();
    private HashMap<Integer, double[]> matrixForwardAlignmentScores = new HashMap<>();
    private HashMap<Integer, double[]> matrixReverseAlignmentScores = new HashMap<>();
    private double[] StepsForMotifs;
	private final int DEFAULT_START = 0;
	private final int DEFAULT_STOP = 100;

	private BlastOutput blastOutput;

	GUI gui;



	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * constructor voor Context ten behoeve van uitbreidbaarheid.
     * @param gui
     */
	public Context(GUI gui){
	    this.gui = gui;
		this.addPropertyChangeListener(this);
		this.choiceUser = new ArrayList<String>();
		this.setCurrentCodonTable(1);						//the ncbi standard coding table always has an id of 1
        graphBool = false;
        graphBoolMotif = false;

		try {
			parseProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private void parseProperties() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream defaultProperties = classLoader.getResourceAsStream("defaultProperties.txt");
        // create and load default properties
        Properties defaultProps = new Properties();
        InputStream in = defaultProperties;
        defaultProps.load(in);
        in.close();
        applicationProps = new Properties(defaultProps);


        File props = new File("./appProperties.txt");
        if (props.exists()) {
            InputStream appProperties = new FileInputStream(props);
            // now load properties
            // from last invocation
            in = appProperties;
            applicationProps.load(in);
            in.close();

        }else{
            InputStream appProperties =classLoader.getResourceAsStream("appProperties.txt");
            // now load propertiescd In
            // from last invocation
            in = appProperties;
            applicationProps.load(in);
            in.close();

        }

    }

	public String getPath(Paths pathEnum){
		return this.applicationProps.getProperty(pathEnum.toString());
	}

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
	public void changeSize(int newStart, int newStop) throws IndexOutOfBoundsException {

		if(this.organism == null || this.curChromosome == null || curChromosome.getSeqTemp() == null) {
			return;
		}

		if(newStop < newStart){
			throw new IndexOutOfBoundsException("nieuwe stop kleiner dan nieuwe start");
		}

		if(newStop > this.curChromosome.getSeqTemp().length()-1){
			newStop = this.curChromosome.getSeqTemp().length()-1;
		}

		if(newStart < 0){
			newStart = 0;
		}

        if(newStop-newStart < 10){
			throw new IndexOutOfBoundsException("minimale afstand van 10 nodig");
		}


        this.setStart(newStart);
        this.setStop(newStop);


		pcs.firePropertyChange("range", null, null); //Fire the range change event
	}



	public void addFasta(String pathFasta) throws Exception{

	    File file = new File(pathFasta);
        if (!file.exists()){
            return;
        }

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

		fastaMap = FastaFileReader.getSequences(pathFasta);

		File f = new File(pathFasta);
		System.out.println(f.getName());
		setFastFileName(f.getName());

		//loopen over de [header]->Sequentie paren.
		for(String id : fastaMap.keySet()){
			organism.addSequence(id,fastaMap.get(id));
		}


		setChromosomeNames(); //update chromosoom namen
		this.setCurChromosome(organism.getChromosome(this.chromosomeNameArray[0])); //chromosome resetten
		this.updateCurrentFeatureList(); //resetten featureList
		defaultSize(); //defaulten qua size
		pcs.firePropertyChange("fasta", null, null); //fire the fasta event
        removeNonsense();


	}
	public ArrayList<String> getChoicesUser(){
		return choiceUser;
	}

    /**
     * super hackish snel panels on the fly verwijderen.
     */
    private void removeNonsense() {

        if(graphBool || graphBoolMotif){
            for(Component c : gui.organism.getComponents()){
                if(c instanceof GraphPanel){
                    gui.organism.remove(c);
                    graphBool = false;
                }
                if(c instanceof MotifGraphPanel){
                    gui.organism.remove(c);
                    graphBoolMotif = false;
                }
            }

        }

    }

	/**
	 * Functie voor het toevoegen van een GFF File aan het organism/aanmaken van
	 * @param path
	 * @throws Exception
	 */
	public void addGFF(String path) throws Exception {

        File file = new File(path);
        if (!file.exists()){
            return;
        }

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
	public int getFullLenght() throws NullPointerException {
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
		this.updateCurrentFeatureList();
		if(this.stop > this.curChromosome.getSeqTemp().length()-1) {
            defaultSize();
		}

        this.updateCurrentFeatureList();
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
		this.updateCurrentFeatureList();
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

	public ArrayList<Read> getCurrentReads() { return currentReads; }

	public void setCurrentReads(ArrayList<Read> readArrayList) {
		this.currentReads = readArrayList;
	}

	public void readReads(String path){

        FastqReader reader = new FastqReader();
        try {
        	String pathOut = path + ".fasta";
            reader.convertToFasta(path,pathOut);
			pcs.firePropertyChange("Reads", null, currentReads);
        } catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOE error. something went wrong while reading the file. check if: the file isn't corrupted, your user account has access rigts, each contig in the file is sepperated by enters onto 4 lines.");
        } catch (InvalidFileTypeException e){
			JOptionPane.showMessageDialog(null, "file extention does not end with \".txt\"");
		}

    }

    public void addMatrix(PositionScoreMatrix x){
	    this.matrixes.add(x);
    }

    public void removeMatrix(int x){
	    this.matrixes.remove(x);
    }

    public ArrayList<PositionScoreMatrix> getMatrixes(){
	    return this.matrixes;
    }

    public void addMatrixForSearch(PositionScoreMatrix x){
        this.matrixesForSearch.add(x);
    }

    public void removeAllMatrixForSearch(){
        this.matrixesForSearch.clear();
    }

    public ArrayList<PositionScoreMatrix> getMatrixesforSearch(){
        return this.matrixesForSearch;
    }

    public void gogo(){
        this.matrixForwardAlignmentScores = MotifAlignment.Align(curChromosome.getSeqTemp(), getMatrixesforSearch());
        this.matrixReverseAlignmentScores = MotifAlignment.Align(MakeCompStrand.getReverseComplement(curChromosome.getSeqTemp()), getMatrixesforSearch());
        this.StepsForMotifs = MotifAlignment.GenerateNrOfScores(curChromosome.getSeqTemp(), getMatrixesforSearch());
	} ///562643
    public double[] getStepsForMotifs(){
	    return this.StepsForMotifs;
    }

    public void drawMotifs(){
        if(!graphBoolMotif) {
            gui.organism.add(new MotifGraphPanel(this));
            graphBoolMotif = true;
            gui.organism.revalidate();
            gui.organism.repaint();
        }
    }
    public HashMap<Integer, double[]> getmatrixForwardAlignmentScores(){return this.matrixForwardAlignmentScores;}

    public HashMap<Integer, double[]> getMatrixReverseAlignmentScores(){return this.matrixReverseAlignmentScores;}

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
	    if (curChromosome.getSeqTemp() == null){
            return; //als oepsie.
        }
		curChromosome.createListOrf(lenghtORF);
	}

	/**
	 *
	 * @return een ArrayList met ORFs van het hele chromosoom/contig.
	 */
	public ArrayList<ORF> getCurORFListALL(){
		return curChromosome.getListORF();
	}

	/**
	 * Naam van het fasta file setten (input .fna file)
	 * @param nameFastaFile
	 */
	public void setFastFileName(String nameFastaFile){
		this.nameFastaFile = nameFastaFile;
	}

	/**
	 * @return Naam van het .fna (input) file.
	 */
	public String getNameFasta(){
		return nameFastaFile;
	}

	public BlastOutput getBlastOutput() {
		return blastOutput;
	}

	public void setBlastOutput(BlastOutput blastOutput) {
		this.blastOutput = blastOutput;
	}

	/**
	 * Wegschrijven ORFs in fasta bestand.
	 * @param curORFList
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void saveORFs(ArrayList<ORF> curORFList, String buttonClick) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writerSeq = new PrintWriter(getPath(Paths.SAVE_ORF) +"saveORF.fasta", "UTF-8");
		PrintWriter writerAA = new PrintWriter(getPath(Paths.SAVE_BLAST_ORF) +"blastORF.fasta", "UTF-8");
		for(ORF o: curORFList){
			// Sequentie in nt.
			String subSeq = this.getOrganism().getChromosome(o.getChromosomeID()).getSeqTemp().substring(o.getStart(), o.getStop());
			if (o.getStrand() == Strand.NEGATIVE) {
				subSeq = MakeCompStrand.getReverseComplement(subSeq);
			}
			if(buttonClick == "saveORF"){

				writerSeq.println(">" + o.getIdORF() + "|RF:" + o.getReadingframe() + "|start:" + o.getStart() + "|stop:" + o.getStop() + "|strand:" + o.getStrand() + "|chromosome:"+o.getChromosomeID());

				writerSeq.println(subSeq);
			}
			// Sequentie in aa.
			else if(buttonClick == "blastORF"){
				writerAA.println(">" + o.getIdORF() + "|RF:" + o.getReadingframe() + "|start:" + o.getStart() + "|stop:" + o.getStop() + "|strand:" + o.getStrand() + "|chromosome:"+o.getChromosomeID());
				String aaSeq = TranslationManager.getInstance().getAminoAcids(o.getStrand(),subSeq,getCurrentCodonTable());
				if(o.getStrand() == Strand.NEGATIVE){
					//aaSeq = new StringBuilder(aaSeq).reverse().toString();
				}
				writerAA.println(aaSeq);
			}
		}
		writerSeq.close();
		writerAA.close();
	}

    /**
     * Functie voor het blasten tegen een referentie regelen.
     * @param fastqFile de fastQfile die naar fasta moet en dan gebruikt
     * @param genomeFile file van het genoom, al in fasta.
     * @throws IOException IOException
     * @throws InvalidFileTypeException InvalidFileTypeException
     */
    public void blastAgainstReference(String fastqFile, String genomeFile) throws IOException, InvalidFileTypeException {

        if((!new File(fastqFile).exists()) || (!new File(genomeFile).exists())){
            return;
        }

	    FastqReader fastqReader = new FastqReader();
	    String fastqOutputPath = getPath(Paths.OUTPUT_READS) + "readsTemp.fna";
	    fastqReader.convertToFasta(fastqFile,fastqOutputPath);

	    String blastDBOutputPath = getPath(Paths.OUTPUT_READS) + "refGenome";

        BLAST.makeBlastDB(genomeFile,blastDBOutputPath);

        String temp = new File(fastqFile).getName();
        int pos = temp.lastIndexOf(".");
        if (pos > 0) {
            temp = temp.substring(0, pos);
        }

        temp += "_";

        temp += new File(genomeFile).getName();
        pos = temp.lastIndexOf(".");
        if (pos > 0) {
            temp = temp.substring(0, pos); //hackish naam genereren.
        }
        temp += ".csv";

        String blastResultName = getPath(Paths.OUTPUT_READS) + temp;

        System.out.println(blastResultName);
        BLAST.runBLASTAgainstReference(fastqOutputPath,blastResultName,"blastn",blastDBOutputPath);

    }

	public ReadCoverage getReadCoverage_fw() {
		return readCoverage_fw;
	}

	public void setReadCoverage_fw(ReadCoverage readCoverage_fw) {
		this.readCoverage_fw = readCoverage_fw;
	}

    public ReadCoverage getReadCoverage_rv() {
        return readCoverage_rv;
    }

    public void setReadCoverage_rv(ReadCoverage readCoverage_rv) {
        this.readCoverage_rv = readCoverage_rv;
    }

    /**
     * Functie voor het parsen van blastreads tot depth arrays
     * @param path path van de blast file.
     * @throws IOException IOException
     */
    public void parseBlastedReads(String path) throws IOException {

        File f = new File(path);
        if (f.exists() == false) return;

        if (f.getName().contains("_1")){
            this.readCoverage_fw = new ReadCoverage(this.organism);
            readCoverage_fw.parseBlastCSV(path);


        }else if (f.getName().contains("_2")){
            this.readCoverage_rv = new ReadCoverage(this.organism);
            readCoverage_rv.parseBlastCSV(path);

        }
        else{
            readCoverage_fw = new ReadCoverage(this.organism);
            readCoverage_fw.parseBlastCSV(path);
            readCoverage_rv = null;
        }

        if(!graphBool) {
            gui.organism.add(new GraphPanel(this));
            graphBool = true;
            gui.organism.revalidate();
            gui.organism.repaint();
        }
    }

	public ArrayList<int[]> getCurrentReadCoverage() {

        ArrayList<int[]> readList = new ArrayList<int[]>(2);

        if(readCoverage_fw == null && readCoverage_rv == null) return null;


        if (readCoverage_fw != null){
            readList.add(readCoverage_fw.getCoverageBetween(curChromosome.getId(),start,stop));
        }
        if (readCoverage_rv != null){
            readList.add(readCoverage_rv.getCoverageBetween(curChromosome.getId(),start,stop));
        }
        return readList;


	}

	public void looptestWhile() {
		//ArrayList<SamRead> elist = this.readMap.get("BK006935.2");
		//	ArrayList<SamRead> asd = this.readMap.
		//	System.out.println("start sort");
		//	Collections.sort(elist, (a,b) -> a.getStart() < b.getStart() ? -1: a.getStart() == b.getStart() ? 0 : 1);
		//	System.out.println("stop sort");}
		//   Iterator entries = this.readMap.entrySet().iterator();
		//for (this.readMap.Entry<String, ArrayList<SamRead>> item)
		System.out.println("start sort");
		int kdkd = 1;
		for (ArrayList<SamRead> value : this.readMap.values()){
			Collections.sort(value, (a,b) -> a.getStart() < b.getStart() ? -1: a.getStart() == b.getStart() ? 0 : 1);
		//Map.Entry entry = (Map.Entry) entries.next();
		//Integer key = (Integer) entry.getKey();
			 }
		kdkd += 2;
		System.out.println("stop sort");
    }

    public HashMap<String,ArrayList<SamRead>> getReadMap(){
    	return this.readMap;
	}

	public void sortReadmapByStart() {
		for (ArrayList<SamRead> value : this.readMap.values()) {
			Collections.sort(value, (a, b) -> a.getStart() < b.getStart() ? -1 : a.getStart() == b.getStart() ? 0 : 1);
		}
	}

	public void setReadHeightLayers(){
    	System.out.println("start readheight");
		HashMap<String, ArrayList<SamRead>> d = this.getReadMap();

		for (ArrayList<SamRead> samArray : d.values()) {
			int[] heightArray = new int[samArray.size()];
			int previousStartPos = samArray.get(0).getStart();
			int maxheight = 0;
			System.out.println("chrom position" + samArray.size());

			for (int x = 0; x < samArray.size(); x++){
				int currentStartPos = samArray.get(x).getStart();

				if (previousStartPos != currentStartPos){ //TODO als er een nieuwe positie is moet er gekeken worder naar de afstand tussen de vorige en zovaak de aftrek loop uitvoeren
					int xposdiff = currentStartPos - previousStartPos;

					for (int i = 0; i < maxheight + 1; i++){
						heightArray[i] = Math.max(0,heightArray[i]-xposdiff);
					}

					previousStartPos = currentStartPos;

				}
				for (int i = 0; i < heightArray.length; i++){
					if (i > maxheight){
						maxheight = i;
					}
					if (heightArray[i] == 0){
						heightArray[i] += samArray.get(x).getTotalLength();
						samArray.get(x).setHeightLayer(i);

						break;

					}
				}
			}

		}
		System.out.println("stop readheight");
    }

    public void setCurrentSamReads(){
		ArrayList<SamRead> samArray = this.getReadMap().get(this.curChromosome.getId());
		ArrayList<SamRead> samArray2 = new ArrayList<>();
		for (int i = 0; i < samArray.size(); i++){
			SamRead currentSamRead = samArray.get(i);
			if(currentSamRead.getStart() + currentSamRead.getTotalLength() - 1 >= this.start && currentSamRead.getStart() <= this.stop){
				samArray2.add(currentSamRead);
			}
		}
	//	System.out.println(Collections.max(samArray2, Comparator.comparing(c -> c.getHeightLayer())).getHeightLayer() + "size max");
		Collections.sort(samArray2, (a,b) -> a.getHeightLayer() < b.getHeightLayer() ? -1: a.getHeightLayer() == b.getHeightLayer() ? 0 : 1);

		this.currentSamReads = samArray2;
				//currentSamReads
	}

    public ArrayList<SamRead> getCurrentSamReads(){ return this.currentSamReads;}

    public int getCurrentSamReadMaxHeight(){
		if (this.currentSamReads.size() == 0){
			return 0;
		} else {
			System.out.println(this.currentSamReads.get(Math.max(0,this.currentSamReads.size()-1)).getHeightLayer() + "size last index");
			return this.currentSamReads.get(Math.max(0,this.currentSamReads.size()-1)).getHeightLayer();
		}

	}
    /**	try {

		//	ArrayList<SamRead> elist = this.readMap.get("BK006935.2");
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			for (int z = 0; z < elist.size(); z++){
				int y = elist.get(z).getStart();
				String str = elist.get(z).getSam()+","+String.valueOf(z)+","+String.valueOf(y);
				writer.write(str);
				writer.newLine();
			}


	 		writer.close();
		}
	 catch (IOException e) {
		e.printStackTrace();
	}
    }**/

/**
             BufferedReader br = null;
                   try {
                     br = new BufferedReader(new FileReader(path));
                   String line;

                 while ((line = br.readLine()) != null) {
                  int pl = 1;
                      pl += 1;
                     System.out.println();
               }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                    System.out.println("closeattempt1");
                }
                if (br != null) {

                    br.close();
                    System.out.println("closeattempt2");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }**/

	public void resetInsertTooltipsMap(){
		this.insertTooltipsMap = new HashMap<>();
	}

	public void putIntoInsertTooltipsMap(Rectangle rect, String insertSequence){
		this.insertTooltipsMap.put(rect, insertSequence);
	}

	public String getTooltipForPoint(Point point){

		for (Map.Entry<Rectangle, String> entry : this.insertTooltipsMap.entrySet()) {
			Rectangle key = entry.getKey();
			String value = entry.getValue();
			if (key.contains(point)){
				return value;
			}
		}
		return null;
	}
    public void parseSamReads(String path)  {
        BufferedReader br = null;
        this.barCoverageMap = new HashMap<>(organism.getChromosomes().keySet().size());
        this.readMap = new HashMap<>();

        for (String chrID : organism.getChromosomes().keySet()){

            int[][] errey = new int[5][organism.getChromosome(chrID).getSeqTemp().length()];
     //       Arrays.fill(errey,0);

            this.barCoverageMap.put(chrID,errey); //int array ter grote van chromosoom's sequentie.

        }
        try {

            br = new BufferedReader(new FileReader(path));
            String line;
            br.readLine(); // useless header
            System.out.println(Arrays.toString("1=6I87=".split("(?<=\\D)")));



        while ((line = br.readLine()).startsWith("@SQ") == true) {


            System.out.println(line);

        }
        br.readLine(); // input files header
        while ((line = br.readLine()) != null) {
           // System.out.println(line);
            String[] lineArray = line.split("\\s+");
			String samnum = lineArray[0];
            //  System.out.println(lineArray[5].toString());
            String chromString = lineArray[2].toString();
            String cigarString = lineArray[5].toString();
            String sequenceString = lineArray[9].toString();
            int collumIndex = Integer.parseInt(lineArray[3])-1;   //position on the genome substract 1 because the SAM format starts counting at 1 and this program at zero
			int startPosition = collumIndex;
			// System.out.println(Arrays.toString(cigarString.split("(?<=\\D)")));
            //SamRead t = new SamRead(lineArray[9].toString(), cigarString.split("(?<=\\D)"));
            //this.memorySAM.add(t);


            //String  = new String("AAGGTTTTCCCCC");
            //String[] cigarArray = "2=2I4D5=".split("(?<=\\D)");



            String[] cigarArray = cigarString.split("(?<=\\D)");

            if (!cigarString.equals("*")) {
				int[] operrationsarray = new int[cigarArray.length];
				char[] cigarCharArray = new char[cigarArray.length];


                int[][] crommap = this.barCoverageMap.get(chromString);
                // int[][] crommap = new int[5][16];

				int lengthOfSamRead = 0;
                int positionInReadModifier = 0;
                for (int x = 0; x < cigarArray.length; x++) {
                    int operatrions = Integer.parseInt(cigarArray[x].substring(0, cigarArray[x].length() - 1));
					operrationsarray[x] += operatrions;

                    if (cigarArray[x].endsWith("=")) {
						cigarCharArray[x] = '=';

                        for (int z = 0; z < operatrions; z++) {

                            crommap[0][collumIndex] += 1;
                            collumIndex += 1;
                        }
                        positionInReadModifier += operatrions;

                        lengthOfSamRead += operatrions;
                    }

                    if (cigarArray[x].endsWith("D")) {
						cigarCharArray[x] = 'D';
                        for (int z = 0; z < operatrions; z++) {

                            //crommap[0][collumIndex] += 1;
                            collumIndex += 1;
                        }
                        //positionInReadModifier += operatrions;
						lengthOfSamRead += operatrions;
                    }

                    if (cigarArray[x].endsWith("I")) {			// inserts: pieces of sequence that are in the read but not in the reference sequence
						cigarCharArray[x] = 'I';
                        for (int z = 0; z < operatrions; z++) {

                            //crommap[0][collumIndex] += 1;
                            //collumIndex += 1;
							//ACCACCACACCCACACTTTTCACATCTACCTCTACTCTCGCTGTCACTCCTTACCCGGCTTTCTGACCGAAATTAAAAAAAAAAAAATGAAA
                        }
                        positionInReadModifier += operatrions;
                    }

                    if (cigarArray[x].endsWith("X")) {
						cigarCharArray[x] = 'X';
                        for (int z = 0; z < operatrions; z++) {
                            if (sequenceString.charAt(z + positionInReadModifier) == 'A') {
                                crommap[1][collumIndex] += 1;
                                collumIndex += 1;
                            }
                            if (sequenceString.charAt(z + positionInReadModifier) == 'T') {
                                crommap[2][collumIndex] += 1;
                                collumIndex += 1;
                            }
                            if (sequenceString.charAt(z + positionInReadModifier) == 'C') {
                                crommap[3][collumIndex] += 1;
                                collumIndex += 1;
                            }
                            if (sequenceString.charAt(z + positionInReadModifier) == 'G') {
                                crommap[4][collumIndex] += 1;
                                collumIndex += 1;
                            }
                        }
                        positionInReadModifier += operatrions;
						lengthOfSamRead += operatrions;

                    }
                }
                SamRead newRead = new SamRead(sequenceString, startPosition, lengthOfSamRead, samnum, cigarCharArray, operrationsarray);
				if (readMap.containsKey(chromString) == true){
					readMap.get(chromString).add(newRead);
				} else {
					ArrayList<SamRead> list = new ArrayList<>();
					readMap.put(chromString ,list);
					readMap.get(chromString).add(newRead);
				}
            }
        }
  //      System.out.println(Arrays.toString("1=2I4D5=".split("(?<=\\D)")));

  //      br.close();
      //  System.out.println(organism.getChromosomes().keySet().size());
      //  System.out.println(organism.getChromosomes().keySet());
     //   for (int i = 0; i < 5; i++){
       //     line = br.readLine();
         //   System.out.println(line);
        //}
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null){
                    br.close();
                    System.out.println("close");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   // publiv insertTooltips

    public int[][] getCurrentBarCovarage(){
        int[][] crommap = this.barCoverageMap.get(this.curChromosome.getId());
        int[][] currentmap = new int[crommap.length][getLength()];

        //for (int row = this.start; row < this.stop; row++){
        for (int row = 0; row < crommap.length; row++){
            for (int collum = this.start; collum < this.stop; collum++){
                currentmap[row][collum-this.start] = crommap[row][collum];
            }
        }
        return currentmap;
    }

    public int getCoverageReadsPerPixel(){return this.coverageReadsPerPixel;}

    public void setCoverageReadsPerPixel(int newHeightInReadsPerPixel){
    	this.coverageReadsPerPixel = newHeightInReadsPerPixel;
        pcs.firePropertyChange("coverageReadsPerPixel",null,null);
	}
	public void setpixelHeightReads(int newPixelHeightReadsValue){
		this.pixelHeightReads = newPixelHeightReadsValue;
		pcs.firePropertyChange("pixelHeightReads",null,null);
	}

	public int getPixelHeightReads(){
		return this.pixelHeightReads;
	}

	public void SetPixelSpaceBetweenReads(int newPixelSpaceBetweenReadsValue){
		this.pixelSpaceBetweenReads = newPixelSpaceBetweenReadsValue;
		pcs.firePropertyChange("PixelSpaceBetweenReads",null,null);
	}

	public int getPixelSpaceBetweenReads(){
		return this.pixelSpaceBetweenReads;
	}
	//public void xxx(){
	//	pcs.firePropertyChange("area",null,null);
	//	System.out.println("xxx");

	//}

	public void drawBarmap(){
        gui.organism.add(new ReadBarPanel(this));
        gui.organism.revalidate();
        gui.organism.repaint();
    }
	public void drawReadAllignment(){
		ReadAlignmentPanel k = new ReadAlignmentPanel(this);
	//	k.addMouseListener(new MouseInputAdapter() {
	//	});
		JScrollPane scroller = new JScrollPane(k);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		System.out.println(scroller.getVerticalScrollBar().getWidth());
		scroller.setPreferredSize(new Dimension(882,142));
		scroller.setMaximumSize(new Dimension(2000,40));
		scroller.setBorder(null);
		gui.organism.add(scroller);

		gui.organism.revalidate();
		gui.organism.repaint();


	}
}