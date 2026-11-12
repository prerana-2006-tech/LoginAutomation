
package com.login.test;

import java.nio.file.Path;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {

        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);

        String filePath = Path.of(
            "src/main/resources/login.html"
        ).toAbsolutePath().toUri().toString();

        driver.get(filePath);
    }

    @Test
    public void validLoginTest() {

        driver.findElement(By.id("username"))
              .sendKeys("admin");

        driver.findElement(By.id("password"))
              .sendKeys("password123");

        driver.findElement(By.id("loginButton"))
              .click();

        String actualMessage = new WebDriverWait(
            driver, Duration.ofSeconds(5)
        ).until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("message")
            )
        ).getText();

        Assert.assertEquals(
            actualMessage,
            "Login Successful!"
        );

        System.out.println("Valid Login Test Passed!");
    }

    @Test
    public void invalidLoginTest() {

        driver.findElement(By.id("username"))
              .sendKeys("wronguser");

        driver.findElement(By.id("password"))
              .sendKeys("wrongpassword");

        driver.findElement(By.id("loginButton"))
              .click();

        String actualMessage = new WebDriverWait(
            driver, Duration.ofSeconds(5)
        ).until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("message")
            )
        ).getText();

        Assert.assertEquals(
            actualMessage,
            "Error: Invalid username or password"
        );

        System.out.println("Invalid Login Test Passed!");
    }

    @AfterMethod
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
        }
    }
}
