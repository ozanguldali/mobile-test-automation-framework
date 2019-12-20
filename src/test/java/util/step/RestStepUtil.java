package util.step;

import com.google.gson.JsonObject;
import helper.step.RestStepHelper;
import io.restassured.path.json.JsonPath;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Assert;

import java.util.Arrays;
import java.util.HashMap;

import static helper.step.RestStepHelper.getResponse;
import static util.LoggingUtil.*;
import static util.PropertiesUtil.CACHE_CONTROL;

public class RestStepUtil {

    public static HashMap< String, Object > postTypeRequest(String restPage, String host, JsonObject pageObject) {

        MediaType mediaType = null;

        try {
            mediaType =   MediaType.parse( pageObject.get( "headers" ).getAsJsonObject().get( "Content-Type" ).getAsString() );
        } catch (NullPointerException npe) {

            LOGGER.warn( "\tMediaType could NOT be found\t\n" );
            Assert.fail( "\tMediaType could NOT be found\t\n" );

        }

        RequestBody requestBody     =   null;
        String stringBody           =   "";

        switch ( String.valueOf( mediaType ).toLowerCase().replace( " ", "" ).replace( ";charset=utf-8", "" ).trim() ) {

            case "application/x-www-form-urlencoded":
                int keySetSize = 0;

                try {

                    keySetSize = pageObject.get( "jsonBody" ).getAsJsonObject().keySet().size();

                } catch (Exception e) {

                    LOGGER.warn( "\tBody has NO element to send in request\t\n" );
                    Assert.fail( "\tBody has NO element to send in request\t\n" );

                }

                for ( int i = 0; i < keySetSize; i++ ) {

                    try {

                        if ( i != 0 )
                            stringBody = stringBody + "&";

                        String formKey = pageObject.get( "jsonBody" ).getAsJsonObject().keySet().iterator().next();
                        String formValue = pageObject.get( "jsonBody" ).getAsJsonObject().get( formKey ).getAsString();

                        stringBody = stringBody + formKey + "=" + formValue;
                        requestBody = RequestBody.create(mediaType, stringBody);

                        pageObject.get( "jsonBody" ).getAsJsonObject().remove( formKey );

                    } catch (NullPointerException e){

                        LOGGER.fatal( "\t" + e.getMessage() + "\t\n" );
                        Assert.fail( "\t" + e.getMessage() + "\t\n" );

                    }

                }

                break;

            case "application/json":

                try {

                    stringBody = String.valueOf( pageObject.get( "jsonBody" ) );
                    requestBody = RequestBody.create( mediaType, stringBody );

                } catch (NullPointerException e){

                    LOGGER.fatal( "\t" + e.getMessage() + "\t\n" );
                    Assert.fail( "\t" + e.getMessage() + "\t\n" );

                }

                break;

            case "application/jose":

                try {

                    stringBody = String.valueOf( pageObject.get( "jsonBody" ).getAsJsonObject().get( "rawText" ).getAsString() );
                    requestBody = RequestBody.create( mediaType, stringBody );

                } catch (Exception e){

                    LOGGER.warn( "\t" + Arrays.toString( e.getStackTrace() ) + "\t\n" );
                    Assert.fail( "\t" + Arrays.toString( e.getStackTrace() ) + "\t\n" );

                }

                break;

            default:

                LOGGER.warn( "\tMediaType could NOT be recognized\t\n" );
                Assert.fail( "\tMediaType could NOT be recognized\t\n" );

        }

        if ( requestBody == null ) {

            LOGGER.warn( "\tBody has NO element to send in request\t\n" );
            Assert.fail( "\tBody has NO element to send in request\t\n" );

        }

        Request.Builder requestBuilder = new Request.Builder()
                .post( requestBody )
                .addHeader( "Cache-Control", CACHE_CONTROL );

        RestStepHelper.setUrl( restPage, host, pageObject, requestBuilder );

        Request request = RestStepHelper.setRestServiceHeader( requestBuilder, pageObject ).build();

        postRequestLogger( restPage, stringBody, request );

        return getResponse( restPage, request );

    }

    public static HashMap< String, Object > getTypeRequest(String restPage, String host, JsonObject pageObject) {

        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .addHeader( "Cache-Control", CACHE_CONTROL );

        RestStepHelper.setUrl( restPage, host, pageObject, requestBuilder );

        Request request = RestStepHelper.setRestServiceHeader( requestBuilder, pageObject ).build();

        getRequestLogger( restPage, request );

        return getResponse( restPage, request );

    }

    public static HashMap< String, Object > optionsTypeRequest(String restPage, String host, JsonObject pageObject) {

        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .addHeader( "Cache-Control", CACHE_CONTROL );

        RestStepHelper.setUrl( restPage, host, pageObject, requestBuilder );

        Request request = RestStepHelper.setRestServiceHeader( requestBuilder, pageObject ).build();

        optionsRequestLogger( restPage, request );

        return getResponse( restPage, request );

    }

    public static HashMap< String, Object > putTypeRequest(String restPage, String host, JsonObject pageObject) {

        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .addHeader( "Cache-Control", CACHE_CONTROL );

        RestStepHelper.setUrl( restPage, host, pageObject, requestBuilder );

        Request request = RestStepHelper.setRestServiceHeader( requestBuilder, pageObject ).build();

        putRequestLogger( restPage, request );

        return getResponse( restPage, request );

    }

    public static HashMap< HashMap< String, Object >, JsonPath> restResponseHandler(String restPage, String restHost, JsonObject restObject) {

        HashMap< HashMap< String, Object >, JsonPath> responseValue = new HashMap<>();

        HashMap< String, Object > responseMap = RestStepHelper.getMethodType( restPage, restHost, restObject );

        JsonPath responsePath = RestStepHelper.setResponsePath( responseMap );

        if ( responsePath == null ) {

            LOGGER.warn( "\tThe response is null\t\n" );
            Assert.fail( "\tThe response is null\t\n" );

        }

//        responseValue.clear();
        responseValue.put( responseMap, responsePath );

        responseLogger(restPage, responseMap, responsePath);

        return responseValue;

    }

    public static void checkStatusCode(int actualStatusCode, int expectedStatusCode) {

        if ( actualStatusCode == expectedStatusCode )
            LOGGER.trace( String.format( "\tThe status code is expected: [%d] == actual: [%d]\t\n", expectedStatusCode, actualStatusCode ) );

        else {

            LOGGER.warn( String.format( "\tThe status code was expected: [%d] BUT actual: [%d]\t\n", expectedStatusCode, actualStatusCode ) );
            Assert.fail( String.format( "\tThe status code was expected: [%d] BUT actual: [%d]\t\n", expectedStatusCode, actualStatusCode ) );

        }

    }

}
