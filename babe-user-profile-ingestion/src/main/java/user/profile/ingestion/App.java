package user.profile.ingestion;

import user.profile.ingestion.processor.BulkInsertProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.security.InvalidParameterException;

/**
 * Created by riskaamalia on 05/07/17.
 */
@SpringBootApplication
public class App {
    public static void main (String [] args) {

        ApplicationContext context = SpringApplication.run(App.class, args);
        /*args format ex : file phoenix 2017-07-05 , means data read from file and write to phoenix*/
        if(args.length < 1) {
            throw new InvalidParameterException("App needs arg for date name folder, reader source DB and writer source DB , ex : rawfile phoenix 2017-07-05");
        }

        String readerSource = args[0];
        String writerSource = args[1];
        String date = args[2];

        if( !date.isEmpty() && !readerSource.isEmpty() && !writerSource.isEmpty() ) {
            BulkInsertProcessor service = context.getBean(BulkInsertProcessor.class);

            if (service != null) {
                service.run(readerSource, writerSource, date);
            }
        } else {
            throw new InvalidParameterException("App needs arg for date name folder, reader source DB and writer source DB , ex : rawfile phoenix 2017-07-05");
        }

    }
}
