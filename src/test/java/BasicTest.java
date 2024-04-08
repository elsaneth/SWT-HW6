import com.sun.tools.javac.Main;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class BasicTest extends TestHelper {


    private final String username = "elsa";
    private String password = "elsa123";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void loginLogoutTest(){

        login(username, "elsa123");

         WebElement adminHeader = driver.findElement(By.id("Products"));
         String mainText = adminHeader.getText();
         if (mainText.equals("Products")) {
             assertEquals("Products", mainText);
         } else {
             fail("loginLogoutTest failed");
         }

        logout();
    }

    @Test
    public void loginFalsePassword() {
         login(username, "password");
         WebElement adminHeader = driver.findElement(By.id("notice"));
         String actualText = adminHeader.getText();
         if (actualText.equals("Invalid user/password combination")) {
             assertEquals("Invalid user/password combination", actualText);
         } else {
             fail("loginFalsePassword failed");
         }
    }

    @Test
    public void adminLogOut() {
        login(username, "elsa123");
        logout();
        boolean isLoginPage = isElementPresent(By.cssSelector("a[href='/login']"));
        if (!isLoginPage) {
            assertFalse(isLoginPage);
        }
    }

    @Test
    public void registerAccountWithWrongConfirmation() {
        register("neljas", "neli", "kaks");
        WebElement mainHeader = driver.findElement(By.id("main"));
        WebElement registerHeader = mainHeader.findElement(By.id("Register"));
        String mainText = registerHeader.getText();
        assertEquals("Register", mainText);
    }

    @Test
    public void registerAccountWithValidInput() {
        String user = "seitse";
        register(user, "seitse", "seitse");
        deleteUser(user);
        WebElement adminHeader = driver.findElement(By.id("notice"));
        String noticeText = adminHeader.getText();
        System.out.println(noticeText);
        if (Objects.equals(noticeText, "User was successfully deleted.")) {
            assertEquals("User was successfully deleted.", noticeText);
        } else {
            fail("Notice element does not indicate successful deletion after creation or no new user was created.");
        }
    }

    @Test
    public void addProductsWithValidInput() {
        login(username, password);
        String productName = "Book of Life";
        addProduct(productName, "Very good book, good price", "Books", "20");

        boolean isProductAdded = isElementPresent(By.id(productName));
        assertTrue("Product was not added successfully.", isProductAdded);

        deleteProduct(productName);
        boolean isProductDeleted = !isElementPresent(By.id(productName));
        assertTrue("Product was not deleted successfully.", isProductDeleted);

        logout();
    }

    @Test
    public void editProductTitleWithValidInput() {
        login(username, password);
        String oldTitle = "Web Application Testing Book";
        getProductToEdit(oldTitle);
        WebElement titleInput = driver.findElement(By.id("product_title"));

        String newTitle = "New Title";
        titleInput.clear();
        titleInput.sendKeys(newTitle);
        WebElement updateProduct = driver.findElement(By.xpath("//input[@data-disable-with='Update Product']"));
        updateProduct.click();

        WebElement notice = driver.findElement(By.id("notice"));
        String noticeText = notice.getText();
        assertEquals("Product was successfully updated.", noticeText);

        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();

        // change back
        WebElement titleInput2 = driver.findElement(By.id("product_title"));
        titleInput2.clear();
        titleInput2.sendKeys(oldTitle);
        WebElement updateProduct2 = driver.findElement(By.xpath("//input[@data-disable-with='Update Product']"));
        updateProduct2.click();
    }

}
