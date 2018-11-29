package com.mycompany.minorigv.gffparser;
import java.util.HashMap;

/**
 * Het object CDS maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin CDS in de derde kolom staat wordt een object gemaakt.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class CDS extends Feature{

    /**
     * Het opslaan van alle informatie van het CDS (coding sequence) in een object.
     * Constructor
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het CDS aanwezig is.
     * @param start         Start positie van het CDS op het chromosoom/contig
     * @param end           Stop positie van het CDS op het chromosoom/contig
     * @param score         De score van de CDS
     * @param strand        Of het CDS aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het CDS voorkomt (0,1,2)
     * @param attributes    HashMap met daarin de informatie over het CDS.
     */
    CDS(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase, attributes);
    }
}
