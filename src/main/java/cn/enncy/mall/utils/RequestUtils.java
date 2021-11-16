package cn.enncy.mall.utils;


import cn.enncy.mybatis.core.ResultSetHandler;
import cn.enncy.mybatis.core.SqlStringHandler;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 11:35 2021/11/16
 *
 * @author: enncy
 */
public class RequestUtils {

    /**
     *  转换 request 成为目标对象
     *
     * @param request 请求
     * @param target 目标类
     * @return T
     */
    public static <T> T parseObject(HttpServletRequest request, Class<T> target) throws Exception {

        return (T) RequestUtils.replaceObject(request, target.getConstructor().newInstance());
    }

    /**
     *  根据 request 替换对象的值
     *
     * @param request 请求
     * @param target 对象
     * @return java.lang.Object
     */
    public static Object replaceObject(HttpServletRequest request, Object target) throws Exception {
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object value = ResultSetHandler.stringToTarget(request.getParameter(field.getName()), field.getType());
            field.set(target,value);
        }
        return target;
    }


}