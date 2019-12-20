package step;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import io.restassured.path.json.JsonPath;
import okhttp3.Response;
import org.junit.Assert;
import util.ParserUtil;
import util.step.RestStepUtil;

import java.util.HashMap;

import static helper.JsonHelper.setSavedElements;
import static helper.ParserHelper.getJsonPathValue;
import static helper.step.RestStepHelper.setResponseObjects;
import static helper.step.RestStepHelper.getResponsePathValue;
import static util.HasMapUtil.context;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.REST_HOST;
import static util.RestUtil.manageRestFile;
import static util.step.CommonStepUtil.*;
import static util.step.RestStepUtil.checkStatusCode;

public class RestStepDefinitions {

    public static Response response;
    public static JsonPath responsePath;
    public static JsonPath requestPath;
    private static HashMap< HashMap< String, Object >, JsonPath> responseMap = new HashMap<>();

    @When("^I go (\\w+(?: \\w+)*) rest service$")
    public static void goRestService(String restPage) {

        JsonObject restObject = ParserUtil.jsonFileParsing( "rest-bases/" + restPage );

        restObject = setSavedElements( restObject, context);

        requestPath = JsonPath.from( String.valueOf( restObject ) );

        responseMap.clear();
        responseMap = RestStepUtil.restResponseHandler( restPage, REST_HOST, restObject );

        setResponseObjects( responseMap );

    }

    @When("^I go (\\w+(?: \\w+)*) rest service by playing with existing parameters:$")
    public void goRestServiceWithParameters(String restPage, DataTable dataTable) {

        JsonObject restObject   =   ParserUtil.jsonFileParsing( "rest-bases/" + restPage );

        restObject = setSavedElements( restObject, context );

        manageRestFile(restPage, dataTable, restObject);

        responseMap.clear();
        responseMap = RestStepUtil.restResponseHandler( restPage, REST_HOST, restObject );

        setResponseObjects( responseMap );

    }

