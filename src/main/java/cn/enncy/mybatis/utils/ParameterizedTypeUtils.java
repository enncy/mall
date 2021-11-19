package cn.enncy.mybatis.utils;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mybatis.core.SqlSession;

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
     *  根据索引获取泛型实例类
     *
     * @param clazz
     * @param index
     * @return java.lang.Class<?>
     */
    public static Class<?> get(Class<?> clazz,int index){
        ParameterizedType aClass = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] actualTypes = aClass.getActualTypeArguments();
        return ((Class<?>) actualTypes[index]);
    }

    /**
     *  根据类型获取泛型实例类
     *
     * @param clazz 类
     * @param type 目标父类
     * @return java.lang.Class<?>
     */
    public static Class<?> getByType(Class<?> clazz,Class<?> type){
        ParameterizedType aClass = (ParameterizedType)clazz.getGenericSuperclass();
        Type[] actualTypes = aClass.getActualTypeArguments();
        for (Type actualType : actualTypes) {
            Class<?> actual = ((Class<?>) actualType);
            if (type.isAssignableFrom(actual)) {
                return actual;
            }
        }
        return null;
    }
}
