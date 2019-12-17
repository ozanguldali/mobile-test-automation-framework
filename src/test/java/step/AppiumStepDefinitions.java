package step;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.PropertiesUtil;

import java.net.URL;
import java.util.Map;

import static helper.AppiumStepHelper.*;
import static util.AppiumStepUtil.navigateToURL;
import static util.AppiumUtil.startAppiumServer;
import static util.AppiumUtil.stopAppiumServer;
import static util.CommonStepUtil.closeAppiumDriver;
import static util.DriverUtil.setDriver;
import static util.HasMapUtil.context;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.*;
import static util.ServerUtil.isPortAvailableSocket;
import static util.HasMapUtil.config;

public class AppiumStepDefinitions {

    public static AppiumDriver appiumDriver = null;
    public static DesiredCapabilities desiredCapabilities;

    public static AppiumDriverLocalService service;
    private static JsonObject pageObject;

    public static String host;
    public static String port;

    @Given("^I start appium server for:$")
    public static void startServer(Map<String, String> dataMap) {

        AppiumServiceBuilder builder = new AppiumServiceBuilder();

        desiredCapabilities = new DesiredCapabilities();

        setDesiredCapabilities( desiredCapabilities, dataMap );

        if( isPortAvailableSocket( Integer.parseInt( port ), true ) )
            startAppiumServer( builder, desiredCapabilities, host, Integer.parseInt( port ) );

        else {

            LOGGER.error( "Appium Server already running on Port - " + port );
            Assert.fail();

        }

        URL url = setRemoteAddress( host, port );

        if ( url == null ) {

            LOGGER.error( "\tThe remote url is null, but it can NOT be null.\t\n" );
            Assert.fail();

        }

        appiumDriver = new AppiumDriver( url, desiredCapabilities );

    }

    @Given("^I close appium server$")
    public void stopServer() {

        stopAppiumServer( port );

    }

    @Given("^I use propertied capabilities for (\\w+(?: \\w+)*) device (\\w+(?: \\w+)*)$")
    public static void usePropertiedCapabilities(String osTag, String deviceTag) {

        if ( desiredCapabilities == null )
            desiredCapabilities = new DesiredCapabilities();

        setPropertiedCapabilities( desiredCapabilities, osTag, deviceTag );

    }

    @Given("^I use following capabilities:$")
    public static void useFollowingCapabilities(Map<String, String> dataMap) {

        if ( desiredCapabilities == null )
            desiredCapabilities = new DesiredCapabilities();
        
        setDesiredCapabilities( desiredCapabilities, dataMap );

    }

    @Given("^I use (\\w+(?: \\w+)*) driver$")
    public static void useDriver(String driverSelect) {

        URL url = setRemoteAddress( APPIUM_HOST, APPIUM_PORT );

        if ( url == null ) {

            LOGGER.error( "\tThe remote url is null, but it can NOT be null.\t\n" );
            Assert.fail();

        }

        if ( appiumDriver != null )
            appiumDriver.launchApp();

        else {

            try {

                appiumDriver = setDriver( driverSelect, url, desiredCapabilities );

                LOGGER.info( String.format("\tDriver has been selected as: [%s]\t\n", driverSelect ) );

            } catch ( Exception e ) {

                LOGGER.info( String.format( "\tDriver could NOT been selected as: [%s], because { error: [%s] }\t\n", driverSelect, e.getMessage() ) );
                Assert.fail( String.format( "\tDriver could NOT been selected as: [%s], because { error: [%s] }\t\n", driverSelect, e.getMessage() ) );

            }

        }

    }

    @Given("^I close driver$")
    public static void closeDriver() {

        closeAppiumDriver( true );

    }

    @Given("^I reset driver$")
    public static void resetDriver() {

        try {

            appiumDriver.resetApp();

            LOGGER.trace( String.format( "\tThe appium driver: [ %s ] has been reset.\t\n", appiumDriver.toString() ) );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] could NOT been reset\t\n", appiumDriver.toString() ) );
            Assert.fail( String.format( "\tThe appium driver: [ %s ] could NOT been reset\t\n", appiumDriver.toString() ) );

        }

    }

    @When("^I open (\\w+(?: \\w+)*) page$")
    public void openPage(String pageKey) {

        usePage( pageKey );

        String urlString = getPageObjectURL( pageObject );

        navigateToURL( appiumDriver, urlString );

    }

    @When("^I use (\\w+(?: \\w+)*) page$")
    public void usePage(String pageKey) {

        JsonObject pageElementObject = getPageElementJsonObject();

        pageObject = getPageKeyJsonObject( pageObject, pageElementObject, pageKey );

    }

}
