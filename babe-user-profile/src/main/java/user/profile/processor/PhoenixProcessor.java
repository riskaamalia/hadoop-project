package user.profile.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<EventModel> getData (Map<String,Object> params, String phoenixJDBCConfig, String phoenixTableName) {

        List<EventModel> datas = new ArrayList<>();
        EventModel data = new EventModel();

        // Create variables
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        //change param to phoenix param
        Map<String,Object> phoenixParam = new HashMap<String,Object> ();
        UtilConvertion utilConvertion = new UtilConvertion();

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
            ps = connection.prepareStatement(constructSQL(phoenixParam,phoenixTableName));
            rs = ps.executeQuery();

            System.out.println("Table Values");

            while(rs.next()) {

                //translate again and convert to datas by column index
//                data.setEventName(rs.getString(utilConvertion.getPhoenixParam("event")));
                data.setUserId(rs.getString(utilConvertion.getPhoenixParam("uid")));
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


                datas.add(data);
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

    private String constructSQL (Map<String,Object> params, String phoenixTableName) {
        //default value
        String sql = "select * from "+phoenixTableName+" where ";
        int loop = 0;
        for (Map.Entry<String, Object> param:params.entrySet()) {

            //concat key first
            sql = sql.concat(param.getKey()+" = ");

            //convert value
            if ( param.getValue() instanceof Integer || param.getValue() instanceof Double || param.getValue() instanceof Long ) {
                sql = sql.concat( param.getValue().toString() );
            } else {
                //handle for logtime and pubdate
                sql = sql.concat( "'"+param.getValue().toString()+"'" );
            }

            //operator AND
            loop++;
            if (loop < params.size()) {
                sql = sql.concat(" and ");
            }
        }

        return sql.concat(" limit 10");
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
