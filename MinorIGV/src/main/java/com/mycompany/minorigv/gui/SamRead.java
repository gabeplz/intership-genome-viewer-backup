package com.mycompany.minorigv.gui;

public class SamRead {
    private String sequence;
    private String samcode;
 //   private String[] gappedSequence;
    private int heightLayer;
    private int start;
    private int totalLength;
   // private String[] insertSequences;
  //  private int[] insertSequencesPos;
    private char[] cigarChars;
    private int[] cigarNumbers;

    public SamRead(String sequence, int start, int totalLenght, String samcode, char[] cigarChars, int[] cigarNumbers){
        this.sequence = sequence;
        //this.gappedSequence = gappedSequence;
        this.start = start;
        this.totalLength = totalLenght;
        this.samcode = samcode;
        this.cigarChars = cigarChars;
        this.cigarNumbers = cigarNumbers;
    }

  /**  public static Comparator<SamRead> startPosComparator = new Comparator<SamRead>() {
        @Override
        public int compare(SamRead s1, SamRead s2) {
            int startPos1 = s1.getStart();
            int startPos2 = s2.getStart();

            //ascending order
            if (startPos1 == startPos2)
                return 0;
            else if (startPos1 > startPos2)
                return 1;
            else
                return -1;
        }

    }; **/


        public void setHeightLayer(int height){
        this.heightLayer = height;
    }
    public String getSequence(){return this.sequence;}
    public int getHeightLayer(){return this.heightLayer;}

    public int getStart(){return this.start;}
    public int getTotalLength(){return this.totalLength;}
    public String getSam(){return this.samcode;}
    public int[] getcigarNumbers(){return this.cigarNumbers;}
    public char[] getcigarChars(){return this.cigarChars; }
    public void addToGapped (String a){

    }

}
