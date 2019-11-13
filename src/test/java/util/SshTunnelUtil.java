package util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.Assert;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import static util.EnvironmentUtil.*;
import static util.LoggingUtil.LOGGER;

public class SshTunnelUtil {

   /* private static Session session = null;
    private static Channel channel = null;

    public static void openTunnel() {

        String localIP = "127.0.0.1";

        try {

            localIP = InetAddress.getLocalHost().getHostAddress();

            LOGGER.info( "\tThe local machine IP is: [" + localIP + "]\t\n" );

        } catch (Exception e) {

            LOGGER.warn( "\tcould NOT be read the local machine IP\t\n" );
            Assert.fail( "\tcould NOT be read the local machine IP\t\n" );

        }

        if ( localIP.equals( SSH_HOST ) ) {

            LOGGER.info( "\tThe local machine IP and server IP are equal, " +
                    "thus there is NO need to open port forwarding tunnel\t\n" );

        } else {

            JSch jSch = new JSch();

            try {

                jSch.addIdentity( PROJECT_DIR + SLASH + "src" + SLASH + "test" + SLASH +
                        "resources" + SLASH + "config" + SLASH + "key-bases" + SLASH + KEY_FILE + "_id_rsa" );

                LOGGER.info( String.format( "\tThe identity [%s] is successfully added.\t\n", KEY_FILE ) );

            } catch (Exception e) {

                LOGGER.info( String.format( "\tThe identity <private_key> could NOT be added, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe identity <private_key> could NOT be added, because { error: [%s] }\t\n", e.getMessage() ) );

            }

            try {

                session = jSch.getSession( SSH_USER, SSH_HOST );

                LOGGER.info( String.format( "\tThe session is created with user: [%s] and host: [%s]\t\n", SSH_USER, SSH_HOST) );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe session could NOT be created, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe session could NOT be created, because { error: [%s] }\t\n", e.getMessage() ) );

            }

            java.util.Properties config = new java.util.Properties();
            config.put( "StrictHostKeyChecking", "no" );
            session.setConfig( config );

            try {

                session.connect();

                LOGGER.trace( "\tThe session is connected.\t\n" );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe session could NOT be connected, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe session could NOT be connected, because { error: [%s] }\t\n", e.getMessage() ) );

            }

            try {

                int port = Integer.parseInt( SSH_PORT );

                while ( !isPortAvailable( port ) && port <= 65535 ) {

                    port = port + 1;
                    LOGGER.warn( String.format( "\tThe new port is: [%d], BUT this may cause some troubles on database connections...\t\n", port ) );

                }

                session.setPortForwardingL( port, SSH_HOST, port );

//                SSH_PORT = String.valueOf( port );

                LOGGER.trace( String.format( "\tPort Forwarding is set to the tunnel for [%s] @ [%s] : [%d]\t\n", SSH_USER, SSH_HOST, port ) );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tPort Forwarding could NOT be set to the tunnel, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tPort Forwarding could NOT be set to the tunnel, because { error: [%s] }\t\n", e.getMessage() ) );

            }

            try {

                channel = session.openChannel( "direct-tcpip" );
                LOGGER.trace( "\tThe tunnel channel is successfully opened.\t\n" );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe tunnel channel could NOT be opened, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe tunnel channel could NOT be opened, because { error: [%s] }\t\n", e.getMessage() ) );

            }

        }

    }

    public static void closeTunnel() {

        if ( channel.isConnected() ) {

            try {

                channel.disconnect();
                LOGGER.trace( "\tThe tunnel channel is successfully closed.\t\n" );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe tunnel channel could NOT be closed, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe tunnel channel could NOT be closed, because { error: [%s] }\t\n", e.getMessage() ) );

            }

        }

        if ( session.isConnected() ) {

            try {


                session.disconnect();
                LOGGER.trace( "\tThe session is disconnected.\t\n" );

            } catch (Exception e) {

                LOGGER.warn( String.format( "\tThe session could NOT be disconnected, because { error: [%s] }\t\n", e.getMessage() ) );
                Assert.fail( String.format( "\tThe session could NOT be disconnected, because { error: [%s] }\t\n", e.getMessage() ) );

            }

        }



    }

    private static boolean isPortAvailable(int port) {

        try ( ServerSocket serverSocket = new ServerSocket() ) {

            if ( OS_VALUE.toLowerCase().contains( "mac" ) )
                serverSocket.setReuseAddress( false );

            serverSocket.bind( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ), 1 );
            LOGGER.info( String.format( "\tThe port is available: [%d]\t\n", port ) );
            return true;

        } catch (Exception e) {

            LOGGER.warn( String.format( "\tThe port is NOT available: [%d]\t\n", port ) );

//            try {
//
//                if ( serverSocket != null ) {
//
//                    serverSocket.close();
//                    LOGGER.info( String.format( "\tThe port is closed: [%d]\t\n", port ) );
//                    isPortAvailable( port );
//
//                }
//
//            } catch (Exception ex) {
//
//                ex.printStackTrace();
//
//            }

            return false;

        }

    }
*/
}
