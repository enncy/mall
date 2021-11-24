package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;

import java.lang.reflect.Field;
import java.util.*;
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
        List<Field> allFields = getAllFields(obj.getClass());
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        for (Field field : allFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                linkedHashMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                linkedHashMap.put(field.getName(), "无");
            }
        }
        return linkedHashMap;

    }

    public static <T extends BaseObject> Map<String, Info> getInfosMap(Class<T> obj) {
        return getAllFields(obj).stream().collect(Collectors.toMap(Field::getName, field -> field.getAnnotation(Info.class)));
    }

    public static <T extends BaseObject> Stream<Field> getInfoFields(Class<T> obj) {
        return Stream.of(obj.getDeclaredFields(), obj.getSuperclass().getDeclaredFields()).flatMap(Arrays::stream).filter(field -> field.isAnnotationPresent(Info.class));
    }

    public static List<Field> getAllFields(Class<? extends BaseObject> obj) {
        return getInfoFields(obj)
                .sorted(Comparator.comparing(c -> ((Field) c).getAnnotation(Info.class).rank()).reversed())
                .collect(Collectors.toList());
    }
}
