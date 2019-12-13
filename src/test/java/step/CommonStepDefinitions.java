package step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static step.AppiumStepDefinitions.appiumDriver;
import static util.CommonStepUtil.*;
import static util.LoggingUtil.LOGGER;
import static util.HasMapUtil.context;

public class CommonStepDefinitions {

    private static int scenariosCounter = 0;
    private static int failedScenariosCounter = 0;

    @Before
    public void beforeScenario(Scenario scenario) {
        context.clear();
        LOGGER.info( String.format( "\t[%d] > Scenario [%s] started\t\n", ++scenariosCounter, scenario.getName() ) );

    }

    @After
    public void afterScenario(Scenario scenario) {

        if ( scenario.isFailed() ) {

            ++failedScenariosCounter;

            if ( appiumDriver != null ) {

                try {

                    scenario.embed( ( ( TakesScreenshot ) appiumDriver ).getScreenshotAs( OutputType.BYTES ), "image/png" );
                    LOGGER.info(String.format("\tThe screenshot has been taken for scenario: [ %s ]\t\n", scenario.getName() ) );

                } catch (Exception e) {

                    LOGGER.info(String.format("\tThe screenshot could NOT been taken for scenario: [ %s ]\t\n", scenario.getName() ) );
                    Assert.fail(String.format("\tThe screenshot could NOT been taken for scenario: [ %s ]\t\n", scenario.getName() ) );

                }

                quitAppiumSession(true);
                //closeAppiumDriver( appiumDriver );

            }

//            if ( service != null  ) {
//
//                stopAppiumServer( port );
//
//            }

        } else {

            if ( appiumDriver != null )
                quitAppiumSession(true);
                //closeAppiumDriver( appiumDriver );

//            stopAppiumServer( port );

        }

        appiumDriver = null;

        LOGGER.info( "\tAppium Driver is set as 'null'\t\n" );

        String result = scenario.isFailed() ? "with errors" : "succesfully";

        LOGGER.info(String.format( "\t[%d] > Scenario [%s] finished %s\n\t", scenariosCounter, scenario.getName(), result ) );
        LOGGER.info(String.format( "\t%d of %d scenarios failed so far\n\t", failedScenariosCounter, scenariosCounter ) );

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

}
