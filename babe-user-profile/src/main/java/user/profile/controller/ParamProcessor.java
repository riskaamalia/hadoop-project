package user.profile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import user.profile.model.EventModel;
import user.profile.processor.PhoenixProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by riskaamalia on 20/07/17.
 */

@RestController
public class ParamProcessor {

    @Value("${phoenix.jdbc}")
    String phoenixJDBCConfig ;

    @Value("${phoenix.table-name}")
    String phoenixTableName ;

    @RequestMapping("/profile")
    public Map<String,List<EventModel>> paramConverter (
            @RequestParam(value="date", required = false) String date,
            @RequestParam(value="dt_start", required = false) String dt_start,
            @RequestParam(value="dt_end", required = false) String dt_end,
            @RequestParam(value="uid", required = false) String uid,
            @RequestParam(value="aid", required = false, defaultValue = "1") Integer aid,
            @RequestParam(value="ver", required = false) String ver,
            @RequestParam(value="did", required = false) String did,
            @RequestParam(value="osv", required = false) String osv,
            @RequestParam(value="icu", required = false) String icu,
            @RequestParam(value="new_user", required = false, defaultValue = "-1") Integer new_user,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="country", required = false) String country,
            @RequestParam(value="city", required = false) String city,
            @RequestParam(value="net", required = false) String net,
            @RequestParam(value="event", required = false) String event,
            @RequestParam(value="id", required = false, defaultValue = "-1") Long id,
            @RequestParam(value="type", required = false) String type,
            @RequestParam(value="category", required = false) String category,
            @RequestParam(value="publisher", required = false) String publisher,
            @RequestParam(value="label", required = false) List<String> label,
            @RequestParam(value="pos", required = false) String pos,
            @RequestParam(value="event_type", required = false) String event_type,
            @RequestParam(value="loc", required = false) String loc,
            @RequestParam(value="value", required = false, defaultValue = "-1") Double value,
            @RequestParam(value="data", required = false) List<String> data,
            @RequestParam(value="entity", required = false) List<String> entity,
            @RequestParam(value="published", required = false) String published,
            @RequestParam(value="pub_start", required = false) String pub_start,
            @RequestParam(value="pub_end", required = false) String pub_end,
            @RequestParam(value="order_by", required = false, defaultValue = "date") String order_by,
            @RequestParam(value="sort", required = false,defaultValue = "desc") String sort,
            @RequestParam(value="limit", required = false, defaultValue = "10") Integer limit


    ) {

        Map<String,Object> params = new HashMap<String, Object>();
        PhoenixProcessor phoenixProcessor = new PhoenixProcessor();

        System.out.println("Config : "+phoenixJDBCConfig);
        System.out.println("Table Name : "+phoenixTableName);

        //set each params to Map
        if (!StringUtils.isEmpty(dt_start)) { params.put("date", date); }
        if (!StringUtils.isEmpty(dt_start)) { params.put("dt_start", dt_start); }
        if (!StringUtils.isEmpty(dt_end)) { params.put("dt_end", dt_end); }
        if (!StringUtils.isEmpty(uid)) { params.put("uid", uid); }
        if (aid.intValue() != -2) { params.put("aid",aid.intValue()); }
        if (!StringUtils.isEmpty(ver)) { params.put("ver", ver); }
        if (!StringUtils.isEmpty(did)) { params.put("did", did); }
        if (!StringUtils.isEmpty(osv)) { params.put("osv", osv); }
        if (!StringUtils.isEmpty(icu)) { params.put("icu", icu); }
        if (new_user.intValue() != -1) { params.put("new",new_user.intValue()); }
        if (!StringUtils.isEmpty(author)) { params.put("author", author); }
        if (!StringUtils.isEmpty(country)) { params.put("country", country); }
        if (!StringUtils.isEmpty(city)) { params.put("city", city); }
        if (!StringUtils.isEmpty(net)) { params.put("net", net); }
        if (!StringUtils.isEmpty(event)) { params.put("event", event); }
        if (id.longValue() != -1) { params.put("id", id.longValue()); }
        if (!StringUtils.isEmpty(type)) { params.put("type", type); }
        if (!StringUtils.isEmpty(category)) { params.put("category", category); }
        if (!StringUtils.isEmpty(publisher)) { params.put("publisher", published); }
        if (!StringUtils.isEmpty(label)) { params.put("label", label); }
        if (!StringUtils.isEmpty(pos)) { params.put("pos", pos); }
        if (!StringUtils.isEmpty(event_type)) { params.put("event_type",event_type); }
        if (!StringUtils.isEmpty(loc)) { params.put("loc",loc); }
        if (value.doubleValue() != -1) { params.put("value",value.doubleValue()); }
        if (!StringUtils.isEmpty(data)) { params.put("data",data); }
        if (!StringUtils.isEmpty(entity)) { params.put("entity",entity); }
        if (!StringUtils.isEmpty(published)) { params.put("published",published); }
        if (!StringUtils.isEmpty(pub_start)) { params.put("pub_start",pub_start); }
        if (!StringUtils.isEmpty(pub_end)) { params.put("pub_end",pub_end); }
        if (!StringUtils.isEmpty(order_by)) { params.put("order_by",order_by); }
        if (!StringUtils.isEmpty(sort)) { params.put("sort",sort); }
        if (limit.intValue() > 0) { params.put("limit",limit.intValue()); }

        Map<String,List<EventModel>> profileResults = phoenixProcessor.getData(params, phoenixJDBCConfig, phoenixTableName);

        return profileResults;
    }

}
