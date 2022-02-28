package allTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;


//import static resources.AllFunctions.signIn;

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
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

/*    @Test
    public void test1() {
        driver.get(startPage);
        signIn();
        shoppingTab();
        createList(generateString());
        addItem();
        checkItem();
       // driver.quit();
    }
*/
    @Test
    public void test2() {
        String list;
        list = generateString();

        driver.get(startPage);
        signIn();
        shoppingTab();
        createList(list);
        deleteList(list);
        //checkMyList(list);
        // driver.quit();
    }

    public void signIn() {
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.xpath("//button[@data-testid='auth-continue-button']")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("password")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-testid='auth-login-button']")).click();
    //    wait.until(ExpectedConditions.visibilityOf("You’ve been signed in, welcome back!"))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ui3089 sc-jrQzAO cxgmiJ s-t7xdks jXNaWT']"))); //Отображается кнопка нотификаций
    }

    public void shoppingTab() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='sc-bBHHxi gnkMdA']")))); //Ждем пока текст об успешном логине перестанет отображаться
        driver.findElement(By.xpath("//a[@href='/shopping-list/']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), "Shopping List"));
    }

    public void createList(String listname) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@data-testid='create-new-shopping-list-button']")))); //Проверяем видимость кнопки
        driver.findElement(By.xpath("//a[@data-testid='create-new-shopping-list-button']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("name")))); //Проверяем видимость поля

        // driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE)); //очищаем поле ввода

        driver.findElement(By.name("name")).sendKeys(listname);
        driver.findElement(By.xpath("//button[@data-testid='create-new-shopping-list-create-button']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), listname));
    }

    public void addItem() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@data-testid='desktop-add-item-autocomplete']")))); //Проверяем видимость поля ввода
        driver.findElement(By.xpath("//input[@data-testid='desktop-add-item-autocomplete']")).click();

        for(int i=0; i < shopList.size(); i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")))); //Проверяем видимость поля ввода
            driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")).click();
        }
    }

    public void checkItem() {
        driver.findElement(By.xpath("//div[@data-testid='shopping-list-name']")).click(); //Нажать на название списка для закрытия выпадающего списка

        for(int i=0; i < shopList.size(); i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")))); //Проверяем видимость
        }
    }

    public void deleteList(String listname) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), listname));
        driver.findElement(By.xpath("//*[text()='" + listname + "']")).click(); //Выбрать список
        driver.findElement(By.xpath("//button[@data-testid='vertical-dots-shopping-list-button']")).click(); //открыть контекстное меню
        driver.findElement(By.xpath("//button[@data-testid='shopping-list-delete-menu-button']")).click(); //delete list
        driver.findElement(By.xpath("//button[@data-testid='confirm-delete-button']")).click(); //confirm delete
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='sc-gKclnd hZvGiH']"), "List was deleted"));
    }
/*
    from selenium.common.exceptions import NoSuchElementException
    def check_exists_by_xpath(xpath):
            try:
            webdriver.find_element_by_xpath(xpath)
    except NoSuchElementException:
            return False
    return True
 */
 /*   public boolean checkMyList(String listname) {
        //<div class="s-41yarc iYCgSR">
        try {
            driver.findElement(By.xpath("//div[@class='s-41yarc iYCgSR']"));
//wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='sc-gKclnd hZvGiH']"), "List was deleted"));
//XPath(Sign Up): //button[contains(@name, 'websubmit')]
        }
        catch (NoSuchElementException exception) {
            System.out.println("Списка нет");
            return false;
        }
        return true;

    }
*/
    /*@After
    public void closeBrowser() {
        driver.close();
    }
    */

    SecureRandom random = new SecureRandom();

    public String generateString() {
        return new BigInteger(60, random).toString(32);
    }




    /* @Test
    public void registerFree() {

        driver.get(startPage);
        /*element = driver.findElement(By.xpath("//a[@href='#!/register/free']"));
        element.click();
        basicInfo();
        username = generateString();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("passwordConfirmation")).sendKeys(password);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElementByClassName("load-container"))); //loading
        driver.findElement(By.xpath("//*[@ng-click=\"registerFree()\"]")).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElementByClassName("load-container"))); //loading
        driver.findElement(By.xpath("//a[contains(@href,'/signup')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#!/register/free']")));
*/
//    }

 /*
    public void signIn (String username, String password, String type, String typeDate) {

        try {
            driver.findElement(By.xpath("//a[contains(@href,'/signup/login')]")).click();  //Sign In from main page
        }
        catch (Exception e) {
        }

        driver.findElement(By.xpath("//input[@name=\"username\"]")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);

        if (type == "Domain")
            driver.findElement(By.id("promdfree")).click();

        driver.findElement(By.cssSelector("button.btn.btn-green")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("menuName"), username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '" + typeDate + "')]")));
    }
*/

}
