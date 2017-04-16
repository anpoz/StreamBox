package io.playcode.streambox.util;

/**
 * Created by anpoz on 2017/4/16.
 */

public class FormatUtil {
    public static String formatPersonNum(String src) {
        String personNum;
        if (src.length() <= 4) {
            personNum = src;
        } else if (src.length() == 5) {
            if (src.charAt(1) == '0') {
                personNum = src.charAt(0) + "万";
            } else {
                personNum = src.charAt(0) + "." + src.charAt(1) + "万";
            }
        } else {
            personNum = src.substring(0, src.length() - 4) + "万";
        }
        return personNum;
    }
}
