package utility;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataproviderForLogin {
	
	
		
		 @DataProvider(name = "getdata")
		    public Object[][] getdata() throws IOException {
		        // Define the file path
		        String path = System.getProperty("user.dir") + "/src/test/resources/jenkintest.xlsx";
		        ExcelUtility xl = new ExcelUtility(path);
		        
		        // Get row and column counts
		        int totalRows = xl.getrowcount(path);
		        int totalColumns = xl.getcolumncount(path, 1); // Assuming data starts in the first row

		        // Initialize a 2D array for login data
		        Object[][] loginData = new Object[totalRows][totalColumns];

		        // Populate the data array
		        for (int i = 1; i <= totalRows; i++) {
		            for (int j = 0; j < totalColumns; j++) {
		                loginData[i - 1][j] = xl.getcellData(path, i, j);
		            }
		        }

		        return loginData;
		    }
		}