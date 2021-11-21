package cn.enncy.mybatis.core.result;


import cn.enncy.mybatis.core.ReflectUtils;
import cn.enncy.mybatis.core.SqlStringHandler;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 15:30 2021/11/6
 *
 * @author enncy
 */
public class ObjectResultHandler implements ResultSetHandler {

    ResultSet resultSet;
    Class<?> resultType;

    public ObjectResultHandler(ResultSet resultSet, Class<?> resultType) {
        this.resultSet = resultSet;
        this.resultType = resultType;
    }

    @Override
    public Object handle() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (resultSet.next()) {
            return ObjectResultHandler.createResultTarget(resultSet, resultType);
        }
        return null;
    }


    /**
     * 处理查询的返回值，并根据返回类型，创建新的目标返回值对象
     *
     * @param resultSet  查询返回集
     * @param resultType 函数的返回值类型
     * @return: java.lang.Object
     */
    public static Object createResultTarget(ResultSet resultSet, Class<?> resultType) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Object target = resultType.getConstructor().newInstance();
        Field[] fields = ReflectUtils.getObjectFields(target.getClass());
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(target, resultSet.getObject(SqlStringHandler.humpToUnderline(field.getName()), field.getType()));

        }

        return target;
    }

}