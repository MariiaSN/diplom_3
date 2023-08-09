package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final By homePageHeader = By.xpath(".//*[@class='active']");
    private final By buttonLk = By.xpath(".//p[text() = 'Личный Кабинет']");
    private final By buttonLogin = By.xpath(".//button[text()='Войти в аккаунт']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLk() {
        driver.findElement(buttonLk).click();
    }

    public void clickLoginButton() {
        driver.findElement(buttonLogin).click();
    }

    public void waitLoadHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> (driver.findElement(homePageHeader).isEnabled()
        ));
    }
}
