package com.mycompany.minorigv.gffparser;

import javax.swing.*;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Motif {
    int start;
    int stop;
    String sPattern;
    String motif;

    private static TreeMap<Integer, Motif> motifMapForward = new TreeMap<>();
    private static TreeMap<Integer, Motif> motifMapReverse = new TreeMap<>();


    public Motif(int start, int stop, String sPattern, String motif){
        this.start = start;
        this.stop = stop;
        this.sPattern = sPattern;
        this.motif = motif;
    }

    public static boolean checkCompile (String sPattern) {
        try {
            Pattern r = Pattern.compile(sPattern);
            return true;
        } catch (PatternSyntaxException e) {    // case of illegal pattern
            JOptionPane.showMessageDialog(null, "fout patroon");
            return false;
        }

    }

    public static TreeMap<Integer, Motif> buildMotifMapForward(String sPattern, String sequence) {

        System.out.println(sequence);
        System.out.println(sPattern);

            Pattern r = Pattern.compile(sPattern);
            Matcher m = r.matcher(sequence);

            while (m.find()) {

                Motif loopMotif = new Motif(m.start(), m.end(), sPattern, m.group());
                System.out.println(loopMotif.getStart() + "get start");
                System.out.println(loopMotif.getStop() + "get stop");
                motifMapForward.put(loopMotif.start, loopMotif);
                System.out.println(motifMapForward.get(0).getStart() + motifMapForward.get(0).motif);
            }
            return motifMapForward;
    }

    public static TreeMap<Integer, Motif>  buildMotifMapReverse(String sPattern, String sequence) {

        System.out.println(sequence);
        System.out.println(sPattern);

        Pattern r = Pattern.compile(sPattern);
        Matcher m = r.matcher(sequence);

        while (m.find()) {

            Motif loopMotif = new Motif(m.start(), m.end(), sPattern, m.group());
            System.out.println(loopMotif.getStart() + "get start");
            System.out.println(loopMotif.getStop() + "get stop");
            motifMapReverse.put(loopMotif.start, loopMotif);
            System.out.println(motifMapReverse.get(0).getStart() + motifMapReverse.get(0).motif);
        }
        return motifMapReverse;
    }


         //   System.out.print("Start index: " + m.start());
         //   System.out.print(" End index: " + m.end());
         //   System.out.println(" Found: " + m.group());




    public int getStart() {
        return start;
    }



    public int getStop() {
        return stop;
    }


    public String getSPattern() {
        return sPattern;
    }

    public String getMotif(){
        return motif;
    }

}
    /**
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Motif motif = (Motif) o;

        if (start != motif.start) return false;
        if (stop != motif.stop) return false;
        return pattern != null ? pattern.equals(motif.pattern) : motif.pattern == null;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + stop;
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        return result;
    } **/

