package com.dzm.jar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 邓治民
 *         data 2018/6/27 下午5:27
 */

public class SimpleTimeUtils {

    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd hh:mm");


    public static String simpleYYYY$$$$SS(Date date){
        return YYYY_MM_DD_HH_MM_SS.format(date);
    }

    public static String simpleYYYY$$$$SS(long date){
        return YYYY_MM_DD_HH_MM_SS.format(new Date(date));
    }

    public static String simpleYYYY$$$MM(Date date){
        return YYYY_MM_DD_HH_MM.format(date);
    }

    public static String simpleYYYY$$$MM(long date){
        return YYYY_MM_DD_HH_MM.format(new Date(date));
    }

}
