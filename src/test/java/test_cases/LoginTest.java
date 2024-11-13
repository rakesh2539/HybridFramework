package test_cases;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pageobjects.Login_page;
import pageobjects.Searchh_Page;
import utility.DataproviderForLogin;

public class LoginTest extends BaseTest {
    Login_page lp;
    Searchh_Page sp;
    Logger logger = LogManager.getLogger(LoginTest.class);
    
    @Test(dataProvider = "getdata", dataProviderClass = DataproviderForLogin.class)
    public void LoginTest(String username, String password) {
        
        lp = new Login_page(driver);
        System.out.println("Attempting to log in with username: " + username + " and password: " + password);
        logger.info("Attempting to log in with username: {} and password: {}", username, password);

        
        lp.enterusername(username);
        lp.enterpassword(password);
        lp.clicklogin();
        
       
        String title_afterlogin = driver.getTitle();
        logger.debug("Title after login attempt: {}", title_afterlogin);
        
        String ExpectedTitle = prop.getProperty("LoginExp_Title");
        Assert.assertEquals(title_afterlogin, ExpectedTitle, "Login title mismatch!");
        logger.info("Login successful for user: {}", username);

        
    }

    @Test
    public void LogoutTest() {
    	
    	sp = new Searchh_Page(driver);

    	sp.click_logout();
        String title_afterlogout = driver.getTitle();
        logger.debug("Title after logout attempt: {}", title_afterlogout);
 
        String ExpectedTitle = "Adactin.com - Logout";
        Assert.assertEquals(title_afterlogout, ExpectedTitle, "Logout title mismatch!");
        logger.info("Logout successful, returned to expected title: {}", ExpectedTitle);

    }
}
