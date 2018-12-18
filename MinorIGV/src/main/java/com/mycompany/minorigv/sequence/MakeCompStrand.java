/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2007-2015 Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.minorigv.sequence;

public class MakeCompStrand {

   /**
    * creates a reverse complemantary strand van nucleotides. de volgorde van de nucleotide word bepaald door de volgorde
    * waarin ze tegengekomen zouden worden als de leesrichting van het readingframe gevolgd word
    * in: atg aaa ccg -> out:ccg ttt cat
    * @param sequence een string die een nucleotide sequentie voorsteld.
    * @return complement een string die een nucleotide sequentie voorsteld.
    */ 
    public static String getReverseComplement(String sequence) {


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
                case 't':
                    complement[jj] = 'a';
                    break;
                case 'a':
                    complement[jj] = 't';
                    break;
                case 'c':
                    complement[jj] = 'g';
                    break;
                case 'g':
                    complement[jj] = 'c';
                    break;
                default:
                    complement[jj] = c;
            }
        }
        return new String(complement);
    }
}
