import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ToDoElements {
    protected WebDriver driver;
    private By ToDoBarBy = By.cssSelector(".new-todo");
    private By ToDoListBy = By.cssSelector(".view > label");

    private By CompletedToggleBy = By.cssSelector(".toggle");

    private By ClearCompletedBy = By.className("clear-completed");




    public ToDoElements(WebDriver driver) {
        this.driver = driver;
    }

    public void addToDoItem(String ToDoTerm) {
        WebElement ToDoBar = driver.findElement(ToDoBarBy);
        ToDoBar.sendKeys(ToDoTerm);
        ToDoBar.sendKeys(Keys.ENTER);
    }

//    public void ToDoList() {
//        WebElement ToDoList = driver.findElement(ToDoListBy);
//        ToDoList.click();
//    }



    public boolean clickToggle() {
        WebElement completedToggle = driver.findElement(CompletedToggleBy);
        completedToggle.click();
        return completedToggle.isSelected();
    }

    public boolean ClearCompletedItems() {
        WebElement ClearCompleted = driver.findElement(ClearCompletedBy);
        ClearCompleted.click();
        return ClearCompleted.isSelected();
    }
}
