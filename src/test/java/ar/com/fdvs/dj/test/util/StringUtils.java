package ar.com.fdvs.dj.test.util;

public class StringUtils {
    public static int compare(String str1, String str2) {
        boolean nullIsLess = true;

        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : - 1;
        }
        return str1.compareTo(str2);
    }
}
