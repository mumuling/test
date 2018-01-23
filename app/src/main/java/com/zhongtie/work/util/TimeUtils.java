/*
 *
 *  Copyright (C) 2016 ShallCheek
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.zhongtie.work.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    private static DateFormat formatEnd = new SimpleDateFormat("MM月dd日");
    private static DateFormat formatPinJia = new SimpleDateFormat("yyy-MM-dd");
    private static DateFormat formatStart = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static DateFormat formatCreat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat startTimeFormat = new SimpleDateFormat("HH:mm");
    private static DateFormat TemporaryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat from_temporaryDate = new SimpleDateFormat("dd日 HH:mm");


    private static SimpleDateFormat mYDateTimeFormat;
    private static SimpleDateFormat mDateTimeFormat;
    private static SimpleDateFormat mYDateFormat;
    private static String[] sTimeUnits;

    public static String formatYDateTime(long timestamp) {
        if (mYDateTimeFormat == null) {
            mYDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
        if (timestamp == 0) {
            return "-";
        }
        return mYDateTimeFormat.format(new Date(timestamp));
    }


    public static String formatDateTime(long timestamp) {
        if (mDateTimeFormat == null) {
            mDateTimeFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        }
        if (timestamp == 0) {
            return "-";
        }
        return mDateTimeFormat.format(new Date(timestamp));
    }

    public static String formatDateTimeAll(long timestamp) {
        if (mDateTimeFormat == null) {
            mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        }
        if (timestamp == 0) {
            return "-";
        }
        return mDateTimeFormat.format(new Date(timestamp));
    }

    public static String formatYDate(long timestamp) {
        if (mYDateFormat == null) {
            mYDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
        if (timestamp == 0) {
            return "-";
        }
        return mYDateFormat.format(new Date(timestamp));
    }

    public static String formatDate(String format, long timestamp) {
        if (timestamp == 0) {
            return "-";
        }
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(timestamp));
    }


    /**
     * 得到当前系统日期,格式:yyyy-mm-dd
     *
     * @return
     */
    public static String getCurrentDate() {

        return getFormatDate("yyyy-MM-dd");
    }

    /**
     * 返回年份
     *
     * @return
     */
    public static String getCurrentYear() {
        return getFormatDate("yyyy");
    }

    /**
     * 返回月份
     */
    public static String getCurrentMonth() {
        return getFormatDate("MM");
    }

    /**
     * 返回特定格式的日期 格式如下: yyyy-mm-dd
     *
     * @param formatString
     * @return
     */
    public static String getFormatDate(String formatString) {
        String currentDate = "";
        SimpleDateFormat format1 = new SimpleDateFormat(formatString);
        currentDate = format1.format(new Date());
        return currentDate;
    }

    /**
     * 返回时间格式样式
     *
     * @return
     */
    public static String getFormateDateAllDate() {
        return getFormatDate("MM-dd HH:mm");
    }

    /**
     * 返回时间格式样式
     *
     * @return
     */
    public static String getFormatDateAll() {
        return getFormatDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatDateTime() {
        return getFormatDate("yyyy-MM-dd HH:mm");
    }

    /**
     * 获取简化时间格式
     *
     * @return
     */
    public static String getFormateDateSimple() {
        return getFormatDate("yyyyMMddHHmmss");
    }

    /**
     * 获取几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfterDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 返回几个小时后的时间
     *
     * @param d
     * @param hour
     * @return
     */
    public static Date getDateAfterHour(Date d, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
        return now.getTime();
    }

    /**
     * 返回几分钟后的某个时间
     *
     * @param d
     * @param minutes
     * @return
     */
    public static Date getDateAfterMinute(Date d, int minutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minutes);
        return now.getTime();
    }

    /**
     * 比较两个日期的大小 true date1比date2前 false date1比date2后
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean dateCompare(Date date1, Date date2) {
        boolean dateComPareFlag = true;
        if (date2.compareTo(date1) != 1) {
            dateComPareFlag = false;
        }
        return dateComPareFlag;
    }


    public static String getVocherDate(String time) throws ParseException {
        // 设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 得到指定模范的时间
        Date d2 = sdf.parse(time);
        return df.format(d2);
    }

    /**
     * 返回两时间之差
     *
     * @param startTime 时间1
     * @param endTime   时间2
     * @return 时间1-时间2的时间
     */
    public static long dateMinus(Date startTime, Date endTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 获取需求 支付剩余时间
     *
     * @param time 订单创建时间
     * @return 时间ms
     */
    public static long demandDownTime(String time, String nowTime, int minute) {
        try {
            Date createTime = TemporaryDate.parse(time);
            Date overdueTime = getDateAfterMinute(createTime, minute);
            Date nowData = TemporaryDate.parse(nowTime);
            return dateMinus(nowData, overdueTime) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 30 * 60;
    }

    public static long converter(String time) {
        try {
            if (TextUtil.isEmpty(time)) {
                return 0;
            }
            Date createTime = TemporaryDate.parse(time);
            return createTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * long 格式化时间
     *
     * @param l long
     * @return 时间
     */
    public static String fromChatLongTime(long l) {
        return TemporaryDate.format(l);
    }

    public static String getAddTemporayTime(String time) {
        if (time == null || time.equals(""))
            return "";
        try {
            return from_temporaryDate.format(TemporaryDate.parse(time));
        } catch (ParseException e) {
            return time;
        }
    }


    public static Long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }


    /**
     * 准备第一个模板，从字符串中提取出日期数字
     */
    private static String pat1 = "yyyy/MM/dd HH:mm:ss";
    /**
     * 准备第二个模板，将提取后的日期数字变为指定的格式
     */
    private static String pat2 = "yyyy/MM/dd HH:mm:ss";
    /**
     * 实例化模板对象
     */
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);

    public static String getProjectDeadTime(String time, int deadLineTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date(String.valueOf(dateFormat.parse(time)));
            Date deadLineDate = getDateAfterDay(date, -deadLineTime);
            return dateFormat.format(deadLineDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return deadLineTime + "";
    }

    public static String getEventTime(String day) {
        try {
            if (day != null) {
                if (day.contains("-")) {
                    String[] date = day.split("-");
                    int y = Integer.valueOf(date[0]);
                    int m = Integer.valueOf(date[1]);
                    int d = Integer.valueOf(date[2]);
                    return y + "-" + m + "-" + d;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return day;
        }
        return day;
    }


    /**
     * 转换违规情况时间
     *
     * @param time 时间格式yyyy/MM/dd HH:mm
     * @return 返回时间格式只需要展示年月日
     */
    public static String formatWrongTime(String time) {
        // 设定时间的模板
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 得到指定模范的时间
        Date d2 = null;
        try {
            d2 = oldTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        return newTimeFormat.format(d2);

    }

    public static String formatWrongTime2(String time) {
        // 设定时间的模板
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 得到指定模范的时间
        Date d2 = null;
        try {
            d2 = oldTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        return newTimeFormat.format(d2);

    }

    public static String formatEventSelectTime(String time) {
        // 设定时间的模板
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 得到指定模范的时间
        Date d2 = null;
        try {
            d2 = oldTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        return newTimeFormat.format(d2);
    }

    public static String formatEventTime(String time) {
        // 设定时间的模板
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 得到指定模范的时间
        Date d2 = null;
        try {
            d2 = oldTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        return newTimeFormat.format(d2);
    }

    public static long formatSignTime(String time) {
        if (TextUtil.isEmpty(time)) {
            return 0L;
        }
        // 设定时间的模板
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = oldTimeFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}