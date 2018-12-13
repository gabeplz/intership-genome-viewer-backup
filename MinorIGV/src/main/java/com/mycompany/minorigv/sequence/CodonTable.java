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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jrobinso, Stan Wehkamp
 * bevat constructor for codontable
 * bevat method "build" om arguments te genereren voor de constuctor
 */
public class CodonTable {
	private Integer key;
	private String[] names;
	private Set<String> starts;
	private Map<String, String> codonMap;

        /**
         * constructor voor CodonTable
         * @param key key functions as id for the table and as key for the hasmap that will contaion codonTable objects
         * @param names array voor de namen van de codontabel
         * @param starts set voor de alternative start aminozuren
         * @param codonMap Map met een 3 letterige codons als key en een een letterige afkorting voor een aminozuur als value
         */
	public CodonTable(Integer key, String[] names, Set<String> starts, Map<String, String> codonMap){
		this.key = key;                 
		this.names = names;              
		this.starts = starts;            
		this.codonMap = codonMap;        
	}
	
        /**
	 * genereerd de argumenten codonMap en Starts voor de CodonTable constructor en returnt een new CodonTable
	 * @param key functioneerd als id voor the table and als key voor de hasmap die de CodonTable objecten gaat bevatten 
	 * @param names array voor de namen van de codontabel
	 * @param base arrays met nucleotides om elke codon combinatie te maken
	 * @param aas aminozuren
	 * @param startString alternative start aminozuren
	 * @return call naar codontable contstructor (new CodonTable). als het object gemaakt word returnd het de nieuwe CodonTable
	 **/
	public static CodonTable build(Integer key, String[] names, String[] base, String aas, String startString) {

		String base1 = base[0];
		String base2 = base[1];
		String base3 = base[2];

		

		Map<String, String> codonMap = new HashMap<String, String>(aas.length());
		Set<String> starts = new HashSet<String>(aas.length());

		for (int cc = 0; cc < aas.length(); cc++) {
			String codon = base1.substring(cc, cc + 1) + base2.substring(cc, cc + 1) + base3.substring(cc, cc + 1);
			String aa = aas.substring(cc, cc +1);
			//                AminoAcid aa = AANameMap.get(aas.substring(cc, cc + 1));

			codonMap.put(codon, aa);

			if (startString.charAt(cc) == 'M') {
				starts.add(aa);
			}
		}

		return new CodonTable(key, names, starts, codonMap);
	}

	/**
	 * @return the key
	 */
	public Integer getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Integer key) {
		this.key = key;
	}

	/**
	 * @return the names
	 */
	public String[] getNames() {
		return names;
	}

	/**
	 * @param names the names to set
	 */
	public void setNames(String[] names) {
		this.names = names;
	}

	/**
	 * @return the starts
	 */
	public Set<String> getStarts() {
		return starts;
	}

	/**
	 * @param starts the starts to set
	 */
	public void setStarts(Set<String> starts) {
		this.starts = starts;
	}

	/**
	 * @return the codonMap
	 */
	public Map<String, String> getCodonMap() {
		return codonMap;
	}

	/**
	 * @param codonMap the codonMap to set
	 */
	public void setCodonMap(Map<String, String> codonMap) {
		this.codonMap = codonMap;
	}
}
