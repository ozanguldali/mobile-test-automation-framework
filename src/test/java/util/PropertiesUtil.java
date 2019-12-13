package util;

import helper.PropertiesHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PropertiesUtil extends PropertiesHelper {

    public static final String OS_VALUE = System.getProperty( "os.name" );
    public static final String PROJECT_DIR = System.getProperty( "user.dir" );
    public static final String SLASH = System.getProperty( "file.separator" );

    public static final Map<String, String > ENV_MAP = new HashMap<>( System.getenv() );

    public static final Properties properties = new Properties();

    public static final String CONFIG_FILE = getConfigFilePath();

    public static final String APPIUM_HOST = setProperty( "appiumHost" );
    public static final String APPIUM_PORT = setProperty( "appiumPort" );
    public static final String NEW_COMMAND_TIMEOUT = setProperty( "appiumNewCommandTimeout" );
    public static final String TRY = setProperty( "try" );

    public PropertiesUtil() {

//        setConfigProperty( CONFIG_FILE );

    }

/*    public static void setPropDesiredCapabilities(DesiredCapabilities desiredCapabilities ,String key , String device) {

        String subKey = key.substring(key.indexOf('.'));
        String value = setProperty(key, device);

        desiredCapabilities.setCapability( subKey , value );

    }*/

}
