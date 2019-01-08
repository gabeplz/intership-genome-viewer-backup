package com.mycompany.minorigv.gffparser;

import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.gui.Context;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class OrganismFiles {

    public String fnaFile, gffFile, gzFNA, gzGFF, pathNewFNA, pathNewGFF;
    FastaFileReader fr;

    public void getFiles(String pathNAS, String nameOrg) throws Exception {
        String pathDirectory = pathNAS + nameOrg + File.separator;
        File f = new File(pathDirectory);
        String[] directories = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(!name.contains("from") && !name.contains(".txt")){
                    if(name.contains("fna")){
                        fnaFile = name;
                    }else if(name.contains("gff")){
                        gffFile = name;
                    }
                }
                return new File(dir, name).isDirectory();
            }
        });

        gzFNA = pathDirectory + fnaFile;
        pathNewFNA = pathDirectory + fnaFile.replace(".gz", "");
        gzGFF = pathDirectory + gffFile;
        pathNewGFF = pathDirectory + gffFile.replace(".gz", "");

        if(gzFNA.contains(".gz")){
            unzip(gzFNA, pathNewFNA);
        }if(gzGFF.contains(".gz")){
            unzip(gzGFF, pathNewGFF);
        }

        fr.getSequences(pathNewFNA);

        //        BufferedReader reader = new BufferedReader(new FileReader(pathNewGFF));
//        System.out.println(reader.readLine());
    }


    private void unzip(String pathMap, String pathNewFile){
        try {
            //
            // Create a file input stream to read the source file.
            //
            FileInputStream fis = new FileInputStream(pathMap);

            //
            // Create a gzip input stream to decompress the source
            // file defined by the file input stream.
            //
            GZIPInputStream gzis = new GZIPInputStream(fis);

            //
            // Create a buffer and temporary variable used during the
            // file decompress process.
            //
            byte[] buffer = new byte[1024];
            int length;

            //
            // Create file output stream where the decompress result
            // will be stored.
            //
            FileOutputStream fos = new FileOutputStream(pathNewFile);

            //
            // Read from the compressed source file and write the
            // decompress file.
            //
            while ((length = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            //
            // Close the resources.
            //
            fos.close();
            gzis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File deleteFile = new File(pathMap);
        deleteFile.delete();

    }


    public String getFNAPath(){
        return pathNewFNA;
    }

    public String getGFFPath(){
        return pathNewGFF;
    }

}
