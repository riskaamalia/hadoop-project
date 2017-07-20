package user.profile.ingestion.processor.read;

import com.google.gson.Gson;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.profile.ingestion.model.EventModel;
import user.profile.ingestion.processor.write.EventWriter;

import java.io.*;
import java.util.*;

/**
 * Created by riskaamalia on 17/07/17.
 */

@Service("FileFromServerReader")
public class FileFromServerReader implements Reader {

    private static Logger LOGGER = LoggerFactory.getLogger(FileFromServerReader.class);

    @Value("${processor.thread-number}")
    int threadNumber;

    @Value("${rawFileSftp.folder-location}")
    String directoryPath;

    @Value("${rawfileSftp.username}")
    String userName;

    @Value("${rawfileSftp.host}")
    String host;

    @Value("${rawfileSftp.port}")
    int port;

    @Value("${rawfileSftp.password}")
    String password;

    long tStart = System.currentTimeMillis();

    @Override
    public void run(EventWriter writer, String date) {
        directoryPath = directoryPath+date+"/";
        JSch jsch = new JSch();
        Session session = null;

        Gson gson = new Gson();
        List<EventModel> resultList = new ArrayList<EventModel>();
        int count = 0;

        InputStream inputStreamReader = null;

        try
        {
            session = jsch.getSession(userName, host,port);
            session.setPassword(password);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            System.out.println("Is connected to IP:"+channel.isConnected());

            Vector ls=sftpChannel.ls(directoryPath+"hour=00/");
            for(int i=0;i<ls.size();i++){


                String fileDesc = ls.get(i).toString();
                if (fileDesc.contains("raw-log")) {
                    String fileName = fileDesc.substring(fileDesc.lastIndexOf(" ")).trim();
                    sftpChannel.cd(directoryPath+"hour=00/");
                    inputStreamReader = sftpChannel.get(fileName);
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStreamReader));

                    String getLine = "";
                    while ((getLine = br.readLine()) != null) {
                        EventModel eventJson = gson.fromJson(getLine, EventModel.class);
                        resultList.add(eventJson);

                        count++;

                        //This is process of insert
                        if(count % 2000 == 0) {
                            writer.writeEvent(resultList);
                            resultList.clear();
                            Thread.sleep(1000);
                            LOGGER.info("FileFromServerReader : File {} , {} datas inserted by thread {}, total time {} minutes "
                                    ,fileName,count,Thread.currentThread().getId(),(System.currentTimeMillis() - tStart) / (1000.0 * 60.0) );
                        }
                    }
                }
            }

            sftpChannel.exit();
            session.disconnect();

            LOGGER.info("End : {} process end , Total Time : {} minutes",FileFromServerReader.class,(System.currentTimeMillis() - tStart) / (1000.0 * 60.0));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

