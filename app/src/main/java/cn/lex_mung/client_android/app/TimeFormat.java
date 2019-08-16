package cn.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jiguang.api.JCoreInterface;

public class TimeFormat {
    public static final String s1 = "yyyy-MM-dd HH:mm:ss";

    @SuppressLint("SimpleDateFormat")
    public static String getTime(String timeStamp) {
        if (TextUtils.isEmpty(timeStamp)) return "";
        try {
            return getTime(new SimpleDateFormat(s1).parse(timeStamp).getTime());
        } catch (ParseException ignored) {
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime(String timeStamp, String formatType) {
        if (TextUtils.isEmpty(timeStamp)) return "";
        try {
            return getTime(new SimpleDateFormat(formatType).parse(timeStamp).getTime());
        } catch (ParseException ignored) {
        }
        return "";
    }

    public static String longToStr(long dateLong) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(dateLong);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String longToStr(long dateLong, String formatType) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
        long lt = new Long(dateLong);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将秒数转为时分秒
     * */
    public static String countDownToStr(Long timelong, int type) {
        /*
        type
        0 *天*时*分*秒
        1 *天*时*分
        2 *分*秒
         */
        timelong = timelong / 1000;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = timelong.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }

        if (hour > 60) {
            day = hour / 24;
            hour = hour % 24;
        }
        String secondFormat = second < 10 ? "0" + String.valueOf(second) : "" + second;
        String minuteFormat = minute < 10 ? "0" + String.valueOf(minute) : "" + minute;
        String hourFormat = hour < 10 ? "0" + String.valueOf(hour) : "" + hour;
        String dayFormat = hour < 10 ? "0" + String.valueOf(day) : "" + day;

        switch (type) {
            case 0:
                return dayFormat + "天" + hourFormat + "时" + minuteFormat + "分" + secondFormat + "秒";
            case 1:
                return dayFormat + "天" + hourFormat + "时" + minuteFormat + "分";
            case 2:
                return minuteFormat + "分" + secondFormat + "秒";
        }
        return dayFormat + "天 " + hourFormat + "时" + minuteFormat + "分" + secondFormat + "秒";
    }

    public static String getHMSStr(int timelong) {
        if (timelong == 0) return "";
        long hours = timelong / 3600;//转换小时数
        timelong = timelong % 3600;//剩余秒数
        long minutes = timelong / 60;//转换分钟
        timelong = timelong % 60;//剩余秒数
        if (hours > 0) {
            return hours + "小时" + minutes + "分" + timelong + "秒";
        }
        if (minutes > 0) {
            return minutes + "分" + timelong + "秒";
        }
        return timelong + "秒";
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long strToLong(String strTime, String formatType) {
        Date date = str2Date(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    //String 转 date
    public static Date str2Date(String dateStr) {
        return str2Date(dateStr, s1);
    }

    //String 转 date
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
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
                    if (!TextUtils.isEmpty(newMinute) && !TextUtils.isEmpty(oldMinute) &&
                            newMinute.length() > 0 && oldMinute.length() > 0 &&
                            Integer.valueOf(newHour + newMinute) - Integer.valueOf(oldHour + oldMinute) <= 5) {
                        return "刚刚";
                    }
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

    public static String getCurrentTime() {
        //时间转字符串
        //截取当前系统时间
        Date currentTime = new Date();
        //改变输出格式（自己想要的格式）
        SimpleDateFormat formatter = new SimpleDateFormat(s1);
        //得到字符串时间
        return formatter.format(currentTime);
    }
}
