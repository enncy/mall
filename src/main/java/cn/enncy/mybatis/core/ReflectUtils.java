package cn.enncy.mybatis.core;


import java.lang.reflect.Field;
import java.util.*;

/**
 * //TODO
 * <br/>Created in 16:50 2021/11/6
 *
 * @author enncy
 */
public class ReflectUtils {

    /**
     * 获取对象中的 key - value 键值对的集合
     *
     * @param object 对象
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getObjectValueMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        Field[] field = getObjectFields(object.getClass());

        for (Field f : field) {
            try {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                if(f.getType().equals(boolean.class)){
                    map.put(f.getName(), (Boolean.parseBoolean(f.get(object).toString()) ? "1" : "0"));
                }else{
                    map.put(f.getName(), f.get(object));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Field[] getObjectFields(Class... clazz) {
        List<Field> list = new ArrayList<>();
        for (Class aClass : clazz) {
            list.addAll(Arrays.asList(getObjectFields(aClass)));
        }
        return list.toArray(new Field[0]);
    }

    /**
     * 获取基本类型对象的属性
     *
     * @param clazz          基本对象类
     * @return: java.lang.reflect.Field[]
     */
    public static Field[] getObjectFields(Class<?> clazz) {

        LinkedList<Field> list = new LinkedList<>(Arrays.asList(clazz.getDeclaredFields()));

        list.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        //id在前显示
        Field id = null;
        for (Field field : list) {
            if ("id".equals(field.getName())) {
                id = field;
                break;
            }
        }
        if(id!=null){
            list.remove(id);
            list.addFirst(id);
        }

        return list.toArray(new Field[]{});
    }


    public static Field getObjectField(Class clazz, String fieldName) {
        List<Field> list = new LinkedList<>(Arrays.asList(clazz.getDeclaredFields()));
        list.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        for (Field field : list) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取多个对象中的 key - value 键值对的集合
     *
     * @param objects 对象的数组，将全部属性一并加入
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getObjectsValueMap(Object... objects) {
        Map<String, Object> map = new HashMap<>();
        for (Object object : objects) {
            map.putAll(getObjectValueMap(object));
        }
        return map;
    }



    /**
     * 将数组的值赋值给对象
     *
     * @param values 数组
     * @param target 目标对象
     * @return: java.lang.Object[]
     */
    public static Object valueArrayToObject(Object[] values, Object target) throws Exception {
        Field[] declaredFields = ReflectUtils.getObjectFields(target.getClass());

        if (values.length != declaredFields.length) {
            throw new Exception("values length is not match target object fields length");
        }
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];

            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            declaredField.set(target, values[i]);
        }
        return target;
    }


}