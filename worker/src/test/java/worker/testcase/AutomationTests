package testcase;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AutomationTests {

    WebDriver driver;
    WebDriverWait wait;
    String BASE_URL = "https://demowebshop.tricentis.com/";

    @BeforeClass
    public void setup() {

        // 🔥 HEADLESS MODE ENABLED
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");      // New Chrome headless mode
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===========================================================
    // 1️⃣ TEST – Verify Home Page Loads
    // ===========================================================
    @Test(priority = 1)
    public void test01_verifyHomePage() {
        driver.get(BASE_URL);
        Assert.assertTrue(driver.getTitle().contains("Demo Web Shop"));
    }

    // ===========================================================
    // 2️⃣ TEST – Search Product
    // ===========================================================
    @Test(priority = 2)
    public void test02_searchProduct() {
        driver.get(BASE_URL);

        WebElement searchBox = driver.findElement(By.id("small-searchterms"));
        searchBox.sendKeys("laptop" + Keys.ENTER);

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".page-title h1")
                )
        );

        Assert.assertTrue(title.getText().contains("Search"));
    }

    // ===========================================================
    // 3️⃣ TEST – Add Product to Cart
    // ===========================================================
    @Test(priority = 3)
    public void test03_addProductToCart() {
        driver.get(BASE_URL);

        driver.findElement(By.id("small-searchterms")).sendKeys("laptop" + Keys.ENTER);

        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".product-item .product-title a")
                )
        );
        firstProduct.click();

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-31"))
        );
        addToCartBtn.click();

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".bar-notification.success")
                )
        );

        Assert.assertTrue(successMsg.getText().contains("The product has been added"));
    }

    // ===========================================================
    // 4️⃣ TEST – Registration
    // ===========================================================
    @Test(priority = 4)
    public void test04_registerUser() {
        driver.get(BASE_URL);

        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");

        String email = "testuser" + System.currentTimeMillis() + "@mail.com";
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys("Test@12345");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test@12345");

        driver.findElement(By.id("register-button")).click();

        WebElement result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".result")
                )
        );

        Assert.assertTrue(result.getText().contains("completed"));
    }

    // ===========================================================
    // 5️⃣ TEST – Verify Category Navigation
    // ===========================================================
    @Test(priority = 5)
    public void test05_verifyCategoryNavigation() {
        driver.get(BASE_URL);

        driver.findElement(By.linkText("Books")).click();

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        Assert.assertEquals(title.getText(), "Books");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
