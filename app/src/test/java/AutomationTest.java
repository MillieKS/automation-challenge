import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationTest {
    private static ChromeDriver driver;


    @BeforeEach
    void launchBrowser() {
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
        assertTrue(currentURL.contains("https://todomvc.com/examples/react/"));

    }

    @Test
    void AddOneToDoItem() throws Exception {

//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");

//        WebElement ToDoBar = new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(driver -> driver.findElement(By.cssSelector(".new-todo")));
//        ToDoBar.sendKeys("item 1");
//        ToDoBar.sendKeys(Keys.ENTER);

        WebElement ToDoList = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector(".view > label")));
//                driver.findElement(By.cssSelector(".view > label"));
//        Thread.sleep(10000);
        assertTrue(ToDoList.getText().contains("item 1"));

        takeScreenshot(driver, "add_todo.png");

    }
//    Can't add a todo item if the input is empty
    @Test
    void InsertEmptyToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");
        Thread.sleep(5000);

//        here we call addToDoItem
        toDoElements.addToDoItem("");

//        WebElement ToDoBar = new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(driver -> driver.findElement(By.cssSelector(".new-todo")));
////                driver.findElement(By.cssSelector(".new-todo"));
//
//        ToDoBar.sendKeys("");
//        ToDoBar.sendKeys(Keys.ENTER);

        // WebElement ToDoList = driver.findElement(By.cssSelector(".view > label"));
        // assertFalse("TodoList exists", ToDoList != null);
        takeScreenshot(driver, "empty_todo.png");


    }

    @Test
    void AddSingleCharacterToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("A");

//        WebElement ToDoBar = new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(driver -> driver.findElement(By.cssSelector(".new-todo")));
//        ToDoBar.sendKeys("A");
//        ToDoBar.sendKeys(Keys.ENTER);

        WebElement ToDoList = driver.findElement(By.cssSelector(".view > label"));
        assertTrue(ToDoList.getText().contains("A"));

        takeScreenshot(driver, "add_single_character_todo.png");


    }

    @Test
    void AddAccentedCharacterToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("à");

        WebElement ToDoList = driver.findElement(By.cssSelector(".view > label"));
        assertTrue(ToDoList.getText().contains("à"));

        takeScreenshot(driver, "add_accented_character_todo.png");


    }

//    @Test
//    void AddEmojiToDoItem() throws Exception {
////        here we import from the ToDoElements Class
//        ToDoElements toDoElements = new ToDoElements(driver);
//
//        driver.get("https://todomvc.com/examples/react/#/");
//
////        here we call addToDoItem
//        toDoElements.addToDoItem("\uD83D\uDE01");
//
//        WebElement ToDoList = driver.findElement(By.cssSelector(".view > label"));
//        assertTrue(ToDoList.getText().contains("\uD83D\uDE01"));
//
//        takeScreenshot(driver, "add_emoji_todo.png");
//
//
//    }

    @Test
    void UntickCompletedToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");


//        we click toggle to mark as completed
        toDoElements.clickToggle();
        takeScreenshot(driver, "first_click_toggle.png");
        

//        click toggle again to mark as incomplete
        toDoElements.clickToggle();
        takeScreenshot(driver, "second_unclick_toggle.png");

        assertTrue(toDoElements.clickToggle());



    }

    @Test
    void DeleteToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");
        toDoElements.addToDoItem("item 2");


//        we click toggle to mark as completed
//        li:nth-child(1) .toggle

        WebElement FirstItemToggle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector("li:nth-child(1) .toggle")));

        FirstItemToggle.click();

        takeScreenshot(driver, "click_first_toggle.png");



        WebElement ClearCompleted = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector(".clear-completed")));

        ClearCompleted.click();

        WebElement ToDoList = driver.findElement(By.cssSelector(".view > label"));
        assertFalse(ToDoList.getText().contains("item 1"));

        takeScreenshot(driver, "clear_completed.png");


//        clear the completed
//        toDoElements.ClearCompletedItems();
//        takeScreenshot(driver, "clear_completed.png");
//
//        assertTrue(toDoElements.ClearCompletedItems());

    }

    @Test
    void CompleteAllToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");
        toDoElements.addToDoItem("item 2");

//        we click toggle to mark all as completed

        WebElement AllToggleItems = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector(".main > label")));

        AllToggleItems.click();

        WebElement ToDoCount = driver.findElement(By.className("todo-count"));

        assertTrue(ToDoCount.getText().contains("0 items left"));
        takeScreenshot(driver, "0_items_left.png");

    }

    @Test
    void OneItemLeft() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");
        toDoElements.addToDoItem("item 2");

//        we click to mark 1 item completed
//        li:nth-child(1) .toggle
        WebElement FirstItemToggle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector("li:nth-child(1) .toggle")));

        FirstItemToggle.click();



        WebElement ToDoCount = driver.findElement(By.className("todo-count"));

        assertTrue(ToDoCount.getText().contains("1 item left"));
        takeScreenshot(driver, "1_item_left.png");

    }

    @Test
    void TwoItemsLeft() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");
        toDoElements.addToDoItem("item 2");
        toDoElements.addToDoItem("item 3");

//        we click to mark 1 item completed
//        li:nth-child(1) .toggle
        WebElement FirstItemToggle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector("li:nth-child(1) .toggle")));

        FirstItemToggle.click();

        WebElement ToDoCount = driver.findElement(By.className("todo-count"));

        assertTrue(ToDoCount.getText().contains("2 items left"));
        takeScreenshot(driver, "2_items_left.png");

    }







    //    for taking screenshots
    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception{
        TakesScreenshot screenshot = ((TakesScreenshot)webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }

    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}
