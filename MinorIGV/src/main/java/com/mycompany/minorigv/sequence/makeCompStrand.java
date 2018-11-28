package com.mycompany.minorigv.sequence;

public class makeCompStrand {

   /**
    * creates a reverse complemantary strand van nucleotides. de volgorde van de nucleotide word bepaald door de volgorde
    * waarin ze tegengekomen zouden worden als de leesrichting van het readingframe gevolgd word
    * in: atg aaa ccg -> out:ccg ttt cat
    * @param sequence
    * @return 
    */ 
    public String getReverseComplement(String sequence) {
        char[] complement = new char[sequence.length()];
        int jj = complement.length;
        for (int ii = 0; ii < sequence.length(); ii++) {
            char c = sequence.charAt(ii);
            jj--;
            switch (c) {
                case 'T':
                    complement[jj] = 'A';
                    break;
                case 'A':
                    complement[jj] = 'T';
                    break;
                case 'C':
                    complement[jj] = 'G';
                    break;
                case 'G':
                    complement[jj] = 'C';
                    break;
                default:
                    complement[jj] = c;
            }
        }
        return new String(complement);
    }
}
