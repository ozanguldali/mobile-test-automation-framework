package util;

import com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import helper.JsonHelper;
import org.junit.Assert;

import java.util.HashMap;

import static util.LoggingUtil.LOGGER;

public class JsonUtil {

    public static boolean isJsonFormed( String string ) {

        try {

            new JsonParser().parse( string );

            return string.startsWith( "{" ) && string.endsWith( "}" );

        } catch ( Exception e ) {

            return false;

        }

    }

    public static void manageJsonElement(HashMap restMap, String restPage, String value) {

        switch ( ( String ) restMap.get( "elementType" ) ) {

            case "jsonArrayElement":

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonArrayElementHelper.removeJsonArrayElement( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), ( Integer ) restMap.get( "index" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonArrayElementHelper.updateJsonArrayElement( (JsonObject) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), ( Integer ) restMap.get( "index" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonArrayElementHelper.setJsonArrayElement( (JsonObject) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), ( Integer ) restMap.get( "index" ), value, restPage );

                break;

            case "jsonArrayString":

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonArrayStringHelper.removeJsonArrayString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonArrayStringHelper.updateJsonArrayString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonArrayStringHelper.setJsonArrayString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );

                break;

            case "jsonArrayObject":

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonArrayObjectHelper.removeJsonArrayObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonArrayObjectHelper.updateJsonArrayObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonArrayObjectHelper.setJsonArrayObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );

                break;

            case "jsonObject":

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonObjectHelper.removeJsonObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonObjectHelper.updateJsonObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonObjectHelper.setJsonObject( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );

                break;

            case "jsonString":

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonStringHelper.removeJsonString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonStringHelper.updateJsonString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonStringHelper.setJsonString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );

                break;

            default:

                if ( restMap.get( "operation" ).equals( "remove" ) )
                    JsonHelper.JsonStringHelper.removeJsonString( ( JsonObject ) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), restPage );
                else if ( restMap.get( "operation" ).equals( "update" ) )
                    JsonHelper.JsonStringHelper.updateJsonString( (JsonObject) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );
                else if ( restMap.get( "operation" ).equals( "add" ) )
                    JsonHelper.JsonArrayStringHelper.setJsonArrayString( (JsonObject) restMap.get( "jsonObject" ), ( String ) restMap.get( "key" ), value, restPage );

                break;

        }

    }

    public static HashMap< String, Object > reachJsonElement(JsonObject jsonObject, String key) {

        HashMap< String, Object > hashMap = new HashMap<>();

        String elementType;

        if ( !key.contains( "." ) ) {

            if ( !( key.contains( "[" ) && key.contains( "]" ) ) ) {

                elementType = JsonHelper.getJsonElementType( jsonObject.get( key ) );

                if ( "jsonArray".equals( elementType ) ) {

                    elementType = "jsonArrayString";

                    hashMap.put( "jsonObject"    , jsonObject );
                    hashMap.put( "key"           , key );
                    hashMap.put( "elementType"   , elementType );

                    return hashMap;

                }

                hashMap.put( "jsonObject", jsonObject );
                hashMap.put( "key", key );
                hashMap.put( "elementType", elementType );

                return hashMap;

            } else {

                String keyArrayElementIndexStr = key
                        .substring(
                                key.indexOf( "[" ) + 1,
                                key.indexOf( "]" )
                        );
                int keyArrayElementIndexInt = Integer.parseInt( keyArrayElementIndexStr );

                key = key.replace( "[", "" )
                        .replace( keyArrayElementIndexStr, "" )
                        .replace( "]", "" );

                hashMap.put( "jsonObject"   , jsonObject );
                hashMap.put( "key"          , key );
                hashMap.put( "elementType"  , "jsonArrayElement" );
                hashMap.put( "index"        , keyArrayElementIndexInt );

                return hashMap;

            }


        } else {

            String[] keyArray   =   key.split( "\\." );

            if ( keyArray.length < 1 ) {

                LOGGER.info( "\tThe key would be added was entered as null\t\n" );
                Assert.fail( "\tThe key would be added was entered as null\t\n" );

            }

            if ( !( keyArray[ 0 ].contains( "[" ) && keyArray[ 0 ].contains( "]" ) ) ) {

                hashMap = reachJsonElement( jsonObject.getAsJsonObject( keyArray[ 0 ] ), key.substring(keyArray[ 0 ].length() + 1 ) );

            } else {

                String keyArrayElementIndexStr = keyArray[ 0 ]
                        .substring(
                                keyArray[ 0 ].indexOf( "[" ) + 1,
                                keyArray[ 0 ].indexOf( "]" )
                        );
                int keyArrayElementIndexInt = Integer.parseInt( keyArrayElementIndexStr );

                hashMap = reachJsonElement(
                        jsonObject.getAsJsonArray( keyArray[ 0 ].replace( "[", "" )
                                .replace( keyArrayElementIndexStr, "" )
                                .replace( "]", "" ) )
                                .get( keyArrayElementIndexInt )
                                .getAsJsonObject(),
                        key.substring( keyArray[ 0 ].length() + 1 )
                );

            }

            return hashMap;

        }

    }

}
