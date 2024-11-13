package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BaseTest;

public class Login_page extends BaseTest {
	
	WebDriver driver;
	public  Login_page(WebDriver driver) {
		this.driver=driver;
	}
	
By username_field=	By.xpath("//input[@id='username']");
By password_field=	By.xpath("//input[@id='password']");
By Login_btn =	By.xpath("//input[@id='login']");


public void enterusername(String uname) {
	driver.findElement(username_field).sendKeys(uname);
}
public void enterpassword(String pword) {
	driver.findElement(password_field).sendKeys(pword);
}
public void clicklogin() {
	driver.findElement(Login_btn).click();
}
}
	
	

