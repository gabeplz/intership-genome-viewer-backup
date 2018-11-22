package com.mycompany.minorigv.gffparser;
import java.util.HashMap;
import java.util.Map;

/**
 * Kolom 8 van het ingeladen bestand bevat verdere informatie over het element. Deze informatie wordt in een hashmap gezet
 * en zo opgeslagen in het object. Hierdoor kan deze informatie altijd opgehaald worden. Per regel van het bestand wordt deze
 * class aangeroepen.
 *
 */
public class attributes {
    Map<String, Object> attribute = new HashMap<String, Object>();

    /**
     * De kolom 8 (attributen) wordt gesplit waarna de informatie wordt opgeslagen in een hashmap.
     * Deze hashmap wordt gereturned zodat deze informatie ook opgeslagen kan worden in het object.
     *
     * @param attr     Kolom 8 uit de ingeladen bestanden die informatie van element bevat.
     * @return         Er wordt een hashmap gereturned verdere informatie over het element.
     */
    public HashMap splitAtt(String attr){
        // Alle informatie in kolom 8 is gescheiden doormiddel van ;.
        String[] subAttr = attr.split(";");

        // Er wordt een hashmap gemaakt van de informatie van het element.
        for(String s: subAttr){
            String[] split_s = s.split("=");
            attribute.put(split_s[0], split_s[1]);
        }

        // Als de hashmap een key heeft Dbxref, word er gekeken of er een "," aanwezig is.
        if (attribute.containsKey("Dbxref")){
            Map<String, String> dbxref_ids = new HashMap<String, String>();
            String line = (String) attribute.get("Dbxref");

            // Een "," geef aan dat er meerdere IDs zijn. Er wordt een nieuwe hashmap gemaakt met deze IDs.
            if(line.contains(",")){
                String[] splitIDs = line.split(",");
                for(String ID: splitIDs){
                    String[] ID_kv = ID.split(":");
                    dbxref_ids.put(ID_kv[0], ID_kv[1]);
                }
            }else{
                String[] ID_kv = line.split(":");
                dbxref_ids.put(ID_kv[0], ID_kv[1]);
            }

            attribute.put("Dbxref", dbxref_ids);
        }
        return (HashMap) attribute;
    }
}
