package util;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static util.EnvironmentUtil.OS_VALUE;
import static util.EnvironmentUtil.SLASH;
import static util.LoggingUtil.LOGGER;

public class DriverUtil {

    public static AppiumDriver setDriver(String driverSelect, URL url, DesiredCapabilities desiredCapabilities) {

        if ( driverSelect.equals( "appium" ) )
            return ( new AppiumDriver( url, desiredCapabilities ) );

        String osLower = OS_VALUE.toLowerCase();

        try {

            if ( osLower.contains( "win" ) ) {

                final String winPath = "tools" + SLASH + "appium" + SLASH + "drivers" + SLASH + "win";

                try {

                    if ( driverSelect.equals( "chrome" ) ) {

                        System.setProperty( "appiumdriver.chrome.driver", ( new File( winPath + SLASH + "chromedriver.exe" ) ).getAbsolutePath() );
                        ChromeOptions chromeOptions = new ChromeOptions();
                        desiredCapabilities.merge( chromeOptions );
                    }
                    else if ( driverSelect.contains( "firefox" ) )
                        System.setProperty( "appiumdriver.gecko.driver", ( new File( winPath + SLASH + "geckodriver.exe" ) ).getAbsolutePath() );
                    else if ( driverSelect.contains("edge" ) )
                        System.setProperty( "appiumdriver.edge.driver", ( new File( winPath + SLASH + "MicrosoftWebDriver.exe" ) ).getAbsolutePath() );
                    else {

                        LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                        Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                    }

                } catch (Exception e) {

                    LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                    Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                }

            } else if ( osLower.contains( "mac" ) ) {

                final String macPath = "tools" + SLASH + "appium" + SLASH + "drivers" + SLASH + "mac";

                try {

                    if ( driverSelect.equals( "chrome" ) ) {
                        System.setProperty( "appiumdriver.chrome.driver", ( new File( macPath + SLASH + "chromedriver" ) ).getAbsolutePath() );
                        ChromeOptions chromeOptions = new ChromeOptions();
                        desiredCapabilities.merge( chromeOptions );
                    }
                    else if ( driverSelect.contains( "firefox" ) )
                        System.setProperty( "appiumdriver.gecko.driver", ( new File( macPath + SLASH + "geckodriver" ) ).getAbsolutePath() );
                    else if ( driverSelect.contains( "opera" ) )
                        System.setProperty( "appiumdriver.opera.driver", ( new File( macPath + SLASH + "operadriver" ) ).getAbsolutePath() );
                    else if ( driverSelect.contains( "safari" ) )
                        System.setProperty( "appiumdriver.safari.driver", ( new File( macPath + SLASH + "safaridriver" ) ).getAbsolutePath() );
                    else {

                        LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                        Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                    }

                } catch (Exception e) {

                    LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                    Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                }

            } else if ( osLower.contains( "sunos" )
                    || osLower.contains( "nix" )
                    || osLower.contains( "nux" )
                    || osLower.contains( "aix" ) ) {

                final String linuxPath = "tools" + SLASH + "appium" + SLASH + "drivers" + SLASH + "linux";

                try {

                    if ( driverSelect.equals( "chrome" ) ) {
                        System.setProperty( "appiumdriver.chrome.driver", ( new File( linuxPath + SLASH + "chromedriver" ) ).getAbsolutePath() );
                        ChromeOptions chromeOptions = new ChromeOptions();
                        desiredCapabilities.merge( chromeOptions );
                    }
                    else if ( driverSelect.contains("firefox" ) )
                        System.setProperty( "appiumdriver.gecko.driver", ( new File( linuxPath + SLASH + "geckodriver" ) ).getAbsolutePath() );
                    else if ( driverSelect.contains( "opera" ) )
                        System.setProperty( "appiumdriver.opera.driver", ( new File( linuxPath + SLASH + "operadriver" ) ).getAbsolutePath() );
                    else {

                        LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                        Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                    }

                } catch (Exception e) {

                    LOGGER.info( "\tBrowser Type Could NOT Been Found !!!" );
                    Assert.fail( "\tBrowser Type Could NOT Been Found !!!" );

                }


            } else {

                LOGGER.info( "\tOperating System Could NOT Been Found !!!\t\n" );
                Assert.fail( "\tOperating System Could NOT Been Found !!!\t\n" );

            }

        } catch (Exception e) {

            LOGGER.info( "\tOperating System Could NOT Been Found !!!" );
            Assert.fail( "\tOperating System Could NOT Been Found !!!" );

        }

        return ( new AppiumDriver( url, desiredCapabilities ) );

    }

}
