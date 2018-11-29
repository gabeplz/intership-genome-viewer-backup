package com.mycompany.minorigv.gffparser;
import java.util.HashMap;

/**
 * Het object Exon maken waarbij de superclass Feature wordt overgeërfd. Van elke regel in het bestand
 * waarin Exon in de derde kolom staat wordt een object gemaakt.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class Exon extends Feature{
    /**
     * Het opslaan van alle informatie van het exon in een object.
     * Contstructor.
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het exon aanwezig is.
     * @param start         Start positie van het exon op het chromosoom/contig
     * @param end           Stop positie van het exon op het chromosoom/contig
     * @param score         Score van het exon
     * @param strand        Of het exon aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het Exon voorkomt (0,1,2 of ".")
     * @param attributes    HashMap met daarin de informatie over het exon.
     */
    Exon(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        super(seqid, start, end, score, strand, phase, attributes);
    }
}
