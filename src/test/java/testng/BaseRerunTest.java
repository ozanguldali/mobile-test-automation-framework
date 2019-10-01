package testng;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Factory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static util.BannerUtil.paintBanner;
import static util.EnvironmentUtil.SLASH;
import static util.SshTunnelUtil.openTunnel;

public class BaseRerunTest {
    private static final String RERUN_DIR = "build/rerun";

    public BaseRerunTest() {

        paintBanner();

        openTunnel();

    }


    @Factory
    public Object[] createTests() {

        String[] rerunFiles = ( new File( RERUN_DIR ) ).list( ( dir, name ) -> name.endsWith( ".rerun" ) );

        assert rerunFiles != null;

        for ( int i = 0; i < rerunFiles.length; i++ ) {

            if ( new File( RERUN_DIR + SLASH + rerunFiles[ i ] ).length() == 0 )
                ArrayUtils.remove( rerunFiles, i );

        }

        return Arrays.stream( rerunFiles ).map( this::createCucumberTest ).toArray();

    }

    private CucumberTest createCucumberTest(String rerunFile) {

        String feature = rerunFile.replaceFirst( ".rerun", "" );

        ArrayList< String > options = new ArrayList<>();

        options.add( "-s" );
        options.add( "-m" );
        options.add( "-g" );
        options.add( "src/test/resources/features/" );
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
        options.add("@build/rerun/" + feature + ".rerun");
        return new CucumberTest(options);

    }

}
