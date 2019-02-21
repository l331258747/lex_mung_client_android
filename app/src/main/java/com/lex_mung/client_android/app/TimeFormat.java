package com.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.jiguang.api.JCoreInterface;

public class TimeFormat {
    private static String s1 = "yyyy-MM-dd HH:mm:ss";

    @SuppressLint("SimpleDateFormat")
    public static String getTime(String timeStamp) {
        if (TextUtils.isEmpty(timeStamp)) return "";
        try {
            return getTime(new SimpleDateFormat(s1).parse(timeStamp).getTime());
        } catch (ParseException ignored) {
        }
        return "";
    }

    /**
     * 会话内时间显示规则：
     * 当天消息只显示具体时间, 举例子：18:09
     * 昨天和前天，举例: 昨天 18:09
     * 近7天（排除今天，昨天，前天）举例：周日 18:09
     * 今年其他时间，举例：4-22 18:09
     * 今年之前的时间，举例：2015-4-22 18:09
     * 时间显示的间隔：当两次发送或收取消息间隔大于5分钟，则显示新的时间
     */
    public static String getTime(long mTimeStamp) {
        //最后一条消息的 年 月 日 时 分
        //yyyy-MM-dd HH:mm:ss
        Date date = new Date(mTimeStamp);
        String dateStr = format(date, s1);
        String oldYear = dateStr.substring(0, 4);
        int oldMonth = Integer.parseInt(dateStr.substring(5, 7));
        int oldDay = Integer.parseInt(dateStr.substring(8, 10));
        String oldHour = dateStr.substring(11, 13);
        String oldMinute = dateStr.substring(14, 16);

        //当前时间
        long today = JCoreInterface.getReportTime();//当前时间
        Date now = new Date(today * 1000);//当前时间
        String nowStr = format(now, s1);

        String newYear = nowStr.substring(0, 4);
        int newMonth = Integer.parseInt(nowStr.substring(5, 7));
        int newDay = Integer.parseInt(nowStr.substring(8, 10));//当前 日
        String newHour = nowStr.substring(11, 13);
        String newMinute = nowStr.substring(14, 16);
        String result = "";
        long l = today * 1000 - mTimeStamp;
        long days = l / (24 * 60 * 60 * 1000);
        long hours = (l / (60 * 60 * 1000) - days * 24);
        long min = ((l / (60 * 1000)) - days * 24 * 60 - hours * 60);
        long s = (l / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - min * 60);

        if (!oldYear.equals(newYear)) {
            //往年
            result = oldYear + "-" + oldMonth + "-" + oldDay + " " + oldHour + ":" + oldMinute;
        } else {
            //今年
            //同月
            if (oldMonth == newMonth) {
                //同天
                if (oldDay == newDay) {
                    result = oldHour + ":" + oldMinute;
                } else {
                    //不同天
                    int day = newDay - oldDay;
                    if (day == 1) {
                        result = "昨天 " + oldHour + ":" + oldMinute;
                    } else if (day == 2) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else if (day > 2 && day < 8) {
                        int week = date.getDay();
                        if (week == 1) {
                            result = "周一 " + oldHour + ":" + oldMinute;
                        } else if (week == 2) {
                            result = "周二 " + oldHour + ":" + oldMinute;
                        } else if (week == 3) {
                            result = "周三 " + oldHour + ":" + oldMinute;
                        } else if (week == 4) {
                            result = "周四 " + oldHour + ":" + oldMinute;
                        } else if (week == 5) {
                            result = "周五 " + oldHour + ":" + oldMinute;
                        } else if (week == 6) {
                            result = "周六 " + oldHour + ":" + oldMinute;
                        } else {
                            result = "周日 " + oldHour + ":" + oldMinute;
                        }
                    } else {
                        result = oldMonth + "-" + oldDay + " " + oldHour + ":" + oldMinute;
                    }
                }
            } else {
                if (oldMonth == 1 || oldMonth == 3 || oldMonth == 5 || oldMonth == 7 || oldMonth == 8 || oldMonth == 10 || oldMonth == 12) {
                    if (newDay == 1 && oldDay == 30) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else if (newDay == 1 && oldDay == 31) {
                        result = "昨天 " + oldHour + ":" + oldMinute;
                    } else if (newDay == 2 && oldDay == 31) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else {
                        result = oldMonth + "-" + oldDay + " " + oldHour + ":" + oldMinute;
                    }
                } else if (oldMonth == 2) {
                    if (newDay == 1 && oldDay == 27 || newDay == 2 && oldDay == 28) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else if (newDay == 1 && oldDay == 28) {
                        result = "昨天 " + oldHour + ":" + oldMinute;
                    } else {
                        result = oldMonth + "-" + oldDay + " " + oldHour + ":" + oldMinute;
                    }
                } else if (oldMonth == 4 || oldMonth == 6 || oldMonth == 9 || oldMonth == 11) {
                    if (newDay == 1 && oldDay == 29) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else if (newDay == 1 && oldDay == 30) {
                        result = "昨天 " + oldHour + ":" + oldMinute;
                    } else if (newDay == 2 && oldDay == 30) {
                        result = "前天 " + oldHour + ":" + oldMinute;
                    } else {
                        result = oldMonth + "-" + oldDay + " " + oldHour + ":" + oldMinute;
                    }
                }
            }
        }
        return result;
    }

    @SuppressLint("SimpleDateFormat")
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
