package util;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;

import static step.AppiumStepDefinitions.appiumDriver;
import static step.AppiumStepDefinitions.desiredCapabilities;
import static util.LoggingUtil.LOGGER;

public class CommonStepUtil {

    public static void closeAppiumDriver() {

        try {

            appiumDriver.closeApp();

//            appiumDriver.close();

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] has been closed.\t\n", appiumDriver.toString() ) );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.toString() ) );
            Assert.fail( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.toString() ) );

        }

    }

    public static void quitAppiumSession() {

//        closeAppiumDriver( appiumDriver );

        try {

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] is closing.\t\n", appiumDriver.getSessionId() ) );

            appiumDriver.quit();

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );
            Assert.fail( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", appiumDriver.getSessionId() ) );

        }

    }

    public static void killAppiumDriver() {

        closeAppiumDriver();

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

}
