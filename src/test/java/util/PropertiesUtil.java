package util;

import helper.PropertiesHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PropertiesUtil extends PropertiesHelper {

    public static final String OS_VALUE = System.getProperty( "os.name" );
    public static final String PROJECT_DIR = System.getProperty( "user.dir" );
    public static final String SLASH = System.getProperty( "file.separator" );

    public static final String CACHE_CONTROL = "false";

    public static final Map<String, String > ENV_MAP = new HashMap<>( System.getenv() );

    public static final Properties properties = new Properties();

    public static final String CONFIG_FILE = getConfigFilePath();
    public static final String FEATURE_PROPERTY_FILE = getFeaturePropertyFilePath();

    public static final String APPIUM_HOST = setProperty( CONFIG_FILE, "appiumHost" );
    public static final String APPIUM_PORT = setProperty( CONFIG_FILE, "appiumPort" );
    public static final String NEW_COMMAND_TIMEOUT = setProperty( CONFIG_FILE, "appiumNewCommandTimeout" );

    public static final String REST_HOST = PropertiesHelper.setProperty( CONFIG_FILE, "restHost_env" ).isEmpty() ?
            null :
            PropertiesHelper.setProperty( CONFIG_FILE, "restHost_" + PropertiesHelper.setProperty( CONFIG_FILE, "restHost_env" ) );

    public static final String PAGE_HOST = PropertiesHelper.setProperty( CONFIG_FILE, "pageHost_env" ).isEmpty() ?
            null :
            PropertiesHelper.setProperty( CONFIG_FILE, "pageHost_" + PropertiesHelper.setProperty( CONFIG_FILE, "pageHost_env" ) );

    public PropertiesUtil() {

//        setConfigProperty( CONFIG_FILE );

    }

/*    public static void setPropDesiredCapabilities(DesiredCapabilities desiredCapabilities ,String key , String device) {

        String subKey = key.substring(key.indexOf('.'));
        String value = setProperty(key, device);

        desiredCapabilities.setCapability( subKey , value );

    }*/

}
