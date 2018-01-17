package com.mkalita.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static final String datePattern = "dd/MM/yyyy";
    private static final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat(datePattern));

    public static String dateToStr(Date date) {
        return df.get().format(date);
    }

    public static Date dateFromStr(String strDate) {
        try {
            return df.get().parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("Couldn't parse date: %s", strDate));
        }
    }

}
