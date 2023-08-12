import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.HomePage;
import ru.yandex.practicum.LoginPage;
import ru.yandex.practicum.RegisterPage;
import ru.yandex.practicum.generator.UserGenerator;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    public static final String INCORRECT_PASSWORD_EXCEPTION = "Некорректный пароль";
    private final UserGenerator userGenerator = new UserGenerator();
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private WebDriver driver;
    private final String site = "https://stellarburgers.nomoreparties.site/";
    private final String expectedUrl = "https://stellarburgers.nomoreparties.site/login";
    private String actual;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(site);

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    @DisplayName("Корректная регистрация")
    public void createCorrectUser() {
        toRegisterPage();
        registerPage.setName(userGenerator.getName())
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getValidPassword())
                .clickRegisterButton();

        loginPage.waitLoadHeader();

        actual = driver.getCurrentUrl();

        assertEquals(expectedUrl, actual);
    }

    @Test
    @DisplayName("Регистрация с некорректным паролем (< 6 символов)")
    public void createUserIncorrectPassword() {
        toRegisterPage();
        registerPage.setName(userGenerator.getName())
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getInvalidPassword())
                .clickRegisterButton();

        actual = registerPage.getTextException();

        assertEquals(INCORRECT_PASSWORD_EXCEPTION, actual);
    }

    @Step("Переход на форму регистрации")
    private void toRegisterPage() {
        homePage.clickLk();
        loginPage.clickRegister();
    }
}
