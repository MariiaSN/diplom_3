package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserPage {
    private final WebDriver driver;
    private final By profileButton = By.xpath(".//*[text()='Профиль']");
    private final By allUserFields = By.xpath(".//*[@class='text input__textfield text_type_main-default input__textfield-disabled']");

    public UserPage(WebDriver driver) {
        this.driver = driver;
    }

    public UserPage waitLoadingPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> (driver.findElement(profileButton).isEnabled()
        ));
        return this;
    }

    public String getUserName() {
        return driver.findElements(allUserFields).get(0).getAttribute("value");
    }

    public String getUserLogin() {
        return driver.findElements(allUserFields).get(1).getAttribute("value");
    }
}
