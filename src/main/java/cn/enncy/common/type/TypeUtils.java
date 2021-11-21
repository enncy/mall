package cn.enncy.common.type;


import com.mysql.cj.util.StringUtils;

import java.lang.reflect.Constructor;

/**
 * //TODO
 * <br/>Created in 16:38 2021/11/20
 *
 * @author enncy
 */
public class TypeUtils {

    public static  Object stringToTarget(String string, Class<?> t) throws Exception {
        boolean nullOrEmpty = StringUtils.isNullOrEmpty(string);
        if(String.class.equals(t)){
            return  string;
        }

        if (double.class.equals(t)) {
            return nullOrEmpty?0: Double.parseDouble(string);
        } else if (long.class.equals(t)) {
            return nullOrEmpty?0: Long.parseLong(string);
        } else if (int.class.equals(t)) {
            return nullOrEmpty?0: Integer.parseInt(string);
        } else if (float.class.equals(t)) {
            return nullOrEmpty?0: Float.parseFloat(string);
        } else if (short.class.equals(t)) {
            return nullOrEmpty?0: Short.parseShort(string);
        } else if (boolean.class.equals(t)) {
            return nullOrEmpty?0: Boolean.parseBoolean(string);
        }
        else {
            return string;
        }

    }

}
