package com.automation.pages;

import com.automation.utils.ReusableMethods;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"username_input_field\")")
    private WebElement usernameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"password_input_field\")")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"date_of_birth_field_calendar_icon\")")
    private WebElement dobCalendarIcon;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"login_form_submit_button\")")
    private WebElement loginButton;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void enterCredentials(String username, String password, String dateOfBirth) {
        // Enter username and password
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);

        // Open the date picker and select the date
        wait.until(ExpectedConditions.elementToBeClickable(dobCalendarIcon)).click();
        selectDate(dateOfBirth);

        // Click the login button
        loginButton.click();
    }

    private void selectDate(String date) {
        // Select the specified date
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + ReusableMethods.getDate(date) + "\")")
        ));
        dateElement.click();

        // Click Confirm to finalize the date selection
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Confirm\")")
        ));
        confirmButton.click();
    }

    public boolean isLoginButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
