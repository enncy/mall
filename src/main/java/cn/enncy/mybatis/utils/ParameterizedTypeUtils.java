package cn.enncy.mybatis.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 * <br/>Created in 11:44 2021/11/19
 *
 * @author enncy
 */
public class ParameterizedTypeUtils {


    /**
     * 根据索引获取泛型实例类
     *
     * @param type  类型
     * @param index
     * @return java.lang.Class<?>
     */
    public static Type get(Type type, int index) {
        ParameterizedType aClass = (ParameterizedType) type;
        Type[] actualTypes = aClass.getActualTypeArguments();
        return actualTypes[index];
    }
}