package testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor; 
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
// IMPORT REQUIRED FOR CHROMEOPTIONS
import org.openqa.selenium.chrome.ChromeOptions; 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions; 
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;


public class AutomationTests {

    private WebDriver driver;
    private WebDriverWait wait;
    // Base URL for Amazon India
    private final String BASE_URL = "https://www.amazon.in/";

//    @BeforeClass
//    public void setup() {
//        // ⭐ 1. Set the system property for GeckoDriver
//        // NOTE: Replace "path/to/your/geckodriver" with the actual path to your geckodriver executable
//        // Example: System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
//        // If you are using WebDriverManager or have geckodriver in your PATH, you can skip this line.
//        // System.setProperty("webdriver.gecko.driver", "path/to/your/geckodriver"); 
//        
//        // ⭐ 2. Initialize FirefoxDriver instead of ChromeDriver
//        driver = new FirefoxDriver();
//        driver.manage().window().maximize();
//        // Set an implicit wait for better stability
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        
//        System.out.println("WebDriver initialized for: Firefox Browser");
//    }

    @BeforeClass
    public void setup() {
        // === REQUIRED FIX FOR JENKINS/LINUX HEADLESS EXECUTION ===
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080"); // Optional: Set consistent screen size

        driver = new ChromeDriver(options);
        // =========================================================

        driver.manage().window().maximize();
        // Set an implicit wait for better stability
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        System.out.println("WebDriver initialized for: Chrome Headless Browser");
    }

    // ====================================================================
    // 5 POSITIVE AUTOMATION SCENARIOS FOR AMAZON.IN
    // ====================================================================

    /**
     * Scenario 1: Verify the Amazon India homepage loads correctly and has the expected title.
     */
    @Test(priority = 1)
    public void test01_VerifyHomePageLoad() {
        driver.get(BASE_URL);
        System.out.println("Running Scenario 1: Verifying Home Page Load and Title.");
        
        // Use explicit wait to ensure the page title is available
        wait.until(ExpectedConditions.titleContains("Amazon"));
        
        String pageTitle = driver.getTitle();
        // Positive assertion: title should contain "Amazon"
        Assert.assertTrue(pageTitle.contains("Amazon"), "Homepage title does not contain 'Amazon'. Actual title: " + pageTitle);
        System.out.println("Successfully verified home page title: " + pageTitle);
    }

