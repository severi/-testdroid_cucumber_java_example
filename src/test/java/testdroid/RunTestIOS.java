package testdroid;

import cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(
		monochrome = false,
		dryRun = false,
		features = "src/test/resources/testdroid/ios/",
		glue     ={"testdroid.ios"},
		format = {"pretty", 
				"html:target/cucumber-html-report", 
				"json-pretty:target/cucumber-report.json"
				}
		)
public class RunTestIOS {

}
