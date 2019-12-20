package helper;

import cucumber.api.DataTable;
import org.junit.Assert;
import step.RestStepDefinitions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static util.HasMapUtil.context;
import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.PROJECT_DIR;
import static util.PropertiesUtil.SLASH;

public class CommonStepHelper {

    public static String getStringValueOfSource(String key, String source) {

        switch ( source ) {

            case "text":
                return String.valueOf( key );

            case "file":

                try {

                    return new String( Files.readAllBytes( Paths.get( PROJECT_DIR + SLASH + "src" + SLASH + "test"
                            + SLASH + "resources" + SLASH + "config" + SLASH + "test-bases" + SLASH + key ) ) );

                } catch (IOException e) {

                    LOGGER.warn( String.format( "\tThe file could NOT be found: [%s]\t\n", key ) );
                    Assert.fail( String.format( "\tThe file could NOT be found: [%s]\t\n", key ) );
                    return null;

                }

            case "context":
                return context.getAsString( key );

            default:
                LOGGER.warn( String.format( "\tThe source type could NOT be recognised: [%s]\t\n", source ) );
                Assert.fail( String.format( "\tThe source type could NOT be recognised: [%s]\t\n", source ) );
                return null;


        }

    }

    public static int getCheckDigit(String number) {

        int sum = 0;

        for ( int i = 0; i < number.length(); i++ ) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring( i, ( i + 1 ) ) );

            if ( ( i % 2 ) == 0) {

                digit = digit * 2;

                if ( digit > 9 )
                    digit = ( digit / 10 ) + ( digit % 10 );

            }

            sum += digit;

        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;

        return ( ( mod == 0) ? 0 : 10 - mod );
    }

    public static class getCommonElements {


        public static void getOracleElements(DataTable dataTable) {


        }

        public static void getRestElements(DataTable dataTable) {

            RestStepDefinitions.saveValuesOfResponse( dataTable );

        }

        public static void getJMeterElements(DataTable dataTable) {


        }

        public static void getSeleniumElements(DataTable dataTable) {


        }

    }

    public static String getFilePath(String type) {

        switch ( type ) {

            case "html":
                return PROJECT_DIR + SLASH + "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config" + SLASH + "html-bases";

            case "json":
                return PROJECT_DIR + SLASH + "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config" + SLASH + "json-bases";

            case "text":
                return PROJECT_DIR + SLASH + "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config" + SLASH + "text-bases";

            default:
                return PROJECT_DIR + SLASH + "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config" + SLASH + "file-bases";

        }

    }

}
