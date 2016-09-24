package testdroid.ios;

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


public class IOSCucumberTestDefs extends BaseIOSTest{

    @Given("^I am at the main page$")
    public void I_am_at_the_main_page() throws Throwable {
        waitUntilElementDisplayed("What is the best way to test your application against over one hundred devices?");
    }

    @When("^I click to use testdroid cloud$")
    public void I_click_to_use_testdroid_cloud() throws Throwable {
        wd.findElement(By.name("answer2")).click();
    }

    @And("^I insert my name to proceed$")
    public void I_insert_my_name_to_proceed() throws Throwable {
    	WebElement el = wd.findElementByName("userName");
    	el.click();
    	el.sendKeys("John Testdroid");
    	wd.findElement(By.name("Return")).click();
    }

    @Then("^I can submit my answer$")
    public void I_can_submit_my_answer() throws Throwable {
    	WebElement el = wd.findElement(By.name("sendAnswer"));
    	el.click();
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



