package helper;

import com.google.gson.*;
import org.junit.Assert;

import java.util.HashMap;

import static util.CommonStepUtil.replaceSavedElement;
import static util.LoggingUtil.*;

public class JsonHelper {

    public static String getJsonElementType(JsonElement jsonElement) {

        String type = null;

        try {

            if ( jsonElement.isJsonPrimitive() ) {

                if ( jsonElement.getAsString().equals( "true")
                        || jsonElement.getAsString().equals( "false" ) )
                    type = "boolean";

                else
                    type = "jsonString";

            } else {

                try {

                    if ( jsonElement.isJsonObject() )
                        type = "jsonObject";

                    else if ( jsonElement.isJsonArray() )
                        type = "jsonArray";

                    else if ( jsonElement.isJsonNull() )
                        type = "null";

                } catch (NullPointerException e) {

                    LOGGER.fatal( "\tJson Element Type could NOT be Recognized !!!\t\n" );
                    Assert.fail( "\tJson Element Type could NOT be Recognized !!!\t\n" );

                }

            }

        } catch (NullPointerException e) {

            LOGGER.warn( "\tJson Element Type could NOT be Recognized !!!\t\n" );
            Assert.fail( "\tJson Element Type could NOT be Recognized !!!\t\n" );

        }

        return type;

    }

    public static JsonObject setSavedElements(JsonObject restObject, HashMap dataMap) {

        return new JsonParser().parse( replaceSavedElement( String.valueOf( restObject ), dataMap ) ).getAsJsonObject();

    }

    public static class JsonArrayStringHelper {

        public static void removeJsonArrayString(JsonObject jsonObject, String key, String restPage) {

            try {
                jsonObject.remove(key);
                jsonElementRemoveLogger(restPage, key);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

        public static void setJsonArrayString(JsonObject jsonObject, String key, String value, String restPage) {

            jsonObject.add( key, new Gson().fromJson(value, JsonElement.class) );
            jsonElementSetLogger(restPage, key, value);
        }

        public static void updateJsonArrayString(JsonObject jsonObject, String key, String value, String restPage) {

            try {
                jsonObject.add( key, new Gson().fromJson(value, JsonElement.class) );
                jsonElementUpdateLogger(restPage, key, value);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

    }

    public static class JsonArrayObjectHelper {

        public static void removeJsonArrayObject(JsonObject jsonObject, String key, String restPage) {

            try {
                jsonObject.remove(key);
                jsonElementRemoveLogger(restPage, key);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

        public static void setJsonArrayObject(JsonObject jsonObject, String key, String value, String restPage) {

            jsonObject.add( key, new Gson().fromJson(value, JsonElement.class) );
            jsonElementSetLogger(restPage, key, value);

        }

        public static void updateJsonArrayObject(JsonObject jsonObject, String key, String value, String restPage) {

            try {
                jsonObject.add( key, new Gson().fromJson(value, JsonElement.class) );
                jsonElementUpdateLogger(restPage, key, value);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }
    }

    public static class JsonArrayElementHelper {

        public static void removeJsonArrayElement(JsonObject jsonObject, String key, Integer index, String restPage) {

            try {
                jsonObject.getAsJsonArray( key ).remove( index );
                jsonElementRemoveLogger(restPage, key);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

        public static void setJsonArrayElement(JsonObject jsonObject, String key, Integer index, String value, String restPage) {

            jsonObject.getAsJsonArray( key ).set( index, new Gson().fromJson(value, JsonElement.class) );
            jsonElementSetLogger(restPage, key + "[" + index + "]", value);

        }

        public static void updateJsonArrayElement(JsonObject jsonObject, String key, Integer index, String value, String restPage) {

            try {
                jsonObject.getAsJsonArray( key ).set( index, new Gson().fromJson(value, JsonElement.class) );
                jsonElementUpdateLogger(restPage, key + "[" + index + "]", value);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }
    }

    public static class JsonObjectHelper {

        public static void removeJsonObject(JsonObject jsonObject, String key, String restPage) {

            try {
                jsonObject.remove(key);
                jsonElementRemoveLogger(restPage, key);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

        public static void setJsonObject(JsonObject jsonObject, String key, String value, String restPage) {

            jsonObject.add( key, new Gson().fromJson( value, JsonElement.class ) );
            jsonElementSetLogger(restPage, key, value);

        }

        public static void updateJsonObject(JsonObject jsonObject, String key, String value, String restPage) {

            try {
                jsonObject.add(key, new Gson().fromJson(value, JsonElement.class));
                jsonElementUpdateLogger(restPage, key, value);
            } catch ( Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }
    }

    public static class JsonStringHelper {

        public static void removeJsonString(JsonObject jsonObject, String key, String restPage) {

            try {
                jsonObject.remove(key);
                jsonElementRemoveLogger(restPage, key);
            } catch (Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

        public static void setJsonString(JsonObject jsonObject, String key, String value, String restPage) {

            jsonObject.add( key, new Gson().fromJson( value, JsonElement.class ) );
            jsonElementSetLogger(restPage, key, value);

        }

        public static void updateJsonString(JsonObject jsonObject, String key, String value, String restPage) {

            try {
                jsonObject.add(key, new Gson().fromJson(value, JsonElement.class));
                jsonElementUpdateLogger(restPage, key, value);
            } catch ( Exception e ) {
                jsonElementManageExceptionLogger(restPage, key);
            }

        }

    }

    public static void setJsonElement(JsonObject jsonObject, String key) {

        String[] keyArray = key.split( "\\." );

        if ( keyArray.length < 1 ) {

            LOGGER.warn( "\tThe key would be added was entered as null\t\n" );
            Assert.fail( "\tThe key would be added was entered as null\t\n" );

        }

        for ( String keyElement : keyArray ) {

            if ( !( keyElement.contains( "[" ) && keyElement.contains( "]" ) ) ) {

                if ( !jsonObject.has( keyElement ) )
                    jsonObject.add( keyElement, null );
                else
                    jsonObject = jsonObject.getAsJsonObject( keyElement );

            } else {

                String keyArrayElementIndexStr = keyElement
                        .substring(
                                keyElement.indexOf( "[" ) + 1,
                                keyElement.indexOf( "]" )
                        );

                int keyArrayElementIndexInt = Integer.parseInt( keyArrayElementIndexStr );

                keyElement = keyElement.replace("[", "")
                        .replace( keyArrayElementIndexStr, "" )
                        .replace("]", "");

                if ( keyArrayElementIndexInt >= jsonObject.getAsJsonArray( keyElement ).size() )
                    jsonObject.getAsJsonArray( keyElement ).add( new Gson().fromJson( "", JsonArray.class ) );

                else {

                    if ( jsonObject.getAsJsonArray( keyElement ).get( keyArrayElementIndexInt ).isJsonNull() )
                        jsonObject.add( keyElement, new Gson().fromJson( "", JsonArray.class ) );

                    else
                        jsonObject.getAsJsonArray( keyElement ).get( keyArrayElementIndexInt ).getAsJsonObject();

                }

            }

        }

    }

}
