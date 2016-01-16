package com.demo.POM.pages
import com.demo.POM.BasePageObject
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

public class QuestionsPage extends BasePageObject {
	@FindBy(css=".youarehere #nav-questions")
	WebElement youAreHere;
	
	@FindBy(id="nav-users")
	List<WebElement> usersTab;

	public QuestionsPage(WebDriver driver) {
		super(driver);
	}

	@Override
    protected By getUniqueElement() {
        return By.cssSelector(".youarehere #nav-questions");
    }

    public Boolean isUsersTabDisplayed() {
        return usersTab.size() > 0;
    }

}
