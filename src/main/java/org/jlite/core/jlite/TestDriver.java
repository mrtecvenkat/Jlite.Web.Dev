package org.jlite.core.jlite;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jlite.core.datasource.ExcelFileHandler2;
import org.jlite.core.reports.LogReporter;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.util.AutoFileUtil;
import org.jlite.core.util.DateUtil;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public class TestDriver {
	
	public static void main(String[] args) {
		LogReporter log;
		DateUtil dutil = new DateUtil();
		AutoFileUtil fileutil = new AutoFileUtil();
		try{
		
		System.out.println("======================== Started ==================");
		
		String basepath="";
		if(TestKeywords.PATH_BASE_PATH.equalsIgnoreCase(""))
		{
			basepath ="";
		}else{
			basepath = TestKeywords.PATH_BASE_PATH;
		}
		
		File cfile = new File(basepath);
		String temp = cfile.getAbsolutePath();
//		System.out.println("File >>>> @"+temp);
		File nfile = new File(temp).getParentFile();
//		System.out.println(nfile);
        String ifile = nfile.getAbsolutePath();
		String inputpath = ifile+"\\"+TestKeywords.FOLDER_NAME_INPUT;
		
		String outputpath = ifile+"\\"+TestKeywords.FOLDER_NAME_OUTPUT;
		String resoucepath = ifile+"\\"+TestKeywords.FOLDER_NAME_RESOURSE;	
		File[] files = new File(inputpath).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".xls");
		    }
		});
		log = new LogReporter(outputpath);
		if(files.length!=0)
		{
			
			ExcelFileHandler2 excelfile;
			List<XmlSuite> iterationsuite = new ArrayList<XmlSuite>();
			XmlSuite suite;
			for(int i=0;i<files.length;i++)
			{	
				log.info("Music Started.... @ "+files[i].getPath());
//				log.warn("Music Started....");
				
				String fnamee = files[i].getName();
				String nfolder = dutil.getCurrentTimeStampAsString();
				String corereportpath = fnamee.replaceAll(".xls", "")+nfolder;
				corereportpath = outputpath+"\\"+corereportpath;
				new File(corereportpath).mkdirs();
				String corereportexcelpath = corereportpath+"\\"+files[i].getName();
				fileutil.doCopyFile(files[i].getPath(), corereportexcelpath);
				excelfile = new ExcelFileHandler2(files[i].getPath());
				List<TestIteration> testiters = excelfile.getAllTestIterations();
				
				suite = new XmlSuite();
				
//			    suite.setParameters(suite_params);
			    suite.setName( "TestSuite for "+files[i].getName() );
			    XmlTest xmlTest;
			    for(TestIteration itr:testiters)
			    {
			    	
			    	String tcaseid = itr.getTESTCASE_ID();
			    	String trecordid = itr.getRECORD_ID();
			    	String titerid = itr.getITERATION_ID();
			    	xmlTest = new XmlTest(suite);
			    	Map<String, String> test_params = new HashMap<String, String>();
					test_params.put("inputfilepath", files[i].getPath());
					test_params.put("inputpath", inputpath);
					test_params.put("outputpath", corereportpath);
					test_params.put("resoucepath", resoucepath);
					test_params.put("inputfile_path", files[i].getPath());
			    	test_params.put(TestKeywords.COL_NAME_TESTCASE_ID, tcaseid);
			    	test_params.put(TestKeywords.COL_NAME_ITERATION_ID, titerid);
			    	test_params.put(TestKeywords.COL_NAME_RECORD_ID, trecordid);
			    	test_params.put("corereportexcelpath",corereportexcelpath);
			    			
			    	xmlTest.setName(trecordid+"-"+tcaseid+"-"+titerid);
			    	xmlTest.setParameters(test_params);
   				    xmlTest.setVerbose( 0 );
				    XmlClass xmlClass = new XmlClass(org.jlite.core.jlite.TestCaseRunner.class);
				    xmlTest.getClasses().add(xmlClass);
			    }
			    iterationsuite.add(suite);    
			    
			}
			TestNG testng = new TestNG();
			testng.setParallel("tests");
			testng.setThreadCount(files.length);
			TestListenerAdapter tla = new TestListenerAdapter();
			testng.addListener(tla);
			testng.setXmlSuites(iterationsuite);
			testng.run();
		}else{
			log.err("No Input files found at Location @"+inputpath);
		}
		}catch (Exception e) {
			//log.err("Error Occoured @"+TestDriver.class.getName().toString());
			e.printStackTrace();
		}
	}

}
