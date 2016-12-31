package org.jlite.core.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import org.jlite.core.jlite.TestKeywords;
import org.jlite.core.testmodel.TestCase;
import org.jlite.core.testmodel.TestData;
import org.jlite.core.testmodel.TestGlobalData;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.testmodel.TestStep;

public class ExcelFileHandler {

	String path = "";
	Connection con;

	public ExcelFileHandler(String filepath) {
		System.out.println("Enter excel file");
		System.out.println(filepath);
		path = filepath;

		con = this.getConnection();

	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {

			// log.// log(// log.INFO, "Excel File Manager started processing
			// for the File "+path);
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + path + ";";
			Connection con = DriverManager.getConnection(myDB, "", "");
			return con;
		} catch (Exception er) {
			// log.// log(// log.ERROR, "Exception Occoured: @
			// getConnection"+er.getMessage());
			er.printStackTrace();
			return null;
		}
	}
	public List<TestIteration> getAllTestIterations()
	{
		List<TestIteration> retval = new ArrayList<TestIteration>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_ITERATION_MAP+"$]");
			while (rs.next()) {
				String runflag = rs.getString(TestKeywords.COL_NAME_RUNFLAG);
				if (runflag.equalsIgnoreCase("y")) {
					TestIteration itr = new TestIteration();
					itr.setRECORD_ID(rs.getString(TestKeywords.COL_NAME_RECORD_ID));
					itr.setRUN_FLAG(runflag);
					itr.setITERATION_ID(rs.getString(TestKeywords.COL_NAME_ITERATION_ID));
					itr.setTESTCASE_ID(rs.getString(TestKeywords.COL_NAME_TESTCASE_ID));
					itr.setRUN_RESULTS("");
					itr.setRUN_DESCRIPTION("");
					itr.setRUN_START_TIME("");
					itr.setRUN_END_TIME("");
					itr.setRUN_TOTAL_TIME("");
					retval.add(itr);
				}

			}
		} catch (Exception e) {

		}
		return retval;
	}
	public TestIteration getTestIterations(String recordid)
	{
		TestIteration itr = new TestIteration();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_ITERATION_MAP+"$]");
			while (rs.next()) {
				String runflag = rs.getString(TestKeywords.COL_NAME_RECORD_ID);
				if (runflag.equalsIgnoreCase(recordid)) {
					
					itr.setRECORD_ID(rs.getString(runflag));
					itr.setRUN_FLAG(TestKeywords.COL_NAME_RUNFLAG);
					itr.setITERATION_ID(rs.getString(TestKeywords.COL_NAME_ITERATION_ID));
					itr.setTESTCASE_ID(rs.getString(TestKeywords.COL_NAME_TESTCASE_ID));
					itr.setRUN_RESULTS("");
					itr.setRUN_DESCRIPTION("");
					itr.setRUN_START_TIME("");
					itr.setRUN_END_TIME("");
					itr.setRUN_TOTAL_TIME("");
					
				}

			}
		} catch (Exception e) {

		}
		return itr;
	}
	public List<TestCase> getAllTestCases()
	{
		List<TestCase> retval = new ArrayList<TestCase>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_TESTCASES+"$]");
			while (rs.next()) {
				String runflag = rs.getString(TestKeywords.COL_NAME_RUNFLAG);
				if (runflag.equalsIgnoreCase("y")) {
					TestCase itr = new TestCase();
					itr.setRECORD_ID(rs.getString(TestKeywords.COL_NAME_RECORD_ID));
					itr.setRUN_FLAG(runflag);
					itr.setTESTCASE_ID(rs.getString(TestKeywords.COL_NAME_TESTCASE_ID));
					itr.setPROJECT_ID(rs.getString(TestKeywords.COL_NAME_PROJECT_ID));
					itr.setPROJECT_NAME(rs.getString(TestKeywords.COL_NAME_PROJECT_NAME));
					itr.setFEATURE_ID(rs.getString(TestKeywords.COL_NAME_FEATURE_ID));
					itr.setFEATURE_NAME(rs.getString(TestKeywords.COL_NAME_FEATURE_NAME));
					itr.setUSERSTORY_ID(rs.getString(TestKeywords.COL_NAME_USERSTORY_ID));
					itr.setUSERSTORY_NAME(rs.getString(TestKeywords.COL_NAME_USERSTORY_NAME));
					itr.setTESTCASE_NAME(rs.getString(TestKeywords.COL_NAME_TESTCASE_NAME));
					itr.setRUN_RESULTS("");
					itr.setRUN_DESCRIPTION("");
					itr.setRUN_START_TIME("");
					itr.setRUN_END_TIME("");
					itr.setRUN_TOTAL_TIME("");
					retval.add(itr);
				}

			}
		} catch (Exception e) {

		}
		
		return retval;
	}
	

	public List<TestStep> getTestSteps(String testcaseid) {
		List<TestStep> retval = new ArrayList<TestStep>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_TESTSTEP+"$]");
			while (rs.next()) {
				String runflag = rs.getString(TestKeywords.COL_NAME_RUNFLAG);
				if (runflag.equalsIgnoreCase("y")) {
					String tcaseid = rs.getString(TestKeywords.COL_NAME_TESTCASE_ID);
					if(tcaseid.equalsIgnoreCase(testcaseid))
					{
						TestStep step = new TestStep();
						step.setRECORD_ID(rs.getString(TestKeywords.COL_NAME_RECORD_ID));
						step.setRUN_FLAG(runflag);
						step.setTESTCASE_ID(tcaseid);
						step.setCOMMAND(rs.getString(TestKeywords.COL_NAME_COMMAND));
						step.setTARGET(rs.getString(TestKeywords.COL_NAME_TARGET));
						step.setVALUE(rs.getString(TestKeywords.COL_NAME_VALUE));
						step.setRUN_RESULTS("");
						step.setRUN_DESCRIPTION("");
						step.setRUN_START_TIME("");
						step.setRUN_END_TIME("");
						step.setRUN_TOTAL_TIME("");
						retval.add(step);
					}
				}

			}
		} catch (Exception e) {

		}
		return retval;
	}
	public Hashtable<String, String> getGlobalData() {
		Hashtable<String, String> retval = new Hashtable<String, String>();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from [Global_Data$]");
			while (rs.next()) {
				String tcaseid = rs.getString(TestKeywords.COL_NAME_DATAKEY);
				String iterationid = rs.getString(TestKeywords.COL_NAME_DATAVALUE);
				try {
						retval.put(iterationid, tcaseid);
				} catch (Exception e) {

				}

			}
		} catch (Exception e) {

		}
		return retval;
	}
	public TestCase getTestCase(String testcaeid) {
		TestCase retval = new TestCase();
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			ResultSet rs;
			st = con.createStatement();
			rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_TESTCASES+"$]");
			while (rs.next()) {
				String tcaseid = rs.getString(TestKeywords.COL_NAME_TESTCASE_ID);
				if(tcaseid.equalsIgnoreCase(testcaeid))
				{
					retval.setPROJECT_ID(rs.getString(TestKeywords.COL_NAME_PROJECT_ID));
					retval.setPROJECT_NAME(rs.getString(TestKeywords.COL_NAME_PROJECT_NAME));
					retval.setFEATURE_ID(rs.getString(TestKeywords.COL_NAME_FEATURE_ID));
					retval.setFEATURE_NAME(rs.getString(TestKeywords.COL_NAME_FEATURE_NAME));
					retval.setUSERSTORY_ID(rs.getString(TestKeywords.COL_NAME_USERSTORY_ID));
					retval.setUSERSTORY_NAME(rs.getString(TestKeywords.COL_NAME_USERSTORY_NAME));
					retval.setTESTCASE_ID(tcaseid);
					retval.setTESTCASE_NAME(rs.getString(TestKeywords.COL_NAME_TESTCASE_NAME));
//					retval.setTeststeps(this.getTestSteps(testcaeid));
					break;
				}

			}
		} catch (Exception e) {

		}
		return retval;
	}
	public TestData getTestData(String itrid)
	{
		TestData data = new TestData();
		TestGlobalData gdata = new TestGlobalData();
		gdata.setData(this.getGlobalData());
		data.setGlobalData(gdata);
		data.setITERATION_ID(itrid);
		data.setTestdata(this.getDataSetByRecordId(itrid));
		return data;
	}
	public Hashtable<String, String> getDataSetByRecordId(String iterationid) {
		// TODO Auto-generated method stub
		// Hashtable<String, Hashtable<String, String>> recordValues= null;

		Hashtable<String, String> colNameAndValue = null;
		if (con == null) {
			con = this.getConnection();
		}
		try {
			Statement st;
			// ResultSetMetaData rsm;
			ResultSet rs;
			st = con.createStatement();

			try {
				rs = st.executeQuery("Select * from ["+TestKeywords.TBL_NAME_TEST_DATA+"$]");
				// recordValues= new Hashtable<String,
				// Hashtable<String,String>>();
				ArrayList<String> colNames = this.getTableColNames(rs);

				while (rs.next()) {
					try {
						String rflag = rs.getString(TestKeywords.COL_NAME_RUNFLAG);
						String itrid = rs.getString(TestKeywords.COL_NAME_ITERATION_ID);
						if (itrid.equalsIgnoreCase(iterationid) && rflag.equalsIgnoreCase("Y")) {
							colNameAndValue = new Hashtable<String, String>();
							for (int j = 0; j < colNames.size(); j++) {
								
									String colvalue = rs.getString(colNames.get(j));
									colNameAndValue.put(colNames.get(j).toUpperCase(), colvalue);
								
							}

							// recordValues.put(recordid, colNameAndValue);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				con.close();
				con = null;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return colNameAndValue;
	}

	private ArrayList<String> getTableColNames(ResultSet vrs) {
		ArrayList<String> retval = null;
		if (con == null) {
			con = this.getConnection();
		}
		try {
			// Statement st;
			ResultSetMetaData rsm;
			// ResultSet rs;
			rsm = vrs.getMetaData();
			retval = new ArrayList<String>();
			for (int j = 1; j < rsm.getColumnCount() + 1; j++) {
				try {

					retval.add(rsm.getColumnName(j));
				} catch (Exception e) {
					// TODO: handle exception
					// e.printStackTrace();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return retval;
	}

//	private ArrayList<String> getTableNames() {
//		ArrayList<String> retval = null;
//		if (con == null) {
//			con = this.getConnection();
//		}
//		try {
//			retval = new ArrayList<String>();
//			ResultSet rs = con.getMetaData().getTables(null, null, "%", null);
//
//			// ResultSetMetaData rsm=rs.getMetaData();
//			// int cno=rsm.getColumnCount();
//			// for(int i=0; i<cno; i++){
//			// System.out.print ("\t"+rsm.getColumnName(i+1));
//			// }
//			while (rs.next()) {
//				String tablename = rs.getString(3).replace("_$", "");
//				tablename = tablename.replace("$", "");
//				if (!(tablename.equalsIgnoreCase("Components") || tablename.equalsIgnoreCase("GlobalData")
//						|| tablename.equalsIgnoreCase("Scenaios") || tablename.equalsIgnoreCase("TestCases")
//						|| tablename.equalsIgnoreCase("TestIteration") || tablename.equalsIgnoreCase("TestSteps"))) {
//					retval.add(tablename);
//				}
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return retval;
//	}

	public void doCloseCon() {
		try {
			con.close();
			con = null;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
