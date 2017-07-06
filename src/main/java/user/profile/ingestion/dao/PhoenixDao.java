package user.profile.ingestion.dao;

import user.profile.ingestion.model.EventModel;
import user.profile.ingestion.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * Created by riskaamalia on 05/07/17.
 */

public class PhoenixDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoenixDao.class);

    public void InsertToPhoenix (List<EventModel> eventList, String phoenixJDBCConfig, String phoenixTableName) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(phoenixJDBCConfig);
            connection.setAutoCommit(false);

            for (EventModel event : eventList) {
                java.sql.Date logTime = CommonUtil.getDateFromTimeStampObject(event.getLogTime());
                Integer appId = event.getAppId();
                String icu = event.getIcu();
                String eventName = event.getEventName();
                String userId = event.getUserId();
                String appVer = event.getAppVer();
                String devMod = event.getDevMod();
                String osv = event.getOsv();
                Integer newUsr = event.getNewUsr();
                String author = event.getAuthor();
                String country = event.getCountry();
                String city = event.getCity();
                String net = event.getNet();
                Long idItem = event.getIdItem();
                String typeItem = event.getTypeItem();
                String cateItem = event.getCateItem();
                String pubItem = event.getPubItem();
                String[] labelItemIdArray = event.getLabelItem().toArray(new String[0]);
                String posItem = event.getPosItem();
                String type = event.getType();
                String loc = event.getLoc();
                Integer posId = event.getPosId();
                Double eventValue = CommonUtil.getDoubleFromObject(event.getValue());
                String[] dataArray = event.getData().toArray(new String[0]);
                String[] entitiesArray = event.getEntities().toArray(new String[0]);
                java.sql.Date pubDate = CommonUtil.getDateFromTimeStampObject(event.getPubItem());
                Long imprId = event.getImprId();

                Array labelItem = connection.createArrayOf("VARCHAR", labelItemIdArray);
                Array data = connection.createArrayOf("VARCHAR", dataArray);
                Array entities = connection.createArrayOf("VARCHAR", entitiesArray );

                statement = connection.prepareStatement(
                        "UPSERT INTO "+phoenixTableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                statement.setString(1, userId);
                statement.setDate(2, logTime);
                statement.setInt(3, appId);
                statement.setString(4, icu);
                statement.setString(5, eventName);
                statement.setString(6, appVer);
                statement.setString(7, devMod);
                statement.setString(8, osv);
                statement.setInt(9, newUsr);
                statement.setString(10, author);
                statement.setString(11, country);
                statement.setString(12, city);
                statement.setString(13, net);
                statement.setLong(14, idItem);
                statement.setString(15, typeItem);
                statement.setString(16, cateItem);
                statement.setString(17, pubItem);
                statement.setArray(18, labelItem);
                statement.setString(19, posItem);
                statement.setString(20, type);
                statement.setString(21, loc);
                statement.setInt(22, posId);
                statement.setDouble(23, eventValue);
                statement.setArray(24, data);
                statement.setArray(25, entities);
                statement.setDate(26, pubDate);
                statement.setLong(27, imprId);

                statement.executeUpdate();
            }
            connection.commit();
            LOGGER.info("Send : Sending datas to Phoenix , " + eventList.size() + " items");

        } catch (SQLException e) {
            LOGGER.error("Error : PhoenixWriter , SQL error {}",e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
