package cn.enncy.mybatis.core.result;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 0:13 2021/11/19
 *
 * @author enncy
 */
public class SingleResultHandler implements ResultSetHandler{


    @Override
    public Object handle(ResultSet resultSet, Map<String, Class<?>> resultMap, Class<?> resultType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (Map.Entry<String, Class<?>> entry : resultMap.entrySet()) {
            if(resultSet.next()){
                return resultSet.getObject(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }
}
