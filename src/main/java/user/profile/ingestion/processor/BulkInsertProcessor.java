package user.profile.ingestion.processor;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import user.profile.ingestion.processor.read.Reader;
import user.profile.ingestion.processor.write.EventWriter;

import java.security.InvalidParameterException;

/**
 * Created by riskaamalia on 05/07/17.
 */

@Controller
public class BulkInsertProcessor {

    @Autowired
    @Qualifier("RawFileReader")
    private Reader readerFromRawFile;

    @Autowired
    @Qualifier("PhoenixWriter")
    private EventWriter writerToPhoenix;

    public void run (String readerSource, String writerSource, String date) {

        if (readerSource.equals("rawfile") && writerSource.equals("phoenix")) {
            if ( date.matches("\\d{4}-\\d{2}-\\d{2}") ) {
                readerFromRawFile.run(writerToPhoenix, date);
            } else {
                throw new InvalidParameterException("Date Format should : YYYY-MM-DD");
            }
        } else {
            throw new InvalidParameterException("Reader or Writer source does not exist");
        }
    }
}
