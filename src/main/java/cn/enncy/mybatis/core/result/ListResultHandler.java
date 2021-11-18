package cn.enncy.mybatis.core.result;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 23:23 2021/11/18
 *
 * @author enncy
 */
public class ListResultHandler  implements ResultSetHandler{

    @Override
    public Object handle(ResultSet resultSet, Map<String, Class<?>> resultMap, Class<?>  resultType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 目标转换类型根据 resultType 字段 sql 注解的优先级最高，其次 到mapper 的注解
        List<Object> list = new ArrayList<>();
        while (resultSet.next()) {
            Object resultTarget = ObjectResultHandler.createResultTarget(resultSet, resultType);
            list.add(resultTarget);
        }
        return list;

    }
}
