package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;


public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Headless режим для Docker/Jenkins
        options.addArguments("--no-sandbox");   // Не блокувати sandbox
        options.addArguments("--disable-dev-shm-usage"); // Уникнути помилок через маленький shared memory
        options.addArguments("--user-data-dir=/tmp/chrome_profile_" + System.currentTimeMillis()); // унікальний профіль
        options.addArguments("--window-size=1920,1080"); // Задаємо розмір вікна

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        BasePage.setDriver(driver);
    }


    @AfterMethod
    public void tearDown(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            // Створюємо скріншот
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // Додаємо в Allure
            Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
        }
        driver.close();
        driver.quit();
    }

}
