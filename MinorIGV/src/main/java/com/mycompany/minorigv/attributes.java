package com.mycompany.minorigv;
import java.util.HashMap;
import java.util.Map;

public class attributes {
    Map<String, Object> attribute = new HashMap<String, Object>();

    public HashMap splitAtt(String attr){


        String[] subAttr = attr.split(";");
        for(String s: subAttr){
            String[] split_s = s.split("=");
            attribute.put(split_s[0], split_s[1]);
        }

        for (String name: attribute.keySet()){

            String key =name.toString();
            String value = attribute.get(name).toString();
        }


        if (attribute.containsKey("Dbxref")){
            Map<String, String> dbxref_ids = new HashMap<String, String>();
            String line = (String) attribute.get("Dbxref");

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
