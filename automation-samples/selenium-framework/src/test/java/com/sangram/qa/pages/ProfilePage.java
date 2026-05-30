package com.sangram.qa.pages;

import com.sangram.qa.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    private final By title = By.cssSelector("[data-testid='profile-title']");

    public ProfilePage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/profile");
    }

    public String titleText() {
        return waitHelper.visible(title).getText();
    }
}
