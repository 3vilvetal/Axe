package system.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date 
{
	/**
	 * Gets current date in needed format
	 * @return
	 */
	public static String getCurrentDate()
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss");
		
		String date = sdf.format(calendar.getTime());
		
		return date;
	}
}
