package com.automation.steps;

import com.automation.utils.AppiumServer;
import com.automation.utils.ReusableMethods;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Driver {
    private static AppiumDriver appDriver;
    private static final ReusableMethods reusableMethods = new ReusableMethods();
    private static Properties properties;

    public static AppiumDriver getDriver(String platform) throws MalformedURLException {
        if (appDriver == null) {
            appDriver = initializeDriver(platform);
        }
        return appDriver;
    }

    private static AppiumDriver initializeDriver(String platform) throws MalformedURLException {

        properties = ReusableMethods.loadProperties();
        AppiumServer.startServer();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if ("android".equalsIgnoreCase(platform)) {
            desiredCapabilities.setCapability("platformName", properties.getProperty("platformName"));
            desiredCapabilities.setCapability("appium:platformVersion", properties.getProperty("platformVersion"));
            desiredCapabilities.setCapability("appium:deviceName", properties.getProperty("deviceName"));
            desiredCapabilities.setCapability("appium:automationName", properties.getProperty("automationName"));
            desiredCapabilities.setCapability("appium:appPackage", properties.getProperty("appPackage"));
            desiredCapabilities.setCapability("appium:appActivity", properties.getProperty("appActivity"));
            desiredCapabilities.setCapability("appium:app", properties.getProperty("app"));
            return new AndroidDriver(new URL(properties.getProperty("appiumServerURL")), desiredCapabilities);
        }
        throw new IllegalArgumentException("Unsupported platform: " + platform);
    }

    public static void quitDriver() {
        if (appDriver != null) {
            appDriver.quit();
            appDriver = null;
        }
    }
}
