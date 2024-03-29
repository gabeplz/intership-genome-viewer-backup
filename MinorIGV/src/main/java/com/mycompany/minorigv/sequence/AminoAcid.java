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

/**
 * @author jrobinso, stan Wehkamp
 * 
 * bevat de constructor voor AminoAcid en een method om een string te vergelijken met de strings van de instance variabelen
 */
public class AminoAcid {

    private String fullName;

    private String abbrevName;

    private char symbol;

    public static AminoAcid NULL_AMINO_ACID = new AminoAcid("", "", ' ');
    /**
     * 
     * @param fullName      string  die staat voor de volledige naam van het aminozuur
     * @param abbrevName    string die staat voor de drieletterige afkorting van het aminozuur
     * @param symbol        char die staat voor de eenletterige afkorting van het aminozuur
     */
    AminoAcid(String fullName, String abbrevName, char symbol) {
        this.fullName = fullName;
        this.abbrevName = abbrevName;
        this.symbol = symbol;
    }
    /**
     * 
     * @return fullName string
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * 
     * @return abbrevName string die staat voor de 3 letterige afkorting van het aminozuur
     */
    public String getShortName() {
        return abbrevName;
    }
    /**
     * 
     * @return symbol char
     */
    public char getSymbol() {
        return symbol;
    }
    /**
     * vergelijkt de meegegeven string met de fullName, abbrevName(de shortname) en symbol (een letterige afkorting voor aminozuur)
     * @param mutAA string
     * @return True or False
     */
    public boolean equalsByName(String mutAA) {
        return fullName.equals(mutAA) || abbrevName.equals(mutAA)
                || String.valueOf(symbol).equals(mutAA);
    }
}
