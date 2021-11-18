package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * //TODO
 * <br/>Created in 21:43 2021/11/18
 *
 * @author enncy
 */
public class BaseObjectUtils {


    public static <T extends BaseObject> Map<String, Object> getValuesMap(T obj) {
        return BaseObjectUtils.getInfoFields(obj.getClass()).collect(Collectors.toMap(Field::getName, field -> {
            try {
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                return field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }));

    }

    public static <T extends BaseObject> Map<String, String> getInfosMap(Class<T> obj) {
        return BaseObjectUtils.getInfoFields(obj).collect(Collectors.toMap(Field::getName, field -> field.getAnnotation(Info.class).value()));
    }

    public static <T extends BaseObject> Stream<Field> getInfoFields(Class<T> obj) {
        return Arrays.stream(obj.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Info.class));

    }
}
