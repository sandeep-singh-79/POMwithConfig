package com.demo.POMwithConfig

import org.openqa.selenium.Platform
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
    private REMOTE_DRIVER = true

	protected static def driver
    protected static def config = FrameworkConfig.getConfig()

    private def browser
	
	@BeforeMethod (alwaysRun = true)
	public void beforeMethod() {
		driver.manage().window().maximize()
		driver.get(config.url)
	}

	@AfterMethod (alwaysRun = true)
	public void afterMethod() {
		driver.manage().deleteAllCookies()
	}

	@BeforeClass (alwaysRun = true)
	public void beforeClass() throws MalformedURLException {
        setUpDriver("local")

		driver.manage().timeouts().implicitlyWait(config.IMPLICITWAIT_TIMEOUT, TimeUnit.SECONDS)
	}

    private void setUpDriver(String driverType) {
        if (driverType.equals("remote")) {
            setupRemoteDriver()
            driver.setFileDetector(new LocalFileDetector())
        } else {
            setupLocalDriver()
        }
    }

    @AfterClass (alwaysRun = true)
	public void afterClass() {
		driver.quit()
	}

	private void setupLocalDriver() {
		String path = ""
        browser = config.seleniumConfigs.local.browser
		if (browser.equals("firefox")) {
			driver = new FirefoxDriver()
		} else if (browser.equals("chrome")) {
			path = "lib/chromedriver"
			if (System.getProperty("os.name").contains("Windows")) {
				path = "lib/chromedriver.exe"
			}
			System.setProperty("webdriver.chrome.driver", path)
			driver = new ChromeDriver()
		} else if (browser.equals("internetExplorer")) {
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
        def hostAddress = config.seleniumConfigs.remote.ip
        def hostPort = config.seleniumConfigs.remote.port
        def platform = config.seleniumConfigs.remote.platform
        def version = config.seleniumConfigs.remote.version

        browser = config.seleniumConfigs.remote.browser
		DesiredCapabilities capabilities
		if (browser.equals("firefox")) {
			capabilities = DesiredCapabilities.firefox()
		} else if (browser.equals("internetExplorer")) {
			capabilities = DesiredCapabilities.internetExplorer()
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
		} else if (browser.equals("chrome")) {
			capabilities = DesiredCapabilities.chrome()
		} else {
			throw new RuntimeException("Browser type unsupported")
		}

        capabilities.setVersion(version)
        capabilities.setPlatform(Platform.fromString(platform))
		driver = new RemoteWebDriver(
				new URL("http://${hostAddress}:${hostPort}/wd/hub"), capabilities)
	}
}
