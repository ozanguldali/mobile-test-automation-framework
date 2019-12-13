package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    private static final String CONFIG = "src/test/resources/config";
    private static final String FILE = "config.properties";



    public static Properties readPropertyFile(String file) {

        FileInputStream fileInputStream = null;
        Properties configProps = null;

        try {
            fileInputStream = new FileInputStream(file);
            configProps = new Properties();

            configProps.load(fileInputStream);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fileInputStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return configProps;


    }

    public static String setProperty(String key, String device) {

        String value = "";

        Properties properties = readPropertyFile(CONFIG + FILE);
        try{

            value = properties.getProperty(device + "." + key);

        }catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }


}
