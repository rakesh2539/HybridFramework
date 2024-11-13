package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseTest;

public class ExtentReportManager implements ITestListener {

    
    private ExtentReports extent;
    private ExtentSparkReporter sparkReporter;
    private ExtentTest test;
    private String reportName;
  
    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss").format(new Date());
        reportName = "TestReport-" + timestamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);
        sparkReporter.config().setDocumentTitle("Selenium Project");
        sparkReporter.config().setReportName("Selenium Hybrid Framework");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "HotelManagement");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = context.getCurrentXmlTest().getParameter("OS");
        String browser = context.getCurrentXmlTest().getParameter("Browser");
        extent.setSystemInfo("Browser", browser != null ? browser : "Unknown");
        extent.setSystemInfo("OS", os != null ? os : "Unknown");

        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) {
            extent.setSystemInfo("Groups", groups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getMethod().getMethodName() + " executed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            
            String imagePath = new BaseTest().takeScreenshot(result.getName());
            test.addScreenCaptureFromPath(imagePath);
        } catch (IOException e) {
           System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    } 

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getMethod().getMethodName() + " was skipped.");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
        
        String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportName;
        File extentReportFile = new File(reportPath);
        
        try {
            Desktop.getDesktop().browse(extentReportFile.toURI());
        } catch (IOException e) {
            System.err.println("Failed to open report: " + e.getMessage());
        }
    }
}
