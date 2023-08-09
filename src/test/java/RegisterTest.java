import io.github.bonigarcia.wdm.WebDriverManager;
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
    private UserGenerator userGenerator = new UserGenerator();
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
    public void createCorrectUser() {
        homePage.clickLk();
        loginPage.clickRegister();
        registerPage.setName(userGenerator.getName())
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getValidPassword())
                .clickRegisterButton();

        loginPage.waitLoadHeader();

        actual = driver.getCurrentUrl();

        assertEquals(expectedUrl, actual);
    }

    @Test
    public void createUserIncorrectPassword() {
        homePage.clickLk();
        loginPage.clickRegister();
        registerPage.setName(userGenerator.getName())
                .setEmail(userGenerator.getEmail())
                .setPassword(userGenerator.getInvalidPassword())
                .clickRegisterButton();

        actual = registerPage.getTextException();

        assertEquals(INCORRECT_PASSWORD_EXCEPTION, actual);
    }
}
