package com.demo.POM
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert

abstract class BasePageObject {
	protected WebDriver driver;
	protected WebDriverWait wait;

	BasePageObject(WebDriver driver) {
		this.driver = driver
		wait = new WebDriverWait(this.driver,30,10)
		
		isLoaded()
	}
	
	/**
	 * Each page object must implement this method to return the identifier of a unique WebElement on that page.
	 * The presence of this unique element will be used to assert that the expected page has finished loading
	 * @return the By locator of unique element on the page
	 */
	protected abstract By getUniqueElement();
	
	protected def isLoaded() throws Error{
		//Define a list of WebElements that match the unique element locator for the page
        List<WebElement> uniqueElement = driver.findElements(getUniqueElement())

        // Assert that the unique element is present in the DOM
        Assert.assertTrue((uniqueElement.size() > 0),
				"Unique Element \'${getUniqueElement().toString()}\' not found for ${this.class.simpleName}")

        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        wait.until(ExpectedConditions.visibilityOfElementLocated(getUniqueElement()))
	}

}
