package user.profile.ingestion.processor.write;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.profile.ingestion.dao.PhoenixDao;
import user.profile.ingestion.model.EventModel;

import java.util.List;

/**
 * Created by riskaamalia on 06/07/17.
 */

@Service("PhoenixWriter")
public class PhoenixWriter implements EventWriter {

    @Value("${phoenix.jdbc}")
    String phoenixJDBCConfig;

    @Value("${phoenix.table-name}")
    String phoenixTableName;

    PhoenixDao phoenixDao = new PhoenixDao();

    @Override
    public void writeEvent(List<EventModel> events) {
        phoenixDao.InsertToPhoenix(events,phoenixJDBCConfig,phoenixTableName);
    }
}
