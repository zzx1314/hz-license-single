package org.huazhi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    // 默认时间格式
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前系统时间
     */
    public static Date date() {
        return new Date();
    }

    /**
     * 解析日期字符串为 Date 对象
     * 支持：yyyy-MM-dd、yyyy-MM-dd HH:mm:ss
     */
    public static Date parse(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        // 判断格式
        String pattern = dateStr.contains(":") ? DEFAULT_PATTERN : "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误：" + dateStr, e);
        }
    }

    /**
     * 比较两个日期大小
     * 返回值：
     *   >0 : date1 晚于 date2
     *   =0 : 相等
     *   <0 : date1 早于 date2
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
        return date1.compareTo(date2);
    }

    /**
     * 将 Date 转为字符串（格式化输出）
     */
    public static String format(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
        return sdf.format(date);
    }

    /**
     * 获取当前时间的字符串表示
     */
    public static String now() {
        return format(date());
    }
}

