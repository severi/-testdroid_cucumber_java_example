package testdroid;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, dryRun = false, features = "src/test/resources/testdroid/android/", glue = {
		"testdroid.android" }, plugin = { "pretty", "junit:target/TEST-all.xml" })
public class RunTestAndroid {

}
