package org.jlite.core.jlite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlite.core.datasource.ExcelFileHandler2;
import org.jlite.core.reports.LogReporter;
import org.jlite.core.testmodel.TestData;
import org.jlite.core.testmodel.TestIteration;
import org.jlite.core.testmodel.TestResults;
import org.jlite.core.testmodel.TestStep;
import org.jlite.core.util.DateUtil;
import org.openqa.selenium.WebDriver;

public class TestStepRunner {
	
	private TestData data;
	private TestIteration iteration;
	private ExcelFileHandler2 excelinputfile;
	private ExcelFileHandler2 excelreport;
	private LogReporter logreport;
	private ActionManager antman;
	private String inputfile;
	private String inputpath;
	private String baseoutputpath;
	private String testexceloutputpath;
	private String testresoucepath;
	private Hashtable<String, String> contextdata;
	private DateUtil dateutil;
	private List<TestStep> teststeps;
	private TestRunContext rcontext;
	public TestStepRunner(TestRunContext context)
	{
		rcontext = context;
		rcontext = context=rcontext;
		data=rcontext.getData();
		iteration=rcontext.getIteration();
		excelinputfile=rcontext.getExcelinputfile();
		excelreport=rcontext.getExcelreport();
		logreport=rcontext.getLogreport();
		antman=rcontext.getAntman();
		inputfile=rcontext.getInputfile();
		inputpath=rcontext.getInputpath();
		baseoutputpath=rcontext.getBaseoutputpath();
		testexceloutputpath=rcontext.getTestexceloutputpath();
		testresoucepath=rcontext.getTestresoucepath();
		contextdata=rcontext.getContextdata();
		dateutil=rcontext.getDateutil();
		teststeps=rcontext.getTeststeps();
		
		
	}
	public TestResults doRunTestSteps()
	{
		TestResults res = new TestResults();
		String oRes = "";
		for(TestStep step:teststeps)
		{
			String cmd = step.getCOMMAND();
			step.setTARGET(this.getFormatedValue(step.getTARGET()));
			step.setVALUE(this.getFormatedValue(step.getVALUE()));
					
			if(!cmd.contains(TestKeywords.FLAG_CONTROLLER))
			{
				oRes = antman.doAction(step);
			}else{
				oRes = this.doRunController(cmd.replaceAll(TestKeywords.FLAG_CONTROLLER, ""), step.getVALUE());
			}
			
			if(oRes.equalsIgnoreCase(""))
			{
				 res.setResult(0);
				 res.setDescription("");
			}
//			else if(step.getCOMMAND().toLowerCase().contains(TestKeywords.ACTION_STORE))
//			{
//				tcstep.setRESULTS(AKW.LOG_KEY_PASS);
//				 tcstep.setDESCRIPTION(retval);
//				 //dh.doStore(tcstep.getVALUE(), itrid, retval);
//			}
			else{
				res.setResult(1);
				res.setDescription(oRes);
				
			}
			
		}
		
		return res;
	}
	public String getFormatedValue(String expression)
	{
		String retval="";
		String pattern = "(\\{.+?::.+?\\})";
		List<String> matchlist = new ArrayList<String>();
		Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(expression);
	    while(m.find())
	    {
	        matchlist.add(m.group()); 
	    }
	    if(matchlist.size()!=0)
	    {
	    	for(int i=0;i<matchlist.size();i++)
	    	{
	    		String matchitem = matchlist.get(i);
	    		String itemtowork[] = matchitem.replace("{", "").replace("}", "").split("::");
	    		if(itemtowork[0].equalsIgnoreCase("gbldata"))
	    		{
	    			retval = this.getValueFromData(data.getGlobalData().getData(),itemtowork[1]);
	    			
	    		}else{
	    			retval = this.getValueFromRecordData(data.getTestdata(), itemtowork[1]);
	    		}
	    		if(retval!=null)
	    		{
	    			expression = expression.replace(matchitem, retval);
	    		}
	    	}
	    }else{
	    	//retval = expression;
	    	
	    }

		return expression;
	}
	
	public String getValueFromData(Hashtable<String, String> gbldata, String vkey)
	{
		String retval="";
		if(gbldata.containsKey(vkey.toUpperCase()))
		{
			retval = gbldata.get(vkey.toUpperCase());
		}
		return retval;
	}
	public String getValueFromRecordData(Hashtable<String, String> gbldata, String vkey)
	{
		String retval="";
		if(gbldata.containsKey(vkey.toUpperCase()))
		{
			retval = gbldata.get(vkey.toUpperCase());
		}
		return retval;
	}
	
	@SuppressWarnings("unchecked")
	private String doRunController(String controllername,String methodname)
	{
		String retval=null;
		Object vrval = null;
		try{
			String mname="";
			if(methodname.equals(TestKeywords.EMPTY))
			{
				mname = TestKeywords.FLAG_CONTROLLERMAINMETHOD;
			}else{
				mname = methodname;
			}
			
			@SuppressWarnings("rawtypes")
			Class c = Class.forName(TestKeywords.FLAG_CONTROLLERPACKNAME+controllername);
			Method ms[]=c.getDeclaredMethods();
			for(int i=0;i<ms.length;i++)
			{
				
					
					if(ms[i].getName().equalsIgnoreCase(mname))
					{
						vrval = ms[i].invoke(c.newInstance(),rcontext);
						break;
					}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		return retval = vrval.toString();	
	}
	

}
