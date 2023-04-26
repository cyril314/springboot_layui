package com.fit.util;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @className: DateUtil
 * @description: 时间处理工具
 * @author: Aim
 * @date: 2023/4/13
 **/
public class DateUtil {

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String STR_DATE_FULL = "yyyy-MM-dd HH:mm:ss";
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String STR_DATE_LONG = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 年月日 yyyy-MM-dd
     */
    public static final String STR_DATE_SMALL = "yyyy-MM-dd";
    /**
     * 年月日时分秒(无横划线) yyyyMMddHHmmss
     */
    public static final String STR_DATE_All_KEY = "yyyyMMddHHmmss";

    /**
     * 获取当前timestamp
     */
    public static String getCurTimeStamp() {
        return System.currentTimeMillis() + "";
    }

    /**
     * 获取YYYY格式
     */
    public static String getYear() {
        return getYear(new Date());
    }

    /**
     * 获取YYYY格式
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 获取YYYY-MM-DD格式
     */
    public static String getDay() {
        return getDay(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     */
    public static String getDay(Date date) {
        return formatDate(date, STR_DATE_SMALL);
    }

    /**
     * 获取YYYYMMDD格式
     */
    public static String getDays() {
        return getDays(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     */
    public static String getTime() {
        return formatDate(new Date(), STR_DATE_FULL);
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     */
    public static String getMsTime() {
        return formatDate(new Date(), STR_DATE_LONG);
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return formatDate(new Date(), STR_DATE_All_KEY);
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     */
    public static String getTime(Date date) {
        return formatDate(date, STR_DATE_FULL);
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.hasLength(pattern)) {
            formatDate = format(date, pattern);
        } else {
            formatDate = format(date, STR_DATE_SMALL);
        }
        return formatDate;
    }

    /**
     * (日期比较，如果s>=e 返回true 否则返回false)
     *
     * @param s
     * @param e
     */
    public static boolean compareDate(String s, String e) {
        if (parseDate(s) == null || parseDate(e) == null) {
            return false;
        }
        return parseDate(s).getTime() >= parseDate(e).getTime();
    }

    /**
     * 格式化日期
     */
    public static Date parseDate(String date) {
        return parse(date, STR_DATE_SMALL);
    }

    /**
     * 格式化日期
     */
    public static Date parseTime(String date) {
        return parse(date, STR_DATE_FULL);
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化日期
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return simpleDateFormat.format(calender.getTime());
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     */
    public static boolean isValidDate(String s) {
        return parse(s, STR_DATE_FULL) != null;
    }

    /**
     * 校验日期是否合法
     */
    public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat(STR_DATE_SMALL);
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Aim
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(STR_DATE_SMALL);
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        return format(date, STR_DATE_FULL);
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            //sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }

    /**
     * 格式化Oracle Date
     *
     * @return
     */
//	public static String buildDateValue(Object value){
//		if(Func.isOracle()){
//			return "to_date('"+ value +"','yyyy-mm-dd HH24:MI:SS')";
//		}else{
//			return Func.toStr(value);
//		}
//	}
    public static void main(String[] args) {
        System.out.println(getTime(new Date()));
        System.out.println(getAfterDayWeek("3"));
    }
}
