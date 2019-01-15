package com.mycompany.minorigv.motif;

import com.mycompany.minorigv.gui.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PositionScoreMatrix {
    private static float[] a = new float[0];
    private static float[] t;
    private float[] c;
    private float[] g;
    private String n1;
    private String n2;
    private String n3;

    public static ArrayList<HashMap<String, Double>>  buildMatrix(String input){
        input = input.toUpperCase();
        ArrayList<HashMap<String, Double>> newMatrix = new ArrayList<HashMap<String, Double>> ();
        String lines[] = input.split("\\r?\\n");

        Array[][] echtMatrix = new Array[3][lines[0].length()];

        for (int x = 0; x < lines[0].length(); x++){     //lengte van 1 motief
            int aCount = 0;
            int tCount = 0;
            int cCount = 0;
            int gCount = 0;


          for (int y = 0; y < lines.length; y++) {      //het aantal motieven

              //lines[y].substring(0, 1);
              System.out.println(lines[y].substring(x, x+1));
              if (lines[y].substring(x, x+1).equals("A")) {
                  aCount += 1;
              } else if (lines[y].substring(x, x+1).equals("T")) {
                  tCount += 1;
              }else if (lines[y].substring(x, x+1).equals("C")) {
                  cCount += 1;
              }else if (lines[y].substring(x, x+1).equals("G")) {
                  gCount += 1;
              }
          }
          System.out.println(aCount);
          System.out.println(tCount);
          System.out.println(cCount);
          System.out.println(gCount);
          for (int row = 0; row < 3; row++) {
              if (row == 0){
                  echtMatrix[row][x] = aCount;
              }
              echtMatrix[row][x] = ;
              System.out.println(row);
          }



        }

        return newMatrix;
    }


}