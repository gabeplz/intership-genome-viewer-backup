package com.mycompany.minorigv;
import java.io.*;
public class FastaFileReader {

    public String seq (String fastafile)throws IOException {
        System.out.println("path here"+fastafile);
        BufferedReader br = null;
        FileReader fr = null;
        String sequentie = "";

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(fastafile);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                if (!sCurrentLine.contains(">")){sequentie+=sCurrentLine;}

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }return sequentie;

    }


}