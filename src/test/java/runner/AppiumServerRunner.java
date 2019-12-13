package runner;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static util.HasMapUtil.config;
import static util.PropertiesUtil.*;
import static util.LoggingUtil.LOGGER;
import static util.ServerUtil.isPortAvailableScript;

public class AppiumServerRunner {

    private static AppiumDriverLocalService service;

    public static class open {

        public static void main(String[] args) {

            AppiumServiceBuilder builder = new AppiumServiceBuilder();

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, "false");
            desiredCapabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);

            if (isPortAvailableScript(Integer.parseInt(APPIUM_PORT), true)) {

                ENV_MAP.put("PATH", "/usr/local/bin:" + ENV_MAP.get("PATH"));

                builder.withIPAddress(APPIUM_HOST);
                builder.usingPort(Integer.parseInt(APPIUM_PORT));
                builder.withCapabilities(desiredCapabilities);
                builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
                builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
                builder.withEnvironment(ENV_MAP);

                service = AppiumDriverLocalService.buildService(builder);
                service.start();

            } else {

                System.out.println("Appium Server already running on Port - " + APPIUM_PORT);
                System.exit(0);

            }

        }

    }

    static class close {

        public static void main(String[] args) {

            try {

                service.stop();

                System.out.println( "\tThe server is closed.\t\n" );

            } catch (Exception e) {

                System.out.println( "\tThe server could NOT been closed.\t\n" );
                e.printStackTrace();
                System.exit( 0 );

            }

        }

    }

    public static class emulatorRunner {

        public static void main(String[] args) {

            String emulatorRoot = ENV_MAP.get( "ANDROID_EMULATOR_ROOT" );

            executeRuntimeCommand( emulatorRoot );

        }

        private static void executeRuntimeCommand(String emulatorRoot) {

            Process process = null;

            try {

                File emulatorFile = new File( emulatorRoot );

                process = Runtime.getRuntime().exec( "./emulator -avd Nexus_5_API_28", null, emulatorFile );

            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }

            BufferedReader reader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );

            String line = "";

            while ( true ) {

                try {

                    if ( ( line = reader.readLine() ) == null ) break;

                } catch (IOException e) {

                    Assert.fail();

                }

                LOGGER.info( line + "\n" );

            }

            try {

                process.waitFor();

            } catch (InterruptedException e) {

                Assert.fail();

            }

        }

    }

}
