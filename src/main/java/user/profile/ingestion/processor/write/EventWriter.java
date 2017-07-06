package user.profile.ingestion.processor.write;

import user.profile.ingestion.model.EventModel;

import java.util.List;

/**
 * Created by riskaamalia on 06/07/17.
 */
public interface EventWriter {

    public void writeEvent(List<EventModel> events);
}
