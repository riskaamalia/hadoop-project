package user.profile.ingestion.processor.read;

import user.profile.ingestion.processor.write.EventWriter;

/**
 * Created by riskaamalia on 06/07/17.
 */
public interface Reader {

    public void run(EventWriter writer, String date);
}
