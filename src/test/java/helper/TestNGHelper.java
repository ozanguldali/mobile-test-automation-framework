package helper;

import org.junit.Assert;

import java.io.File;
import java.util.List;

import static util.PropertiesUtil.SLASH;
import static util.LoggingUtil.LOGGER;

public class TestNGHelper {

//    private static final String SLASH = System.getProperty( "file.separator" );

    public static String[] getAllFiles(String dir, List<String> fileList) {

        File directory = new File( dir );

        File[] fileArray = directory.listFiles();

        if ( fileArray != null ) {

            for ( File file : fileArray ) {

                if ( file.isFile() ) {

                    String fileName = file.getName();

                    if ( ( dir + file.getName() ).equals( file.getPath() ) ) {

                        fileList.add( fileName );

                    } else {

                        String[] fileDirArr = file.getAbsolutePath().split( SLASH );
                        fileList.add( fileDirArr[ fileDirArr.length - 2 ] + SLASH + fileName );

                    }

                } else if ( file.isDirectory() ) {

                    getAllFiles( file.getPath(), fileList );

                }

            }

        } else {

            LOGGER.fatal( "No Feature Files under the [" + dir + "]" );
            Assert.fail( "No Feature Files under the [" + dir + "]" );

        }

        if ( fileList.isEmpty() ) {

            LOGGER.fatal( "No Feature Files under the [" + dir + "]" );
            Assert.fail( "No Feature Files under the [" + dir + "]" );

        }

        return fileList.toArray( new String[ fileList.size() ] );


    }

}
