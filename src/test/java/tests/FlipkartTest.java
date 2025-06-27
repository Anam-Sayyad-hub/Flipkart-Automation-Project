package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FlipkartTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void flipkartAddToCartTest() {
        // Step 1: Open Flipkart
        driver.get("https://www.flipkart.com");

        // Step 2: Close the login popup if it appears
        try {
            WebElement closeBtn = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
            closeBtn.click();
        } catch (Exception e) {
            System.out.println("Login popup not displayed.");
        }

        // Step 3: Search for a product
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Shoes for women");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Step 4: Click the first product
        WebElement firstProduct = driver.findElement(By.xpath("(//div[@data-id]//img)[1]"));
        firstProduct.click();

        // Step 5: Switch to new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // Step 6: Scroll and Add to Cart
        try {
            Thread.sleep(3000); // Let product page load
            WebElement addToCart = driver.findElement(By.xpath("//button[text()='Add to cart']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCart);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
            Thread.sleep(3000);
            System.out.println("✅ Product added to cart successfully!");
        } catch (Exception e) {
            System.out.println("❌ Could not add product to cart.");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("🧹 Browser closed. Test finished.");
    }
}