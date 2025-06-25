package feature;

import action.InventoryPage;
import action.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Hook;

import java.util.Objects;

import static utils.TestContext.selectedProducts;

public class InventoryPageTest extends Hook {
    InventoryPage inventoryPage;
    LoginPage loginPage;

    @BeforeMethod
    public void preToTest(){
        selectedProducts.clear();
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
        Assert.assertTrue(inventoryPage.isSortByActive(),"SortBy is not active");
        Assert.assertEquals(inventoryPage.getInventoryCount(),6,"Number of products is not enough.");
        Assert.assertTrue(inventoryPage.isFooterDisplayed());
        Assert.assertTrue(inventoryPage.isShoppingCartActive(),"ShoppingCart is not display!");
    }

    @Test
    public void getDetailsByName(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickProductName("Sauce Labs Backpack");
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory-item.html?id="));
    }

    @Test
    public void getDetailsByImage(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickProductImage("Sauce Labs Backpack");
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory-item.html?id="));
    }

    @Test
    public void checkBackButtonActive(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickProductImage("Sauce Labs Backpack");
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory-item.html?id="));

        inventoryPage.clickBackToProductsButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
    }

    @Test
    public void checkAddToCartFromDetailPage(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        inventoryPage.clickResetAppState();
        String productName = "Sauce Labs Backpack";
        inventoryPage.clickProductName(productName);
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory-item.html?id="));

        if (selectedProducts.containsKey(productName)){
            inventoryPage.clickRemove(productName);
            Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
            Assert.assertFalse(inventoryPage.isButtonAddToCart(productName), "Add to cart button for " + productName + " is not displayed!");
        }
        inventoryPage.clickAddToCart(productName);
        Assert.assertFalse(inventoryPage.isButtonAddToCart(productName));
        Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
    }

    @Test
    public void checkRemoveButtonFromDetailPage(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        String productName1 = "Sauce Labs Backpack";

        if(inventoryPage.isButtonAddToCart(productName1)){
            inventoryPage.clickAddToCart(productName1);
            Assert.assertFalse(inventoryPage.isButtonAddToCart(productName1), "Remove button for " + productName1 + " is not displayed!");
            Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
        }
        inventoryPage.clickRemove(productName1);
        Assert.assertTrue(inventoryPage.isButtonAddToCart(productName1));
        Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
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

        String productName = "Sauce Labs Backpack";

        if (!inventoryPage.isButtonAddToCart(productName)){
            inventoryPage.clickRemove(productName);
            Assert.assertTrue(inventoryPage.isButtonAddToCart(productName), "Add to cart button for " + productName + " is not displayed!");
            Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
        }
        inventoryPage.clickAddToCart(productName);
        Assert.assertFalse(inventoryPage.isButtonAddToCart(productName));
        Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
    }

    @Test
    public void checkRemoveButton(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "URL mismatch!");
        Assert.assertEquals(inventoryPage.getTitleInventoryPage(), "Products", "Title mismatch!");

        String productName1 = "Sauce Labs Backpack";

        if(inventoryPage.isButtonAddToCart(productName1)){
            inventoryPage.clickAddToCart(productName1);
            Assert.assertFalse(inventoryPage.isButtonAddToCart(productName1), "Remove button for " + productName1 + " is not displayed!");
            Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
        }
        inventoryPage.clickRemove(productName1);
        Assert.assertTrue(inventoryPage.isButtonAddToCart(productName1));
        Assert.assertEquals(inventoryPage.checkNumOfCart(), selectedProducts.size(), "Number of products in Shopping Cart is incorrect!");
    }

}
