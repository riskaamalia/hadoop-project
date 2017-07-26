package user.profile.processor;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import user.profile.model.EventModel;
import user.profile.util.UtilConvertion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by riskaamalia on 20/07/17.
 */

public class PhoenixProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoenixProcessor.class);

    UtilConvertion utilConvertion = new UtilConvertion();

    public Map<String,List<EventModel>> getData (Map<String,Object> params, String phoenixJDBCConfig, String phoenixTableName) {

        Map<String,List<EventModel>> datas = new HashMap<>();
        EventModel data = new EventModel();
        String userId = "";

        // Create variables
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        //change param to phoenix param
        Map<String,Object> phoenixParam = new HashMap<String,Object> ();

        for (Map.Entry<String, Object> param:params.entrySet()) {
            String key = param.getKey();
            phoenixParam.put(utilConvertion.getPhoenixParam(key),param.getValue());
        }

        try {
            // Connect to the database
            connection = DriverManager.getConnection(phoenixJDBCConfig);

            // Create a JDBC statement
            statement = connection.createStatement();

            // Query for table
            String sqlQuery = constructSQL(phoenixParam,phoenixTableName) ;
            System.out.println("Query : "+sqlQuery);
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();

            while(rs.next()) {
                //translate again and convert to datas by column index

                userId = rs.getString(utilConvertion.getPhoenixParam("uid"));
                data.setUserId(userId);
                data.setAppId(rs.getInt(utilConvertion.getPhoenixParam("aid")));
                data.setValue(rs.getDouble(utilConvertion.getPhoenixParam("value")));
                data.setTypeItem(rs.getString(utilConvertion.getPhoenixParam("type")));
                data.setPubItem(rs.getString(utilConvertion.getPhoenixParam("publisher")));
                data.setPubDate(rs.getString(utilConvertion.getPhoenixParam("published")));
                data.setPosItem(rs.getString(utilConvertion.getPhoenixParam("pos")));
                data.setPosId(rs.getInt("POSID"));
                data.setOsv(rs.getString(utilConvertion.getPhoenixParam("osv")));
                data.setNewUsr(rs.getInt(utilConvertion.getPhoenixParam("new")));
                data.setAppVer(rs.getString(utilConvertion.getPhoenixParam("ver")));
                data.setAuthor(rs.getString(utilConvertion.getPhoenixParam("author")));
                data.setType(rs.getString(utilConvertion.getPhoenixParam("event_type")));
                data.setEventName(rs.getString(utilConvertion.getPhoenixParam("event")));
                data.setCateItem(rs.getString(utilConvertion.getPhoenixParam("category")));
                data.setCity(rs.getString(utilConvertion.getPhoenixParam("city")));
                data.setCountry(rs.getString(utilConvertion.getPhoenixParam("country")));
                data.setNet(rs.getString(utilConvertion.getPhoenixParam("net")));
                data.setDevMod(rs.getString(utilConvertion.getPhoenixParam("did")));
                data.setLogTime(rs.getString(utilConvertion.getPhoenixParam("dt_start")));
                data.setIdItem(rs.getLong(utilConvertion.getPhoenixParam("id")));
                data.setImprId(rs.getLong("IMPRID"));
                data.setIcu(rs.getString(utilConvertion.getPhoenixParam("icu")));
                data.setLoc(rs.getString(utilConvertion.getPhoenixParam("loc")));
                data.setSesId(rs.getString(utilConvertion.getPhoenixParam("ses_id")));
                data.setSesBeTm(rs.getLong(utilConvertion.getPhoenixParam("ses_time")));


                Array labelArray = rs.getArray(utilConvertion.getPhoenixParam("label"));
                if (labelArray != null) {
                    data.setLabelItem((String[]) labelArray.getArray());
                }

                Array dataArray = rs.getArray(utilConvertion.getPhoenixParam("data"));
                if (dataArray != null) {
                    data.setData((String[]) dataArray.getArray());
                }

                Array entityArray = rs.getArray(utilConvertion.getPhoenixParam("entity"));
                if (entityArray != null) {
                    data.setEntities((String[]) entityArray.getArray());
                }

                //store to a new object
                EventModel copyData = collectData(data);
                System.out.println(copyData.getUserId()+" === "+copyData.getLogTime()+" === "+copyData.getSesId());

                //store to maps per user id
                if (datas.containsKey(userId)) {
                    //if already store , just add new one
                    datas.get(userId).add(copyData);
                } else {
                    List<EventModel> newUserId = new ArrayList<>();
                    newUserId.add(copyData);
                    datas.put(userId,newUserId);
                }

            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(ps != null) {
                try {
                    ps.close();
                }
                catch(Exception e) {}
            }
            if(rs != null) {
                try {
                    rs.close();
                }
                catch(Exception e) {}
            }
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(Exception e) {}
            }
            if(connection != null) {
                try {
                    connection.close();
                }
                catch(Exception e) {}
            }
        }

        return datas;
    }

    private String constructSQL (Map<String,Object> phoenixParams, String phoenixTableName) {
        //default value
        String sql = "select * from "+phoenixTableName+" where ";
        String endSql = "";

        //for order and limit first
        if (phoenixParams.containsKey("ORDER BY")) {
            String orderByValue = phoenixParams.get("ORDER BY").toString();
            endSql = " order by "+utilConvertion.getPhoenixParam(orderByValue);
            phoenixParams.remove("ORDER BY");
        }

        //asc or desc
        if (phoenixParams.containsKey("SORT")) {
            endSql = endSql.concat(" "+phoenixParams.get("SORT").toString());
            phoenixParams.remove("SORT");
        }

        if (phoenixParams.containsKey("LIMIT")) {
            endSql = endSql.concat(" limit "+phoenixParams.get("LIMIT").toString());
            phoenixParams.remove("LIMIT");
        }
            
        int loop = 0;
        for (Map.Entry<String, Object> param:phoenixParams.entrySet()) {

            
            //concat key first
            sql = sql.concat(param.getKey()+" = ");

            if ( param.getValue() instanceof Integer || param.getValue() instanceof Double || param.getValue() instanceof Long ) {
                sql = sql.concat( param.getValue().toString() );
            } else {
                //handle for logtime and pubdate
                sql = sql.concat( "'"+param.getValue().toString()+"'" );
            }

            //operator AND
            loop++;
            if (loop < phoenixParams.size()) {
                sql = sql.concat(" and ");
            }
        }

        return sql.concat(endSql);
    }

    private EventModel collectData ( EventModel data ) {
        EventModel copyData = new EventModel();

        copyData.setUserId(data.getUserId());
        copyData.setAppId(data.getAppId());
        copyData.setValue(data.getValue());
        copyData.setTypeItem(data.getTypeItem());
        copyData.setPubItem(data.getPubItem());
        copyData.setPubDate(data.getPubDate());
        copyData.setPosItem(data.getPosItem());
        copyData.setPosId(data.getPosId());
        copyData.setOsv(data.getOsv());
        copyData.setNewUsr(data.getNewUsr());
        copyData.setAppVer(data.getAppVer());
        copyData.setAuthor(data.getAuthor());
        copyData.setType(data.getType());
        copyData.setEventName(data.getEventName());
        copyData.setCateItem(data.getCateItem());
        copyData.setCity(data.getCity());
        copyData.setCountry(data.getCountry());
        copyData.setNet(data.getNet());
        copyData.setDevMod(data.getDevMod());
        copyData.setLogTime(data.getLogTime());
        copyData.setIdItem(data.getIdItem());
        copyData.setImprId(data.getImprId());
        copyData.setIcu(data.getIcu());
        copyData.setLoc(data.getLoc());
        copyData.setSesId(data.getSesId());
        copyData.setSesBeTm(data.getSesBeTm());

        return copyData;
    }

    /*public static void main (String [] args) {
        PhoenixProcessor phoenixProcessor = new PhoenixProcessor();
        Map<String,Object> params = new HashMap<String,Object> ();

        params.put("time","2017-08-01");
        params.put("id",1);
        params.put("value",0.21);
        long panjang = 1289129122;
        params.put("long",panjang);

        System.out.println(phoenixProcessor.constructSQL(params));
    }*/

}
