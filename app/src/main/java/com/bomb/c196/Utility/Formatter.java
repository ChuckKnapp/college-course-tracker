package com.bomb.c196.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MM d");
        return simpleDateFormat.format(date);
    }

    public static Date formatString(String string) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = formatter.parse(string);
        return date;
    }
}
