package feature;

import action.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Hook;

public class LoginPageTest extends Hook {
    LoginPage loginPage;

    @BeforeMethod
    public void setupPage(){
        driver.get("https://www.saucedemo.com");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginSuccessfully(){
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin đúng
        loginPage.inputLogin("standard_user", "secret_sauce");
        loginPage.clickLoginButton();

        // Xác nhận vào trang inventory
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        // Kiểm tra logo & sản phẩm
        loginPage.isLogoDisplayed();
        loginPage.getInventoryCount();
    }

    @Test
    public void testLoginWithEmptyUsername(){
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin: bỏ trống username
        loginPage.inputLogin("", "secret_sauce");
        loginPage.clickLoginButton();
        // Xác nhận báo lỗi
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Should stay on login page!");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match") || loginPage.getErrorMessage().contains("Epic sadface"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithEmptyPassword(){
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin: bỏ trống password
        loginPage.inputLogin("standard_user", "");
        loginPage.clickLoginButton();

        // Xác nhận báo lỗi
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Should stay on login page!");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match") || loginPage.getErrorMessage().contains("Epic sadface"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithInvalidUsername(){
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin: bỏ trống username
        loginPage.inputLogin("standard_user1", "secret_sauce");
        loginPage.clickLoginButton();
        // Xác nhận báo lỗi
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Should stay on login page!");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match") || loginPage.getErrorMessage().contains("Epic sadface"), "Unexpected error message!");
    }

    @Test
    public void testLoginWithInvalidPassword(){
        // Kiểm tra tiêu đề và URL
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        // Điền thông tin: bỏ trống password
        loginPage.inputLogin("standard_user", "secret_sauce1");
        loginPage.clickLoginButton();

        // Xác nhận báo lỗi
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Should stay on login page!");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match") || loginPage.getErrorMessage().contains("Epic sadface"), "Unexpected error message!");
    }
}
