package com.mycompany.minorigv.gffparser;

public class ORF {
    private int start;
    private int stop;
    private int readingframe;
    private int idORF;
    private int aaStart;
    private int aaStop;
    private String DNA_ORF;

    public ORF(int start, int stop, int readingframe, int idORF, int aaStart, int aaStop, String DNA_ORF) {
        this.start = start;
        this.stop = stop;
        this.readingframe = readingframe;
        this.idORF = idORF;
        this.aaStart = aaStart;
        this.aaStop = aaStop;
        this.DNA_ORF = DNA_ORF;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getReadingframe() {
        return readingframe;
    }

    public void setReadingframe(int readingframe) {
        this.readingframe = readingframe;
    }

    public int getIdORF() {
        return idORF;
    }

    public void setIdORF(int idORF) {
        this.idORF = idORF;
    }

    public int getAaStart() {
        return aaStart;
    }

    public void setAaStart(int aaStart) {
        this.aaStart = aaStart;
    }

    public int getAaStop() {
        return aaStop;
    }

    public void setAaStop(int aaStop) {
        this.aaStop = aaStop;
    }

    public String getDNA_ORF() {
        return DNA_ORF;
    }

    public void setDNA_ORF(String DNA_ORF) {
        this.DNA_ORF = DNA_ORF;
    }
}
