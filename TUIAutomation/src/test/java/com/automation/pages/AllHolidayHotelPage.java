package com.automation.pages;

import com.automation.utils.ReusableMethods;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AllHolidayHotelPage {
    private final AppiumDriver driver;
    private final ReusableMethods reusableMethods;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"All\")")
    private WebElement dashboard;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Hotels\")")
    private WebElement hotelsTab;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Holidays\")")
    private WebElement holidaysTab;

    public AllHolidayHotelPage(AppiumDriver driver) {
        this.driver = driver;
        this.reusableMethods = new ReusableMethods();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isDashboardDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOf(dashboard)).isDisplayed();
    }

    public boolean isHotelsTabDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOf(hotelsTab)).isDisplayed();
    }

    public boolean isHolidaysTabDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOf(holidaysTab)).isDisplayed();
    }

    public void navigateToHotels() {
        getWait().until(ExpectedConditions.elementToBeClickable(hotelsTab)).click();
    }

    public void navigateToHolidays() {
        getWait().until(ExpectedConditions.elementToBeClickable(holidaysTab)).click();
    }

    private void verifyItemDetails(JSONObject item, int index, boolean isAll) {
        String type = item.getString("type");
        String expectedTitle = type + ": " + item.getString("name");
        if(!isAll){
            expectedTitle = item.getString("name");
        }
        String expectedDescription = item.getString("description");
        String expectedBoardType = item.getString("boardType");

        if (reusableMethods.findElementByDynamicResourceId("content_card_board_type_" + index, driver)) {
            WebElement actualTitle = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"content_card_hotel_name_" + index + "\")"));
            WebElement actualDescription = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"content_card_destination_" + index + "\")"));
            WebElement actualBoardType = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"content_card_board_type_" + index + "\")"));
            Assert.assertEquals(expectedTitle, actualTitle.getText());
            Assert.assertEquals(expectedDescription, actualDescription.getText());
            Assert.assertEquals(expectedBoardType, actualBoardType.getText());
        }
    }

    public void allItems() {
        JSONArray items = ReusableMethods.getAllItems();
        for (int i = 0; i < items.length(); i++) {
            verifyItemDetails(items.getJSONObject(i), i,true);
        }
    }

    public void allHotelItems() {
        JSONArray items = ReusableMethods.getAllItems();
        int index = 0;
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if ("Hotel".equals(item.getString("type"))) {
                verifyItemDetails(item, index,false);
                index++;
            }
        }
    }

    public void allHolidayItems() {
        JSONArray items = ReusableMethods.getAllItems();
        int index = 0;
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if ("Holiday".equals(item.getString("type"))) {
                verifyItemDetails(item, index,false);
                index++;
            }
        }
    }
}
