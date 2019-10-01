package util;

import gherkin.deps.com.google.gson.JsonParser;

public class JsonUtil {

    public static boolean isJsonFormed( String string ) {

        try {

            new JsonParser().parse( string );

            return string.startsWith( "{" ) && string.endsWith( "}" );

        } catch ( Exception e ) {

            return false;

        }

    }

}
