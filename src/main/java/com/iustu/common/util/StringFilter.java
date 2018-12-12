package com.iustu.common.util;

public class StringFilter {
    public static String format(String s) {
        if (s != null && s.length() > 0) {
            s = s.replaceAll("(\r\n|\n\r|\n)", "");
            s = s.replaceAll("\\\\", "\\\\\\\\");
            s = s.replaceAll("\"", "\\\\" + "\"");
            s = s.replaceAll("\'", "\\\\" + "\'");
            return s;
        } else {
            return "";
        }
    }
}
