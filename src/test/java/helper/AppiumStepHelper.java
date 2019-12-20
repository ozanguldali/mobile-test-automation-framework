package helper;

import com.google.gson.JsonObject;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.ParserUtil;

import java.net.URL;
import java.util.*;

import static helper.PropertiesHelper.setDeviceProperty;
import static step.AppiumStepDefinitions.host;
import static step.AppiumStepDefinitions.port;
import static util.HasMapUtil.config;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.NEW_COMMAND_TIMEOUT;

public class AppiumStepHelper {

    private static Map< String, List< String > > capabilityKeyMap = new HashMap<String, List<String>>() { {

        put( "default", Arrays.asList( "automationName", "platformName", "platformVersion", "deviceName", "udid", "screenshotWaitTimeout" ) );

        put( "iOS", Arrays.asList( "xcodeOrgId", "xcodeSigningId" ) );

        put( "Android", Arrays.asList( ) );

    } };

    public static void setPropertiedCapabilities(DesiredCapabilities desiredCapabilities, String osTag, String deviceTag) {

        if ( ! ( osTag.equals( "iOS" ) || osTag.equals( "Android" ) ) ) {

            LOGGER.warn( "\tUnknown Device OS Type: [" + osTag + "]\t\n" );
            Assert.fail( "\tUnknown Device OS Type: [" + osTag + "]\t\n" );

        }

        Map<String, String> dataMap = new HashMap<>();

        capabilityKeyMap.get( "default" ).forEach( k -> dataMap.put( k, setDeviceProperty( deviceTag, k ) ) );

        capabilityKeyMap.get( osTag ).forEach( k -> dataMap.put( k, setDeviceProperty( deviceTag, k ) ) );

        setDesiredCapabilities( desiredCapabilities, dataMap );

    }

    public static void setDesiredCapabilities(DesiredCapabilities desiredCapabilities, Map<String, String> dataMap) {



        setDefaultCapabilities( dataMap, desiredCapabilities, MobileCapabilityType.NO_RESET, true );
        setDefaultCapabilities( dataMap, desiredCapabilities, MobileCapabilityType.FULL_RESET, false );
        setDefaultCapabilities( dataMap, desiredCapabilities, MobileCapabilityType.CLEAR_SYSTEM_FILES, true );
        setDefaultCapabilities( dataMap, desiredCapabilities, MobileCapabilityType.NEW_COMMAND_TIMEOUT, Integer.parseInt( NEW_COMMAND_TIMEOUT ) );
        setDefaultCapabilities( dataMap, desiredCapabilities, MobileCapabilityType.TAKES_SCREENSHOT, true );
        setDefaultCapabilities( dataMap, desiredCapabilities, "isHeadless", true );
        setDefaultCapabilities( dataMap, desiredCapabilities, "adbExecTimeout", 60000 );
        setDefaultCapabilities( dataMap, desiredCapabilities, "WDALOCALPORT", 8200 );

        dataMap.forEach( ( key, value ) -> {

            switch ( key ) {

                case "networkSpeed":

                case "appPackage":

                case "appActivity":

                case "bundleId":

                case MobileCapabilityType.AUTOMATION_NAME:

                case MobileCapabilityType.UDID:

                case MobileCapabilityType.APP:

                case "xcodeOrgId":

                case "xcodeSigningId":

                case MobileCapabilityType.PLATFORM_NAME:

                case MobileCapabilityType.PLATFORM_VERSION:

                case MobileCapabilityType.BROWSER_NAME:

                case MobileCapabilityType.DEVICE_NAME:

                case MobileCapabilityType.NO_RESET:

                case MobileCapabilityType.FULL_RESET:

                case MobileCapabilityType.CLEAR_SYSTEM_FILES:

                case MobileCapabilityType.NEW_COMMAND_TIMEOUT:

                case MobileCapabilityType.TAKES_SCREENSHOT:

                case "screenshotWaitTimeout":

                case "WDALOCALPORT":

                case "isHeadless":

                case "adbExecTimeout":
                    setDesiredCapabilities( desiredCapabilities, key, value );
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

        } );

    }

    private static void setDesiredCapabilities(DesiredCapabilities desiredCapabilities, String key, Object value) {


        desiredCapabilities.setCapability( key, value );
        LOGGER.info( "\tThe capability key: [" + key + "] set as value: [" + value + "].\t\n" );

    }

    private static void setDefaultCapabilities(Map<String, String> dataMap, DesiredCapabilities desiredCapabilities, String defaultKey, Object defaultValue) {

        if ( !dataMap.containsKey( defaultKey ) && desiredCapabilities.asMap().containsKey( defaultKey ) ) {

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
