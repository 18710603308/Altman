package com.mzy.sax_demo;

import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Jimmy Shan
 * @date 2021-04-09
 * @desc 格式EXCEL2007单元格工具 日期处理
 */
public class XSSFDateUtil extends DateUtil {

	public static Date absoluteDay(String data, boolean use1904windowing) {
        String[] split = data.split("/");
        Calendar instance = Calendar.getInstance();
        instance.set(Integer.parseInt("20" + split[2]),Integer.parseInt(split[1]) - 1,Integer.parseInt(split[0]),
                     0,0,0);

        /*System.out.println(time);
        Date day = getStartOfDay(time);
        System.out.println(day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(day));*/
        return instance.getTime();
    }
    public static Date getStartOfDay(Date date) {
        if(date == null){
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

    }



    public static void main(String[] args) {
        absoluteDay("1/13/30",false);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(getStartOfDay(absoluteDay("1/13/30",false))));
    }
}
