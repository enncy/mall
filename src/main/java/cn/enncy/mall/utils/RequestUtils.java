package cn.enncy.mall.utils;


import cn.enncy.common.type.TypeUtils;
import cn.enncy.mall.pojo.BaseObject;
import cn.enncy.mall.pojo.BaseObjectUtils;
import cn.enncy.mybatis.core.result.ObjectResultHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 11:35 2021/11/16
 *
 *  enncy
 */
public class RequestUtils {

    /**
     *  转换 request 成为目标对象
     *
     * @param request 请求
     * @param target 目标类
     * @return T
     */
    public static <T extends BaseObject> T parseObject(HttpServletRequest request, Class<?> target) throws Exception {
        return (T) RequestUtils.replaceObject(request, target.getConstructor().newInstance());
    }

    /**
     *  根据 request 替换对象的值
     *
     * @param request 请求
     * @param target 对象
     * @return java.lang.Object
     */
    public  static Object replaceObject(HttpServletRequest request, Object target) throws Exception {
        List<Field> allFields = BaseObjectUtils.getAllFields(((Class<? extends BaseObject>) target.getClass()));

        for (Field field : allFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            String parameter = request.getParameter(field.getName());
            if(parameter!=null){

                Object value = TypeUtils.stringToTarget(parameter, field.getType());
                field.set(target,value);
            }
        }
        return target;
    }


}
