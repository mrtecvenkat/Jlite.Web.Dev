package org.jlite.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public String getCureentDateTimeStamp()
	{
		Calendar cal = Calendar.getInstance();
		int cMONTH = cal.get(Calendar.MONTH)+1;
		int cDAY = cal.get(Calendar.DATE);
		int cYEAR = cal.get(Calendar.YEAR);
		int cHOUR = cal.get(Calendar.HOUR);
		int cMIN = cal.get(Calendar.MINUTE);
		int cSEC = cal.get(Calendar.SECOND);
		return this.getByZero(cMONTH)+"/"+this.getByZero(cDAY)+"/"+cYEAR+" "+this.getByZero(cHOUR)+":"+this.getByZero(cMIN)+":"+this.getByZero(cSEC);
	}
	public String getCurrentTimeStampAsString()
	{
		String cstamp = this.getCureentDateTimeStamp();
		return cstamp.replaceAll("/", "").replaceAll(":", "").replaceAll(" ", "");
	}
	public String getDateTimeDiffernect(String pdatetime)
	{
		String cdatetime = this.getCureentDateTimeStamp();
		String retval = "";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(pdatetime);
			d2 = format.parse(cdatetime);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffMilSeconds = diff / 1000;
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			retval = this.getByZero(diffHours)+":"+this.getByZero(diffMinutes)+":"+this.getByZero(diffSeconds)+":"+this.getByZero(diffMilSeconds);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retval;

	}
	private String getMonth(int month)
	{
		String retval = "";
		switch(month)
		{
		
		case 1: retval = "JAN"; break;
		case 2: retval = "FEB"; break;
		case 3: retval = "MAR"; break;
		case 4: retval = "APR"; break;
		case 5: retval = "MAY"; break;
		case 6: retval = "JUN"; break;
		case 7: retval = "JUL"; break;
		case 8: retval = "AUG"; break;
		case 9: retval = "SEP"; break;
		case 10: retval = "OCT"; break;
		case 11: retval = "NOV"; break;
		case 12: retval = "DEC"; break;
		default : break;
		}
		return retval;
	}
	private String getByZero(int day)
	{
		String retval ="";
		if(day<10)
		{
			retval = "0"+day;
		}else{
			retval = String.valueOf(day);
		}
		return retval;
	}
	private String getByZero(long day)
	{
		String retval ="";
		if(day<10)
		{
			retval = "0"+day;
		}else{
			retval = String.valueOf(day);
		}
		return retval;
	}

}
