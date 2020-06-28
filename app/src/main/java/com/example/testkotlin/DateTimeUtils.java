package com.example.testkotlin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String formatTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(new Date(time));
    }

    public static String getWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week){
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }
        return "";
    }

}
