package com.mycompany.minorigv.gffparser;

/**
 * In de sequentie van een chromosoom/contig wordt er gezocht naar Open Reading Frames (ORFs) en
 * informatie van een ORF en de sequentie zelf worden opgeslagen in een object.
 */
public class ORF extends Elements{
    private String idORF;
    private int aaStart;
    private int aaStop;
    //private String DNA_ORF;

    /**
     *
     * Het opslaan van alle informatie van een ORF in een object.
     *
     * @param start             Start positie van de eerste nucleotide van het startcodon
     * @param stop              Stop positie van de eerste nucleotide van het stopcodon
     * @param readingframe      In welk readingframe én strand (-/+) het ORF is gevonden.
     * @param idORF             Het ID dat wordt meegegeven aan het ORF om ORFs te kunnen onderscheiden
     * @param aaStart           Start positie van het aminozuur
     * @param aaStop            Stop positie van het aminozuur
     * //@param DNA_ORF           De DNA sequentie van het gevonden ORF
     */
    public ORF(int start, int stop, int readingframe, String strand, String idORF, int aaStart, int aaStop) {
        super(start, stop, strand, readingframe);
        this.idORF = idORF;
        this.aaStart = aaStart;
        this.aaStop = aaStop;
        //this.DNA_ORF = DNA_ORF;
    }

//    /**
//     * Het ophalen van de startpositie van de eerste nucleotide van het startcodon
//     *
//     * @return      De start positie van de eerste nucleotide van het startcodon
//     */
//    public int getStart() {
//        return start;
//    }
//
//    /**
//     * Het opslaan van de startpositie van de eerste nucleotide van het startcodon
//     *
//     * @param start De start positie van de eerste nucleotide van het startcodon
//     */
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    /**
//     * Het ophalen van de stop positie van de eerste nucleotide van het stopcodon
//     * @return      De stop positie van de eerste nucleotide van het stopcodon
//     */
//    public int getStop() {
//        return stop;
//    }
//
//    /**
//     * Het opslaan van de stop positie van de eerste nucleotide van het stopcodon
//     * @param stop   De stop positie van de eerste nucleotide van het stopcodon
//     */
//    public void setStop(int stop) {
//        this.stop = stop;
//    }

//    /**
//     * Het ophalen van het reading frame waarin het ORF is gevonden
//     * @return      De reading frame van het ORF met de strand (+1, +2, +3, -1, -2, -3)
//     */
//    public int getReadingframe() {
//        return readingframe;
//    }
//
//    /**
//     * Het opslaan van het reading frame waarin het ORF is gevonden, samen met de strand waarin het ORF zit (+ of -)
//     * @param readingframe      De reading frame waarin het ORF is gevonden met de strand (+1, +2, +3, -1, -2, -3)
//     */
//    public void setReadingframe(int readingframe) {
//        this.readingframe = readingframe;
//    }

    /**
     * Het ophalen van het ID van het gevonden ORF
     * @return      Een ID voor het ORF
     */
    public String getIdORF() {
        return idORF;
    }

    /**
     * Het opslaan van het ID van het gevonden ORF
     * @param idORF     ID van het ORF
     */
    public void setIdORF(String idORF) {
        this.idORF = idORF;
    }

    /**
     * Het ophalen van de positie van het startcodon in de aminozuursequentie
     * @return      De positie van het startcodon in de aminozuursequentie
     */
    public int getAaStart() {
        return aaStart;
    }

    /**
     * Het opslaan van de positie van het startcodon in de aminozuursequentie
     * @param aaStart   De positie van het startcodon in de aminozuursequentie
     */
    public void setAaStart(int aaStart) {
        this.aaStart = aaStart;
    }

    /**
     * Het ophalen van de positie van het stopcodon in de aminozuursequentie
     * @return          De positie van het stopcodon in de aminozuursequentie
     */
    public int getAaStop() {
        return aaStop;
    }

    /**
     * Het opslaan van de positie van het stopcodon in de aminozuursequentie
     * @param aaStop    De positie van het stopcodon in de aminozuursequentie
     */
    public void setAaStop(int aaStop) {
        this.aaStop = aaStop;
    }

//    /**
//     * Het ophalen van de DNA sequentie van het ORF
//     * @return          De DNA sequentie van het ORF
//     */
//    public String getDNA_ORF() {
//        return DNA_ORF;
//    }
//
//    /**
//     * Het opslaan van de DNA sequentie van het ORF
//     * @param DNA_ORF   De DNA sequentie van het ORF
//     */
//    public void setDNA_ORF(String DNA_ORF) {
//        this.DNA_ORF = DNA_ORF;
//    }
}