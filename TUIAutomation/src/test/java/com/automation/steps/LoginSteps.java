package com.automation.steps;

import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;

import java.io.FileReader;
import java.net.MalformedURLException;

public class LoginSteps {

    private LoginPage loginPage;
    private JSONObject testData;

    public LoginSteps() throws MalformedURLException {
        loginPage = new LoginPage(Driver.getDriver("Android"));

        // Load test data from JSON
        try {
            FileReader reader = new FileReader("src/test/resources/LoginData.json");
            JSONTokener tokener = new JSONTokener(reader);
            testData = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to load test data: " + e.getMessage());
        }
    }

    @Given("User launches the app")
    public void user_launches_the_app() {
        Assert.assertNotNull("App is not launched", loginPage);
    }

    @When("User enters valid credentials")
    public void user_enters_valid_credentials() {
        // Get test data from JSON
        String username = testData.getJSONObject("validLogin").getString("username");
        String password = testData.getJSONObject("validLogin").getString("password");
        String dob = testData.getJSONObject("validLogin").getString("datOfBirth");

        // Enter credentials including DOB selection
        loginPage.enterCredentials(username, password, dob);
    }
}
