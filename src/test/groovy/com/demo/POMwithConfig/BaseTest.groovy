package com.demo.POMwithConfig

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod

import java.util.concurrent.TimeUnit

class BaseTest {

	protected static final String WEB_SERVER = System.getProperty("WEB_SERVER", "http://stackoverflow.com/")
	protected static final String BROWSER = System.getProperty("BROWSER", "firefox")
	protected static final boolean REMOTE_DRIVER = Boolean.valueOf(System.getProperty("REMOTE_DRIVER", "false"))
	protected static final String SELENIUM_HOST = System.getProperty("SELENIUM_HOST", "localhost")
	protected static final int SELENIUM_PORT = Integer.valueOf(System.getProperty("SELENIUM_PORT", "4444"))

	public static RemoteWebDriver driver

	
	@BeforeMethod (alwaysRun = true)
	public void beforeMethod() {
		driver.manage().window().maximize()
		driver.get(WEB_SERVER)
	}

	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		driver.manage().deleteAllCookies()
	}

	@BeforeClass (alwaysRun = true)
	public void beforeClass() throws MalformedURLException {
		if (REMOTE_DRIVER) {
			setupRemoteDriver()
			driver.setFileDetector(new LocalFileDetector())
		} else {
			setupLocalDriver()
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
	}

	@AfterClass (alwaysRun = true)
	public void afterClass() {
		driver.quit()
	}

	private void setupLocalDriver() {
		String path = ""
		if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver()
		} else if (BROWSER.equals("chrome")) {
			path = "lib/chromedriver"
			if (System.getProperty("os.name").contains("Windows")) {
				path = "lib/chromedriver.exe"
			}
			System.setProperty("webdriver.chrome.driver", path)
			driver = new ChromeDriver()
		} else if (BROWSER.equals("internetExplorer")) {
			path = "lib/IEDriverServer.exe"
			System.setProperty("webdriver.ie.driver", path)
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer()
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
			driver = new InternetExplorerDriver(capabilities)
		} else {
			throw new RuntimeException("Browser type unsupported")
		}
	}

	private void setupRemoteDriver() throws MalformedURLException {
		DesiredCapabilities capabilities
		if (BROWSER.equals("firefox")) {
			capabilities = DesiredCapabilities.firefox()
		} else if (BROWSER.equals("internetExplorer")) {
			capabilities = DesiredCapabilities.internetExplorer()
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
		} else if (BROWSER.equals("chrome")) {
			capabilities = DesiredCapabilities.chrome()
		} else {
			throw new RuntimeException("Browser type unsupported")
		}
		driver = new RemoteWebDriver(
				new URL("http://" + SELENIUM_HOST + ":" + SELENIUM_PORT + "/wd/hub"),
				capabilities)
	}
}
