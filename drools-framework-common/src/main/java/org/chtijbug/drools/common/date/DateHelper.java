package org.chtijbug.drools.common.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 04/06/13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class DateHelper {
	public static String sFormat = "yyyy-MM-dd";

	public static Date getDate(String sDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		return sdf.parse(sDate);
	}

	public static Date getDate(String sDate, String anotherFormat)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(anotherFormat);
		return sdf.parse(sDate);
	}
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        String formatedDate = sdf.format(date);
        return formatedDate;
    }
    public static String getDate(Date date, String anotherFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(anotherFormat);
        String formatedDate = sdf.format(date);
        return formatedDate;
    }
}
