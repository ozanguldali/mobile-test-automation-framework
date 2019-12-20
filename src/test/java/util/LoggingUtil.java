package util;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import gherkin.deps.com.google.gson.JsonParser;
import io.restassured.path.json.JsonPath;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class LoggingUtil {

    public static final Logger LOGGER = Logger.getLogger( LoggingUtil.class );

    public static final Gson beautifier = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void postRequestLogger(String restPage, String jsonBody, Request request) {

        String prettyRequestUrl         =   beautifier.toJson( request.url().url() );
        String prettyRequestMethod      =   beautifier.toJson( request.method() );
        String prettyRequestHeaders     =   beautifier.toJson( request.headers() );
        String prettyRequestBody;

        if ( JsonUtil.isJsonFormed( jsonBody ) )
            prettyRequestBody   =   beautifier.toJson( new JsonParser().parse( jsonBody ) );
        else
            prettyRequestBody   =   jsonBody;

        LOGGER.trace( String.format( "\tThe request to [ %s ] is:\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|" +
                "\n" +
                "Url:\t" + prettyRequestUrl +
                "\n" +
                "Method:\t" + prettyRequestMethod +
                "\n" +
                "Headers:\t" + prettyRequestHeaders +
                "\n" +
                "Body:\t" + prettyRequestBody +
                "\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n\n", restPage ) );

    }

    public static void getRequestLogger(String restPage, Request request) {

        String prettyRequestUrl         =   beautifier.toJson( request.url().url() );
        String prettyRequestMethod      =   beautifier.toJson( request.method() );
        String prettyRequestHeaders     =   beautifier.toJson( request.headers() );

        LOGGER.trace(String.format( "\tThe request to [ %s ] is:\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|" +
                "\n" +
                "Url:\t" + prettyRequestUrl +
                "\n" +
                "Method:\t" + prettyRequestMethod +
                "\n" +
                "Headers:\t" + prettyRequestHeaders +
                "\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n\n", restPage ) );

    }

    public static void optionsRequestLogger(String restPage, Request request) {

        String prettyRequestUrl         =   beautifier.toJson( request.url().url() );
        String prettyRequestMethod      =   beautifier.toJson( request.method() );
        String prettyRequestHeaders     =   beautifier.toJson( request.headers() );

        LOGGER.trace(String.format( "\tThe request to [ %s ] is:\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|" +
                "\n" +
                "Url:\t" + prettyRequestUrl +
                "\n" +
                "Method:\t" + prettyRequestMethod +
                "\n" +
                "Headers:\t" + prettyRequestHeaders +
                "\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n\n", restPage ) );

    }

    public static void putRequestLogger(String restPage, Request request) {

        String prettyRequestUrl         =   beautifier.toJson( request.url().url() );
        String prettyRequestMethod      =   beautifier.toJson( request.method() );
        String prettyRequestHeaders     =   beautifier.toJson( request.headers() );

        LOGGER.trace(String.format( "\tThe request to [ %s ] is:\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|" +
                "\n" +
                "Url:\t" + prettyRequestUrl +
                "\n" +
                "Method:\t" + prettyRequestMethod +
                "\n" +
                "Headers:\t" + prettyRequestHeaders +
                "\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n\n", restPage ) );

    }

    public static void responseLogger(String restPage, HashMap<String, Object> responseMap, JsonPath responsePath) {

        if ( responseMap.containsKey( "error" ) )
            errorLogger( restPage, responsePath );

        else {

            Response response = (Response) responseMap.get( "response" );
            String prettyResponseCode       =   beautifier.toJson( response.code() );
            String prettyResponseHeaders    =   beautifier.toJson( response.headers() );
            String prettyResponseMessage    =   beautifier.toJson( response.message() );
            String prettyResponseBody       =   beautifier.toJson( responsePath.getMap( "" ) );

            LOGGER.trace( String.format( "\tThe response of [ %s ] is:", restPage) );
            System.out.println( "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|" +
                    "\n" +
                    "Status:\t" + prettyResponseCode + " " + prettyResponseMessage +
                    "\n" +
                    "Headers:\t" + prettyResponseHeaders +
                    "\n" +
                    "Body:\t" + prettyResponseBody +
                    "\n" +
                    "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );

        }

    }

    private static void errorLogger(String restPage, JsonPath responsePath) {

        String prettyResponseBody = beautifier.toJson( responsePath.getMap( "" ) );

        LOGGER.warn( String.format( "\tThe error of [ %s ] is:", restPage) );
        System.out.println( "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|" +
                "\n" +
                "Body:\t" + prettyResponseBody +
                "\n" +
                "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );

    }

    public static void jsonElementRemoveLogger(String restPage, String key) {

        LOGGER.info( String.format( "\tRequest sending [%s] with removing [%s]\t\n", restPage, key ) );

    }

    public static void jsonElementSetLogger(String restPage, String key, String value) {

        LOGGER.info( String.format( "\tRequest sending [%s] with adding [%s] : [%s]\t\n", restPage, key, value ) );

    }

    public static void jsonElementUpdateLogger(String restPage, String key, String value) {

        LOGGER.info( String.format( "\tRequest sending [%s] with replacing [%s] : [%s]\t\n", restPage, key, value ) );

    }

    public static void jsonElementManageExceptionLogger(String restPage, String key) {

        LOGGER.info(String.format("\tRest page [%s] does not contain key [%s]\t\n", restPage, key));
        Assert.fail(String.format("\tRest page [%s] does not contain key [%s]\t\n", restPage, key));

    }

    public static void queryExecuteLogger(String query) {

        LOGGER.trace( "\tThe executed query is:\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|" +
                "\n" +
                "Query:\t" + query +
                "\n" +
                "|>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|\n\n");

    }

/*    static void queryReturnLogger(ResultSet resultSet) throws SQLException {

        int queryRowSize = getQueryResultRowSize( resultSet );

        resultSet.beforeFirst();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int queryColumnSize = resultSetMetaData.getColumnCount();

        Object[][] rowArray = new Object[ queryColumnSize ][ queryRowSize ];

        String[] columnArray = new String[ queryColumnSize ];

        int rowIterator = 1;

        for (int c = 0; c < queryColumnSize; c++ )
            columnArray[ c ] = resultSetMetaData.getColumnName( c + 1 );

        while ( resultSet.next() && rowIterator <= queryRowSize ) {

            for ( int c = 0; c < queryColumnSize; c++ )
                rowArray[ c ][ rowIterator - 1 ] = resultSet.getString( columnArray[ c ] );

            rowIterator++;

        }

        String returnValue = queryReturnToJsonString( columnArray, rowArray );

        LOGGER.trace( "\tThe return of query is:" );
        System.out.println( "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|" +
                "\n" +
                "Return:\t" + returnValue +
                "\n" +
                "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );

    }*/

    static void queryResultLogger(int resultNumber) {

        String resultValue;

        if ( resultNumber == 0 )
            resultValue = "NONE of rows could be manipulated!";
        else
            resultValue = "Number of rows manipulated by executing the DML query =  " + resultNumber;

        LOGGER.trace( "\tThe result of query is:" );
        System.out.println( "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|" +
                "\n" +
                "Result:\t" + resultValue +
                "\n" +
                "|<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<|\n" );


    }

}
