package com.fit.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: TimeUtil 
* @Description: 获取当前时间处理类
* @author: chenwt
* @date: 2017年6月7日 下午5:39:59 
* @Company: zkzj
* @Copyright: zkzj
*/
public class TimeUtil {

	public static String getFormatString(String formatString){
	   DateFormat df = new SimpleDateFormat(formatString);
	   return  df.format(new Date());
	}
	

	public static DateFormat getDateFormat(String formatString){
		   DateFormat df = new SimpleDateFormat(formatString);
		   return df;
	}
	
	public static Timestamp getTimestamp(String timeString,String formatString) throws ParseException{
		DateFormat df = getDateFormat(formatString);
		Timestamp ts = new Timestamp(df.parse(timeString).getTime());
		return ts;
	}
	
	public static String getStringByFormat(Timestamp ts,String formatString) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        String ret = simpleDateFormat.format(ts);
		return ret;
	}
	 /* 
     * 将时间戳转换为时间Timestamp
     */
    public static Timestamp stampToDate(String timeStamp) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.FORMAT_YYYYMMDDHHMMSS_STRING);
        long longTime = new Long(timeStamp);
        Date date = new Date(longTime);
        String timeString = simpleDateFormat.format(date);
        return getTimestamp(timeString ,Const.FORMAT_YYYYMMDDHHMMSS_STRING);
    }
	
	public static void main(String[] args) throws ParseException{
		Timestamp ts = getTimestamp("2017-06-27","yyyy-MM-dd");
		Timestamp ts2 = TimeUtil.stampToDate("1497819295275");
		System.out.println("--ts---"+ts);
		System.out.println("--ts2---"+ts2);

	}
}
