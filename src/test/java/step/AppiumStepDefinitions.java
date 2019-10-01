package step;

import com.google.gson.JsonObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;

import static helper.AppiumStepHelper.*;
import static util.AppiumStepUtil.navigateToURL;
import static util.AppiumUtil.startAppiumServer;
import static util.AppiumUtil.stopAppiumServer;
import static util.DriverUtil.setDriver;
import static util.EnvironmentUtil.APPIUM_HOST;
import static util.EnvironmentUtil.APPIUM_PORT;
import static util.LoggingUtil.LOGGER;
import static util.ServerUtil.isPortAvailableSocket;

public class AppiumStepDefinitions {

    static AppiumDriver appiumDriver = null;
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

    @Given("^I close appium server")
    public void stopServer() {

        stopAppiumServer( port );

    }

    @Given("^I use following capabilities:")
    public static void useCapabilities(Map<String, String> dataMap) {

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

        try {

            appiumDriver = setDriver( driverSelect, url, desiredCapabilities );

            LOGGER.info( String.format("\tDriver has been selected as: [%s]\t\n", driverSelect ) );

        } catch ( Exception e ) {

            LOGGER.info( String.format( "\tDriver could NOT been selected as: [%s], because { error: [%s] }\t\n", driverSelect, e.getMessage() ) );
            Assert.fail( String.format( "\tDriver could NOT been selected as: [%s], because { error: [%s] }\t\n", driverSelect, e.getMessage() ) );

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
