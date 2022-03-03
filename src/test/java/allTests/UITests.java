package allTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.AllFunctions;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;

public class UITests {
    public static ChromeDriver driver;
    WebElement element;
    String username = "testwhisk0@gmail.com";
    String password = "testwhisk0pas";
    String startPage = "https://my.whisk-dev.com";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    List<String> shopList = Arrays.asList("Milk", "Bread", "Eggs", "Potato", "Onion");


    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();

        try {
            driver = new ChromeDriver();
        }
        catch (NoSuchSessionException exception) {
            driver = new ChromeDriver();
        }

     //   driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        AllFunctions.driver = driver;

    }

   @After
    public void closeBrowser() {
        driver.close();
    }


    @Test
    public void test1() {

        driver.get(startPage);
        AllFunctions.signIn(username, password);
        AllFunctions.shoppingTab();
        AllFunctions.createList(AllFunctions.generateString());
        AllFunctions.addItem(shopList);
        AllFunctions.checkItem(shopList);
    }

    @Test
    public void test2() {
        String list;
        list = AllFunctions.generateString();

        try {
            driver.get(startPage);
        }
        catch (NoSuchSessionException exception) {
            driver = new ChromeDriver();
            driver.get(startPage);
        }

        //driver.get(startPage);
        AllFunctions.signIn(username, password);
        AllFunctions.shoppingTab();
        AllFunctions.createList(list);
        AllFunctions.deleteList(list);
        AllFunctions.checkMyList(list);
    }

}
