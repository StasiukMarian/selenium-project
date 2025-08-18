package pages.singletone;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends BasePage {
    @FindBy(xpath = "//div[@class='inventory_item_name ']")
    List<WebElement> headers;

    public MainPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public List<String> getElementsHeaders(){
        return headers.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
