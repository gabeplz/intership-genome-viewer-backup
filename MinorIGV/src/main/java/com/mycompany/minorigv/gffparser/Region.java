package com.mycompany.minorigv.gffparser;
import java.util.HashMap;

/**
 * Het object Region maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin Region in de derde kolom staat wordt een object gemaakt.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class Region extends Feature{

    /**
     * Het opslaan van alle informatie van de regio in een object. De regio bevat informatie over het
     * chromosoom zelf.
     * Constructor
     *
     * @param seqid         Het ID van het contig/chromosoom.
     * @param start         Start positie van het contig/chromosoom
     * @param end           Stop positie van het contig/chromosoom
     * @param score         Score van het Region
     * @param strand        Of het contig/chromosoom aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin de Region voorkomt (0,1,2 of ".")
     * @param attributes    HashMap met daarin de informatie over een Region.
     */
    Region(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase, attributes);
    }
}
