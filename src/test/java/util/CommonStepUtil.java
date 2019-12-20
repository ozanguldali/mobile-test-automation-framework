package util;

import cucumber.api.Scenario;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static helper.CommonStepHelper.getCheckDigit;
import static step.AppiumStepDefinitions.appiumDriver;
import static step.AppiumStepDefinitions.desiredCapabilities;
import static step.CommonStepDefinitions.waitForNSeconds;
import static util.HasMapUtil.context;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.NEW_COMMAND_TIMEOUT;
import static util.PropertiesUtil.SLASH;
import static step.RestStepDefinitions.goRestService;

public class CommonStepUtil {

    public static void closeAppiumDriver(Boolean isFirst) {

        try {

//            appiumDriver.closeApp();

//            appiumDriver.close();

            final String SID = String.valueOf( appiumDriver.getSessionId() );

            context.put( "session_id", SID );

            goRestService( "closeApp" );

            LOGGER.info( String.format( "\tThe appium driver: [ %s ] has been closed.\t\n", SID ) );

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

        final String SID = String.valueOf( appiumDriver.getSessionId() );

        try {

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] is quiting.\t\n", SID ) );

            appiumDriver.quit();

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] is quit.\t\n", SID ) );

            if ( isFirst && appiumDriver.getSessionId() != null ) {

                waitForNSeconds( 5 );
                quitAppiumSession( false );

            }

        } catch (Exception e) {

            waitForNSeconds( Integer.parseInt( NEW_COMMAND_TIMEOUT ) + 5  );

            LOGGER.info( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", SID ) );
            Assert.fail( String.format( "\tThe appium driver session: [ %s ] could NOT been closed\t\n", SID ) );

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

        appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {

            byte[] screenshot = ( ( TakesScreenshot ) appiumDriver ).getScreenshotAs( OutputType.BYTES );
            scenario.embed(screenshot, "image/png");

            LOGGER.info(String.format("\tThe screenshot has been taken for scenario: [ %s ]\t\n", scenario.getName() ) );

        } catch (Exception e) {

            LOGGER.info(String.format("\tThe screenshot could NOT been taken for scenario: [ %s ]; because error: [ %s ]\t\n", scenario.getName(), e.getMessage() ) );
            Assert.fail(String.format("\tThe screenshot could NOT been taken for scenario: [ %s ]; because error: [ %s ]\t\n", scenario.getName(), e.getMessage() ) );

        }

    }

    public static String replaceSavedElement(String string,  HashMap dataMap) {

        HashMap tempMap = ( HashMap ) dataMap.clone();

        Iterator iterator = tempMap.entrySet().iterator();

        while ( iterator.hasNext() ) {

            Map.Entry pair = (Map.Entry) iterator.next();

            String savedKey     =   "{{" + ( pair.getKey() ) + "}}";
            String savedValue   =   String.valueOf( pair.getValue() );

            string = string.replace( savedKey, savedValue );

            iterator.remove();

        }

        return string;

    }

    public static String readLineByLine(String filePath) {

        StringBuilder contentBuilder = new StringBuilder();

        try ( Stream<String> stream = Files.lines( Paths.get( filePath ), StandardCharsets.UTF_8 ) ) {

            stream.forEach(s -> contentBuilder.append(s).append("\n"));

        } catch ( IOException e ) {

            e.printStackTrace();

        }

        return contentBuilder.toString();

    }

    public static void saveValues(String leftKey, String rightKey, String value){

        if ( value == null ) {

            LOGGER.warn(String.format("\tThe parameter [%s] could NOT be saved since it is NOT included\t\n", rightKey));
            Assert.fail(String.format("\tThe parameter [%s] could NOT be saved since it is NOT included\t\n", rightKey));

        }

        if ( !value.equals("")
                && Arrays.asList("\"", "'", "{", "}", "[", "]", "(", ")")
                .contains( String.valueOf( value.charAt( 0 ) ) ) )
            value = value.replace( String.valueOf( value.charAt( 0 ) ), "" );

        if ( !value.equals("")
                && Arrays.asList("\"", "'", "{", "}", "[", "]", "(", ")")
                .contains( String.valueOf( value.charAt( value.length() - 1 ) ) ) )
            value = value.replace( String.valueOf( value.charAt( value.length() - 1 ) ), "" );

        if( value.equals("") )
            value = "null";

        context.put( leftKey, value );

        LOGGER.trace( String.format( "\tSaving parameter [%s] <- [%s] : [%s]\t\n", leftKey, rightKey, value ) );

    }

    public static boolean doesEqualTwoStrings(String current, String expected) {

        if ( current == null )
            current = "";

        if ( expected == null )
            expected = "";

        if ( current.equals( expected ) ) {

            LOGGER.trace( String.format( "\tThe expected: [%s] == actual: [%s]\t\n", current, expected ) );
            return true;

        } else {

            LOGGER.warn( String.format( "\tThe expected: [%s] BUT actual: [%s]\t\n", current, expected ) );
            return false;

        }

    }

    public static boolean doesContainsTwoStrings(String current, String expected) {

        if ( current == null )
            current = "";

        if ( expected == null )
            expected = "";

        if ( current.contains( expected ) ) {

            LOGGER.trace( String.format( "\tThe expected: [%s] == actual: [%s]\t\n", current, expected ) );
            return true;

        } else {

            LOGGER.warn( String.format( "\tThe expected: [%s] BUT actual: [%s]\t\n", current, expected ) );
            return false;

        }

    }

    public static String generateValidCard(String bin, String startRange, String endRange) {

        Random random = new Random( System.currentTimeMillis() );

        int randomNumberLength = endRange.length() - ( bin.length() + 1 );

        StringBuilder builder = new StringBuilder( bin );

        for ( int i = 0; i < randomNumberLength; i++ ) {

            int digit = random.nextInt(10);

            builder.append(digit);

        }

        // Do the Luhn algorithm to generate the check digit.
        int checkDigit = getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();

    }

    public static boolean checkCardGenerator(String bin, String startRange, String endRange) {

        int binLength = bin.length();

        String firstPart_start = startRange.substring( 0, binLength );
        String firstPart_end = endRange.substring( 0, binLength );

        return ( firstPart_start.equals( bin ) && firstPart_end.equals( bin ) );

    }

    public static void deleteFile(String name, final String path) {

        File file = new File( path + SLASH + name );

        if ( !( file.exists() && !file.isDirectory() ) ) {

            LOGGER.warn( "\tThe file [" + name + "] is not exist\t\n" );

        } else {

            String filePath = file.getAbsolutePath();

            try {

                Files.deleteIfExists( Paths.get( filePath ) );

            } catch (NoSuchFileException nsf) {

                LOGGER.warn( "\tThe file [" + name + "] is not exist\t\n" );

            } catch (IOException io) {

                LOGGER.warn( "\tInvalid permissions !!\t\n" );

            } catch (Exception e) {

                LOGGER.fatal( String.format( "\tThe file [" + name + "] could NOT be deleted, because { error : [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe file [" + name + "] could NOT be deleted, because { error : [%s] }\t\n", e.getMessage() ) );

            }

            LOGGER.trace( "\tThe file [" + name + "] is deleted\t\n" );

        }

    }

}
