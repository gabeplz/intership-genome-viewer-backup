/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gebruiker
 */
public class CodonTabel {
    private Integer key;
    private String[] names;
    private Set<String> starts;
    private Map<String, String> codonMap;

    public CodonTabel(Integer key, String[] names, Set<String> starts, Map<String, String> codonMap){
        this.key = key;
        this.names = names;
        this.starts = starts;       
        this.codonMap = codonMap;                      
    }


    public static CodonTabel build(Integer key, String[] names, String[] base, String aas, String startString) {

            String base1 = base[0];
            String base2 = base[1];
            String base3 = base[2];
            
         //   checkLengths(base1, base2, base3, aas, startString);

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

            return new CodonTabel(key, names, starts, codonMap);
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

