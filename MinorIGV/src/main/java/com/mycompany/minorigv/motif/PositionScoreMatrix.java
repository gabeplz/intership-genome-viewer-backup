package com.mycompany.minorigv.motif;

import com.mycompany.minorigv.gui.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PositionScoreMatrix {
    private String name;
    private double sumScore;
    private double[][] scoreMatrix;
    private String userInputSequences;

    public PositionScoreMatrix(String name, double sumScore, double[][] scoreMatrix, String userInputSequences){
        this.name = name;
        this.sumScore = sumScore;
        this.scoreMatrix = scoreMatrix;
        this.userInputSequences = userInputSequences;
    }

    /**
     * gebruik dit om door de matrix per rij te loopen
     * for (int x = 0; x < frequentieMatrix.length; x++){
     * for (int q = 0; q < frequentieMatrix[0].length; q++){
     * System.out.print(frequentieMatrix[x][q]);
     * }
     * }
     * // (frequentieMatrix.length) = 4 = de 4 rijen voor ATCG
     * //(frequentieMatrix[0]) = freqentie A voor elke positie in de motieven
     * //(frequentieMatrix[x].lenght) = lengte van een motief. de motieven moeten even lang zijn
     **/
    public static PositionScoreMatrix buildMatrix(String inputSequences, String name) {
        ArrayList<HashMap<String, Double>> rrrrMatrix = new ArrayList<HashMap<String, Double>>();

        String[] lines = splitInput(inputSequences);
        System.out.println(lines.length + "liness");
        double[][] matrix = buildFrequentieMatrix(lines);


        for (int x = 0; x < matrix.length; x++) {
            for (int q = 0; q < matrix[0].length; q++) {
                System.out.print(matrix[x][q] + " ");
            }
            System.out.println("row");
        }

        for (int x = 0; x < matrix[0].length; x++) {
            for (int q = 0; q < matrix.length; q++) {
                System.out.print(matrix[q][x] + " ");
            }
            System.out.println("collum");
        }

        matrix = normalizeByNumberOfLines(matrix, lines.length);

        for (int x = 0; x < matrix.length; x++) {
            for (int q = 0; q < matrix[0].length; q++) {
                System.out.print(matrix[x][q] + " ");
            }
            System.out.println("rown");
        }

        for (int x = 0; x < matrix[0].length; x++) {
            for (int q = 0; q < matrix.length; q++) {
                System.out.print(matrix[q][x] + " ");
            }
            System.out.println("collumn");
        }

        double sumScore = calculateSumScore(matrix);

        matrix = normalizeBySumScore(matrix, sumScore);

        for (int x = 0; x < matrix.length; x++) {
            for (int q = 0; q < matrix[0].length; q++) {
                System.out.print(matrix[x][q] + " ");
            }
            System.out.println("rown");
        }

        for (int x = 0; x < matrix[0].length; x++) {
            for (int q = 0; q < matrix.length; q++) {
                System.out.print(matrix[q][x] + " ");
            }
            System.out.println("collumn");
        }

        PositionScoreMatrix newMatrix = new PositionScoreMatrix(name, sumScore, matrix, inputSequences);
        System.out.println(newMatrix.name);
        System.out.println(newMatrix.getSumScore());
        System.out.println(newMatrix.getScoreMatrix());
        System.out.println(newMatrix.getUserInputSequences());

        return newMatrix;
    }

    private static String[] splitInput(String input) {
        input = input.toUpperCase();
        return input.split("\\r?\\n");
    }

    private static double[][] buildFrequentieMatrix(String[] lines) {
        double[][] frequentieMatrix = new double[4][lines[0].length()];
        System.out.println(frequentieMatrix.length + "lengt funcite van frequentie matrix");
        for (int column = 0; column < lines[0].length(); column++) {     //lengte van het motief = het aantal kollomen in de matrix
            System.out.println("loopnr" + column);
            int aCount = 0;
            int tCount = 0;
            int cCount = 0;
            int gCount = 0;


            for (int y = 0; y < lines.length; y++) {      //het aantal motieven
                System.out.println("motief" + y);
                if (lines[y].substring(column, column + 1).equals("A")) {
                    aCount += 1;
                } else if (lines[y].substring(column, column + 1).equals("T")) {
                    tCount += 1;
                } else if (lines[y].substring(column, column + 1).equals("C")) {
                    cCount += 1;
                } else if (lines[y].substring(column, column + 1).equals("G")) {
                    gCount += 1;
                } else {
                    System.out.println("exception hier" + lines[y].substring(column, column + 1) + "exception hier end");
                }
            }


            for (int row = 0; row < 4; row++) {
                System.out.println("row " + row);
                if (row == 0) {
                    frequentieMatrix[row][column] = aCount;
                } else if (row == 1) {
                    frequentieMatrix[row][column] = tCount;
                } else if (row == 2) {
                    frequentieMatrix[row][column] = cCount;
                } else if (row == 3) {
                    frequentieMatrix[row][column] = gCount;
                } else {
                    System.out.println("exception hier" + row + "rownummer");
                }


            }
            //       System.out.println(frequentieMatrix[0][x] + " pos"+x);


        }
        return frequentieMatrix;
    }

    private static double[][] normalizeByNumberOfLines(double[][] matrix, int nrLines) {
        for (int column = 0; column < matrix[0].length; column++) {
            for (int row = 0; row < matrix.length; row++) {
                matrix[row][column] = matrix[row][column] / nrLines;
            }

        }
        return matrix;
    }

    private static double calculateSumScore(double[][] frequentieMatrix){
        double[] maxScorePerPosition = new double[frequentieMatrix[0].length];
        double sumScore;

        for (int column = 0; column < frequentieMatrix[0].length; column++) {    // voor elke kollom
            double maxPositionScore = 0;

            for (int row = 0; row < frequentieMatrix.length; row++) {   // voor elke rij
                if (frequentieMatrix[row][column] > maxPositionScore){
                    maxPositionScore = frequentieMatrix[row][column];
                }
            }
            maxScorePerPosition[column] = maxPositionScore;
        }

        sumScore = 0;
        for (int i = 0; i < maxScorePerPosition.length; i++){
            sumScore += maxScorePerPosition[i];
        }

        return sumScore;
}
    private static double[][] normalizeBySumScore(double[][] matrix, double sumScore) {
        int maxScore = matrix[0].length;           //4
        double normalizer = sumScore / maxScore;
        for (int column = 0; column < matrix[0].length; column++) {
            for (int row = 0; row < matrix.length; row++) {
                matrix[row][column] = matrix[row][column] * normalizer;
            }
        }
    return matrix;
    }

    public String getName() {
        return name;
    }

    public double getSumScore() {
        return sumScore;
    }

    public double[][] getScoreMatrix() {
        return scoreMatrix;
    }

    public String getUserInputSequences() {
        return userInputSequences;
    }
}