package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import testng.BaseFeaturesTest;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome  =   true,
        strict      =   true
)

public class MainTestRunner extends BaseFeaturesTest {

    public MainTestRunner() throws IOException {}

}
