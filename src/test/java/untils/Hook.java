package untils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Hook {
    protected WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void exitWeb(){
        if (driver !=null){
            driver.quit();
        }
    }

}
