package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Searchh_Page {
	
	 WebDriver driver;
	
	public Searchh_Page(WebDriver driver) {
		
		this.driver=driver;
      PageFactory.initElements(driver, this);		
	}
	
	@FindBy(xpath="//a[text()='Logout']") WebElement logoutbtn;
	
	public void click_logout() {
		logoutbtn.click();
	}
	

}
