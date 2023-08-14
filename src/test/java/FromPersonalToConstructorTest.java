import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.constant.ButtonNameForConstructor;

import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.constant.ButtonNameForConstructor.CONSTRUCTOR;
import static ru.yandex.practicum.constant.ButtonNameForConstructor.LOGO_STELLAR_BURGER;

@RunWith(Parameterized.class)
public class FromPersonalToConstructorTest extends BaseTest {
    private ButtonNameForConstructor buttonName;

    public FromPersonalToConstructorTest(ButtonNameForConstructor buttonName) {
        this.buttonName = buttonName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {CONSTRUCTOR},
                {LOGO_STELLAR_BURGER}
        };
    }

    @Test
    @DisplayName("Переход из ЛК в Конструктор")
    public void transitionToConstructorFromLk() {
        transitionToLk();

        userPage.waitLoadingPage()
                .changeButton(buttonName);

        String expectedUrl = "https://stellarburgers.nomoreparties.site/";

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Step("Переход в ЛК")
    private void transitionToLk() {
        homePage.clickLk();
        loginPage.waitLoadHeader()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .clickLogin();
        homePage.clickLk();
    }
}
