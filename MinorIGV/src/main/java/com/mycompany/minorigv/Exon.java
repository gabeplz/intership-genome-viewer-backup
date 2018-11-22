package com.mycompany.minorigv;
import java.util.HashMap;

/**
 * Het object Exon maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin Exon in de derde kolom staat wordt een object gemaakt. Class wordt aangeroepen in gffReader.java
 * in methode makeExon.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class Exon extends Feature{
    private HashMap attributes;

    /**
     * Het opslaan van alle informatie van het exon in een object.
     * Contstructor.
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het exon aanwezig is.
     * @param start         Start positie van het exon op het genoom
     * @param end           Stop positie van het exon op het genoom
     * @param score         Score van het exon
     * @param strand        Of het exon aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het Exon voorkomt (0,1,2 of ".")
     * @param attributes    HashMap met daarin de informatie over het exon.
     */
    Exon(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase);
        this.attributes = attributes;
    }

    /**
     * Ophalen van de attributen/informatie van een exon.
     *
     * @return     HashMap met als key de omschrijving van de informatie (bijv. name) en
     *             als value de specifieke informatie van het exon (bijv. PAU8).
     */
    public HashMap getAttributes() {
        return attributes;
    }

    /**
     *
     * @param attributes    Het maken van de HashMap met daarin de informatie: als key de omschrijving van de informatie (bijv. name)
     *                      en als value de specifieke informatie van het exon (bijv. PAU8).
     */
    public void setAttributes(HashMap attributes) {

        this.attributes = attributes;
    }


}
