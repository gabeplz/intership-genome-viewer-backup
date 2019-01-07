package com.mycompany.minorigv.gffparser;

import com.mycompany.minorigv.gui.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotifFinder {
    static Context cont;

    public static void gogo(String sPattern, String sequence) {

     //   String seq = cont.getSubSequentie();
        System.out.println(sequence);
        System.out.println(sPattern);
        Pattern r = Pattern.compile(sPattern);

        Matcher m = r.matcher(sequence);
        System.out.println(m.groupCount());
        while (m.find()) {
            System.out.print("Start index: " + m.start());
            System.out.print(" End index: " + m.end());
            System.out.println(" Found: " + m.group());
        }
       /**
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            //System.out.println("Found value: " + m.group(1));
            //System.out.println("Found value: " + m.group(2));
            //System.out.println("Found value: " + m.group(3));
        } else {
            System.out.println("NO MATCH");
        } **/

    }


}

