package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class AllFunctions {

    public static ChromeDriver driver;
    public static WebDriverWait wait;

    public static void signIn(String username, String password) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.xpath("//button[@data-testid='auth-continue-button']")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("password")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-testid='auth-login-button']")).click();
        //    wait.until(ExpectedConditions.visibilityOf("You’ve been signed in, welcome back!"))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ui3089 sc-jrQzAO cxgmiJ s-t7xdks jXNaWT']"))); //Отображается кнопка нотификаций
    }

    public static void shoppingTab() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='sc-bBHHxi gnkMdA']")))); //Ждем пока текст об успешном логине перестанет отображаться
        driver.findElement(By.xpath("//a[@href='/shopping-list/']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), "Shopping List"));
    }

    public static void createList(String listname) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@data-testid='create-new-shopping-list-button']")))); //Проверяем видимость кнопки
        driver.findElement(By.xpath("//a[@data-testid='create-new-shopping-list-button']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("name")))); //Проверяем видимость поля

        // driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE)); //очищаем поле ввода

        driver.findElement(By.name("name")).sendKeys(listname);
        driver.findElement(By.xpath("//button[@data-testid='create-new-shopping-list-create-button']")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), listname));
    }

    static SecureRandom random = new SecureRandom();

    public static String generateString() {
        return new BigInteger(60, random).toString(32);
    }

    public static void addItem(List<String> shoplist) {
        List<String> shopList = shoplist;
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@data-testid='desktop-add-item-autocomplete']")))); //Проверяем видимость поля ввода
        driver.findElement(By.xpath("//input[@data-testid='desktop-add-item-autocomplete']")).click();

        for(int i=0; i < shopList.size(); i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")))); //Проверяем видимость поля ввода
            driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")).click();
        }
    }

    public static void checkItem(List<String> shoplist) {
        List<String> shopList = shoplist;
        driver.findElement(By.xpath("//div[@data-testid='shopping-list-name']")).click(); //Нажать на название списка для закрытия выпадающего списка

        for(int i=0; i < shopList.size(); i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + shopList.get(i) + "']")))); //Проверяем видимость
        }
    }

    public static void deleteList(String listname) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@data-testid='shopping-list-name']"), listname));
        driver.findElement(By.xpath("//*[text()='" + listname + "']")).click(); //Выбрать список
        driver.findElement(By.xpath("//button[@data-testid='vertical-dots-shopping-list-button']")).click(); //открыть контекстное меню
        driver.findElement(By.xpath("//button[@data-testid='shopping-list-delete-menu-button']")).click(); //delete list
        driver.findElement(By.xpath("//button[@data-testid='confirm-delete-button']")).click(); //confirm delete
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='sc-gKclnd hZvGiH']"), "List was deleted"));
    }

    public static boolean checkMyList(String listname) {
        try {
            driver.findElement(By.xpath("//div[contains(@class='s-41yarc iYCgSR'" + listname + "]"));
            System.out.println("Список " + listname + " найден");
        }
        catch (Exception exception) {
            System.out.println("Списка нет");
            return false;
        }
        return true;
    }

}
