package org.jlite.core.datasource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jlite.core.jlite.TestKeywords;
import org.jlite.core.testmodel.TestCase;
import org.jlite.core.testmodel.TestData;
import org.jlite.core.testmodel.TestGlobalData;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.testmodel.TestStep;

public class ExcelFileHandler2 {
	String path;
	HSSFWorkbook  con;
	FileOutputStream fos;
	public ExcelFileHandler2(String filepath) {
//		System.out.println("Enter excel file");
//		System.out.println(filepath);
		path = filepath;

		con = this.getConnection();

	}
	
	private synchronized HSSFWorkbook getConnection() 
	{
		HSSFWorkbook wb = null;
		try{
			wb = new HSSFWorkbook(new FileInputStream(path));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return wb;
	}
	
	public synchronized List<TestIteration> getAllTestIterations()
	{
		List<TestIteration> rval = new ArrayList<TestIteration>();
		String sheetName=TestKeywords.TBL_NAME_ITERATION_MAP;
		int rowIdIndex = this.getColumnIndex(TestKeywords.COL_NAME_RECORD_ID, sheetName);
		int runFlagIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUNFLAG, sheetName);
		int tcaseIdIndex = this.getColumnIndex(TestKeywords.COL_NAME_TESTCASE_ID, sheetName);
		int tIterIdIndex = this.getColumnIndex(TestKeywords.COL_NAME_ITERATION_ID, sheetName);
		
		HSSFSheet sheet = con.getSheet(sheetName);
		
		Iterator<Row> itr = sheet.rowIterator();
		try{
		while(itr.hasNext())
		{   
			HSSFRow irow = (HSSFRow)itr.next();
			if(irow.getRowNum()!=0)
			{
				
				String rowId = this.getCellValue(irow.getCell(rowIdIndex));
				String rflag = this.getCellValue(irow.getCell(runFlagIndex));
				String tcid = this.getCellValue(irow.getCell(tcaseIdIndex));
				String titrid = this.getCellValue(irow.getCell(tIterIdIndex));
				if(rflag.equalsIgnoreCase("Y"))
				{
					TestIteration titr = new TestIteration();
					titr.setITERATION_ID(titrid);
					titr.setTESTCASE_ID(tcid);
					titr.setRECORD_ID(rowId);
					titr.setRUN_FLAG(rflag);
					rval.add(titr);
				}
				
				
			}
			
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rval;
	}
//	public TestIteration getTestIterations(String recordid)
//	{
//		TestIteration itr = new TestIteration();
//		if (con == null) {
//			con = this.getConnection();
//		}
//		try {
//			String sname=TestKeywords.TBL_NAME_ITERATION_MAP;
//			String fltrCName=TestKeywords.COL_NAME_TESTCASE_ID;
//			String fltrCValue =testcaeid;
//			this.getValue(sname, fltrCName, fltrCValue, dstCName)
//		} catch (Exception e) {
//
//		}
//		return itr;
//	}
//	public List<TestCase> getAllTestCases()
//	{
//		List<TestCase> retval = new ArrayList<TestCase>();
//		if (con == null) {
//			con = this.getConnection();
//		}
//		try {
//			Statement st;
//			ResultSet rs;
//			st = con.createStatement();
//			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_TESTCASES+"$]");
//			while (rs.next()) {
//				String runflag = rs.getString(TestKeywords.COL_NAME_RUNFLAG);
//				if (runflag.equalsIgnoreCase("y")) {
//					TestCase itr = new TestCase();
//					itr.setRECORD_ID(rs.getString(TestKeywords.COL_NAME_RECORD_ID));
//					itr.setRUN_FLAG(runflag);
//					itr.setTESTCASE_ID(rs.getString(TestKeywords.COL_NAME_TESTCASE_ID));
//					itr.setPROJECT_ID(rs.getString(TestKeywords.COL_NAME_PROJECT_ID));
//					itr.setPROJECT_NAME(rs.getString(TestKeywords.COL_NAME_PROJECT_NAME));
//					itr.setFEATURE_ID(rs.getString(TestKeywords.COL_NAME_FEATURE_ID));
//					itr.setFEATURE_NAME(rs.getString(TestKeywords.COL_NAME_FEATURE_NAME));
//					itr.setUSERSTORY_ID(rs.getString(TestKeywords.COL_NAME_USERSTORY_ID));
//					itr.setUSERSTORY_NAME(rs.getString(TestKeywords.COL_NAME_USERSTORY_NAME));
//					itr.setTESTCASE_NAME(rs.getString(TestKeywords.COL_NAME_TESTCASE_NAME));
//					itr.setRUN_RESULTS("");
//					itr.setRUN_DESCRIPTION("");
//					itr.setRUN_START_TIME("");
//					itr.setRUN_END_TIME("");
//					itr.setRUN_TOTAL_TIME("");
//					retval.add(itr);
//				}
//
//			}
//		} catch (Exception e) {
//
//		}
//		
//		return retval;
//	}
	
	public synchronized void doReport(TestIteration itr)
	{
		String sheetName = TestKeywords.TBL_NAME_ITERATION_MAP;
		int fltColIndex = this.getColumnIndex(TestKeywords.COL_NAME_RECORD_ID, sheetName);
		int resColresIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUN_RESULTS, sheetName);
		int resColdecIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUN_DESCRIPTION, sheetName);
		int resColstmIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUN_START_TIME, sheetName);
		int resColetmIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUN_END_TIME, sheetName);
		int resColttmIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUN_TOTAL_TIME, sheetName);
		Iterator<Row> rows = con.getSheet(sheetName).rowIterator();
		while(rows.hasNext())
		{
			HSSFRow irow = (HSSFRow)rows.next();
			String fltCellValue = this.getCellValue(irow.getCell(fltColIndex));
			if(fltCellValue.equalsIgnoreCase(itr.getRECORD_ID()))
			{
				this.doSetCellValue(irow, resColresIndex, itr.getRUN_RESULTS());
				this.doSetCellValue(irow, resColdecIndex, itr.getRUN_DESCRIPTION());
				this.doSetCellValue(irow, resColstmIndex, itr.getRUN_START_TIME());
				this.doSetCellValue(irow, resColetmIndex, itr.getRUN_END_TIME());
				this.doSetCellValue(irow, resColttmIndex, itr.getRUN_TOTAL_TIME());
			}
		}
		
		try{
			if(fos==null)
			{
				fos = new FileOutputStream(path);
				con.write(fos);
				con.close();
				fos.flush();
				con =null;
				fos=null;
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public synchronized void doSetCellValue(HSSFRow irow, int colIndex, String value)
	{
		HSSFCell icell = irow.createCell(colIndex);
		icell.setCellValue(value);
	}
	public synchronized List<TestStep> getTestSteps(String testcaseid) {
		List<TestStep> retval = new ArrayList<TestStep>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			String sheetName = TestKeywords.TBL_NAME_TESTSTEP;
			int rowIdIndex = this.getColumnIndex(TestKeywords.COL_NAME_RECORD_ID, sheetName);
			int runFlgIndex = this.getColumnIndex(TestKeywords.COL_NAME_RUNFLAG, sheetName);
			int tcaseIdIndex = this.getColumnIndex(TestKeywords.COL_NAME_TESTCASE_ID, sheetName);
			int cmdIndex = this.getColumnIndex(TestKeywords.COL_NAME_COMMAND, sheetName); 
			int tarIndex = this.getColumnIndex(TestKeywords.COL_NAME_TARGET, sheetName);
			int valIndex = this.getColumnIndex(TestKeywords.COL_NAME_VALUE, sheetName);
			HSSFSheet sheet = con.getSheet(sheetName);
			Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext())
			{
				HSSFRow irow = (HSSFRow)rows.next();
				if(irow.getRowNum()!=0)
				{
					String rflagVal = this.getCellValue(irow.getCell(runFlgIndex));
					String tcaseidval = this.getCellValue(irow.getCell(tcaseIdIndex));
					String rowidval = this.getCellValue(irow.getCell(rowIdIndex));
					String cmdval = this.getCellValue(irow.getCell(cmdIndex));
					String tarval = this.getCellValue(irow.getCell(tarIndex));
					String valval = this.getCellValue(irow.getCell(valIndex));
					if(rflagVal.equalsIgnoreCase("Y"))
					{
						
						if(tcaseidval.equalsIgnoreCase(testcaseid))
						{
							TestStep step = new TestStep();
							step.setRECORD_ID(rowidval);
							step.setTESTCASE_ID(tcaseidval);
							step.setRUN_FLAG(rflagVal);
							step.setCOMMAND(cmdval);
							step.setTARGET(tarval);
							step.setVALUE(valval);
							
							retval.add(step);
						}
					}
					
				}
			}
		} catch (Exception e) {

		}
		return retval;
	}
	public synchronized Hashtable<String, String> getGlobalData() {
		Hashtable<String, String> retval = new Hashtable<String, String>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
		int keycolindex = this.getColumnIndex(TestKeywords.COL_NAME_DATAKEY, TestKeywords.TBL_NAME_GLOBAL_DATA);
		int valcolindex = this.getColumnIndex(TestKeywords.COL_NAME_DATAVALUE, TestKeywords.TBL_NAME_GLOBAL_DATA);
		HSSFSheet sheet = con.getSheet(TestKeywords.TBL_NAME_GLOBAL_DATA);
		Iterator<Row> itr = sheet.rowIterator();
		while(itr.hasNext())
		{
			HSSFRow irow = (HSSFRow)itr.next();
			if(irow.getRowNum()!=0)
			{
				String key = this.getCellValue(irow.getCell(keycolindex));
				String val = this.getCellValue(irow.getCell(valcolindex));
				retval.put(key, val);
			}
		}
		
		} catch (Exception e) {

		}
		return retval;
	}
	public synchronized TestCase getTestCase(String testcaeid) {
		TestCase retval = new TestCase();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			String sname=TestKeywords.TBL_NAME_TESTCASES;
			String fltrCName=TestKeywords.COL_NAME_TESTCASE_ID;
			String fltrCValue =testcaeid;
			
			retval.setPROJECT_ID(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_PROJECT_ID));
			retval.setPROJECT_NAME(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_PROJECT_NAME));
			retval.setFEATURE_ID(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_FEATURE_ID));
			retval.setFEATURE_NAME(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_FEATURE_NAME));
			retval.setUSERSTORY_ID(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_USERSTORY_ID));
			retval.setUSERSTORY_NAME(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_USERSTORY_NAME));
			retval.setTESTCASE_ID(testcaeid);
			retval.setTESTCASE_NAME(this.getValue(sname, fltrCName, fltrCValue, TestKeywords.COL_NAME_TESTCASE_NAME));
		} catch (Exception e) {

		}
		return retval;
	}
	public synchronized String getValue(String sname,String fltrCName, String fltrCValue, String dstCName)
	{
		if (con == null) {
			con = this.getConnection();
		}
		String retval="";
		try{
		//int filterColIndex = this.getColumnIndex(fltrCName, sname);
		int destColIndex = this.getColumnIndex(dstCName, sname);
		int rowindex = this.getRowIndex(fltrCName, fltrCValue, sname);
		HSSFRow irow = con.getSheet(sname).getRow(rowindex);
		retval = this.getCellValue(irow.getCell(destColIndex));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retval;
	}
	public synchronized TestData getTestData(String itrid)
	{
		TestData data = new TestData();
		try{
		TestGlobalData gdata = new TestGlobalData();
		gdata.setData(this.getGlobalData());
		data.setGlobalData(gdata);
		data.setITERATION_ID(itrid);
		data.setTestdata(this.getDataSetByRecordId(itrid));
//		System.out.println(data.getTestdata().size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	public synchronized Hashtable<String, String> getDataSetByRecordId(String iterationid) {
		Hashtable<String, String> colNameAndValue = new Hashtable<String, String>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			
			String sheetName= TestKeywords.TBL_NAME_TEST_DATA;
			String dstColumnName = TestKeywords.COL_NAME_ITERATION_ID;
			String dstColumnValue = iterationid;
			List<String> columnNames = this.getTableColNames(sheetName);
			
			int rowindex = this.getRowIndex(dstColumnName, dstColumnValue, sheetName);
//			System.out.println("Test Me >> "+rowindex);
			HSSFSheet sheet = con.getSheet(sheetName);
			HSSFRow irow = sheet.getRow(rowindex);
			for(String colName:columnNames)
			{
				
				int colIndex = this.getColumnIndex(colName, sheetName);
				
				String colValue = this.getCellValue(irow.getCell(colIndex));
//				System.out.println("ColName >> "+colName +"="+colValue+" ColIndex >> "+colIndex );
				try{
					colNameAndValue.put(colName, colValue);
				}catch (Exception e) {
					// TODO: handle exception
//					e.printStackTrace();
				}
			}
			
		}catch (Exception e) {
			
		}
		return colNameAndValue;
	}
	
	private synchronized int getRowIndex(String dstColumnName,String dstColumnValue, String sheetName)
	{
		int retval =-1;
		if (con == null) {
			con = this.getConnection();
		}
		try{
			int colIndex = this.getColumnIndex(dstColumnName, sheetName);
			if(colIndex>-1)
			{
				HSSFSheet sheet = con.getSheet(sheetName);
				Iterator<Row> rows = sheet.rowIterator();
				int counter =-1;
				while(rows.hasNext())
				{
					counter++;
					HSSFRow irow =(HSSFRow) rows.next();
					if(irow.getRowNum()!=0)
					{
						HSSFCell vcell = irow.getCell(colIndex);
						String celvalue = this.getCellValue(vcell);
						if(celvalue.equalsIgnoreCase(dstColumnValue))
						{
							retval = counter;
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return retval;
		
	}
	private synchronized int getColumnIndex(String columnName, String sheetName)
	{
		int retval =-1;
		if (con == null) {
			con = this.getConnection();
		}
		try {
			HSSFSheet sheet = con.getSheet(sheetName);
			HSSFRow colRow = sheet.getRow(0);
			Iterator<Cell> cells = colRow.cellIterator();
			int counter =-1;
			while(cells.hasNext())
			{
				
				counter++;
				HSSFCell cell = (HSSFCell)cells.next();
				String celvalue = this.getCellValue(cell);
				if(celvalue.equalsIgnoreCase(columnName))
				{
					retval = counter;
					break;
				}
			}
		}catch (Exception e) {
			
		}

		return retval;
	}
	private synchronized List<String> getTableColNames(String sheetName) {
		List<String> retval = null;
		if (con == null) {
			con = this.getConnection();
		}
		try {
			retval = new ArrayList<String>();
			HSSFSheet sheet = con.getSheet(sheetName);
			HSSFRow colRow = sheet.getRow(0);
			Iterator<Cell> cells = colRow.cellIterator();
			while(cells.hasNext())
			{
				HSSFCell cell = (HSSFCell)cells.next();
				String cval = this.getCellValue(cell);
				retval.add(cval);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return retval;
	}



	private synchronized String getCellValue(HSSFCell cell)
	{
		String rval ="";
		try{
			rval = cell.getStringCellValue();
		}catch(Exception e)
		{
			try{
				rval = String.valueOf(cell.getNumericCellValue());
			}catch (Exception e2) {
				try{
					rval = String.valueOf(cell.getDateCellValue());
				}catch (Exception e3) {
					
				}
			}
		}
		return rval;
	}
	public synchronized void doCloseCon() {
		try {
			con.close();
			con = null;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
