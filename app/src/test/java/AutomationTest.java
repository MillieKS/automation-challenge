import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

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


        WebElement ToDoList = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector(".view > label")));


        assertTrue(ToDoList.getText().contains("item 1"));

        takeScreenshot(driver, "add_todo.png");

    }

    @Test
    void InsertEmptyToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");
        Thread.sleep(5000);

//        here we call addToDoItem
        toDoElements.addToDoItem("");

        // Check if the todo list element is absent or has no todo items
        List<WebElement> toDoList = driver.findElements(By.cssSelector(".view > label"));
        assertEquals(0, toDoList.size(), "Todo List element still present");

        takeScreenshot(driver, "empty_todo.png");


    }

    @Test
    void AddSingleCharacterToDoItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("A");


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

    @Test
    void CheckStatusBarIsEmpty() throws Exception {
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

        takeScreenshot(driver, "click_first_toggle.png");

        WebElement ClearCompleted = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector(".clear-completed")));

        ClearCompleted.click();

        // Check if the todo count element is absent or has no text
        List<WebElement> toDoCount = driver.findElements(By.className("todo-count"));
        assertEquals(0, toDoCount.size(), "Todo count element still present");


    }

    @Test
    void ToggleBetweenActiveCompletedAndAll() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");
        toDoElements.addToDoItem("item 2");


//        we click toggle to mark all as completed

        WebElement AllCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.linkText("All")));

        AllCategory.click();
        takeScreenshot(driver, "click_All_category.png");

        String allCategoryURL = driver.getCurrentUrl();
        assertTrue(allCategoryURL.contains("https://todomvc.com/examples/react/#/"));

        takeScreenshot(driver, "click_All_category.png");


        WebElement ActiveCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.linkText("Active")));

        ActiveCategory.click();
        takeScreenshot(driver, "click_Active_category.png");

        String activeCategoryURL = driver.getCurrentUrl();
        assertTrue(activeCategoryURL.contains("https://todomvc.com/examples/react/#/active"));


        WebElement CompletedCategory = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.linkText("Completed")));

        CompletedCategory.click();
        takeScreenshot(driver, "click_Completed_category.png");

        String completedCategoryURL = driver.getCurrentUrl();
        assertTrue(completedCategoryURL.contains("https://todomvc.com/examples/react/#/completed"));

    }

    @Test
    void ClearCompletedLinkAppearsWhenThereAreCompletedItem() throws Exception {
//        here we import from the ToDoElements Class
        ToDoElements toDoElements = new ToDoElements(driver);

        driver.get("https://todomvc.com/examples/react/#/");

//        here we call addToDoItem
        toDoElements.addToDoItem("item 1");


//        we click to mark 1 item completed
//        li:nth-child(1) .toggle
        WebElement FirstItemToggle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(By.cssSelector("li:nth-child(1) .toggle")));

        FirstItemToggle.click();

//        locate the Clear Completed link
        WebElement ClearCompletedLink = driver.findElement(By.cssSelector(".clear-completed"));

//        assert the Clear Completed Link is live
        assertNotNull(ClearCompletedLink);
        takeScreenshot(driver, "clear_completed_link_appears.png");

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
