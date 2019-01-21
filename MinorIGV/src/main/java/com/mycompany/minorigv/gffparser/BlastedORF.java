package com.mycompany.minorigv.gffparser;

import com.mycompany.minorigv.blast.Hit;
import com.mycompany.minorigv.blast.Hsp;
import com.mycompany.minorigv.blast.Iteration;
import com.mycompany.minorigv.sequence.Strand;

public class BlastedORF extends ORF {

    private Iteration iter;

    /**
     * Het opslaan van alle informatie van een ORF in een object.
     *
     * @param start        Start positie van de eerste nucleotide van het startcodon
     * @param stop         Stop positie van de eerste nucleotide van het stopcodon
     * @param readingframe In welk readingframe én strand (-/+) het ORF is gevonden.
     * @param idORF        Het ID dat wordt meegegeven aan het ORF om ORFs te kunnen onderscheiden
     * @param strand       De strand van het gevonden ORF.
     * @param lengthORF    De lengte van het ORF (stop-start)
     * @param chromosomeID Het ID van het chromosoom waarop dit ORF gevonden is.
     * @iter Iteration
     */
    public BlastedORF(int start, int stop, int readingframe, String idORF, Strand strand, int lengthORF, String chromosomeID, Iteration iter) {
        super(start, stop, readingframe, idORF, strand, lengthORF, chromosomeID);
        this.iter = iter;
    }

    public BlastedORF(ORF o, Iteration iter){
        super(o.getStart(),o.getStop(),o.getReadingframe(),o.getIdORF(),o.getStrand(),o.getLengthORF(),o.getChromosomeID());
        this.iter = iter;

    }

    public boolean hasHit(){
        return iter.getIterationHits().getHit().size() > 0;
    }

    public Hit getBestHit(){
        if(hasHit()) {
            return iter.getIterationHits().getHit().get(0);
        }
        return null;
    }

    public Hsp getBestHsp(){
        if (hasHit()){
            return getBestHit().getHitHsps().getHsp().get(0);
        }
        return null;
    }

}