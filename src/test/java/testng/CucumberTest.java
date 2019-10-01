package testng;

import cucumber.api.testng.FeatureResultListener;
import cucumber.api.testng.TestNgReporter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.Formatter;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CucumberTest {

    private Runtime runtime;
    private RuntimeOptions runtimeOptions;
    private ResourceLoader resourceLoader;
    private FeatureResultListener resultListener;
    private ClassLoader classLoader = this.getClass().getClassLoader();

    public CucumberTest(List<String> options) {

        this.resourceLoader = new MultiLoader( this.classLoader );
        this.runtimeOptions = new RuntimeOptions( options );

        new TestNgReporter( System.out );

        ClassFinder classFinder     =   new ResourceLoaderClassFinder( this.resourceLoader, this.classLoader );
        this.resultListener         =   new FeatureResultListener( this.runtimeOptions.reporter( this.classLoader ), this.runtimeOptions.isStrict() );
        this.runtime                =   new Runtime( this.resourceLoader, classFinder, this.classLoader, this.runtimeOptions );
    }

    @BeforeClass(
            alwaysRun = true
    )
    public void setUpLogger( ITestContext ctx ) {

    }

    @AfterClass
    public void tearDownLogger() {

    }

    @Test
    public void testFeature() throws Throwable {

        List<CucumberFeature> features = this.runtimeOptions.cucumberFeatures( this.resourceLoader );

        if ( !features.isEmpty() ) {

            CucumberFeature cucumberFeature = features.get( 0 );

            this.resultListener.startFeature();

            cucumberFeature.run( this.runtimeOptions.formatter( this.classLoader ), this.resultListener, this.runtime );

            if ( !this.resultListener.isPassed() )
                throw this.resultListener.getFirstError();

        }

    }

    @AfterClass(
            alwaysRun = true
    )
    public void tearDownClass() {

        Formatter formatter = this.runtimeOptions.formatter( this.classLoader );

        formatter.done();
        formatter.close();

        this.runtime.printSummary();

    }

}
