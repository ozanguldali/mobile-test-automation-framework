package util;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import gherkin.formatter.model.DataTableRow;
import helper.JsonHelper;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import step.RestStepDefinitions;

import java.util.HashMap;

import static util.LoggingUtil.LOGGER;

public class RestUtil {

    public static void manageRestFile(String restPage, DataTable dataTable, JsonObject restObject) {
        JsonObject headerObject =   restObject.getAsJsonObject( "headers" );

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            JsonObject bodyObject   =   restObject.getAsJsonObject( "jsonBody" );

            String key      =   row.getCells().get(0);
            String value    =   row.getCells().get(1);

            HashMap<String, Object> restMap; //= new HashMap<>();
            //restMap.clear();

            if ( key.equals( "toBase64URL" ) ) {

                JsonObject encodedObject = new JsonObject();

                try {

                    encodedObject.addProperty( value, EncryptionUtil.toBase64URL( bodyObject ) );
                    LOGGER.trace( String.format( "\tThe body of rest page [%s] is encoded as base64url: [%s]\t\n", restPage, encodedObject.get( value ).getAsString() ) );

                } catch (Exception e) {

                    LOGGER.warn( "\tThe body of rest page [%s] could NOT be encoded as base64url\t\n" );
                    Assert.fail( "\tThe body of rest page [%s] could NOT be encoded as base64url\t\n" );

                }

                restObject.remove( "jsonBody" );

                restObject.add( "jsonBody", encodedObject );

            }
/*            else if ( key.contains( "toJWE" ) ) {

                JsonObject encodedObject = new JsonObject();

                try {

                    encodedObject.addProperty( value, EncryptionUtil.toJWE( bodyObject, restPage ) );
                    LOGGER.trace( String.format( "\tThe body of rest page [%s] is encoded as JWE: [%s]\t\n", restPage, encodedObject.get( value ).getAsString() ) );

                } catch (Exception e) {

                    LOGGER.warn( String.format( "\tThe body of rest page [%s] could NOT be encoded as JWE\t\n", restPage ) );
                    Assert.fail( String.format( "\tThe body of rest page [%s] could NOT be encoded as JWE\t\n", restPage ) );

                }

                restObject.remove( "jsonBody" );

                restObject.add( "jsonBody", encodedObject );

            } */
            else if ( key.contains( "header_" ) ) {

                try {

                    headerObject.addProperty( key.replace( "header_", "" ), value );
                    LOGGER.trace( String.format( "\tRequest sending [%s] with replacing header [%s] : [%s]\t\n", restPage, key, value ) );

                } catch (Exception e) {

                    LOGGER.warn( String.format( "\tRest page [%s] does not contain header [%s]\t\n", restPage, key.replace( "header_", "" ) ) );
                    Assert.fail( String.format( "\tRest page [%s] does not contain header [%s]\t\n", restPage, key.replace( "header_", "" ) ) );

                }

            } else if ( key.contains( "query_" ) ) {

                try {

                    headerObject.addProperty( key.replace( "query_", "" ), value );
                    LOGGER.trace( String.format( "\tRequest sending [%s] with replacing query [%s] : [%s]\t\n", restPage, key, value ) );

                } catch (Exception e) {

                    LOGGER.warn( String.format( "\tRest page [%s] does not contain query [%s]\t\n", restPage, key.replace( "query_", "" ) ) );
                    Assert.fail( String.format( "\tRest page [%s] does not contain query [%s]\t\n", restPage, key.replace( "query_", "" ) ) );

                }

            } else {

                 if ( key.contains( "add_" ) ) {

                    JsonHelper.setJsonElement( bodyObject, key.replace( "add_", "" ) );

                    restMap = JsonUtil.reachJsonElement( bodyObject, key.replace( "add_", "" ) );

                    restMap.put( "operation", "add" );

                } else {


                    restMap = JsonUtil.reachJsonElement( bodyObject, key );

                    if ( value.equals( "remove" ) )
                        restMap.put( "operation", "remove" );

                    else
                        restMap.put( "operation", "update" );

                }

                 JsonUtil.manageJsonElement( restMap, restPage, value );
                 RestStepDefinitions.requestPath = JsonPath.from( String.valueOf( restObject ) );

            }

        }

    }

}
