package testdroid.ios;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.ios.IOSDriver;
import testdroid.BaseTest;

import java.io.IOException;
import java.net.URL;


public class BaseIOSTest extends BaseTest{
	
	@Override
    protected String getTargetAppPath(){
		return testdroidProperties.getProperty("ios_td_applicationPath");
    }
    
    @Override
    protected void setAppiumDriver() throws IOException {    	
        this.wd = new IOSDriver(new URL(testdroidProperties.getProperty("testdroidServer")+"/wd/hub"), capabilities);
    }

    @Override
	protected void setDesiredCapabilities(String fileUUID) {
    	DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", testdroidProperties.getProperty("iosPlatfromName"));
        capabilities.setCapability("device", testdroidProperties.getProperty("iosDevice"));
        capabilities.setCapability("deviceName", testdroidProperties.getProperty("iosDeviceName"));
        capabilities.setCapability("app", testdroidProperties.getProperty("iosApp"));
        
        capabilities.setCapability("testdroid_apiKey", testdroidProperties.getProperty("apiKey"));
        capabilities.setCapability("testdroid_target", testdroidProperties.getProperty("ios_td_target"));
        capabilities.setCapability("testdroid_project", testdroidProperties.getProperty("ios_td_project"));
        capabilities.setCapability("testdroid_testrun", testdroidProperties.getProperty("ios_td_testRun"));
        capabilities.setCapability("testdroid_device", testdroidProperties.getProperty("ios_td_device"));
        capabilities.setCapability("testdroid_app", fileUUID); //to use existing app using "latest" as fileUUID
        capabilities.setCapability("testdroid_description", testdroidProperties.getProperty("ios_td_description"));
        
        System.out.println("Capabilities:" + capabilities.toString());
        this.capabilities = capabilities;
	}
    
    @Override
    protected void setServerSideDesiredCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", testdroidProperties.getProperty("iosPlatfromName"));
        capabilities.setCapability("deviceName", testdroidProperties.getProperty("iosDeviceName"));
        capabilities.setCapability("app", System.getProperty("user.dir")+"/application.ipa");
        System.out.println("Capabilities:" + capabilities.toString());
        this.capabilities = capabilities; 
	}
 
}



