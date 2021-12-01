package cn.enncy.mall.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //TODO
 * <br/>Created in 23:14 2021/12/1
 *
 * @author enncy
 */
public class StringUtils {


    public static boolean isWord(String str) {
        String regEx = "\\w+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();

    }

    public static boolean notEmpty(String str){
        return str != null && str.length()!=0;
    }

    public static boolean isEmpty(String str){
        return str == null || str.length()==0;
    }
}
