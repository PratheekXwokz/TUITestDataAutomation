package com.automation.steps;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;

import java.net.MalformedURLException;
public class Hooks {
    public  static AppiumDriver driver;

    public void setup(Scenario scenario) throws MalformedURLException {
        String platform=System.getProperty("platform","Android");
        driver= Driver.getDriver(platform);
        System.out.println("Test started on: " + platform);
    }

    public void tearDown(){
        Driver.quitDriver();
        System.out.println("Test Completed");
    }
}


