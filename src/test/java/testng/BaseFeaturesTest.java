package testng;

import org.testng.annotations.Factory;
import runner.AppiumServerRunner.emulatorRunner;
import runner.AppiumServerRunner.open;

import java.io.File;
import java.util.Arrays;

import static util.TestNGUtil.getCucumberTest;

public class BaseFeaturesTest {

    private static final String FEATURE_DIR  = "src/test/resources/features";
    private static String tags               = System.getProperty( "tag", "~@ignore" );

    public BaseFeaturesTest() {

        String[] args = new String[ 0 ];

        //emulatorRunner.main( args );

        open.main( args );

    }

    @Factory
    public Object[] createTest() {

        String[] featureFiles = ( new File( FEATURE_DIR ) ).list();

        assert featureFiles != null;
        return Arrays.stream( featureFiles ).map( this::createCucumberTest ).toArray();

    }

    private CucumberTest createCucumberTest(String featureFile) {

        return getCucumberTest( featureFile, tags, false );

    }

}
