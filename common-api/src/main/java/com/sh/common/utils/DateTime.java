package com.sh.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * 时间工具类
 *
 * @author lxy 953250192@qq.com
 * @version 2019/4/11 9:56
 */
@Slf4j
public class DateTime {

    private static final String YMD = "yyyyMMdd";
    private static final String YMDHMS = "yyyyMMddHHmmss";
    private static final String Y_M_D = "yyyy-MM-dd";
    private static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static String getStringYMD(Date date) {
        return getBaseString(date, YMD);
    }

    public static String getStringYMDHMS(Date date) {
        return getBaseString(date, YMDHMS);
    }

    public static String getStringY_M_D(Date date) {
        return getBaseString(date, Y_M_D);
    }

    public static String getStringY_M_D_H_M_S(Date date) {
        return getBaseString(date, Y_M_D_H_M_S);
    }

    public static Date getDateYMD(String date) {
        return getBaseDate(date, YMD);
    }

    public static Date getDateYMDHMS(String date) {
        return getBaseDate(date, YMDHMS);
    }

    public static Date getDateY_M_D(String date) {
        return getBaseDate(date, Y_M_D);
    }

    public static Date getDateY_M_D_H_M_S(String date) {
        return getBaseDate(date, Y_M_D_H_M_S);
    }

    /**
     * 获取Date类型的基础方法
     *
     * @param date str类型的时间戳
     * @param reg  格式
     * @return date时间
     */
    private static Date getBaseDate(String date, String reg) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Y_M_D_H_M_S);
            return format.parse(date);
        } catch (ParseException e) {
            //异常最好针对的范围小一点
            log.error("dateError==={}", e.getMessage());
            return new Date();
        }
    }

    /**
     * 获取String类型的基础方法
     *
     * @param date date类型的时间戳
     * @param reg  格式
     * @return String字符
     */
    private static String getBaseString(Date date, String reg) {
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat(Y_M_D_H_M_S);
            return format.format(date);
        } else {
            return "";
        }
    }
}
