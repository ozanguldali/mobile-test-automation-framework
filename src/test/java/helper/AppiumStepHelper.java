package helper;

import com.google.gson.JsonObject;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.ParserUtil;

import java.net.URL;
import java.util.Map;

import static step.AppiumStepDefinitions.host;
import static step.AppiumStepDefinitions.port;
import static util.LoggingUtil.LOGGER;

public class AppiumStepHelper {
    public static void setDesiredCapabilities(DesiredCapabilities desiredCapabilities, Map<String, String> dataMap) {

        dataMap.forEach( ( key, value ) -> {

            switch ( key ) {

                case "networkSpeed":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "appPackage":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "appActivity":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "bundleId":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.AUTOMATION_NAME:
                    desiredCapabilities.setCapability( MobileCapabilityType.AUTOMATION_NAME, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.UDID:
                    desiredCapabilities.setCapability( MobileCapabilityType.UDID, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.APP:
                    desiredCapabilities.setCapability( MobileCapabilityType.APP, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "xcodeOrgId":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "xcodeSigningId":
                    desiredCapabilities.setCapability( key, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.PLATFORM_NAME:
                    desiredCapabilities.setCapability( MobileCapabilityType.PLATFORM_NAME, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.PLATFORM_VERSION:
                    desiredCapabilities.setCapability( MobileCapabilityType.PLATFORM_VERSION, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.BROWSER_NAME:
                    desiredCapabilities.setCapability( MobileCapabilityType.BROWSER_NAME, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case MobileCapabilityType.DEVICE_NAME:
                    desiredCapabilities.setCapability( MobileCapabilityType.DEVICE_NAME, value );
                    LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );
                    break;

                case "port":
                    port = value;
                    LOGGER.info( "\tThe port is set as value: [" + value + "].\t\n" );
                    break;

                case "host":
                    host = value;
                    LOGGER.info( "\tThe port is set as value: [" + value + "].\t\n" );
                    break;

                default:
                    LOGGER.info( "\tThe capability key: [" + key + "] is not valid, thus default values will be set.\t\n" );
                    break;

            }

            setDefaultCapabilities(desiredCapabilities, MobileCapabilityType.NO_RESET, true, key, value);
            setDefaultCapabilities(desiredCapabilities, MobileCapabilityType.FULL_RESET, false, key, value);
            setDefaultCapabilities(desiredCapabilities, MobileCapabilityType.CLEAR_SYSTEM_FILES, true, key, value);
            setDefaultCapabilities(desiredCapabilities, MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600, key, value);
            setDefaultCapabilities(desiredCapabilities, "isHeadless", true, key, value);
            setDefaultCapabilities(desiredCapabilities, "adbExecTimeout", 60000, key, value);


/*            if ( key.contains( MobileCapabilityType.NO_RESET ) ) {

                desiredCapabilities.setCapability( key, value );
                LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

            } else {

                desiredCapabilities.setCapability( MobileCapabilityType.NO_RESET, true );
                LOGGER.info( "\tThe capability key: [\"noReset\"] set as default value: [\"false\"].\t\n" );
            }

            if ( key.contains( MobileCapabilityType.FULL_RESET ) ) {

                desiredCapabilities.setCapability( key, value );
                LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

            } else {

                desiredCapabilities.setCapability( MobileCapabilityType.FULL_RESET, false );
                LOGGER.info( "\tThe capability key: [\"noReset\"] set as default value: [\"false\"].\t\n" );
            }

            if ( key.contains( MobileCapabilityType.CLEAR_SYSTEM_FILES ) ) {
                desiredCapabilities.setCapability( key, value );
                LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

            } else {

                desiredCapabilities.setCapability( MobileCapabilityType.CLEAR_SYSTEM_FILES, true );
                LOGGER.info( "\tThe capability key: [\"clearSystemFiles\"] set as default value: [\"true\"].\t\n" );

            }

            if ( key.contains( MobileCapabilityType.NEW_COMMAND_TIMEOUT ) ) {
                desiredCapabilities.setCapability( key, value );
                LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

            } else {

                desiredCapabilities.setCapability( MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600 );
                LOGGER.info( "\tThe capability key: [\"clearSystemFiles\"] set as default value: [\"true\"].\t\n" );

            }*/

        } );

    }

    private static void setDesiredCapabilities(DesiredCapabilities desiredCapabilities, String key, Object value) {

        desiredCapabilities.setCapability( key, value );
        LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

    }

    private static void setDefaultCapabilities(DesiredCapabilities desiredCapabilities, String defaultKey, Object defaultValue, String key, String value) {

        if ( key.contains( defaultKey ) ) {
            desiredCapabilities.setCapability( key, value );
            LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

        } else {

            desiredCapabilities.setCapability( defaultKey, defaultValue );
            LOGGER.info( "\tThe capability key: [" + defaultKey + "] set as value: [" + defaultValue + "].\t\n" );

        }

    }

    public static URL setRemoteAddress(String host, String port) {

        if ( host == null ) {

            LOGGER.error( "\tThe host is null, but it can NOT be null.\t\n" );
            Assert.fail();

        }

        if ( port == null ) {

            LOGGER.error( "\tThe port is null, but it can NOT be null.\t\n" );
            Assert.fail();

        }

        try {

            URL url = new URL( "http://" + host + ":" + port + "/wd/hub" );
            LOGGER.info( "\tThe remote address is set as: [" + url.toString() + "]\t\n" );

            return url;

        } catch (Exception e) {

            LOGGER.error( "\tThe remote address could NOT been transformed to URL\t\n", e );
            Assert.fail();
            return null;

        }

    }

    public static String getPageObjectURL(JsonObject pageObject) {

        String urlString = null;

        try {

            urlString = pageObject.get( "url" ).getAsString();
            LOGGER.info( String.format( "\tURL is get from Page Elements: [%s]\t\n", urlString ) );


        } catch (Exception e) {

            LOGGER.info( String.format( "\tURL could NOT been get from Page Elements, because { error : [%s] }\t\n", e.getMessage() ) );
            Assert.fail( String.format( "\tURL could NOT been get from Page Elements, because { error : [%s] }\t\n", e.getMessage() ) );

        }

        return urlString;

    }

    public static JsonObject getPageElementJsonObject() {

        JsonObject pageElementObject = null;

        try {

            pageElementObject = ParserUtil.jsonFileParsing( "pages" );
            LOGGER.info( "\tPROJ_DIR/src/test/resources/pages.json is parsed\t\n" );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tPROJ_DIR/src/test/resources/pages.json could NOT been parsed, because { error : [%s] }\t\n", e.getMessage() ) );
            Assert.fail( String.format( "\tPROJ_DIR/src/test/resources/pages.json could NOT been parsed, because { error : [%s] }\t\n", e.getMessage() ) );

        }

        return pageElementObject;

    }

    public static JsonObject getPageKeyJsonObject(JsonObject pageObject, JsonObject pageElementObject, String pageKey) {

        try {

            pageObject = pageElementObject.get( pageKey ).getAsJsonObject();
            LOGGER.info( String.format( "\tPage Key Element [%s] is get from pages.json\t\n", pageKey ) );


        } catch (Exception e) {

            LOGGER.info( String.format( "\tPage Key Element [%s] could NOT been get from pages.json because { error : [%s] }\t\n", pageKey, e.getMessage() ) );
            Assert.fail( String.format( "\tPage Key Element [%s] could NOT been get from pages.json because { error : [%s] }\t\n", pageKey, e.getMessage() ) );

        }

        return pageObject;

    }

}
