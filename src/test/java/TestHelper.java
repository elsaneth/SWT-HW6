import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResposeTime = 4;
	
	// here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "http://127.0.0.1:3000/admin";
	
	// here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "http://127.0.0.1:3000/";

    @Before
    public void setUp(){

        // if you use Chrome:
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\...\\chromedriver.exe");
//        driver = new ChromeDriver();

        // if you use Firefox:
//        System.setProperty("webdriver.gecko.driver", "/Users/elisabeth/Downloads/geckodriver");
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox");
        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void goToPage(String page){
        WebElement elem = driver.findElement(By.linkText(page));
        elem.click();
        waitForElementById(page);
    }

    void waitForElementById(String id){
        new WebDriverWait(driver, waitForResposeTime).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    void login(String username, String password){

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        By loginButtonXpath = By.xpath("//input[@value='Login']");
        driver.findElement(loginButtonXpath).click();
    }

    void register(String username, String password, String confirmation) {
        driver.get(baseUrlAdmin);
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(confirmation);
        By loginButtonXpath = By.xpath("//input[@value='Create User']");
        driver.findElement(loginButtonXpath).click();
    }


    void deleteUser(String user) {
        goToPage("Admin");
        waitForElementById("Admin");
        WebElement deleteLink = driver.findElement(By.xpath("//p[@id='" + user + "']/a[@data-method='delete']"));
        deleteLink.click();
    }

    void addProduct(String title, String description, String productType, String price) {
        goToPage("Products");
        WebElement newProductLink = driver.findElement(By.xpath("//p[@id='new_product_div']/a[@href='/products/new']"));
        newProductLink.click();

        driver.findElement(By.id("product_title")).sendKeys(title);
        driver.findElement(By.id("product_description")).sendKeys(description);

        WebElement productTypeDropdown = driver.findElement(By.id("product_prod_type"));
        Select select = new Select(productTypeDropdown);
        select.selectByVisibleText(productType);

        driver.findElement(By.id("product_price")).sendKeys(price);

        WebElement createProductLink = driver.findElement(By.xpath("//input[@name='commit']"));
        createProductLink.click();
        System.out.println("Item added.");
    }

    void deleteProduct(String title) {
        goToPage("Products");
        WebElement deleteLink = driver.findElement(By.xpath("//tr[@id='" + title + "']/td[@class='list_actions']/a[@data-method='delete']"));
        deleteLink.click();
        System.out.println("Item deleted");
    }

    void getProductToEdit(String title) {
        goToPage("Products");
        WebElement editLink = driver.findElement(By.xpath("//tr[@id='" + title + "']/td[@class='list_actions']/a[text()='Edit']"));
        editLink.click();
        System.out.println("Item edited");
    }

    void logout(){
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();
        waitForElementById("Admin");
    }

    @After
    public void tearDown(){
        driver.close();
    }

}