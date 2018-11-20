import java.util.HashMap;
import java.util.Map;

public class attributes {
    Map<String, String> attribute = new HashMap<String, String>();

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

        return (HashMap) attribute;
    }
}
