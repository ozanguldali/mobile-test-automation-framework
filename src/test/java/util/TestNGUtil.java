package util;

import testng.CucumberTest;

import java.util.ArrayList;

public class TestNGUtil {

    public static CucumberTest getCucumberTest(String featureFile, String tags, boolean isRerun) {

        ArrayList< String > options = new ArrayList<>();

        String feature;

        if ( isRerun )
            feature = featureFile.replaceFirst( ".rerun", "" );
        else {

            feature = featureFile.replaceFirst( ".feature$", "" );

            String[] splittedTags = tags.split( "&" );

            for ( String tag : splittedTags ) {

                if ( tag.length() > 0 ) {

                    options.add( "-t" );
                    options.add( tag );

                }

            }

        }

        options.add( "-s" );
        options.add( "-m" );
        options.add( "-g" );
        options.add( "src/test/resources/features/" );
        options.add( "-g" );
        options.add( "step" );
        options.add( "-p" );
        options.add( "html:target/reports/" + feature );
        options.add( "-p" );
        options.add( "html:target/site/cucumber-pretty/" + feature );
//        options.add( "-p" );
//        options.add( "json:target/reports/json/" + feature + ".json" );
        options.add( "-p" );
        options.add( "json:target/site/cucumber-files/" + feature + ".json" );
//        options.add( "-p" );
//        options.add( "json:target/site/cucumber-jvm-reports/" + feature + ".json" );
//        options.add( "-p" );
//        options.add( "pretty:src/test/resources/reports/" + feature + "/" + feature + ".txt" );
//        options.add( "-p" );
//        options.add( "junit:src/test/resources/reports/" + feature + "/" + feature + ".xml" );
        options.add( "-p" );
        options.add( "rerun:build/rerun/" + feature + ".rerun" );

        if ( isRerun )
            options.add("@build/rerun/" + feature + ".rerun");
        else
            options.add( "src/test/resources/features/" + featureFile );

        return new CucumberTest( options );

    }

}
