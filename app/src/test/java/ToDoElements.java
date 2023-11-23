import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ToDoElements {
    protected WebDriver driver;
    private By ToDoBarBy = By.cssSelector(".new-todo");
    private By ToDoListBy = By.cssSelector(".view > label");

    public ToDoElements(WebDriver driver) {
        this.driver = driver;
    }

    public void addToDoItem(String ToDoTerm) {
        WebElement ToDoBar = driver.findElement(ToDoBarBy);
        ToDoBar.sendKeys(ToDoTerm);
        ToDoBar.sendKeys(Keys.ENTER);
    }
}
