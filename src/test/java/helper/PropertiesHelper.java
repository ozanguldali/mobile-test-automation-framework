package helper;

import org.testng.Assert;
import util.PropertiesUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static util.LoggingUtil.LOGGER;
import static util.HasMapUtil.config;
import static util.PropertiesUtil.*;

public abstract class PropertiesHelper {

    public PropertiesHelper() {

    }

//    private static final String CONFIG_PATH = "src" + SLASH + "test" + SLASH + "resources" + SLASH;
//    private static final String CONFIG_NAME = "feature.properties";

    protected static String getConfigFilePath() {

        final String CONFIG_PATH = "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config" + SLASH;
        final String CONFIG_NAME = "config.properties";

        return PROJECT_DIR + SLASH + CONFIG_PATH + CONFIG_NAME;

    }

    protected static String getFeaturePropertyFilePath() {

        final String FEATURE_PROPERTY_PATH = "src" + SLASH + "test" + SLASH + "resources" + SLASH + "features" + SLASH;

        return PROJECT_DIR + SLASH + FEATURE_PROPERTY_PATH;

    }

    public static Properties readPropertyFile(String file) {

        try {

            InputStream input = new FileInputStream( file );
            Throwable t = null;

            try {

                properties.load( input );

            } catch ( Throwable e ) {

                t = e;
                LOGGER.fatal( "\tThe properties file could NOT loaded [" + file + "]; error: " + e.getMessage() + "\t\n" );

            } finally {

                if ( t != null ) {

                    try {

                        input.close();

                    } catch ( Throwable e ) {

                        t.addSuppressed( e );

                    }

                } else {

                    input.close();

                }


            }

        } catch ( Exception e ) {

            LOGGER.fatal( "\tException on reading Properties File [" + file + "]; error: " + e.getMessage() + "\t\n" );
            Assert.fail( "\tException on reading Properties File [" + file + "]; error: " + e.getMessage() + "\t\n" );

        }

        return properties;

    }

    /*public static Properties old_readPropertyFile(String file) {

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);

            PropertiesUtil.properties.load(fileInputStream);

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

        return PropertiesUtil.properties;

    }*/

/*    public static void setConfigProperty(String config_file) {

        Properties properties = readPropertyFile( config_file );

        try {

            properties.forEach( (k, v) -> {

                config.put( k.toString(), v );

            } );

        } catch ( Exception e ) {

            LOGGER.fatal( "\tThe Config Properties Map Could NOT Set.\t\n" );
            Assert.fail( "\tThe Config Properties Map Could NOT Set.\t\n" );

        }

    }*/

    public static String setProperty(String file, String key) {

        String value = "";

        Properties properties = readPropertyFile( file );

        if ( !properties.containsKey( key ) ) {

            LOGGER.info( String.format( "\tProperties file does NOT contain key, thus key: [%s] -> value: [ %s ]\t\n", key, value ) );

        } else {

            try {

                value = properties.getProperty( key );
                LOGGER.info( String.format( "\tProperties key: [%s] -> value: [ %s ]\t\n", key, value ) );

            } catch ( Exception e ) {

                LOGGER.warn( String.format( "\tKey [%s] could NOT get from Properties file, error: [ %s ]\t\n", key, e.getMessage() ) );
                Assert.fail( String.format( "\tKey [%s] could NOT get from Properties file, error: [ %s ]\t\n", key, e.getMessage() ) );

            }

        }
        return value;

    }

    public static String setDeviceProperty(String fileName, String key) {

        final String CONFIG_NAME = "feature.properties";

        final String file = FEATURE_PROPERTY_FILE + fileName + SLASH + CONFIG_NAME;

        return setProperty( file, key );

    }

}