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
				"junit:target/TEST-all.xml"
				}
		)
public class RunTestAndroid {

}
