package com.automation.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.util.Properties;

public class AppiumServer {
    private static AppiumDriverLocalService service;
    private static Properties properties;

    public static void startServer() {
        properties = ReusableMethods.loadProperties();
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(System.getProperty("user.home") + "/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
        System.out.println("Appium server started.");
    }

    public static void stopServer() {
        if (service != null) {
            service.stop();
            System.out.println("Appium server stopped.");
        }
    }
}
