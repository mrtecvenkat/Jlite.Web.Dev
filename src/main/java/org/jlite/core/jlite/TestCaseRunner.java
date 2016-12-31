package org.jlite.core.jlite;

import java.io.File;
import org.jlite.core.datasource.ExcelFileHandler2;
import org.jlite.core.reports.LogReporter;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.testmodel.TestResults;
import org.jlite.core.util.DateUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCaseRunner {
	
	private TestRunContext rcontext;
	private LogReporter log;
	private String c_run_starttime;
	private TestStepRunner runner;
	private TestResults results;
	@Parameters({"inputfile_path","RECORD_ID","ITERATION_ID","TESTCASE_ID","outputpath", "corereportexcelpath", "resoucepath"})
	@BeforeTest
	public void doRunBeforeTestCase(String inputfile_path,String RECORD_ID, String ITERATION_ID, String TESTCASE_ID,String outputpath, String corereportexcelpath,String resoucepath)
	{
		log = new LogReporter(outputpath);
		log.info("Test Case <"+TESTCASE_ID+"> run started for iteration <"+ITERATION_ID +" for Record "+RECORD_ID+">");
		TestIteration citr = new TestIteration();
		citr.setITERATION_ID(ITERATION_ID);
		citr.setTESTCASE_ID(TESTCASE_ID);
		citr.setRECORD_ID(RECORD_ID);
		rcontext = new TestRunContext();
		rcontext.setInputfile(inputfile_path);
		rcontext.setBaseoutputpath(outputpath);
		rcontext.setTestresoucepath(resoucepath);
		rcontext.setTestexceloutputpath(corereportexcelpath);
		rcontext.setIteration(citr);
		rcontext.setDateutil(new DateUtil());
		rcontext.setLogreport(log);
		rcontext.setExcelinputfile(new ExcelFileHandler2(rcontext.getInputfile()));
		rcontext.setExcelreport(new ExcelFileHandler2(rcontext.getTestexceloutputpath()));
		rcontext.setData(rcontext.getExcelinputfile().getTestData(rcontext.getIteration().getITERATION_ID()));
		rcontext.setTeststeps(rcontext.getExcelinputfile().getTestSteps(rcontext.getIteration().getTESTCASE_ID()));
		c_run_starttime = rcontext.getDateutil().getCureentDateTimeStamp();
	}
	
	@Test
	public void doRunTestCase()
	{
		rcontext.setAntman(new ActionManager(getDriver(new File(rcontext.getInputfile()).getName())));
		System.out.println("i am test");
		log.info("Started... Test Case <"+rcontext.getIteration().getTESTCASE_ID()+"> run started for iteration <"+rcontext.getIteration().getITERATION_ID() +" for Record "+rcontext.getIteration().getRECORD_ID()+">");
		log.info("Size -- "+rcontext.getTeststeps().size());
		runner = new TestStepRunner(rcontext);
		results = runner.doRunTestSteps();
		if(results.getResult()==0)
		{
			rcontext.getIteration().setRUN_RESULTS("PASS");
			rcontext.getIteration().setRUN_DESCRIPTION("Done");
			Assert.assertEquals("PASS", "PASS");
		}else{
			rcontext.getIteration().setRUN_RESULTS("FAIL");
			rcontext.getIteration().setRUN_DESCRIPTION(results.getDescription());
			Assert.assertEquals(results.getDescription(), "PASS");
		}
		
		
	}
	@AfterTest
	public void doRunAfterTestCase()
	{
		String run_end_time = rcontext.getDateutil().getCureentDateTimeStamp();
		String run_total_time = rcontext.getDateutil().getDateTimeDiffernect(c_run_starttime);
		rcontext.getIteration().setRUN_START_TIME(c_run_starttime);
		rcontext.getIteration().setRUN_END_TIME(run_end_time);
		rcontext.getIteration().setRUN_TOTAL_TIME(run_total_time);
		rcontext.getExcelreport().doReport(rcontext.getIteration());
		rcontext.getExcelinputfile().doCloseCon();
		rcontext.getExcelreport().doCloseCon();
		log.info("completed... Test Case <"+rcontext.getIteration().getTESTCASE_ID()+"> run started for iteration <"+rcontext.getIteration().getITERATION_ID() +" for Record "+rcontext.getIteration().getRECORD_ID()+">");
		log.info("-----------------------------");
	}
	
	public WebDriver getDriver(String filename)
	{
		System.out.println(filename);
		WebDriver driver = null;
		try{
		if(filename.toLowerCase().contains("ie_"))
        {
        	System.setProperty("webdriver.ie.driver", new File(rcontext.getTestresoucepath()).getAbsolutePath()+"\\IEDriverServer.exe");
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
			dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(dc);
        }else if(filename.toLowerCase().contains("chrome_"))
        {
        	System.setProperty("webdriver.chrome.driver", new File(rcontext.getTestresoucepath()).getAbsolutePath()+"\\chromedriver.exe");
        	ChromeOptions c = new ChromeOptions();
			c.addArguments("disable-extensions");
			driver = new ChromeDriver(c); 
        }else if(filename.toLowerCase().contains("mozilla_"))
        {
        	driver = new FirefoxDriver();
        }else{
        	System.out.println("unable to set browser no match prowsers are are"+filename);
        }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return driver;
	}

}
