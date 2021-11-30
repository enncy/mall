package cn.enncy.mybatis.core;


import cn.enncy.mall.constant.ServiceMapping;
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

    public static <T extends BaseService<?>> T resolve(Class<T> type) {

        for (ServiceMapping value : ServiceMapping.values()) {
            if(value.serviceClass.equals(type)){
                type = (Class<T>) value.serviceImplClass;
            }
        }

        Object proxy = services.get(type);
        if (proxy == null) {
            try {
                Object service = type.getConstructor().newInstance();
                proxy = SqlSession.getService(service);
                services.put(type, proxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) proxy;
    }

}
