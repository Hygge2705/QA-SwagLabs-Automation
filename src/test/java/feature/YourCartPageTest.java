package feature;

import action.InventoryPage;
import action.LoginPage;
import action.YourCartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.Hook;

import static untils.TestContext.selectedProducts;

public class YourCartPageTest extends Hook {
    YourCartPage yourCartPage;
    InventoryPage inventoryPage;
    LoginPage loginPage;

    @BeforeMethod
    public void preToTest(){
        //Truy cập vào trang web và đăng nhập
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);

        //Khởi tạo đối tượng page inventory
        inventoryPage = new InventoryPage(driver);

        //Khởi tạo đối tượng yourCartPage
        yourCartPage = new YourCartPage(driver);

        loginPage.inputLogin("standard_user", "secret_sauce");
        loginPage.clickLoginButton();
        inventoryPage.clickShoppingCart();
    }


    @Test
    public void checkYourCartPageUI(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        Assert.assertTrue(yourCartPage.isAPPLogoDisplayed(),"Logo is not displayed!");
        Assert.assertTrue(yourCartPage.isMenuActive(),"Menu is not active");
        Assert.assertTrue(yourCartPage.isShoppingCartDisplayed(),"ShoppingCart is not displayed");
        Assert.assertTrue(yourCartPage.isLabelDisplayed(),"Label is not displayed");
        Assert.assertTrue(yourCartPage.isFooterDisplayed(), "Footer is not displayed");
        Assert.assertTrue(yourCartPage.isBackImageDisplayed(),"Back image is not displayed!");
        if (yourCartPage.checkNumOfCart() != 0 && yourCartPage.checkNumOfCart() == selectedProducts.size()){
            Assert.assertTrue(yourCartPage.isProductInfoEnough(),"Information of products is not enough.");
        }
    }

    @Test
    public void checkContinueButtonActive(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        yourCartPage.clickContinueButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html","Inventory Page is not display!");
    }

    @Test
    public void checkCheckoutButtonActive(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        yourCartPage.clickCheckoutButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-one.html","ShoppingCart is not display!");
    }

    @Test
    public void checkProductInCartAfterAdd(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        String productName = "Sauce Labs Backpack";
        //Add to cart
        yourCartPage.clickContinueButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html","Inventory Page is not display!");
        inventoryPage.clickAddToCart(productName);
        inventoryPage.clickShoppingCart();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertTrue(yourCartPage.isProductsInCartCorrect(selectedProducts), "Information of products in Shopping Cart is incorrect!");
    }

    @Test
    public void checkProductInCartAfterRemoveFromCart(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        String productName = "Sauce Labs Backpack";
        //Remove from cart
        yourCartPage.clickRemoveButton(productName);
        Assert.assertTrue(yourCartPage.isProductsInCartCorrect(selectedProducts), "Information of products in Shopping Cart is incorrect!");
    }

    @Test
    public void checkProductInCartAfterRemoveFromInventoryPage(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "URL mismatch!");
        Assert.assertEquals(yourCartPage.getTitleYourCartPage(), "Your Cart", "Title mismatch!");

        String productName = "Sauce Labs Backpack";

        //Remove from InventoryPage
        yourCartPage.clickContinueButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html","Inventory Page is not display!");
        if(inventoryPage.isButtonAddToCart(productName)){
            inventoryPage.clickAddToCart(productName);
            Assert.assertFalse(inventoryPage.isButtonAddToCart(productName), "Remove button for " + productName + " is not displayed!");
        }
        inventoryPage.clickRemove(productName);
        Assert.assertTrue(yourCartPage.isProductsInCartCorrect(selectedProducts), "Information of products in Shopping Cart is incorrect!");
    }
}
