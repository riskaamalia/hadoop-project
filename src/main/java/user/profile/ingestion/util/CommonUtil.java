package user.profile.ingestion.util;

/**
 * Created by riskaamalia on 05/07/17.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class CommonUtil {
    public static String getJsonContent(String fileName) {
        String result = "";
        ClassLoader classLoader = CommonUtil.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Integer getIntFromObject(Object object) {
        return Integer.valueOf(String.valueOf(object));
    }

    public static Double getDoubleFromObject(Object object) {
        return Double.valueOf(String.valueOf(object));
    }

    public static String getStringFromObject(Object object) {
        return String.valueOf(object);
    }

    public static java.sql.Date getDateFromTimeStampObject(Object object) {
        Long miliseconds = Long.valueOf(String.valueOf(object));
        return new java.sql.Date(miliseconds);
    }

    public static String[] getLisStringFromListObject(List<Object> objectList) {
        String[] returnList = new String[0];
        if(objectList != null && !objectList.isEmpty()) {
            List<String> list = new ArrayList<String>();
            for(Object object : objectList) {
                String v = String.valueOf(object);
                list.add(v);
            }

            returnList = list.toArray(returnList);
        }
        return returnList;
    }
}

