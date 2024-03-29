package step;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import gherkin.formatter.model.DataTableRow;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import static step.AppiumStepDefinitions.appiumDriver;
import static step.AppiumStepDefinitions.desiredCapabilities;
import static util.step.CommonStepUtil.*;
import static util.LoggingUtil.LOGGER;
import static util.HasMapUtil.context;

public class CommonStepDefinitions {

    private static int scenariosCounter = 0;
    private static int failedScenariosCounter = 0;


    @Before
    public void beforeScenario(Scenario scenario) {

        context.clear();
        context.put("scenario", scenario);

        desiredCapabilities = new DesiredCapabilities();

        LOGGER.info( String.format( "\t[%d] > Scenario [%s] started\t\n", ++scenariosCounter, scenario.getName() ) );

    }

    @After
    public void afterScenario(Scenario scenario) {

        if ( scenario.isFailed() ) {

            ++failedScenariosCounter;

            if ( appiumDriver != null ) {

                takeScreenshot(scenario);

                quitAppiumSession(true);
                //closeAppiumDriver( appiumDriver );

            }

/*            if ( service != null  ) {

                stopAppiumServer( port );

            }*/

        } else {

            if ( appiumDriver != null )
                quitAppiumSession(true);
                //closeAppiumDriver( appiumDriver );
                //stopAppiumServer( port );

        }

        appiumDriver = null;

        LOGGER.info( "\tAppium Driver is set as 'null'\t\n" );

        String result = scenario.isFailed() ? "with errors" : "succesfully";

        LOGGER.info(String.format( "\t[%d] > Scenario [%s] finished %s\n\t", scenariosCounter, scenario.getName(), result ) );
        LOGGER.info(String.format( "\t%d of %d scenarios failed so far\n\t", failedScenariosCounter, scenariosCounter ) );

    }

    @Given("^I take screenshot$")
    public void takeScenarioScreenshot() {
        Scenario scenario = (Scenario) context.get("scenario");
        takeScreenshot(scenario);
    }

    @Given("^I wait for (\\d+) seconds$")
    public static void waitForNSeconds(long seconds) {

        try {

            Thread.sleep( seconds * 1000L );
            LOGGER.info( "\tWait for [" + seconds + "] seconds\t\n" );

        } catch (Exception e) {

            LOGGER.error( "\tError during waiting for [" + seconds + "] seconds.\t\n", e );
            Assert.fail();

        }

    }

    @Given("^I set following key value pairs to context$")
    public static void setValuesToContext(DataTable dataTable) {

        for ( DataTableRow row : dataTable.getGherkinRows() ) {

            String key      =   row.getCells().get( 0 );
            String value    =   row.getCells().get( 1 );

            context.put( key, value );

            LOGGER.info( String.format( "\tThe key [%s] : value [%s] is set to the context\t\n", key, value ) );

        }

    }

}
