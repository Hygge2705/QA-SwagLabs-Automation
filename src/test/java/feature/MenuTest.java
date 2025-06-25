package feature;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Hook;
import action.LoginPage;
import action.InventoryPage;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;


public class MenuTest extends Hook {
    LoginPage loginPage;
    InventoryPage inventoryPage;
    WebDriverWait wait;

    @BeforeMethod
    public void preToTest(){
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        //Truy cập vào trang web và đăng nhập
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("standard_user", "secret_sauce");
        loginPage.clickLoginButton();
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void checkMenuActive(){
        Assert.assertTrue(inventoryPage.isMenuActive(),"Menu is not active");
    }

    @Test
    public void testAllItemsOption(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("inventory_sidebar_link"))));
        driver.findElement(By.id("inventory_sidebar_link")).click();
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");
    }

    @Test
    public void testAboutOption(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("about_sidebar_link"))));
        driver.findElement(By.id("about_sidebar_link")).click();
        Assert.assertEquals(driver.getTitle(), "Sauce Labs: Cross Browser Testing, Selenium Testing & Mobile Testing", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/", "URL mismatch!");
    }


    @Test
    public void testLogOutOption(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout_sidebar_link"))));
        driver.findElement(By.id("logout_sidebar_link")).click();
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");
    }

    @Test
    public void testResetAppState(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("reset_sidebar_link"))));
        driver.findElement(By.id("reset_sidebar_link")).click();
        Assert.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty(),"Reset App State is not active.");
        Assert.assertTrue(driver.findElements(By.cssSelector("button[data-test^='remove']")).isEmpty(),"UI not updating by reset option");
    }
}
