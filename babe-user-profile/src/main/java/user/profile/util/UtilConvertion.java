package user.profile.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by riskaamalia on 20/07/17.
 */
public class UtilConvertion {

    private static Map<String, ParamTranslator> mappings = new HashMap<String, ParamTranslator>();

    static {
        mappings.put("dt_start",new ParamTranslator("dt_start","LOGTIME"));
        mappings.put("dt_end", new ParamTranslator("dt_end","LOGTIME"));
        mappings.put("uid",  new ParamTranslator("uid","USERID"));
        mappings.put("aid",  new ParamTranslator("aid","APPID"));
        mappings.put("ver",  new ParamTranslator("ver","APPVER"));
        mappings.put("did",  new ParamTranslator("did","DEVMOD"));
        mappings.put("osv",  new ParamTranslator("osv","OSV"));
        mappings.put("icu",  new ParamTranslator("icu","ICU"));
        mappings.put("new",  new ParamTranslator("new","NEWUSR"));
        mappings.put("author",  new ParamTranslator("author","AUTHOR"));
        mappings.put("country",  new ParamTranslator("country","COUNTRY"));
        mappings.put("city",  new ParamTranslator("city","CITY"));
        mappings.put("net",  new ParamTranslator("net","NET"));
        mappings.put("event",  new ParamTranslator("event","EVENTNAME"));
        mappings.put("id",  new ParamTranslator("id","IDITEM" ));
        mappings.put("type",  new ParamTranslator("type","TYPEITEM"));
        mappings.put("category",  new ParamTranslator("category","CATEITEM"));
        mappings.put("publisher",  new ParamTranslator("publisher","PUBITEM"));
        mappings.put("label",  new ParamTranslator("label","LABELITEM"));
        mappings.put("pos",  new ParamTranslator("pos","POSITEM"));
        mappings.put("event_type",  new ParamTranslator("event_type","TYPE"));
        mappings.put("loc",  new ParamTranslator("loc","LOC"));
        mappings.put("value",  new ParamTranslator("value","EVENTVALUE"));
        mappings.put("data",  new ParamTranslator("data","DATA"));
        mappings.put("entity",  new ParamTranslator("entity","ENTITIES"));
        mappings.put("published",  new ParamTranslator("published","PUBDATE"));
        mappings.put("pub_start",  new ParamTranslator("pub_start", "PUBDATE"));
        mappings.put("pub_end",  new ParamTranslator("pub_end","PUBDATE"));
        mappings.put("impr",  new ParamTranslator("impr","IMPRID"));
    }

    public String getPhoenixParam (String uriParam) {

        return mappings.get(uriParam).getParamPhoenixName();
    }


    private static class ParamTranslator {
        private String paramUriName; //param in uri
        private String paramPhoenixName; //param in phoenix

        public ParamTranslator(String paramUriName, String paramPhoenixName) {
            this.paramUriName = paramUriName;
            this.paramPhoenixName = paramPhoenixName;
        }

        public String getParamUriName() {
            return paramUriName;
        }

        public void setParamUriName(String paramUriName) {
            this.paramUriName = paramUriName;
        }

        public String getParamPhoenixName() {
            return paramPhoenixName;
        }

        public void setParamPhoenixName(String paramPhoenixName) {
            this.paramPhoenixName = paramPhoenixName;
        }
    }
}

 /*if (param.getKey().equals("dt_start")) { paramsForPhoenix.put("DT_START", param.getValue().toString()); }
            if (param.getKey().equals("dt_end")) { paramsForPhoenix.put("DT_END", param.getValue().toString()); }
            if (param.getKey().equals("uid")) { paramsForPhoenix.put("USERID", param.getValue().toString()); }
            if (param.getKey().equals("aid")) { paramsForPhoenix.put("APPID",Integer.valueOf( (String) param.getValue() ) ); }
            if (param.getKey().equals("ver")) { paramsForPhoenix.put("APPVER", param.getValue().toString()); }
            if (param.getKey().equals("did")) { paramsForPhoenix.put("DEVMOD", param.getValue().toString()); }
            if (param.getKey().equals("osv")) { paramsForPhoenix.put("OSV", param.getValue().toString()); }
            if (param.getKey().equals("icu")) { paramsForPhoenix.put("ICU", param.getValue().toString()); }
            if (param.getKey().equals("new")) { paramsForPhoenix.put("NEWUSR",Integer.valueOf( (String) param.getValue() ) ); }
            if (param.getKey().equals("author")) { paramsForPhoenix.put("AUTHOR", param.getValue().toString()); }
            if (param.getKey().equals("country")) { paramsForPhoenix.put("COUNTRY", param.getValue().toString()); }
            if (param.getKey().equals("city")) { paramsForPhoenix.put("CITY", param.getValue().toString()); }
            if (param.getKey().equals("net")) { paramsForPhoenix.put("NET", param.getValue().toString()); }
            if (param.getKey().equals("event")) { paramsForPhoenix.put("EVENTNAME", param.getValue().toString()); }
            if (param.getKey().equals("id")) { paramsForPhoenix.put("IDITEM", Long.valueOf( (String) param.getValue() )); }
            if (param.getKey().equals("type")) { paramsForPhoenix.put("TYPEITEM", param.getValue().toString()); }
            if (param.getKey().equals("category")) { paramsForPhoenix.put("CATEITEM", param.getValue().toString()); }
            if (param.getKey().equals("publisher")) { paramsForPhoenix.put("PUBITEM", param.getValue().toString()); }
            if (param.getKey().equals("label")) { paramsForPhoenix.put("LABELITEM", param.getValue().toString()); }
            if (param.getKey().equals("pos")) { paramsForPhoenix.put("POSITEM", param.getValue().toString()); }
            if (param.getKey().equals("event_type")) { paramsForPhoenix.put("TYPE",param.getValue().toString()); }
            if (param.getKey().equals("loc")) { paramsForPhoenix.put("LOC",param.getValue().toString()); }
            if (param.getKey().equals("value")) { paramsForPhoenix.put("EVENTVALUE",Double.valueOf( (String) param.getValue() )); }
            if (param.getKey().equals("data")) { paramsForPhoenix.put("DATA",param.getValue().toString()); }
            if (param.getKey().equals("entity")) { paramsForPhoenix.put("ENTITIES",param.getValue().toString()); }
            if (param.getKey().equals("published")) { paramsForPhoenix.put("PUBDATE",param.getValue().toString()); }
            if (param.getKey().equals("pub_start")) { paramsForPhoenix.put("PUB_START",param.getValue().toString()); }
            if (param.getKey().equals("pub_end")) { paramsForPhoenix.put("PUB_END",param.getValue().toString()); }*/
