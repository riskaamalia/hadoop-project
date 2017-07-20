package user.profile.ingestion.processor.read;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.profile.ingestion.processor.write.EventWriter;

/**
 * Created by riskaamalia on 06/07/17.
 */

@Service("kafkaReader")
public class KafkaReader implements Reader {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReader.class);

    @Value("${processor.thread-number}")
    int threadNumber;

    long tStart = System.currentTimeMillis();

    @Override
    public void run(final EventWriter writer, String date) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.info("Start : KafkaReader reader , total thread : {} ",threadNumber);

        stopWatch.stop();

        LOGGER.info("End : {} process end , Total Time : {} minutes",RawFileReader.class,stopWatch.getTime()/1000/60);
    }

    public void parseFile(EventWriter writer) {

    }
}
