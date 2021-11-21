package cn.enncy.spring.mvc.core;


import cn.enncy.mall.service.BaseService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 16:28 2021/11/20
 *
 * @author enncy
 */
public class ControllerFactory {
    private static final Map<Class<?>, Object> controllers = new HashMap<>();


    public static <T> T resolve(Class<T> type, HttpServletRequest req,HttpServletResponse resp ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object controller = controllers.get(type);
        if (controller == null) {
            controller = type.getConstructor().newInstance();
            controllers.put(type, controller);
        }

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }

            if(ServletRequest.class.isAssignableFrom(field.getType())){

                field.set(controller,req);
            }
            if(ServletResponse.class.isAssignableFrom(field.getType())){
                field.set(controller,resp);
            }
            if(HttpSession.class.isAssignableFrom(field.getType())){
                field.set(controller,req.getSession());
            }
        }

        return (T) controller;
    }

}
