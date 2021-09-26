package com.tatabuku.app.util;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StringHelper {

    public static final int QTY_LIMIT = 1000000;

    public static String convertStringArrayToString(String[] arr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String obj : arr)
            sb.append(obj).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    public static String npwpFormat(String str) {
        if (str == null) {
            return "";
        }
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (i == 2 || i == 5 || i == 8 || i == 11) {
                result += ".";
            } else if (i == 9) {
                result += "-";
            }
            result += str.charAt(i);
        }
        return result;
    }

    public static String numberFormat(Double number) {
        if (number == null) {
            return "Rp 0";
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###,###", otherSymbols);
        String yourFormattedString = formatter.format(number);
        return "Rp " + yourFormattedString;
    }

    public static String numberFormatWithoutCurrency(Double number) {
        if (number == null) {
            return "Rp 0";
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###,###", otherSymbols);
        String yourFormattedString = formatter.format(number);
        return yourFormattedString;
    }

    public static String numberFormatWithDecimal(Double number) {
        if (number == null) {
            return "Rp 0";
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###,##0.00", otherSymbols);
        String yourFormattedString = formatter.format(number);
        return "Rp " + yourFormattedString;
    }

    public static String getTodayDate(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String result = df.format(new Date());
        return result;
    }

    public static String getTodayDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String result = df.format(new Date());
        return result;
    }

    public static String formatOrderDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatReceivedDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatInvoiceDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatRekeningDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDatePicker(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatBackendDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("dd MMMM yyyy");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);
        return result;
    }

    public static Date getDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String formatDueDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = fromFormat.parse(from);
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String result = df.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatJurnalDate(String from) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("dd MMMM yyyy");
        try {
            Date inputDate = fromFormat.parse(from);
            Date todayDate = new Date();
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(inputDate);
            int date = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            calendar.setTime(todayDate);
            calendar.set(year, month, date);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String result = df.format(calendar.getTime());
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
