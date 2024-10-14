package com.example.dietplanapp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期相关工具
 * <p>
 */
public class DateUtils {

    private static final String TAG = "DateUtils";
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 英文简写如：2010
     */
    public static String FORMAT_Y = "yyyy";

    /**
     * 英文简写如：09
     */
    public static String FORMAT_M = "MM";
    public static String FORMAT_D = "dd";

    /**
     * 获取当前日期
     *
     */
    public static String getCurrentDate(String format ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    public static String getNumDate(int num) {
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DATE,num);
        Date d=cal.getTime();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
        String result=sp.format(d);
        return result;
    }
    public static String getNumDate(int num, String format) {
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DATE,num);
        Date d=cal.getTime();
        SimpleDateFormat sp=new SimpleDateFormat(format);
        String result=sp.format(d);
        return result;
    }

    /*
    *获取第num年时间段num=0:今年 num=-1：去年
     */
    public static String getYearTimeInterval(int num){
        return getTimesYearByNum(num)+","+getTimesYearByNum(num+1);
    }
    /*
     *获取第num月时间段num=0:当月 num=-1：上个月
     */
    public static String getMonthTimeInterval(int num){
        return getTimesMonthByNum(num,"yyyy-MM-dd HH:mm:ss")+","+getTimesMonthByNum(num+1,"yyyy-MM-dd HH:mm:ss");
    }
    /*
     *获取第num周时间段num=0:这周 num=-1：上周
     */
    public static String getWeekTimeInterval(int num){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = c.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        System.out.println("dayweek:"+dayWeek);
        if (1 == dayWeek) {
           num=num-1;
        }
        return getTimesWeekByNum(num*7)+","+getTimesWeekByNum(7*(num+1));
    }
    // 获得本周一0点时间
    @SuppressLint("WrongConstant")
    public static String getTimesWeekmorning() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return sdf.format(cal.getTime());
    }
    // 获得本周日24点时间
    public static String getTimesWeekByNum(int num) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(str2Date(getTimesWeekmorning(),null));
        cal.add(Calendar.DAY_OF_WEEK, num);
        return sdf.format(cal.getTime());
    }

    // 获得本月第一天0点时间
    @SuppressLint("WrongConstant")
    public static String getTimesMonthmorning() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(cal.getTime());
    }

    public static String getTimesMonthByNum(int num, String format) {
        SimpleDateFormat sdf=new SimpleDateFormat(format);

        Calendar cal = Calendar.getInstance();
        cal.setTime(str2Date(getTimesMonthmorning(),null));
        cal.add(Calendar.MONTH, num);
        Date m = cal.getTime();
        return sdf.format(m);
    }
    public static String getMonthByNum(int num, String date, String format) {
        SimpleDateFormat sdf=new SimpleDateFormat(format);

        Calendar cal = Calendar.getInstance();
        cal.setTime(str2Date(date,null));
        cal.add(Calendar.MONTH, num);
        Date m = cal.getTime();
        return sdf.format(m);
    }

    // 获得本月最后一天24点时间
    @SuppressLint("WrongConstant")
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }



    private static String getTimesYearByNum(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(str2Date(getYearStart(),null));
        c.add(Calendar.YEAR, num);
        Date y = c.getTime();
        return format.format(y);
    }

    /**
     * 获取本年的第一天
     * @return String
     * **/
    public static String getYearStart(){
        return new SimpleDateFormat("yyyy").format(new Date())+"-01-01 00:00:00";
    }

    /**
     * 获取本年的最后一天
     * @return String
     * **/
    public static String getYearEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currYearLast = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast);
    }
    public static String getCurYear(String format) {
        return getYear(0, format);
    }
    public static String getYear(int year, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, year);
        return date2Str(calendar, format);
    }

    public static String getRecentYearByNum(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, num);
        Date y = c.getTime();
        String year = format.format(y);
        System.out.println("过去一年："+year);
        return year;
    }
    /**
     * 根据传入的日期，获取指定的日期
     *
     * @param strDate
     * @param day
     * @return
     */
    public static String getDay(String strDate, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);

        Date dateNew = calendar.getTime();
       // SimpleDateFormat format = new SimpleDateFormat("MM.dd");
        return simpleDateFormat.format(dateNew);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }
    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static String getCurMonth(String format) {
        return date2Str(new Date(), format);
    }

    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 比较两个日期的大小.
     *
     * @param format 日期格式
     * @return 后面的时间大于前面的时间时返回 -1，日期相等返回0，否则返回1。
     */
    public static int compareDate(String format, String mDate, String strAnotherDate) {
        if (TextUtils.equals(mDate, strAnotherDate)) {
            return 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date currentDate;
        Date anotherDate;
        try {
            currentDate = simpleDateFormat.parse(mDate);
            anotherDate = simpleDateFormat.parse(strAnotherDate);
            return currentDate.compareTo(anotherDate);
        } catch (ParseException e) {
            return 1;
        }
    }

    /**
     * 获取两个日期相差的月份
     */
    public static int getMonthSpace(String format, String startDate, String endDate) {
        int monthSpace = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(startDate));
            c2.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            Log.e(TAG, "getMonthSpace: ", e);
        }
        monthSpace = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        monthSpace += (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
        return Math.abs(monthSpace);
    }

    /**
     * 获取两个日期相差的天数
     */
    public static int getDaySpace(String format, String startDate, String endDate) {
        int daySpace = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(startDate));
            c2.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            Log.e(TAG, "getMonthSpace: ", e);
        }
        daySpace = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        daySpace += (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 365;
        return Math.abs(daySpace);
    }

    /**
     * 获取两个日期相差的天数
     */
    public static int getWeekSpace(String format, String startDate, String endDate) {
        return getDaySpace(format, startDate, endDate) / 7;
    }

    /**
     * 获取两个日期相差的小时
     */
    public static int getHourSpace(String format, String startDate, String finishDate) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        try {
            Date beginDate = sdf.parse(startDate);
            Date endDate = sdf.parse(finishDate);
            result = Math.abs(((int) (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60)));
        } catch (ParseException e) {
        }
        return result;
    }
    public static int getSecondSpace( String startDate, String finishDate) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.CHINA);
        try {
            Date beginDate = sdf.parse(startDate);
            Date endDate = sdf.parse(finishDate);
            result = Math.abs(((int) (endDate.getTime() - beginDate.getTime()) / (1000)));
        } catch (ParseException e) {
        }
        return result;
    }
}
