package pages.singletone;

import core.BasePage;
import io.qameta.allure.Allure;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    public LoginPage(WebDriver driver) {
        Allure.step("Open URL");
        driver.get("https://www.saucedemo.com/");
        PageFactory.initElements(driver, this);
    }

    public MainPage fillLoginForm(String username, String password) {
        Allure.step("Fill login form with creds: " + username + " " + password);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password, Keys.ENTER);

        return new MainPage(driver);
    }

}
