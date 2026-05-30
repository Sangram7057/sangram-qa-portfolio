package com.sangram.qa.pages;

import com.sangram.qa.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By signInButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/login");
    }

    public void login(String username, String password) {
        waitHelper.visible(usernameField).sendKeys(username);
        waitHelper.visible(passwordField).sendKeys(password);
        waitHelper.clickable(signInButton).click();
    }

    public boolean signInVisible() {
        return waitHelper.visible(signInButton).isDisplayed();
    }
}
