package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllFunctions {

    private static ChromeDriver driver;
    private static WebDriverWait wait;

    public static void signIn(ChromeDriver driver, WebDriverWait wait) {
        AllFunctions.driver = driver;
        AllFunctions.wait = wait;
        driver.findElement(By.name("username")).sendKeys("testwhisk0@gmail.com");
        driver.findElement(By.xpath("//button[@data-testid='auth-continue-button']")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("password")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("testwhisk0pas");
        driver.findElement(By.xpath("//button[@data-testid='auth-login-button']")).click();
        //    wait.until(ExpectedConditions.visibilityOf("You’ve been signed in, welcome back!"))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ui3089 sc-jrQzAO cxgmiJ s-t7xdks jXNaWT']"))); //Отображается кнопка нотификаций
    }

    public static void openShoppingTab(ChromeDriver driver, WebDriverWait wait) {
        AllFunctions.driver = driver;
        AllFunctions.wait = wait;
    }
}
