package tests.firstTest;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static pages.Pages.loginPage;

public class SecondTest extends BaseTest {

    @Test
    public void secondTest() {
        List<String> elementsHeaders = loginPage(driver)
                .fillLoginForm("standard_user", "secret_sauce")
                .getElementsHeaders();

        Assert.assertEquals(elementsHeaders.size(), 5);
    }

}

