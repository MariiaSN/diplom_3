import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonalAreaTest extends BaseTest {

    @Test
    public void personalAreaButtonWithAuthUser() {
        homePage.waitLoadHeader();
        homePage.clickLk();
        loginPage.waitLoadHeader()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .clickLogin();
        homePage.waitLoadHeader();

        homePage.clickLk();
        userPage.waitLoadingPage();

        assertEquals(user.getName(), userPage.getUserName());
        assertEquals(user.getEmail(), userPage.getUserLogin());
    }
}
