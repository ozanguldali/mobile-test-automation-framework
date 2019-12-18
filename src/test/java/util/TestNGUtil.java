package util;

import testng.CucumberTest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static util.PropertiesUtil.PROJECT_DIR;
import static util.PropertiesUtil.SLASH;

public class TestNGUtil {

    public static CucumberTest getCucumberTest(String featureDir, String featureFile, String tags) {

        if ( featureFile.split( SLASH ).length > 1 ) {

            featureDir  = featureDir + featureFile.split( SLASH )[0] + SLASH;
            featureFile = featureFile.split( SLASH )[1];

        }

        String feature = featureFile.replaceFirst( ".feature$", "" );

        ArrayList< String > options = new ArrayList<>();

        String[] splittedTags = tags.split( "&" );

        for (String tag : splittedTags) {

            if (tag.length() > 0) {

                options.add( "-t" );
                options.add( tag );

            }

        }

        options.add( "-s" );
        options.add( "-m" );
        options.add( "-g" );
        options.add( featureDir );
        options.add( "-g" );
        options.add( "step" );
        //options.add( "-p" );
        //options.add( "html:src/test/resources/reports/" + feature );
        options.add( "-p" );
        options.add( "html:target/site/cucumber-pretty/" + feature );
        //options.add( "-p" );
        //options.add( "json:src/test/resources/reports/" + feature + "/" + feature + ".json" );
        options.add( "-p" );
        options.add( "json:target/site/cucumber-files/" + feature + ".json" );
        //options.add( "-p" );
        //options.add( "json:target/site/cucumber-jvm-reports/" + feature + ".json" );
        //options.add( "-p" );
        //options.add( "pretty:src/test/resources/reports/" + feature + "/" + feature + ".txt" );
        //options.add( "-p" );
        //options.add( "junit:src/test/resources/reports/" + feature + "/" + feature + ".xml" );
        options.add( "-p" );
        options.add( "rerun:build/rerun/" + feature + ".rerun" );
        options.add( featureDir + featureFile );

        return new CucumberTest( options );

    }

    public static void cleanRerunBaseDir(String rerunDir) throws IOException {

        String destination = PROJECT_DIR + SLASH + rerunDir;

        Path directory = Paths.get( destination );

        if ( Files.exists( directory ) ) {

            Files.walkFileTree( directory, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    Files.delete(path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
                    Files.delete(directory);
                    return FileVisitResult.CONTINUE;
                }

            } );

        }

    }

}
