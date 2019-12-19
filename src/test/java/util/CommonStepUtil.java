package util;

import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static step.AppiumStepDefinitions.appiumDriver;
import static step.AppiumStepDefinitions.desiredCapabilities;
import static step.CommonStepDefinitions.waitForNSeconds;
import static util.HasMapUtil.config;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.NEW_COMMAND_TIMEOUT;

public class CommonStepUtil {

    public static void closeAppiumDriver(Boolean isFirst) {

        try {

            appiumDriver.closeApp();

//            appiumDriver.close();

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] has been closed.\t\n", appiumDriver.getSessionId() ) );

        } catch (Exception e) {

            if( isFirst )
                closeAppiumDriver( false );

            else {

                LOGGER.info( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );
                Assert.fail( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );

            }

        }

    }

    public static void quitAppiumSession(Boolean isFirst) {

//        closeAppiumDriver( appiumDriver );

        try {

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] is closing.\t\n", appiumDriver.getSessionId() ) );

            appiumDriver.quit();

            if ( isFirst && appiumDriver.getSessionId() != null ) {

                waitForNSeconds( 5 );
                quitAppiumSession( false );

            }

        } catch (Exception e) {

            waitForNSeconds( Integer.parseInt( NEW_COMMAND_TIMEOUT ) + 5  );

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );
            Assert.fail( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );

        }

    }

    public static void killAppiumDriver() {

        closeAppiumDriver( true );

        String bundleId = desiredCapabilities.getCapability( "bundleId" ).toString();

        try {

            appiumDriver.quit();
            //appiumDriver.executeScript( "client:client.applicationClose('" + bundleId + "')" );

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] has been killed from background.\t\n", appiumDriver.toString() ) );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] could NOT been killed\t\n", appiumDriver.toString() ) );
            Assert.fail( String.format( "\tThe appium driver: [ %s ] could NOT been killed\t\n", appiumDriver.toString() ) );

        }

    }

    public static void takeScreenshot(Scenario scenario){

        byte[] screenshot = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");

    }

}
