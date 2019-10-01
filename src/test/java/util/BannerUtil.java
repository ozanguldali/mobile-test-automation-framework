package util;

import org.junit.Assert;

import java.io.FileReader;

import static helper.BannerHelper.setFileToConsole;
import static util.EnvironmentUtil.PROJECT_DIR;
import static util.EnvironmentUtil.SLASH;
import static util.LoggingUtil.LOGGER;

public class BannerUtil {

    private static final String BANNER  =   "src/test/resources/config/banner-bases/banner.txt";
    private static final String LOGO    =   "src/test/resources/config/banner-bases/logo.txt";
    private static final String CONSOLE_BLUE    =   "[36;1m";
    private static final String CONSOLE_GREEN   =   "[32;1m";

    public static void paintBanner() {

       try {

           FileReader readerLogo    =   new FileReader( PROJECT_DIR + SLASH + LOGO );
           FileReader readerBanner  =   new FileReader( PROJECT_DIR + SLASH + BANNER );


           setFileToConsole( readerLogo, CONSOLE_BLUE );

           setFileToConsole( readerBanner, CONSOLE_GREEN );


       } catch (Exception e) {

           LOGGER.warn( "\t\n" + e.getMessage() + "\t\n" );
           Assert.fail( "\t\n" + e.getMessage() + "\t\n" );

       }

    }

}
