package user.profile.ingestion.processor.read;

import org.springframework.stereotype.Service;
import user.profile.ingestion.processor.write.EventWriter;

/**
 * Created by riskaamalia on 06/07/17.
 */

@Service("kafkaReader")
public class KafkaReader implements Reader {
    @Override
    public void run(EventWriter writer, String date) {

    }
}
