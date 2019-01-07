package com.mycompany.minorigv.gffparser;

import java.io.File;
import java.io.FilenameFilter;

public class OrganismFiles {


    public void getFiles(String pathNAS, String nameOrg){
        String pathDirectory = pathNAS + nameOrg + File.separator;
        File f = new File(pathDirectory);
        String[] directories = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                System.out.println(name);
                return new File(dir, name).isDirectory();
            }
        });


    }




}