    @Then("^I save the values of response:$")
    public static void saveValuesOfResponse(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String leftKey      =   row.getCells().get( 0 );
            String rightKey     =   row.getCells().get( 1 );
            String value        =   getResponsePathValue( responsePath, rightKey );

            saveValues( leftKey, rightKey, value );

        }

    }

    @And("^the status is (\\d+) in the response$")
    public void iSeeStatus(int expectedStatusCode) {

        int actualStatusCode = 0;

        try {

            actualStatusCode = response.code();

        } catch (Exception e) {

            LOGGER.warn( "\tThe status code could NOT be recognised\t\n" );
            Assert.fail( "\tThe status code could NOT be recognised\t\n" );

        }

        checkStatusCode(actualStatusCode, expectedStatusCode);

    }

    @And("^the elements equal to the followings in the response$")
    public void equalsToFollowings(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String key              =   row.getCells().get( 0 );
            String expectedValue    =   row.getCells().get( 1 );
            String actualValue      =   responsePath.getString( key );

            if ( responsePath.getJsonObject( key ) == null )
                actualValue = "null";

            if ( doesEqualTwoStrings( actualValue, expectedValue ) )
                LOGGER.info( String.format( "\tIn the parameter [%s]; the expected: [%s] equals to the actual: [%s]\t\n", key, expectedValue, actualValue ) );

            else {

                LOGGER.warn( String.format( "\tIn the parameter [%s]; the expected: [%s] BUT the actual: [%s]\t\n", key, expectedValue, actualValue ) );
                Assert.fail( String.format( "\tIn the parameter [%s]; the expected: [%s] BUT the actual: [%s]\t\n", key, expectedValue, actualValue ) );

            }

        }

    }

    @And("^the elements contains the followings in the response$")
    public void containsFollowings(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String key              =   row.getCells().get( 0 );
            String expectedValue    =   row.getCells().get( 1 );
            String actualValue      =   responsePath.getString( key );

            if ( responsePath.getJsonObject( key ) == null )
                actualValue = "null";

            if ( doesContainsTwoStrings( actualValue, expectedValue ) ) {
                LOGGER.info( String.format( "\tIn the parameter [%s]; the actual: [%s] contains the element: [%s]\t\n", key, actualValue, expectedValue ) );
            }

            else {

                LOGGER.warn( String.format( "\tIn the parameter [%s]; the actual: [%s] DOES NOT contain the element: [%s]\t\n", key, actualValue, expectedValue ) );
                Assert.fail( String.format( "\tIn the parameter [%s]; the actual: [%s] DOES NOT contain the element: [%s]\t\n", key, actualValue, expectedValue ) );

            }

        }

    }

    @And("^the elements are included in the response$")
    public void included(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String expectedKey = row.getCells().get( 0 );

            if ( responsePath.getJsonObject( expectedKey ) != null )
                LOGGER.info( String.format("\tThe expected: [ %s ] is contained in the response\t\n", expectedKey) );

            else {

                LOGGER.warn( String.format("\tThe expected: [ %s ] IS NOT contained in the response\t\n", expectedKey) );
                Assert.fail( String.format("\tThe expected: [ %s ] IS NOT contained in the response\t\n", expectedKey) );

            }

        }

    }

    @And("^the elements are not included in the response$")
    public void notIncluded(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String expectedKey = row.getCells().get( 0 );

            if ( responsePath.getJsonObject( expectedKey ) == null )
                LOGGER.info( String.format("\tThe expected: [ %s ] is not contained in the response\t\n", expectedKey) );

            else {

                LOGGER.warn( String.format("\tThe expected: [ %s ] is UNEXPECTEDLY contained in the response\t\n", expectedKey) );
                Assert.fail( String.format("\tThe expected: [ %s ] is UNEXPECTEDLY contained in the response\t\n", expectedKey) );

            }

        }

    }

    @And("^I compare context values with the response$")
    public void compareResponse(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String keyContext       =   row.getCells().get( 0 );
            String valueContext     =   null;
            String expectedKey      =   row.getCells().get( 1 );
            String expectedValue    =   responsePath.getString( expectedKey );

            try {

                valueContext = context.getAsString( keyContext );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe key [%s] does NOT been included in the context\t\n", keyContext ) );
                Assert.fail( String.format( "\tThe key [%s] does NOT been included in the context\t\n", keyContext ) );

            }

            if ( responsePath.getJsonObject( expectedKey ) == null )
                expectedValue = "null";

            if ( doesEqualTwoStrings( valueContext, expectedValue ) )
                LOGGER.info( String.format( "\tIn the parameter [%s]; the expected: [%s] equals to the context value: [%s]\t\n", expectedKey, valueContext, expectedValue ) );

            else {

                LOGGER.warn( String.format( "\tIn the parameter [%s]; the expected: [%s] BUT the actual: [%s]\t\n", expectedKey, valueContext, expectedValue ) );
                Assert.fail( String.format( "\tIn the parameter [%s]; the expected: [%s] BUT the actual: [%s]\t\n", expectedKey, valueContext, expectedValue ) );

            }

        }

    }

    @And("^I save the values of request:$")
    public void iSaveTheValuesOfRequest(DataTable dataTable) {

        for (DataTableRow row : dataTable.getGherkinRows()) {

            String leftKey = row.getCells().get(0);
            String rightKey = row.getCells().get(1);
            String value = (String) getJsonPathValue(requestPath, "jsonBody." + rightKey);

            saveValues(leftKey, rightKey, value);

        }

    }

    @And("^I set restful timeouts in seconds as:$")
    public void setTimeout(DataTable dataTable) {

        for (DataTableRow row : dataTable.getGherkinRows()) {

            String key = row.getCells().get(0);
            String value = row.getCells().get(1);

            if ( value.matches( "\\d+" ) && Integer.parseInt( value ) != 0 ) {

                if ( key.equals( "connectTimeout" ) || key.equals( "readTimeout" ) || key.equals( "writeTimeout" ) ) {

                    context.put( key, value );

                    LOGGER.info( String.format( "\tThe timeout [%s] is set as [%s] seconds\t\n", key, value ) );


                } else {

                    LOGGER.warn( String.format( "\tThe timeout key is unknown: [%s]\t\n", key ) );
                    Assert.fail( String.format( "\tThe timeout key is unknown: [%s]\t\n", key ) );

                }

            } else {

                LOGGER.warn( String.format( "\tThe timeout value is NOT a positive integer: [%s]\t\n", value ) );
                Assert.fail( String.format( "\tThe timeout value is NOT a positive integer: [%s]\t\n", value ) );

            }


        }

    }

}
