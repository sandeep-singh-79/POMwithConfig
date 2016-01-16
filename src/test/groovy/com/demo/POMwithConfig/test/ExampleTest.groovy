package com.demo.POMwithConfig.test

import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.annotations.Test

import com.demo.POM.BaseTest
import com.demo.POM.pages.HomePage
import com.demo.POM.pages.QuestionsPage

class ExampleTest extends BaseTest{
  
	ExampleTest() {
        super()
    }

    @Test
    public void clickQuestionsTest() {
        HomePage landingPage = PageFactory.initElements(this.driver, HomePage.class)
        QuestionsPage questionsPage = landingPage.clickQuestionsTab()
        Assert.assertTrue(questionsPage.isUsersTabDisplayed())
    }

    @Test
    public void isLogoDisplayedTest() {
        HomePage landingPage = PageFactory.initElements(this.driver, HomePage.class)
        Assert.assertTrue(landingPage.isQuestionsTabDisplayed())
    }
}
