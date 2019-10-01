package helper;

import org.junit.Assert;

import java.io.FileReader;

import static util.LoggingUtil.LOGGER;

public class BannerHelper {

    public static void setFileToConsole(FileReader reader, String color) {

        try {

            int character;

            while ( ( character = reader.read() ) != -1 ) {

                System.out.print( "\u001B" + color + ( char ) character + "\u001B[0m" );

            }

        } catch (Exception e) {

            LOGGER.info( "\t\n" + e.getMessage() + "\t\n" );
            Assert.fail( "\t\n" + e.getMessage() + "\t\n" );

        }

    }

}
