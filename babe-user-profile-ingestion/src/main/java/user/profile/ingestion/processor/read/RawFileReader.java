package user.profile.ingestion.processor.read;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.profile.ingestion.model.EventModel;
import user.profile.ingestion.processor.write.EventWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by riskaamalia on 06/07/17.
 */
@Service("RawFileReader")
public class RawFileReader implements Reader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawFileReader.class);

    @Value("${processor.thread-number}")
    int threadNumber;

    @Value("${rawFile.folder-location}")
    String rawFileLocation;

    long tStart = System.currentTimeMillis();

    @Override
    public void run (final EventWriter writer, String date) {

        LOGGER.info("Start : RawFileReader reader from raw file , total thread : {} ",threadNumber);

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        List<Future> taskList = new ArrayList<Future>();

        List<String> filePathList = new ArrayList<String>();

        File baseFolder = new File(rawFileLocation + "day="+date);
        if(baseFolder.exists()) {
            Collection<File> files = FileUtils.listFiles(baseFolder, new String[] {"log" }, true);
            final Iterator<File> it = files.iterator();
            while(it.hasNext()) {
                File file = it.next();
                filePathList.add(file.getAbsolutePath());
            }
        }

        for(final String path : filePathList) {
            Future future = executorService.submit(new Runnable() {
                public void run() {
                    parseFile(path,writer);
                }
            });

            taskList.add(future);
            LOGGER.info("Path File : File {} is being read",path);
        }

        for(Future future: taskList) {
            try {
                future.get();
                Thread.sleep(2000);

            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Error : RawFileReader , thread/future get error {}",e.getMessage());
            }  //returns null if the task has finished correctly.
        }

        executorService.shutdown();
        LOGGER.info("End : {} process end , Total Time : {} minutes",RawFileReader.class,(System.currentTimeMillis() - tStart) / (1000.0 * 60.0));
    }
    
    public void parseFile(String filePath,EventWriter writer) {

        BufferedReader br = null;
        FileReader fr = null;

        List<EventModel> resultList = new ArrayList<EventModel>();

        Gson gson = new Gson();

        int count = 0;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filePath));
            while ((sCurrentLine = br.readLine()) != null) {
                EventModel eventJson = gson.fromJson(sCurrentLine, EventModel.class);
                resultList.add(eventJson);

                count++;

                //This is process of insert
                if(count % 2000 == 0) {
                    writer.writeEvent(resultList);
                    resultList.clear();
                    Thread.sleep(1000);
                    String fileName = filePath.substring(filePath.lastIndexOf("/"),filePath.length());
                    LOGGER.info("RawFileReader : File Path {} , {} datas inserted by thread {}, total time {} minutes "
                            ,fileName,count,Thread.currentThread().getId(),(System.currentTimeMillis() - tStart) / (1000.0 * 60.0) );
//                    LOGGER.info("RawFileReader : {} row data , thread {}",count,Thread.currentThread().getId());
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error : RawFileReader , FileReader error {}",e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error("Error : RawFileReader , FileReader error {}",e.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                LOGGER.error("Error : RawFileReader , Close File error {}",ex.getMessage());
            }
        }
    }
}
