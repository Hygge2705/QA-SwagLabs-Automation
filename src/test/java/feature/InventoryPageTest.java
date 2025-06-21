package feature;

import action.InventoryPage;
import action.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.Hook;

public class InventoryPageTest extends Hook {
    InventoryPage inventoryPage;
    LoginPage loginPage;

    @BeforeMethod
    public void preToTest(){
        //Truy cập vào trang web và đăng nhập
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("standard_user", "secret_sauce");
        loginPage.clickLoginButton();

        //Khởi tạo đối tượng page inventory
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void checkInventoryPageUI(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        Assert.assertTrue(inventoryPage.isLogoDisplayed(),"Logo is not display!");
        Assert.assertTrue(inventoryPage.isMenuActive(),"Menu is not active");
        Assert.assertEquals(inventoryPage.getInventoryCount(),6,"Number of products is not enough.");
        Assert.assertTrue(inventoryPage.isShoppingCartDisplayed(),"ShoppingCart is not display!");
        Assert.assertTrue(inventoryPage.isSortByActive(),"SortBy is not active");
    }

    @Test
    public void checkSortedByNameAZ(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickToSortByNameAZ();
        Assert.assertTrue(inventoryPage.isSortedByNameAZ(), "Product list not sorted from A-Z.");
    }

    @Test
    public void checkSortedByNameZA(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickToSortByNameZA();
        Assert.assertTrue(inventoryPage.isSortedByNameZA(), "Product list not sorted from A-Z.");
    }

    @Test
    public void checkSortByPriceLow(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickToSortByPriceLow();
        Assert.assertTrue(inventoryPage.isSortedByPriceLowToHigh(), "Product list not sorted from A-Z.");
    }

    @Test
    public void checkSortByPriceHigh(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickToSortByPriceHigh();
        Assert.assertTrue(inventoryPage.isSortedByPriceHighToLow(), "Product list not sorted from A-Z.");
    }

    @Test
    public void checkAddToCart(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        inventoryPage.clickAddToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(inventoryPage.checkNumOfCart(), "2", "Number of products in Shopping Cart is incorrect!");
        inventoryPage.clickShoppingCart();
    }

    @Test
    public void checkRemoveButton(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        inventoryPage.clickAddToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(inventoryPage.checkNumOfCart(), "2", "Number of products in Shopping Cart is incorrect!");

        inventoryPage.clickRemove("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(inventoryPage.checkNumOfCart(), "1", "Number of products in Shopping Cart is incorrect!");

    }

}
