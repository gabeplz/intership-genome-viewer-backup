package com.mycompany.minorigv.gffparser;



public class Elements {
    private int start;
    private int stop;
    private String strand;



    Elements(int start, int end, String strand) {
        this.start = start;
        this.stop = end;
        this.strand = strand;

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

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

}
