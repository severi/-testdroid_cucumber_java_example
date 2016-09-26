package testdroid.android;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import testdroid.BaseTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseAndroidTest extends BaseTest{
	
    @Override
    protected String getTargetAppPath(){
    	return testdroidProperties.getProperty("android_td_applicationPath");
    }
    
    @Override
    protected void setAppiumDriver() throws IOException {    	
         this.wd = new AndroidDriver(new URL(testdroidProperties.getProperty("testdroidServer")+"/wd/hub"), capabilities);
    }
    
    @Override
	protected void setDesiredCapabilities(String fileUUID) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
         capabilities.setCapability("platformName", testdroidProperties.getProperty("androidPlatfromName"));
         capabilities.setCapability("deviceName", testdroidProperties.getProperty("androidDeviceName"));
         capabilities.setCapability("testdroid_apiKey", testdroidProperties.getProperty("apiKey"));
         capabilities.setCapability("testdroid_target", testdroidProperties.getProperty("android_td_target"));
         capabilities.setCapability("testdroid_project", testdroidProperties.getProperty("android_td_project"));
         capabilities.setCapability("testdroid_testrun", testdroidProperties.getProperty("android_td_testRun"));
         capabilities.setCapability("testdroid_device", testdroidProperties.getProperty("android_td_device"));
         capabilities.setCapability("testdroid_app", fileUUID);
         System.out.println("Capabilities:" + capabilities.toString());
         this.capabilities = capabilities;
	}
    
    @Override
    protected void setServerSideDesiredCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", testdroidProperties.getProperty("androidPlatfromName"));
        capabilities.setCapability("deviceName", testdroidProperties.getProperty("androidDeviceName"));
        capabilities.setCapability("app", System.getProperty("user.dir")+"/application.apk");
        capabilities.setCapability("appPackage", testdroidProperties.getProperty("androidAppPackage"));
        System.out.println("Capabilities:" + capabilities.toString());
        this.capabilities = capabilities;        
	}
}



