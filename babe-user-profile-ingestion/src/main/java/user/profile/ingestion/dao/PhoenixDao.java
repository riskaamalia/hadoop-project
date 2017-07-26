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
        LOGGER.info("PhoenixDao : Connect to Phoenix with table name {} ",phoenixTableName );

        try {
            connection = DriverManager.getConnection(phoenixJDBCConfig);
            connection.setAutoCommit(false);

            for (EventModel event : eventList) {
                java.sql.Date logTime = CommonUtil.getDateFromTimeStampObject(event.getLogTime());
                Integer appId = event.getAppId();
                String icu = event.getIcu();
                String eventName = event.getEventName();
                String sesId = event.getSesId();
                Long sesBeTm = event.getSesBeTm();
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
                        "UPSERT INTO "+phoenixTableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                statement.setString(1, userId);
                statement.setDate(2, logTime);
                statement.setInt(3, appId);
                statement.setString(4, icu);
                statement.setString(5, eventName);
                statement.setString(6, sesId);
                statement.setLong(7, sesBeTm);
                statement.setString(8, appVer);
                statement.setString(9, devMod);
                statement.setString(10, osv);
                statement.setInt(11, newUsr);
                statement.setString(12, author);
                statement.setString(13, country);
                statement.setString(14, city);
                statement.setString(15, net);
                statement.setLong(16, idItem);
                statement.setString(17, typeItem);
                statement.setString(18, cateItem);
                statement.setString(19, pubItem);
                statement.setArray(20, labelItem);
                statement.setString(21, posItem);
                statement.setString(22, type);
                statement.setString(23, loc);
                statement.setInt(24, posId);
                statement.setDouble(25, eventValue);
                statement.setArray(26, data);
                statement.setArray(27, entities);
                statement.setDate(28, pubDate);
                statement.setLong(29, imprId);

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
