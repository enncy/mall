package cn.enncy.mybatis.core;


import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * //TODO
 * <br/>Created in 15:30 2021/11/6
 *
 * @author enncy
 */
public class ResultSetHandler {

    /**
     * 处理查询的返回值，并根据返回类型，创建新的目标返回值对象
     * @param resultSet 查询返回集
     * @param resultType    函数的返回值类型
     * @return: java.lang.Object
     */
    public static  Object createResultTarget(ResultSet resultSet,Class<?>  resultType) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Object target = resultType.getConstructor().newInstance();
        Field[] fields = ReflectUtils.getObjectFields(target.getClass());
        for (Field field : fields) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            field.set(target,resultSet.getObject(SqlStringHandler.humpToUnderline(field.getName()), field.getType()));
            

        }

        return target;
    }

    public static   <T> T stringToTarget(String string, Class<T> t) throws Exception {
        Constructor<?> constructor = t.getConstructor(String.class);
        return (T) constructor.newInstance(string);
        //if(double.class.equals(t)){
        //    return Double.parseDouble(string);
        //}
        //if(long.class.equals(t)){
        //    return Long.parseLong(string);
        //}else if(int.class.equals(t)){
        //
        //    return Integer.parseInt(string);
        //}
        //else if(float.class.equals(t)){
        //    return Float.parseFloat(string);
        //}
        //else if(short.class.equals(t)){
        //    return Short.parseShort(string);
        //}
        //else if(boolean.class.equals(t)){
        //    return Boolean.parseBoolean(string);
        //}else{
        //    return string;
        //}
    }

}