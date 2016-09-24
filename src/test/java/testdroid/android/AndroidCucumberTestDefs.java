package testdroid.android;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;


public class AndroidCucumberTestDefs extends BaseAndroidTest{

    @Given("^I am at the main page$")
    public void I_am_on_add_user_page() throws Throwable {
        waitUntilElementDisplayed("What is the best way to test your application against over one hundred devices?");
    }

    @When("^I click to use testdroid cloud$")
    public void I_click_to_add_a_contact() throws Throwable {
        WebElement el = wd.findElement(By.name("Use Testdroid Cloud"));
        el.click();
    }

    @And("^I insert my name to proceed$")
    public void I_complete_the_details() throws Throwable {
    	WebElement el = wd.findElement(By.xpath("//android.widget.EditText[@resource-id='com.bitbar.testdroid:id/editText1']"));
    	el.sendKeys("John Testdroid");
    }

    @Then("^I can submit my answer$")
    public void I_can_save_a_new_contact() throws Throwable {
    	wd.hideKeyboard();
        wd.findElement(By.name("Answer")).click();
        takeScreenshot("answer");
        waitUntilElementDisplayed("You are right!");
    }

    public void waitUntilElementDisplayed(String element){
        WebDriverWait wait = new WebDriverWait(wd, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
    }

    @Before
    public void setUp() throws IOException {
    	setUpTest();
	}
    
    @After
    public void tearDown()
    {
    	quitAppiumSession();
    }
}



