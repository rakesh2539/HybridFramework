package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageobjects.Login_page;
import pageobjects.Searchh_Page;

public class BaseTest {

    public static WebDriver driver;
    public Properties prop;
    public FileInputStream fis;
    public Login_page lp;
    public Searchh_Page sp;
    public DesiredCapabilities cap;
    public Logger logger;

    @BeforeClass
    @Parameters({"browser","os"})
    public void setup(String browser, String os) throws IOException {
    	
        logger= LogManager.getLogger(BaseTest.class);    	
        prop = new Properties();
        fis = new FileInputStream("src\\test\\resources\\config.properties");
        prop.load(fis);
      
        
        String executionEnv = prop.getProperty("execution-env");
        
        if (executionEnv == null) {
            System.out.println("Execution environment not specified in properties file.");
            return;
        }

        if (executionEnv.equalsIgnoreCase("remote")) {
            cap = new DesiredCapabilities();
            
            // OS setup
            if (os.equalsIgnoreCase("windows")) {
                cap.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                cap.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching OS found.");
                return;
            }
            
            // Browser setup
            switch (browser.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");
                    break;
                case "edge":
                    cap.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    System.out.println("No matching browser found.");
                    return;
            }
            
            driver = new RemoteWebDriver(new URL("http://192.168.1.18:4444/wd/hub"), cap);
            logger.info("Remote WebDriver initialized successfully.");

        } 
        else if (executionEnv.equalsIgnoreCase("local")) {
            // Local browser setup
            logger.info("Setting up local WebDriver for browser: {}", browser);

            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            logger.info("Local WebDriver initialized successfully.");

        } 
        else {
            logger.error("Invalid execution environment specified in properties file: {}", executionEnv);

            System.out.println("Invalid execution environment specified in properties file.");
            return;
        }

        driver.manage().window().maximize();
        driver.get("https://adactinhotelapp.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logger.info("Navigated to the application URL successfully.");

    }
    
    public String takeScreenshot(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String targetDir = System.getProperty("user.dir") + "\\screenshots\\" + testName + " - " + timestamp + ".png";
        File screenshotDir = new File(targetDir);
        FileUtils.copyFile(source, screenshotDir);
        logger.info("Screenshot taken for test: {}", testName);

        return targetDir;
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver instance closed.");

        }
    }
}
