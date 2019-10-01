package util;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;

import static util.LoggingUtil.LOGGER;

public class AppiumStepUtil {

    public static void navigateToURL(AppiumDriver appiumDriver, String urlString) {

        try {

            appiumDriver.get( urlString );
            LOGGER.info( String.format( "\tNavigated to the website: %s\t\n", urlString ) );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tDriver could NOT been navigated to: [%s], because { error: [%s] }\t\n", urlString, e.getMessage() ) );
            Assert.fail( String.format( "\tDriver could NOT been navigated to: [%s], because { error: [%s] }\t\n", urlString, e.getMessage() ) );

        }

    }

}
