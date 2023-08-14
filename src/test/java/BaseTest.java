import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.*;
import ru.yandex.practicum.constant.ButtonNameForLogin;
import ru.yandex.practicum.generator.UserGenerator;
import ru.yandex.practicum.user.User;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private static final String SITE = "https://stellarburgers.nomoreparties.site";
    protected WebDriver driver;
    private final UserGenerator userGenerator = new UserGenerator();
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected RegisterPage registerPage;
    protected RestorePasswordPage restorePasswordPage;
    protected UserPage userPage;
    protected User user;

    @Before
    public void create() {
        driver = new ChromeDriver();
        driver.get(SITE);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        restorePasswordPage = new RestorePasswordPage(driver);
        userPage = new UserPage(driver);
        createUser();
    }

    @After
    public void delete() {
        driver.quit();
        deleteUser();
    }

    private void createUser() {
        user = userGenerator.getUser();
        given().contentType(ContentType.JSON)
                .body(user)
                .post(SITE + "/api/auth/register");
    }

    private void deleteUser() {
        String accessToken = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(SITE + "/api/auth/login")
                .body().path("accessToken");
        given().contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(user).delete(SITE + "/api/auth/user");
    }

    public void quiteButton(ButtonNameForLogin buttonName) {
        switch (buttonName) {
            case LOGIN_ON_HOME_PAGE:
                homePage.clickLoginButton();
                break;
            case LOGIN_ON_LK:
                homePage.clickLk();
                break;
            case LOGIN_ON_REGISTER_PAGE:
                homePage.clickLk();
                loginPage.clickRegister();
                registerPage.clickLogin();
                break;
            case LOGIN_ON_RECOVERY_PASSWORD:
                homePage.clickLk();
                loginPage.clickRestorePasswordButton();
                restorePasswordPage.clickLoginButton();
                break;
        }
    }
}
