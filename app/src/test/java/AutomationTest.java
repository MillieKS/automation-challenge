import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomationTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // Your tests will go here!
    @Test
    public void ToDoHomepage() throws Exception {
        driver.get("https://todomvc.com/");

//        assert the page title is "TodoMVC"
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("TodoMVC"));

//        Assert that the page has a link which contains the text "React"
        WebElement ReactLink = driver.findElement(By.linkText("React"));
        ReactLink.click();

//        assert the current url is correct after clicking REACT link
        String currentURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/", currentURL);

    }

    @Test
    void AddOneToDoItem() throws Exception {
        driver.get("https://todomvc.com/examples/react/#/");
        WebElement ToDoBar = driver.findElement(By.cssSelector(".new-todo"));
        ToDoBar.sendKeys("item 1");
        ToDoBar.sendKeys(Keys.ENTER);

        takeScreenshot(driver, "add_todo.png");

    }






//    for taking screenshots
    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception{
        TakesScreenshot screenshot = ((TakesScreenshot)webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
}