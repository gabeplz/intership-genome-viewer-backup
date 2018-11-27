package com.mycompany.minorigv.gffparser;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Het object Gene maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin Gene in de derde kolom staat wordt een object gemaakt.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
class Gene extends Feature{

private HashMap attributes;

    /**
     * Het opslaan van alle informatie van het gen in een object.
     * Contstructor.
     *
     * @param seqid       Het ID van het contig/chromosoom waarin het gen aanwezig is.
     * @param start       Start positie van het gen op het genoom
     * @param end         Stop positie van het gen op het genoom
     * @param score       Score van het gen
     * @param strand      Of het gen aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase       Het geeft het reading frame aan waarin het gen voorkomt (0,1,2 of ".")
     * @param attributes  HashMap met daarin de informatie over het gen.
     */
    Gene(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase, attributes);
        this.attributes = attributes;
    }

    /**
     * Ophalen van de attributen/informatie van een gen.
     *
     * @return     HashMap met als key de omschrijving van de informatie (bijv. name) en
     *             als value de specifieke informatie van het gen (bijv. PAU8).
     */
    public HashMap getAttributes() {
        return attributes;
    }

    /**
     * @param attributes    Het maken van de HashMap met daarin de informatie: als key de omschrijving van de informatie (bijv. name)
     *                      en als value de specifieke informatie van het gen (bijv. PAU8).
     */
    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

}