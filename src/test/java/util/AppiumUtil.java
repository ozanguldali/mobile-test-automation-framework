package util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import static step.AppiumStepDefinitions.service;
import static util.EnvironmentUtil.ENV_MAP;
import static util.LoggingUtil.LOGGER;

public class AppiumUtil {

    public static void startAppiumServer(AppiumServiceBuilder builder, DesiredCapabilities capabilities, String host, int port) {

        ENV_MAP.put( "PATH", "/usr/local/bin:" + ENV_MAP.get( "PATH" ) );

        builder.withIPAddress( host );
        builder.usingPort( port );
//        builder.usingAnyFreePort();
        builder.withCapabilities( capabilities );
        builder.withArgument( GeneralServerFlag.SESSION_OVERRIDE );
        builder.withArgument( GeneralServerFlag.LOG_LEVEL,"debug" );
        builder.withEnvironment( ENV_MAP );

        service = AppiumDriverLocalService.buildService( builder );
        service.start();

    }

    public static void stopAppiumServer(String port) {

//        kill -9 $(lsof -t -i:4723)

        try {

            service.stop();

            LOGGER.info( "\tThe server is closed.\t\n" );

        } catch (Exception e) {

            LOGGER.error( "\tThe server could NOT been closed.\t\n", e );
            Assert.fail();

        }

    }

}
