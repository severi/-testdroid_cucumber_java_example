package testdroid;

import cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(
		monochrome = true,
		dryRun = false,
		features = "src/test/resources/testdroid/android/",
		glue     ={"testdroid.android"},
		format = {"pretty", 
				"html:target/cucumber-html-report", 
				"json-pretty:target/cucumber-report.json"
				}
		)
public class RunTestAndroid {

}