    /**
     * Scenario 2: Search for a product ("wireless keyboard") and verify the search results page header.
     */
    @Test(priority = 2)
    public void test02_SuccessfulProductSearch() {
        // Ensure we are on the base page
        driver.get(BASE_URL);
        System.out.println("Running Scenario 2: Performing Successful Product Search.");

        // Locator for the search box
        By searchBoxLocator = By.id("twotabsearchtextbox");
        // Locator for the search button (magnifying glass)
        By searchButtonLocator = By.id("nav-search-submit-button");

        // 1. Enter search term
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));
        searchBox.sendKeys("wireless keyboard");

        // 2. Click search button
        driver.findElement(searchButtonLocator).click();

        // 3. Verify search results page by checking the displayed search term or a key element
        By searchResultTextLocator = By.xpath("//span[contains(@class, 'a-color-state') and contains(text(), 'wireless keyboard')]");
        
        WebElement searchResultText = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultTextLocator));
        String resultText = searchResultText.getText().replaceAll("\"", "").trim();

        // Positive assertion: The search term should be displayed on the results page
        Assert.assertEquals(resultText, "wireless keyboard", "Search result page did not confirm the search for 'wireless keyboard'.");
        System.out.println("Successfully verified search for 'wireless keyboard'.");
    }

    /**
     * Scenario 3: Navigate to the "Mobiles" category from the main navigation and verify page navigation.
     */
    @Test(priority = 3)
    public void test03_NavigateToMobilesCategory() {
        driver.get(BASE_URL);
        System.out.println("Running Scenario 3: Navigating to Mobiles Category.");

        // Locator for the Mobiles link in the main navigation
        By mobilesLinkLocator = By.xpath("//div[@id='nav-xshop']//a[contains(text(), 'Mobiles')]");
        
        wait.until(ExpectedConditions.elementToBeClickable(mobilesLinkLocator)).click();

        // Verify the navigation by checking the URL or a key element on the Mobiles page
        wait.until(ExpectedConditions.urlContains("/mobile-phones/"));

        // Get the title of the Mobiles page
        String pageTitle = driver.getTitle();
        // Positive assertion: The page title should contain "Mobile Phones"
        Assert.assertTrue(pageTitle.contains("Mobile Phones"), "Navigation to Mobiles category failed. Title: " + pageTitle);
        System.out.println("Successfully navigated to Mobiles page.");
    }

    /**
     * NEW Scenario 4: Hover over the 'Account & Lists' menu and verify the 'Sign In' button appears.
     */
    @Test(priority = 4)
    public void test04_VerifyAccountListHoverMenu() {
        driver.get(BASE_URL);
        System.out.println("Running Scenario 4: Verifying Account & Lists Hover Menu.");

        // Locator for the main Account & Lists element
        By accountListLinkLocator = By.id("nav-link-accountList");
        
        // Locator for the 'Sign in' button inside the flyout menu
        By signInButtonLocator = By.xpath("//div[@id='nav-flyout-accountList']//span[text()='Sign in']");
        
        // 1. Hover over the Account & Lists element
        WebElement accountListLink = wait.until(ExpectedConditions.presenceOfElementLocated(accountListLinkLocator));
        Actions actions = new Actions(driver);
        actions.moveToElement(accountListLink).perform();

        // 2. Verify the 'Sign in' button becomes visible in the flyout menu
        WebElement signInButton = wait.until(ExpectedConditions.visibilityOfElementLocated(signInButtonLocator));

        // Positive assertion: Check if the button is displayed
        Assert.assertTrue(signInButton.isDisplayed(), "Sign In button did not appear after hovering over Account & Lists.");
        System.out.println("Successfully verified 'Sign in' button visibility in hover menu.");
    }

    /**
     * NEW Scenario 5: Search for a product, click the first result, and verify the 'Add to Cart' button is present on the product page.
     */
    @Test(priority = 5)
    public void test05_VerifyProductPageAndAddToCartButton() {
        driver.get(BASE_URL);
        System.out.println("Running Scenario 5: Verifying Product Page and 'Add to Cart' Button (Fixed for Timeout).");

        // Perform search (using a generic, common product)
        By searchBoxLocator = By.id("twotabsearchtextbox");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));
        searchBox.sendKeys("mobile phone" + Keys.ENTER);

        // 1. Locate the first search result link (FIX: Use presenceOfElementLocated)
        By firstResultLinkLocator = By.xpath("//div[@data-component-type='s-search-result'][1]//h2");
        WebElement firstResultLink = wait.until(ExpectedConditions.presenceOfElementLocated(firstResultLinkLocator));
        
        // FIX: Use JavascriptExecutor to perform a reliable click, bypassing potential overlays or rendering issues
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstResultLink);
        
        // 2. Verify navigation to the product detail page by checking the 'Add to Cart' button
        // Locator for the 'Add to Cart' button
        By addToCartButtonLocator = By.xpath("//*[@id=\"a-autoid-3-announce\"]");
        
        // Wait for the button to be visible and clickable
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
        
        // Positive assertion: Check if the 'Add to Cart' button is displayed and enabled
        Assert.assertTrue(addToCartButton.isDisplayed() && addToCartButton.isEnabled(), "Product page loaded, but 'Add to Cart' button is not displayed or enabled.");
        System.out.println("Successfully navigated to product page and verified 'Add to Cart' button.");
    }

    // ====================================================================
    // END OF SCENARIOS
    // ====================================================================

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
