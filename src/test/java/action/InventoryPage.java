package action;

import UI.InventoryPageUI;
import UI.YourCartPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import untils.Hook;

import java.time.Duration;
import java.util.*;

public class InventoryPage extends Hook {
    WebDriver driver;
    WebDriverWait wait;


    public InventoryPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

//1. Check UI
    public boolean isLogoDisplayed(){
        return driver.findElement(InventoryPageUI.APP_LOGO).isDisplayed();
    }

    public boolean isMenuActive(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.MENU));
        driver.findElement(InventoryPageUI.MENU).click();

        // Tạo list các item trong menu để chờ và kiểm tra
        List<By> menuOptions = Arrays.asList(InventoryPageUI.ALL_ITEMS_LINK,InventoryPageUI.ABOUT_LINK,
                InventoryPageUI.LOGOUT_LINK, InventoryPageUI.RESET_APP_STATE_LINK);

        // Chờ tất cả các item hiển thị
        for (By option : menuOptions) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(option));
            } catch (Exception e) {
                System.out.println("Không tìm thấy hoặc không hiển thị: " + option);
                return false; // Nếu 1 item không hiển thị thì trả về false
            }
        }

        // Sau khi đã chờ đủ, xác nhận tất cả đều hiển thị
        for (By option : menuOptions) {
            if (!driver.findElement(option).isDisplayed()) {
                System.out.println("Item không hiển thị: " + option);
                return false;
            }
        }
        return true;
    }

    public String getTitleInventoryPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.PAGE_TITLE));
        return driver.findElement(InventoryPageUI.PAGE_TITLE).getText();
    }

    public boolean isShoppingCartDisplayed(){
        return driver.findElement(InventoryPageUI.SHOPPING_CART).isDisplayed();
    }


    public int getInventoryCount(){
        return driver.findElements(InventoryPageUI.INVENTORY_ITEM).size();
    }

    public boolean isSortByActive(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.MENU));
        driver.findElement(InventoryPageUI.SORY_BY).click();
        return driver.findElement(InventoryPageUI.SORT_BY_NAME_AZ).isDisplayed()
                && driver.findElement(InventoryPageUI.SORT_BY_NAME_ZA).isDisplayed()
                && driver.findElement(InventoryPageUI.SORT_BY_PRICE_LOW).isDisplayed()
                && driver.findElement(InventoryPageUI.SORT_BY_PRICE_HIGH).isDisplayed();
    }

//2. Check Sort By
    private List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(InventoryPageUI.PRODUCT_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText().trim());
        }
        return names;
    }

    public boolean isSortedByName(Comparator<String> comparator) {
        List<String> actual = getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(comparator);
        return actual.equals(expected);
    }

    public void clickToSortByNameAZ(){
        driver.findElement(InventoryPageUI.SORY_BY).click();
        driver.findElement(InventoryPageUI.SORT_BY_NAME_AZ).click();
    }

    public void clickToSortByNameZA(){
        driver.findElement(InventoryPageUI.SORY_BY).click();
        driver.findElement(InventoryPageUI.SORT_BY_NAME_ZA).click();

    }

    public boolean isSortedByNameAZ() {
        return isSortedByName(String::compareToIgnoreCase);
    }

    public boolean isSortedByNameZA() {
        return isSortedByName((s1, s2) -> s2.compareToIgnoreCase(s1));
    }

//    public boolean isSortByNameAZ() {
//        //Tạo danh sách tên sản phẩm
//        List<WebElement> productsOfPage = driver.findElements(InventoryPageUI.PRODUCT_NAME);
//        List<String> listProductNames = new ArrayList<>();
//
//        //Lấy danh sách tên
//        for (WebElement product : productsOfPage){
//            listProductNames.add(product.getText().trim());
//        }
//
//        //Tạo danh sách tên đã sort by A-Z
//        List<String> expectedListNames = new ArrayList<>(listProductNames);
//        expectedListNames.sort(String::compareToIgnoreCase);
//
//        return listProductNames.equals(expectedListNames);
//    }

//    public boolean isSortByNameZA() {
//        //Tạo danh sách tên sản phẩm
//        List<WebElement> productsOfPage = driver.findElements(InventoryPageUI.PRODUCT_NAME);
//        List<String> listProductNames = new ArrayList<>();
//
//        //Lấy danh sách tên
//        for (WebElement product : productsOfPage){
//            listProductNames.add(product.getText().trim());
//        }
//
//        //Tạo danh sách tên đã sort by A-Z
//        List<String> expectedListNames = new ArrayList<>(listProductNames);
//        expectedListNames.sort(String::compareToIgnoreCase);
//        Collections.reverse(expectedListNames);
//
//        return listProductNames.equals(expectedListNames);
//    }

    private List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(InventoryPageUI.PRODUCT_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : elements) {
            String priceText = el.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public boolean isSortedByPrice(Comparator<Double> comparator) {
        List<Double> actual = getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(comparator);
        return actual.equals(expected);
    }

    public void clickToSortByPriceLow(){
        driver.findElement(InventoryPageUI.SORT_BY_PRICE_LOW).click();
    }
    public void clickToSortByPriceHigh(){
        driver.findElement(InventoryPageUI.SORT_BY_PRICE_HIGH).click();
    }

    public boolean isSortedByPriceLowToHigh() {
        return isSortedByPrice(Double::compareTo);
    }

    public boolean isSortedByPriceHighToLow() {
        return isSortedByPrice(Comparator.reverseOrder());
    }

// 3. Check Add to cart /Remove
    public void clickAddToCart(String productName) {
        for (WebElement product : driver.findElements(InventoryPageUI.INVENTORY_ITEM)) {
            String name = product.findElement(InventoryPageUI.PRODUCT_NAME).getText().trim();
            wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.ADD_TO_CART_BUTTON));
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(InventoryPageUI.ADD_TO_CART_BUTTON).click();
                break;
            }
        }
    }

    public void clickRemove(String productName) {
        for (WebElement product : driver.findElements(InventoryPageUI.INVENTORY_ITEM)) {
            String name = product.findElement(InventoryPageUI.PRODUCT_NAME).getText().trim();
            wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.REMOVE_FROM_CART_BUTTON));
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(InventoryPageUI.REMOVE_FROM_CART_BUTTON).click();
                break;
            }
        }
    }

    public String checkNumOfCart(){
        return driver.findElement(InventoryPageUI.ITEMS_IN_CART).getText();
    }

    public void isSelectedProducts(){
        List<WebElement> itemsInCart = driver.findElements(YourCartPageUI.ITEM_IN_SHOPPING_CART);

    }

// 4. Your Cart
    public void clickShoppingCart(){
        driver.findElement(InventoryPageUI.SHOPPING_CART);
    }

}
