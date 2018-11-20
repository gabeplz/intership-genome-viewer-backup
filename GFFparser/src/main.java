import java.io.*;

/**
 *
 * @author amber/anne
 */
public class main {
    public static String path = "D:\\0000 HAN\\00 Minor\\Project\\voorbeeld gff.gff";


    public static void main(String[] args) throws FileNotFoundException, IOException{
        gffReader lees = new gffReader();
        lees.readData(path);
    }
}