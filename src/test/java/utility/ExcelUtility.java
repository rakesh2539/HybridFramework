package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
   FileInputStream fi;
   XSSFWorkbook workbook;
   XSSFSheet sheet;
   XSSFRow row;
   XSSFCell cell;
   String path;
   
   public ExcelUtility(String path) {
	   this.path=path;
   }
   
	public int getrowcount(String path) throws IOException {
		
		fi=new FileInputStream(path);
		workbook =new XSSFWorkbook(fi);
		sheet=workbook.getSheet("Sheet1");
		int rows=sheet.getLastRowNum();
		return rows;
}
	public int getcolumncount(String path,int rownum) throws IOException {
		
		fi=new FileInputStream(path);
		workbook =new XSSFWorkbook(fi);
		sheet=workbook.getSheet("Sheet1");
		row=sheet.getRow(rownum);
		int columns=row.getLastCellNum();
		return columns;
	
}
	public String getcellData(String path,int rownum,int columnnum) throws IOException {
		
		fi=new FileInputStream(path);
		workbook =new XSSFWorkbook(fi);
		sheet=workbook.getSheet("Sheet1");
		row=sheet.getRow(rownum);
		cell=row.getCell(columnnum);
		
		
		DataFormatter formatter=new DataFormatter();
		String data;
	try	{
		data=formatter.formatCellValue(cell);
		
	}
	catch(Exception e) {
		data="";
	}
		
	workbook.close();
	fi.close();
		
	return data;	
		
		
	}
}