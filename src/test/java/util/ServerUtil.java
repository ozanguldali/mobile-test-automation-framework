package util;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

import static step.CommonStepDefinitions.waitForNSeconds;
import static util.PropertiesUtil.OS_VALUE;
import static util.LoggingUtil.LOGGER;

public class ServerUtil {

    private static List<String> readerList = new LinkedList<>();


    public static boolean isPortAvailableSocket(int port, boolean kill) {

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket();

            if ( OS_VALUE.toLowerCase().contains( "mac" ) )
                serverSocket.setReuseAddress( false );

            serverSocket.bind( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ), 1 );

            serverSocket.close();

            LOGGER.info( String.format( "\tThe port is available: [%d]\t\n", port ) );

            return true;

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe port is NOT available: [%d]\t\n", port ) );

            if ( kill ) {

                killPort( port );
                LOGGER.info( String.format( "\tKilling the port: [%d]\t\n", port ) );
                waitForNSeconds( 2 );
//                standPort( port );
//                waitForNSeconds( 2 );
//                isPortAvailableSocket( port, false );

            }

            return false;

        }

    }
    static boolean isPortAvailableServer(int port, boolean kill) {

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket();

            if ( OS_VALUE.toLowerCase().contains( "mac" ) )
                serverSocket.setReuseAddress( false );

            serverSocket.bind( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ), 1 );

            serverSocket.close();

            System.out.println( String.format( "\tThe port is available: [%d]\t\n", port ) );

            return true;

        } catch (Exception e) {

            System.out.println( String.format( "\tThe port is NOT available: [%d]\t\n", port ) );

            if ( kill ) {

                killPort( port );
                System.out.println( String.format( "\tKilling the port: [%d]\t\n", port ) );
                waitForNSeconds( 2 );
//                standPort( port );
//                waitForNSeconds( 2 );

                isPortAvailableServer( port, false );

            }

            return false;

        }

    }

    public static boolean isPortAvailableScript(int port, boolean kill) {

        executeRuntimeCommand( "lsof -t -i:" + port );

        boolean available = true;

        for ( String line : readerList ) {

            if ( line.equals( String.valueOf( port ) ) )
                available = false;

        }

        if ( available ) {

            System.out.println( String.format( "\tThe port is available: [%d]\t\n", port ) );

            return true;

        } else {

            System.out.println( String.format( "\tThe port is NOT available: [%d]\t\n", port ) );

            if ( kill ) {

                killPort( port );
                System.out.println( String.format( "\tKilling the port: [%d]\t\n", port ) );
                waitForNSeconds( 2 );
                //                standPort( port );
                //                waitForNSeconds( 2 );

                isPortAvailableScript( port, false );

            }

            return false;
        }

    }

    private static void standPort(int port) {

        String command = "netstat -a -b -l | grep \"" + port + "\"";

        executeRuntimeCommand( command );

    }

    public static void killPort(int port) {


        String command = "kill -9 $(lsof -t -i:" + port + ")";

        executeRuntimeCommand( command );

    }

    private static void executeRuntimeCommand(String command) {

        Process process = null;

        try {

            process = Runtime.getRuntime().exec( command );

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        BufferedReader reader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );

        String line = "";

        while ( true ) {

            try {

                if ( ( line = reader.readLine() ) == null ) break;

                else
                    readerList.add( line );

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
