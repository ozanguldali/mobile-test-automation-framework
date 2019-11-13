package util;

import com.google.gson.JsonObject;
import helper.EnvironmentHelper;

import java.util.HashMap;
import java.util.Map;

public final class EnvironmentUtil extends EnvironmentHelper {

    public static final String OS_VALUE = System.getProperty( "os.name" );

    public static final String PROJECT_DIR = System.getProperty( "user.dir" );

    public static final String SLASH = System.getProperty( "file.separator" );

    public static final Map<String, String > ENV_MAP = new HashMap<>( System.getenv() );

    private static final JsonObject environmentObject = ParserUtil.jsonFileParsing( "environment" );

    public static final String APPIUM_HOST = getJsonElement( environmentObject, "appiumHost" ).getAsString();
    public static final String APPIUM_PORT = getJsonElement( environmentObject, "appiumPort" ).getAsString();

    public static final String REST_HOST = getJsonElement( environmentObject, "restHost" ).getAsString();

/*    public static final String SSH_USER = getJsonElement( environmentObject, "sshUser" ).getAsString();
    public static final String SSH_HOST = getJsonElement( environmentObject, "sshHost" ).getAsString();
    public static final String SSH_PORT = getJsonElement( environmentObject, "sshPort" ).getAsString();
    public static final String KEY_FILE = getJsonElement( environmentObject, "keyFile" ).getAsString();

    public static final String DB_USERNAME = getJsonElement( environmentObject, "dbUsername" ).getAsString();
    public static final String DB_PASSWORD = getJsonElement( environmentObject, "dbPassword" ).getAsString();
    public static final String DB_HOST = getJsonElement( environmentObject, "dbHost" ).getAsString();
    public static final String DB_DRIVER_CLASS = getJsonElement( environmentObject, "dbDriverClass" ).getAsString();

    public static final String CACHE_CONTROL = getJsonElement( environmentObject, "Cache-Control" ).getAsString();*/

}
