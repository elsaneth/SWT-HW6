import com.sun.tools.javac.Main;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class BasicTest extends TestHelper {


    private String username = "elsa";
    private String password = "elsa123";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

     @Test
    public void loginLogoutTest(){

        login(username, password);

         WebElement adminHeader = driver.findElement(By.id("Products"));
         String mainText = adminHeader.getText();
         if (mainText.equals("Products")) {
             assertEquals("Products", mainText);
         } else {
             assertNotEquals("Products", mainText);
         }

        logout();
    }

     @Test
    public void loginFalsePassword() {
         login(username, password);
         WebElement adminHeader = driver.findElement(By.id("notice"));
         String actualText = adminHeader.getText();
         if (actualText.equals("Invalid user/password combination")) {
             assertEquals("Invalid user/password combination", actualText);
         } else {
             assertNotEquals("Invalid user/password combination", actualText);
         }
    }

}
