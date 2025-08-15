package pages;

import org.openqa.selenium.WebDriver;
import pages.singletone.LoginPage;
import pages.singletone.MainPage;

public class Pages {
    private static LoginPage loginPage;
    private static MainPage mainPage;

    public static LoginPage loginPage(WebDriver driver) {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public static MainPage mainPage(WebDriver driver) {
        if (mainPage == null) {
            mainPage = new MainPage(driver);
        }
        return mainPage;
    }
}
