package com.kanban.model.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DATE_PATTERN = "dd/MM/yyyy";

    public static String dateConverter() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
