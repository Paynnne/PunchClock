package com.chuoengda.punchclock.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author：chupengda
 * @Date: 2017/11/13 14:24
 * @Annotation:
 */

public class TimeUtils {

    /**
     * 获取当前时间 yyyy-MM-dd hh:mm:ss
     */
    public static String getTimeYYYY_MM_DD_HH_MM_SS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 yyyy-MM-dd
     */
    public static String getTimeYYYY_MM_DD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 hh:mm
     */
    public static String getTimeHH_MM() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间的后一天时间
     *
     * @param cl
     * @return
     */
    private static Calendar getAfterDay(Calendar cl) {
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day + 1);
        String mYear = String.valueOf(cl.get(Calendar.YEAR));// 获取当前年份
        String mMonth = String.valueOf(cl.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        return cl;
    }

    /**
     * 将字符串转为时间戳
     */
    public static Long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Long.valueOf((date.getTime() + "").substring(0, 10));
    }

    /**
     * 将时间戳转化为毫秒
     *
     * @param str
     * @return
     */
    public static String dateTimeMs(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long msTime = -1;
        try {
            msTime = simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(msTime);

    }

    /**
     * 获得给定时间与当前时间的时间差
     * 时间戳相减 既是 像隔秒数
     *
     * @return int 毫秒
     */
    public static String getEquationOfTime(String time) {

        //是否是同一天
        if (getStringToDate(getTimeYYYY_MM_DD_HH_MM_SS()) >=
                getStringToDate(getTimeYYYY_MM_DD() + " " + time)) {
            //第二天的
            Calendar cl = Calendar.getInstance();
            int day = cl.get(Calendar.DATE);
            cl.set(Calendar.DATE, day + 1);
            String mYear = String.valueOf(cl.get(Calendar.YEAR));// 获取当前年份
            String mMonth = String.valueOf(cl.get(Calendar.MONTH) + 1);// 获取当前月份
            String mDay = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码

            String settime = mYear + "-" + mMonth + "-" + mDay + " " + time;
            Log.e("aaaaa", "TIME1:" + (getStringToDate(settime) - getStringToDate(getTimeYYYY_MM_DD_HH_MM_SS())) + "/" + settime);
            return (getStringToDate(settime) - getStringToDate(getTimeYYYY_MM_DD_HH_MM_SS())) + "000";

        } else {
            //今天的
            Log.e("aaaaa", "TIME2:" + (getStringToDate(getTimeYYYY_MM_DD() + " " + time) - getStringToDate(getTimeYYYY_MM_DD_HH_MM_SS())) + "");
            return (getStringToDate(getTimeYYYY_MM_DD() + " " + time) - getStringToDate(getTimeYYYY_MM_DD_HH_MM_SS())) + "000";
        }
    }
}
