package cn.enncy.mall.utils;


import cn.enncy.mall.mapper.BaseMapper;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mall.service.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * //TODO
 * <br/>Created in 17:21 2021/11/18
 *
 * @author enncy
 */
public class ServiceFactory {

    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T extends BaseService<?,?>> T resolve(Class<T> type) {
        Object service = services.get(type);
        if (service == null) {
            try {
                service = type.getConstructor().newInstance();
                services.put(type, service);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return (T) service;
    }

}
