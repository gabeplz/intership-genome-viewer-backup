package com.mycompany.minorigv;
import java.util.HashMap;

/**
 * Het object CDS maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin CDS in de derde kolom staat wordt een object gemaakt. Class wordt aangeroepen in gffReader.java
 * in methode makeCDS.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class CDS extends Feature{
    private HashMap attributes;

    /**
     * Het opslaan van alle informatie van het CDS (coding sequence) in een object.
     * Constructor
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het CDS aanwezig is.
     * @param start         Start positie van het CDS op het genoom
     * @param end           Stop positie van het CDS op het genoom
     * @param score         De score van de CDS
     * @param strand        Of het CDS aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het CDS voorkomt (0,1,2)
     * @param attributes    HashMap met daarin de informatie over het CDS.
     */
    CDS(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase);
        this.attributes = attributes;
    }

    /**
     * Ophalen van de attributen/informatie van een CDS.
     *
     * @return     HashMap met als key de omschrijving van de informatie (bijv. name) en
     *             als value de specifieke informatie van het CDS (bijv. PAU8).
     */
    public HashMap getAttributes() {
        return attributes;
    }

    /**
     *
     * @param attributes    Het maken van de HashMap met daarin de informatie: als key de omschrijving van de informatie (bijv. name)
     *                      en als value de specifieke informatie van het CDS (bijv. PAU8).
     */
    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }


}
