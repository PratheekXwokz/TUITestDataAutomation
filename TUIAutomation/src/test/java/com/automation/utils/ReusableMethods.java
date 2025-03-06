package com.automation.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ReusableMethods {

    private static Properties properties;
    private static final String JSON_FILE_PATH = "src/test/resources/HolidayandHotels.json";
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    /**
     * Formats the input date from "dd/MM/yyyy" to "EEEE, MMMM d, yyyy".
     */
    public static String getDate(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        return LocalDate.parse(inputDate, inputFormatter).format(outputFormatter);
    }

    /**
     * Loads configuration properties from the specified file.
     * Uses a singleton pattern to avoid redundant loading.
     */
    public static Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config.properties file.", e);
            }
        }
        return properties;
    }

    /**
     * Retrieves all items from the JSON file based on the "All List" tab.
     */
    public static JSONArray getAllItems() {
        try (InputStream is = new FileInputStream(JSON_FILE_PATH)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(is));
            JSONArray tabs = jsonObject.getJSONArray("tabs");

            for (Object tabObj : tabs) {
                JSONObject tab = (JSONObject) tabObj;
                if ("All List".equalsIgnoreCase(tab.getString("name"))) {
                    return tab.getJSONArray("items");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON file: " + JSON_FILE_PATH, e);
        }
        return new JSONArray();
    }

    /**
     * Scrolls to and verifies if an element with the dynamic resource ID is visible.
     */
    public boolean findElementByDynamicResourceId(String baseResourceId, AppiumDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Perform scrolling and attempt to find the element
            WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                            + "new UiSelector().resourceId(\"" + baseResourceId + "\"))"
            ));
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
