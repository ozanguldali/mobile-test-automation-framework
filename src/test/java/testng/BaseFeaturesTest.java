package testng;

import org.testng.annotations.Factory;
import runner.AppiumServerRunner.emulatorRunner;
import runner.AppiumServerRunner.open;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static helper.TestNGHelper.getAllFiles;
import static util.TestNGUtil.cleanRerunBaseDir;
import static util.TestNGUtil.getCucumberTest;

public class BaseFeaturesTest {

    private static final String FEATURE_DIR     =   "src/test/resources/features/";
    private static final String RERUN_DIR       =   "build/rerun";
    private static String tags                  =   System.getProperty( "tag", "~@ignore" );
    // "@tag1,@tag2" == tag1 || tag2
    // "@tag1&@tag2" == tag1 && tag2

    public BaseFeaturesTest() throws IOException {

        cleanRerunBaseDir( RERUN_DIR );

//        String[] args = new String[ 0 ];

        //emulatorRunner.main( args );

//        open.main( args );

    }

    @Factory
    public Object[] createTest() {

        String[] featureFiles = getAllFiles( FEATURE_DIR, new LinkedList<>() );

        return Arrays.stream( featureFiles ).map( this::createCucumberTest ).toArray();

    }

    private CucumberTest createCucumberTest(String featureFile) {

        return getCucumberTest( FEATURE_DIR, featureFile, tags );

    }

}
