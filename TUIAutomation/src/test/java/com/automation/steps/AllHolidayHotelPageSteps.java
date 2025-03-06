package com.automation.steps;

import com.automation.pages.AllHolidayHotelPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.net.MalformedURLException;

public class AllHolidayHotelPageSteps {

    private final AllHolidayHotelPage allHolidayHotelPage;

    public AllHolidayHotelPageSteps() throws MalformedURLException {
        allHolidayHotelPage = new AllHolidayHotelPage(Driver.getDriver("Android"));
    }

    @Then("validates all available items with relevant details like name, description, and price")
    public void theListShouldDisplayRelevantDetails() {
        allHolidayHotelPage.allItems();
    }

    @Then("validates all holiday destination, duration, and pricing")
    public void eachItemShouldDisplayHolidayDetails() {
        allHolidayHotelPage.allHolidayItems();
    }

    @Then("validates all hotel names, locations, and ratings")
    public void theHotelListShouldShowHotelDetails() {
        allHolidayHotelPage.allHotelItems();
    }

    @Then("Validate home screen is loaded")
    public void validate_home_screen_is_loaded() {
        Assert.assertTrue("Home Screen displayed successfully!", allHolidayHotelPage.isDashboardDisplayed());
    }

    @Given("User is on the home screen")
    public void user_is_on_the_home_screen() {
        Assert.assertTrue("User is on the home screen.", allHolidayHotelPage.isDashboardDisplayed());
    }

    @When("User selects {string}")
    public void user_selects_option(String option) {
        switch (option.toLowerCase()) {
            case "hotels":
                allHolidayHotelPage.navigateToHotels();
                break;
            case "holidays":
                allHolidayHotelPage.navigateToHolidays();
                break;
            default:
                Assert.fail("Unknown option: " + option);
        }
    }

    @Then("Validate {string} screen is loaded")
    public void validate_screen_is_loaded(String screenName) {
        switch (screenName.toLowerCase()) {
            case "hotels":
                Assert.assertTrue("Hotels screen displayed successfully!", allHolidayHotelPage.isHotelsTabDisplayed());
                break;
            case "holidays":
                Assert.assertTrue("Holidays screen displayed successfully!", allHolidayHotelPage.isHolidaysTabDisplayed());
                break;
            default:
                Assert.fail("Unknown screen: " + screenName);
        }
    }
}
