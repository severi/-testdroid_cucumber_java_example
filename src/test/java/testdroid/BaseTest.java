package testdroid;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import com.testdroid.api.http.MultipartFormDataContent;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

public abstract class BaseTest {
	private static final String serverSideTypeDefinition = "serverside";
	private static final String clientSideTypeDefinition = "clientside";

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	protected static AppiumDriver wd;
	private static int counter;
	protected DesiredCapabilities capabilities;
	protected Properties testdroidProperties;

	protected abstract void setDesiredCapabilities(String fileUUID) throws MalformedURLException;

	protected abstract void setServerSideDesiredCapabilities();

	protected abstract void setAppiumDriver() throws IOException;

	protected abstract String getTargetAppPath();

	public void fetchProperties() {
		testdroidProperties = new Properties();
		InputStream input = null;
		try {
			String filename = "testdroid.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}
			testdroidProperties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isServerSideTestRun() {
		return testdroidProperties.get("propertiesType").equals(serverSideTypeDefinition);
	}

	public boolean isClientSideTestRun() {
		return testdroidProperties.get("propertiesType").equals(clientSideTypeDefinition);
	}

	public void setUpAppiumDriver() throws IOException {
		if (isClientSideTestRun()) {
			String apiKey = testdroidProperties.getProperty("apiKey");
			String fileUUID = uploadFile(getTargetAppPath(), testdroidProperties.getProperty("testdroidServer"),
					apiKey);
			setDesiredCapabilities(fileUUID);
		} else if (isServerSideTestRun()) {
			setServerSideDesiredCapabilities();
		} else {
			throw new IllegalStateException(
					"Properties file does not define whether it is used for clientside or serverside test execution.");
		}
		System.out.println("Creating Appium session, this may take couple minutes..");
		setAppiumDriver();
	}

	protected void quitAppiumSession() {
		if (wd != null) {
			wd.quit();
		}
	}

	public void setUpTest() throws IOException {
		fetchProperties();
		setUpAppiumDriver();
	}

	protected static String uploadFile(String targetAppPath, String serverURL, String testdroid_apikey)
			throws IOException {
		final HttpHeaders headers = new HttpHeaders().setBasicAuthentication(testdroid_apikey, "");

		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(JSON_FACTORY));
				request.setHeaders(headers);
			}

		});
		MultipartFormDataContent multipartContent = new MultipartFormDataContent();
		FileContent fileContent = new FileContent("application/octet-stream", new File(targetAppPath));

		MultipartFormDataContent.Part filePart = new MultipartFormDataContent.Part("file", fileContent);
		multipartContent.addPart(filePart);

		HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(serverURL + "/upload"), multipartContent);

		HttpResponse response = request.execute();
		System.out.println("response:" + response.parseAsString());

		AppiumResponse appiumResponse = request.execute().parseAs(AppiumResponse.class);
		System.out.println("File id:" + appiumResponse.uploadStatus.fileInfo.file);

		return appiumResponse.uploadStatus.fileInfo.file;

	}

	protected void takeScreenshot(String screenshotName) {
		counter = counter + 1;
		String fullFileName = System.getProperty("user.dir") + "/screenshots/" + getScreenshotsCounter() + "_"
				+ screenshotName + ".png";

		screenshot(fullFileName);
	}

	private File screenshot(String name) {
		System.out.println("Taking screenshot...");
		File scrFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);

		try {

			File testScreenshot = new File(name);
			FileUtils.copyFile(scrFile, testScreenshot);
			System.out.println("Screenshot stored to " + testScreenshot.getAbsolutePath());

			return testScreenshot;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getScreenshotsCounter() {
		if (counter < 10) {
			return "0" + counter;
		} else {
			return String.valueOf(counter);
		}
	}

	public static class AppiumResponse {
		Integer status;
		@Key("sessionId")
		String sessionId;

		@Key("value")
		BaseTest.UploadStatus uploadStatus;

	}

	public static class UploadedFile {
		@Key("file")
		String file;
	}

	public static class UploadStatus {
		@Key("message")
		String message;
		@Key("uploadCount")
		Integer uploadCount;
		@Key("expiresIn")
		Integer expiresIn;
		@Key("uploads")
		BaseTest.UploadedFile fileInfo;
	}
}