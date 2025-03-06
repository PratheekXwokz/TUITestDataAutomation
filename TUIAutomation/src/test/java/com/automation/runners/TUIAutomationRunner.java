package com.automation.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/automation/features",
        glue = "com.automation.steps",
        tags = "@testingallholidayhoteltab",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TUIAutomationRunner {
}
