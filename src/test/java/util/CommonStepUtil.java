package util;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;

import static util.LoggingUtil.LOGGER;

public class CommonStepUtil {

    public static void quitAppiumDriver(AppiumDriver appiumDriver) {

        try {

            appiumDriver.closeApp();
            LOGGER.info( String.format( "\tThe appium driver: [ %s ] has been closed.\t\n", appiumDriver.toString() ) );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.toString() ) );
            Assert.fail( String.format( "\tThe appium driver: [ %s ] could NOT been closed\t\n", appiumDriver.toString() ) );

        }

    }

}
