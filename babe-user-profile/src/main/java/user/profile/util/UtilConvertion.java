package user.profile.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by riskaamalia on 20/07/17.
 */
public class UtilConvertion {

    private static Map<String, ParamTranslator> mappings = new HashMap<String, ParamTranslator>();

    static {
        mappings.put("date",new ParamTranslator("date","LOGTIME"));
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
        mappings.put("order_by",  new ParamTranslator("order_by","ORDER BY"));
        mappings.put("sort",  new ParamTranslator("sort","SORT"));
        mappings.put("limit",  new ParamTranslator("limit","LIMIT"));
        mappings.put("ses_id",  new ParamTranslator("ses_id","SESID"));
        mappings.put("ses_time",  new ParamTranslator("ses_time","SESBETM"));
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