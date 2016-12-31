package org.jlite.core.reports;

import java.io.File;
import java.io.PrintStream;

import org.jlite.core.util.DateUtil;

public class LogReporter {

	private  File file = null;
	private  PrintStream fileStream = null;
	private  String logfilename = "RunLogger.txt";
	private  DateUtil dutil;
	public  LogReporter (String outputpath)
	{
		dutil = new DateUtil();
		logfilename = outputpath+"\\"+logfilename;
		//System.out.println(logfilename);
		file = new File(logfilename);
		try{
		if(!file.exists()){
			file.createNewFile();
			
		}
		fileStream =  new PrintStream(file);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
//		return new LogReporter();
	}
	
	
	public  void warn(String msg)
	{
//		BufferedWriter bufferWritter= null;
		try{
			String tomsg = "[WARNING] "+ dutil.getCureentDateTimeStamp()+ " >> "+msg;
			fileStream.println(tomsg);
			System.out.println(tomsg);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public  void info(String msg)
	{
//		BufferedWriter bufferWritter= null;
		try{
			String tomsg = "[INFO] "+ dutil.getCureentDateTimeStamp()+ " >> "+msg;
			fileStream.println(tomsg);
			System.out.println(tomsg);
	        
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public  void err(String msg)
	{
//		BufferedWriter bufferWritter= null;
		try{
			String tomsg = "[ERROR] "+ dutil.getCureentDateTimeStamp()+ " >> "+msg;
			fileStream.println(tomsg);
			System.out.println(tomsg);
	        
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
